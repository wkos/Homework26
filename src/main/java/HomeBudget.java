import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeBudget {
    public static void main(String[] args) throws ParseException {
        String dateString = "2019.01.02";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date date = sdf.parse(dateString);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        Transaction emp = new Transaction(true, "pensja", 3800.00, sqlDate);
        TransactionDao dao = new TransactionDao();
        dao.save(emp);
        dao.close();
    }
}
