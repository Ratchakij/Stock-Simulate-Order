package Views;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import Common.GlobalData;
import Controllers.UserController;
import Models.UsersDB;
public class RegisterPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public RegisterPanel(JPanel mainPanel, JPanel mainNavBar) {
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 984, 536);
		add(panel);
		panel.setLayout(null);
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setBounds(412, 60, 160, 30);
		panel.add(lblRegister);
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setFont(new Font("Tahoma", Font.BOLD, 24));
		JLabel lblNewLabel = new JLabel("username");
		lblNewLabel.setBounds(413, 101, 122, 14);
		panel.add(lblNewLabel);
		JTextField tf_username = new JTextField();
		tf_username.setBounds(412, 116, 200, 20);
		panel.add(tf_username);
		tf_username.setColumns(10);
		JLabel lblNewLabel_1 = new JLabel("password");
		lblNewLabel_1.setBounds(412, 147, 122, 14);
		panel.add(lblNewLabel_1);
		//
		JTextField tf_password = new JTextField();
		tf_password.setBounds(412, 160, 200, 20);
		panel.add(tf_password);
		tf_password.setColumns(10);
		JTextField tf_confirmPassword = new JTextField();
		tf_confirmPassword.setBounds(412, 204, 200, 20);
		panel.add(tf_confirmPassword);
		tf_confirmPassword.setColumns(10);
		JLabel lblNewLabel_1_1 = new JLabel("confirm password");
		lblNewLabel_1_1.setBounds(413, 191, 122, 14);
		panel.add(lblNewLabel_1_1);
		JLabel lblNewLabel_1_2 = new JLabel("name");
		lblNewLabel_1_2.setBounds(413, 235, 122, 14);
		panel.add(lblNewLabel_1_2);
		JTextField tf_name = new JTextField();
		tf_name.setBounds(413, 251, 200, 20);
		panel.add(tf_name);
		tf_name.setColumns(10);
		JLabel lblNewLabel_1_3 = new JLabel("surname");
		lblNewLabel_1_3.setBounds(413, 279, 122, 14);
		panel.add(lblNewLabel_1_3);
		JTextField tf_surname = new JTextField();
		tf_surname.setBounds(413, 294, 200, 20);
		panel.add(tf_surname);
		tf_surname.setColumns(10);
		JLabel lblNewLabel_1_4 = new JLabel("email");
		lblNewLabel_1_4.setBounds(413, 320, 122, 14);
		panel.add(lblNewLabel_1_4);
		JTextField tf_email = new JTextField();
		tf_email.setBounds(413, 335, 200, 20);
		panel.add(tf_email);
		tf_email.setColumns(10);
		//
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) mainPanel.getLayout();
				cl.show(mainPanel, "Login");
			}
		});
		btnBack.setBounds(413, 414, 200, 23);
		panel.add(btnBack);
		//
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// id, username, password, name, surname, email, approval_status, privilege
					ArrayList<UsersDB> list = new ArrayList<UsersDB>(UserController.getAllUser());
					if (tf_username.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(RegisterPanel.this, "Input your username.");
						return;
					} ;
					if (tf_password.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(RegisterPanel.this, "Input your password.");
						return;
					} ;
					if (tf_name.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(RegisterPanel.this, "Input your name.");
						return;
					} ;
					if (tf_surname.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(RegisterPanel.this, "Input your surname.");
						return;
					} ;
					if (tf_email.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(RegisterPanel.this, "Input your email.");
						return;
					} ;
					if (!tf_password.getText().trim().equals(tf_confirmPassword.getText().trim())) {
						JOptionPane.showMessageDialog(RegisterPanel.this, "Password and confirm password not match.");
						return;
					}
					for (UsersDB users : list) {
						if ((tf_username.getText().trim().equals(users.username))) {
							JOptionPane.showMessageDialog(RegisterPanel.this, "Username has already been taken.");
							return;
						}
					}
					UsersDB user = new UsersDB(0, tf_username.getText().trim(), tf_password.getText().trim(), tf_name.getText().trim(), tf_surname.getText().trim(), tf_email.getText().trim(), "no", "user");
					UserController.registerUser(user);
					JOptionPane.showMessageDialog(RegisterPanel.this, "Register complete.");
					tf_username.setText("");
					tf_password.setText("");
					tf_confirmPassword.setText("");
					tf_name.setText("");
					tf_surname.setText("");
					tf_email.setText("");
					ArrayList<UsersDB> userList = new ArrayList<UsersDB>(UserController.getAllUser());
					GlobalData.CurrentUser_userID = userList.get(userList.size() - 1).id;
					GlobalData.CurrentUser_userName = userList.get(userList.size() - 1).username.toString();
					GlobalData.CurrentUser_userStatus = userList.get(userList.size() - 1).approval_status.toString();
					GlobalData.CurrentUser_userType = userList.get(userList.size() - 1).privilege.toString();
					//
					JPanel navbar = new NavBar(mainPanel, mainNavBar);
					mainNavBar.add(navbar, "Navbar");
					CardLayout cln = (CardLayout) mainNavBar.getLayout();
					cln.show(mainNavBar, "Navbar");

					JPanel home = new HomePanel();
					mainPanel.add(home, "Home");
					CardLayout cl = (CardLayout) mainPanel.getLayout();
					cl.show(mainPanel, "Home");
				} catch (Exception e2) {
					e2.printStackTrace();
					System.out.println(e2.getMessage());
				}

			}
		});
		btnRegister.setBounds(412, 380, 200, 23);
		panel.add(btnRegister);

	}
}
