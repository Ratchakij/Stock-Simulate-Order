package Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class GlobalData {
	private static final String DATABASE_LOCATION = "127.0.0.1";
	private static final String DATABASE_PORT = "3307";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "";
	private static final String DATABASE_DATABASE_NAME = "ept_set_data";
	public static int CurrentUser_userID;
	public static String CurrentUser_userName;
	public static String CurrentUser_userStatus;
	public static String CurrentUser_userType;

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;
		try {
			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/" + GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			con = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);
			System.out.println("getConnection");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("GlobalData: " + e.getMessage());
		}
		return con;
	};
}
