package Views;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import Common.GlobalData;
import Controllers.UserController;
import Models.UsersDB;
public class LoginPanel extends JPanel {
	private JTextField tf_username;
	private JPasswordField passwordField;
	/**
	 * Create the panel.
	 */
	public boolean checkLogin(String username, String password) {
		ArrayList<UsersDB> list = new ArrayList<UsersDB>(UserController.getAllUser());
		for (UsersDB users : list) {
			if (username.equals(users.username) && password.equals(users.password)) {
				GlobalData.CurrentUser_userID = users.id;
				GlobalData.CurrentUser_userName = users.username;
				GlobalData.CurrentUser_userStatus = users.approval_status;
				GlobalData.CurrentUser_userType = users.privilege;
				return true;
			} ;
		} ;
		return false;
	};
	public LoginPanel(JPanel mainPanel, JPanel mainNavBar) {
		setLayout(null);
		JPanel login = new JPanel();
		login.setBounds(0, 0, 984, 536);
		add(login);
		login.setLayout(null);
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(400, 60, 160, 29);
		login.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(400, 100, 47, 14);
		login.add(lblUsername);
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(400, 144, 46, 14);
		login.add(lblPassword);
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(400, 205, 160, 23);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (tf_username.getText().isEmpty()) {
						JOptionPane.showMessageDialog(LoginPanel.this, "Input username");
						return;
					}
					if (passwordField.getPassword().length == 0) {
						JOptionPane.showMessageDialog(LoginPanel.this, "Input password");
						return;
					}
					if (!checkLogin(tf_username.getText(), new String(passwordField.getPassword()))) {
						JOptionPane.showMessageDialog(LoginPanel.this, "username or password is invalid.");
						return;
					}
					tf_username.setText("");
					passwordField.setText("");
					//
					JPanel navbar = new NavBar(mainPanel, mainNavBar);
					mainNavBar.add(navbar, "Navbar");
					CardLayout cln = (CardLayout) mainNavBar.getLayout();
					cln.show(mainNavBar, "Navbar");
					//
					JPanel home = new HomePanel();
					mainPanel.add(home, "Home");
					CardLayout cl = (CardLayout) mainPanel.getLayout();
					cl.show(mainPanel, "Home");
					return;
				} catch (Exception e2) {
					e2.printStackTrace();
					System.out.println(e2.getMessage());
				}

			}
		});
		login.add(btnLogin);
		//
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(400, 239, 160, 23);
		login.add(btnRegister);
		tf_username = new JTextField();
		tf_username.setBounds(400, 114, 160, 20);
		login.add(tf_username);
		tf_username.setColumns(10);
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tf_username.getText().isEmpty()) {
						JOptionPane.showMessageDialog(LoginPanel.this, "Input username");
						return;
					}
					if (passwordField.getPassword().length == 0) {
						JOptionPane.showMessageDialog(LoginPanel.this, "Input password");
						return;
					}
					if (!checkLogin(tf_username.getText(), new String(passwordField.getPassword()))) {
						JOptionPane.showMessageDialog(LoginPanel.this, "Username or Password is invalid.");
						return;
					}
					tf_username.setText("");
					passwordField.setText("");
					//
					JPanel navbar = new NavBar(mainPanel, mainNavBar);
					mainNavBar.add(navbar, "Navbar");
					CardLayout cln = (CardLayout) mainNavBar.getLayout();
					cln.show(mainNavBar, "Navbar");
					//
					JPanel home = new HomePanel();
					mainPanel.add(home, "Home");
					CardLayout cl = (CardLayout) mainPanel.getLayout();
					cl.show(mainPanel, "Home");
					return;
				}
			}
		});
		passwordField.setBounds(400, 157, 160, 20);
		login.add(passwordField);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) mainPanel.getLayout();
				cl.show(mainPanel, "Register");
			}
		});
	}
}
