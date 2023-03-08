package Controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import Common.GlobalData;
import Models.TestsDB;
public class TestController {
	public TestController() {
	}
	public static ArrayList<TestsDB> getTest() {
		//	id, date, user_id	
		String query = "SELECT * FROM tests";
		ArrayList<TestsDB> testList = new ArrayList<TestsDB>();
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query);
		    ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt(1);
				LocalDate date = rs.getDate(2).toLocalDate();
				int userId = rs.getInt(3);
				TestsDB test = new TestsDB(id, date, userId);
				testList.add(test);
			} ;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return testList;
	};
	public static void createTest(TestsDB test) {
		String query = "INSERT INTO tests VALUES (?,?,?)";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, 0);
			pstmt.setDate(2, java.sql.Date.valueOf(test.date));
			pstmt.setInt(3, test.user_id);
			pstmt.executeUpdate();
			System.out.println("Create test success.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	public static void deleteTest(int testId) {
		String query = "DELETE FROM tests WHERE tests.id = ?";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, testId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	};
	public static void main(String[] args) {
	}
}
