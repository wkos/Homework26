import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TransactionDao {
    private static final String URL = "jdbc:mysql://localhost:3306/home_budget?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
    private Connection connection;

    public TransactionDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Transaction transaction) {
        final String sql = "insert into transaction (type , description, amount, date ) values(?, ?, ?, ?)";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, transaction.getType());
            prepStmt.setString(2, transaction.getDescription());
            prepStmt.setDouble(3, transaction.getAmount());
            prepStmt.setDate(4, transaction.getDate());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Transaction read(long id) {
        final String sql = "select id, type , description, amount, date from transaction where id = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setLong(1, id);
            ResultSet result = prepStmt.executeQuery();
            if (result.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(result.getLong("id"));
                transaction.setType(result.getString("type"));
                transaction.setDescription(result.getString("description"));
                transaction.setAmount(result.getDouble("amount"));
                transaction.setDate(result.getDate("date"));
                return transaction;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Transaction> readType(String type) {
        final String sql = "select id, type , description, amount, date from transaction where type = ?";
        List<Transaction> types = new LinkedList<>();
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, type);
            ResultSet result = prepStmt.executeQuery();
            while (result.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(result.getLong("id"));
                transaction.setType(result.getString("type"));
                transaction.setDescription(result.getString("description"));
                transaction.setAmount(result.getDouble("amount"));
                transaction.setDate(result.getDate("date"));
                types.add(transaction);
            }
            return types;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void update(Transaction transaction) {
        final String sql = "update transaction set type =?, description=?, amount=?, date=? where id = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, transaction.getType());
            prepStmt.setString(2, transaction.getDescription());
            prepStmt.setDouble(3, transaction.getAmount());
            prepStmt.setDate(4, transaction.getDate());
            prepStmt.setLong(5, transaction.getId());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not update record");
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        final String sql = "delete from transaction where id = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setLong(1, id);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
