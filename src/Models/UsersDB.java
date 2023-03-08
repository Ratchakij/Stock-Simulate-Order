package Models;
public class UsersDB {
	public int id;
	public String username;
	public String password;
	public String name;
	public String surname;
	public String email;
	public String approval_status;
	public String privilege;
	public UsersDB() {
	}
	public UsersDB(int xid, String xusername, String xpassword, String xname, String xsurname, String xemail, String xapproval_status, String xprivilege) {
		id = xid;
		username = xusername;
		password = xpassword;
		name = xname;
		surname = xsurname;
		email = xemail;
		approval_status = xapproval_status;
		privilege = xprivilege;
	}
}
