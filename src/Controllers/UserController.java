package Controllers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Common.GlobalData;
import Models.UsersDB;
public class UserController {
	public static ArrayList<UsersDB> getAllUser() {
		String query = "SELECT * FROM users";
		ArrayList<UsersDB> list = new ArrayList<UsersDB>();
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query);
		    ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String name = rs.getString(4);
				String surname = rs.getString(5);
				String email = rs.getString(6);
				String approval_status = rs.getString(7);
				String privilege = rs.getString(8);
				UsersDB cc = new UsersDB(id, username, password, name, surname, email, approval_status, privilege);
				list.add(cc);
				// print the results
				//				System.out.format("%d, %s, %s, %s, %s, %s, %s, %s, \n", id, username, password, name, surname, email, approval_status, privilege);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return list;
	};
	public static void registerUser(UsersDB user) {
		// id, username, password, name, surname, email, approval_status, privilege
		String query = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, 0);
			pstmt.setString(2, user.username);
			pstmt.setString(3, user.password);
			pstmt.setString(4, user.name);
			pstmt.setString(5, user.surname);
			pstmt.setString(6, user.email);
			pstmt.setString(7, user.approval_status);
			pstmt.setString(8, user.privilege);
			pstmt.executeUpdate();
			GlobalData.CurrentUser_userID = user.id;
			GlobalData.CurrentUser_userName = user.username;
			GlobalData.CurrentUser_userStatus = user.approval_status;
			GlobalData.CurrentUser_userType = user.privilege;
			return;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	};
	public static void approveStatus(int id) {
		String query = "UPDATE users SET approval_status = ? WHERE users.id = ? ";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, "yes");
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	};
	public static void unapproveStatus(int id) {
		String query = "UPDATE users SET approval_status = ? WHERE users.id = ? ";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, "no");
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	};
	public static void deleteUser(int id) {
		String query = "DELETE FROM users WHERE users.id = ? ";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	};
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//		ArrayList<UsersDB> ll = UserController.getAllUser();
		//		System.out.println("Total Users = " + ll.size());
	}
}
