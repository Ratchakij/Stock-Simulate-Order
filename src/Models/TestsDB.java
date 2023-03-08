package Models;
import java.time.LocalDate;
public class TestsDB {
	public int id;
	public LocalDate date;
	public int user_id;
	public TestsDB() {
	}
	public TestsDB(int xid, LocalDate xdate, int xuser_id) {
		this.id = xid;
		this.date = xdate;
		this.user_id = xuser_id;
	}
}
