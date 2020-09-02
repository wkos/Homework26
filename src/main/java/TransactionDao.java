import java.sql.*;

public class TransactionDao {
    private static final String URL = "jdbc:mysql://localhost:3306/home_budget";
    private static final String USER = "root";
    private static final String PASS = "root";
    private Connection connection;

    public TransactionDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("No driver found");
        } catch (SQLException e) {
            System.out.println("Could not establish connection");
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
            prepStmt.setBoolean(1, transaction.isType());
            System.out.println(transaction.isType());
            prepStmt.setString(2, transaction.getDescription());
            System.out.println(transaction.getDescription());
            prepStmt.setDouble(3, transaction.getAmount());
            System.out.println(transaction.getAmount());
            prepStmt.setDate(4, transaction.getDate());
            System.out.println(transaction.getDate());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not save record");
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
                transaction.setDescription(result.getString("description"));
                transaction.setAmount(result.getDouble("amount"));
                transaction.setDate(result.getDate("date"));
                return transaction;
            }
        } catch (SQLException e) {
            System.out.println("Could not get employee");
        }
        return null;
    }

    public void update(Transaction transaction) {
        final String sql = "update employees set firstName=?, lastName=?, salary=? where id = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setBoolean(1, transaction.isType());
            prepStmt.setString(2, transaction.getDescription());
            prepStmt.setDouble(3, transaction.getAmount());
            prepStmt.setDate(4, transaction.getDate());
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
            System.out.println("Could not delete row");
        }
    }
}
