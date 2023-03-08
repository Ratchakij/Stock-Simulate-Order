package Controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import Common.GlobalData;
import Models.TransactionsDB;
public class TransactionController {
	public TransactionController() {
	}
	public static ArrayList<TransactionsDB> getTrans() {
		String query = "SELECT * FROM transactions";
		ArrayList<TransactionsDB> transList = new ArrayList<TransactionsDB>();
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query);
		    ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt(1);
				String position = rs.getString(2);
				int qty = rs.getInt(3);
				int test_id = rs.getInt(4);
				LocalDate date = rs.getDate(5).toLocalDate();
				TransactionsDB trans = new TransactionsDB(id, position, qty, test_id, date);
				transList.add(trans);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return transList;
	};
	public static void createTrans(TransactionsDB trans) {
		//	id, position, qty, tests_id, set_data_id	
		String query = "INSERT INTO transactions VALUES (?,?,?,?,?)";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, 0);
			pstmt.setString(2, trans.position);
			pstmt.setInt(3, trans.qty);
			pstmt.setInt(4, trans.test_id);
			pstmt.setDate(5, java.sql.Date.valueOf(trans.date));
			pstmt.executeUpdate();
			System.out.println("Create trans success.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	public static void updateTrans(int qty, int id) {
		// UPDATE `transactions` SET `position` = 'SELL', `qty` = '4000' WHERE `transactions`.`id` = 138;
		String query = "UPDATE transactions SET qty = ? WHERE transactions.id = ?";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, qty);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
			System.out.println("Update trans success.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
