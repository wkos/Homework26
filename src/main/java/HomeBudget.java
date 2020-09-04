import java.text.ParseException;
import java.sql.Date;
import java.util.List;

public class HomeBudget {
    public static void main(String[] args) throws ParseException {
        TransactionDao dao = new TransactionDao();

        createData(dao);

        Transaction row = dao.read(2L);
        System.out.println(row);

        Date sqlDate = Date.valueOf("2020-10-08");
        Transaction transaction = new Transaction(2L, "income", "umowa zlecenie", 545.00, sqlDate);
        dao.update(transaction);

        dao.delete(4L);

        System.out.println("Przychody");
        List<Transaction> incomes = dao.readType("income");
        System.out.println(incomes);

        System.out.println("Wydatki");
        List<Transaction> outcomes = dao.readType("outcome");
        System.out.println(outcomes);
        dao.close();
    }

    public static void saveRow(TransactionDao dao, String type, String description, Double amount, String date) throws ParseException {
        Date sqlDate = Date.valueOf(date);
        Transaction emp = new Transaction(type, description, amount, sqlDate);
        dao.save(emp);
    }

    public static void createData(TransactionDao dao) throws ParseException {
        saveRow(dao, "income", "wynagrodzenie", 1500.00, "2020-10-08");
        saveRow(dao, "income", "umowa zlecenie", 540.00, "2020-10-08");
        saveRow(dao, "outcome", "masło", 15.00, "2020-10-11");
        saveRow(dao, "outcome", "chleb", 2.50, "2020-10-10");
        saveRow(dao, "outcome", "cukier", 1.00, "2020-10-09");
    }
}
