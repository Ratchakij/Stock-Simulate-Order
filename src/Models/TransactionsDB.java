package Models;
import java.time.LocalDate;
public class TransactionsDB {
	public int id;
	public String position;
	public int qty;
	public int test_id;
	public LocalDate date;
	public TransactionsDB() {
	}
	public TransactionsDB(int xid, String xposition, int xqty, int xtest_id, LocalDate xdate) {
		this.id = xid;
		this.position = xposition;
		this.qty = xqty;
		this.test_id = xtest_id;
		this.date = xdate;
	}

}
