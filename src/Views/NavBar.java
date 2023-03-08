package Views;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import Common.GlobalData;
public class NavBar extends JPanel {
	/**
	 * Create the panel.
	 */
	public NavBar(JPanel mainPanel, JPanel mainNavBar) {
		setLayout(null);
		JPanel Navbar = new JPanel();
		Navbar.setBackground(new Color(255, 255, 255));
		Navbar.setBounds(0, 0, 984, 25);
		Navbar.setLayout(null);
		add(Navbar);
		//
		JLabel lblUsername = new JLabel("Login");
		lblUsername.setBounds(530, 0, 350, 24);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setForeground(new Color(0, 0, 0));
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsername.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (GlobalData.CurrentUser_userName != null) {
						lblUsername.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						return;
					} else {
						CardLayout cl = (CardLayout) mainPanel.getLayout();
						cl.show(mainPanel, "Login");
						return;
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					System.out.println(e2.getMessage());
				}
			}
		});
		if (GlobalData.CurrentUser_userName == null) {
			lblUsername.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			String status;
			if (!GlobalData.CurrentUser_userStatus.equals("yes")) status = "Unapproved";
			else status = "Approved";
			lblUsername.setText("ID: " + GlobalData.CurrentUser_userID + "   Name: " + GlobalData.CurrentUser_userName + "   Status: (" + GlobalData.CurrentUser_userType + ") " + status);
		}
		Navbar.add(lblUsername);
		//
		JLabel lblHome = new JLabel("Home");
		lblHome.setBounds(430, 0, 90, 24);
		lblHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblHome.setForeground(new Color(0, 128, 255));
		lblHome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Update State HomePanel
				JPanel Home = new HomePanel();
				mainPanel.add(Home, "Home");
				// Navigate to new HomePanel after update state
				CardLayout cl = (CardLayout) mainPanel.getLayout();
				cl.show(mainPanel, "Home");
				return;
			}
		});
		Navbar.add(lblHome);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 255));
		menuBar.setBounds(0, 0, 90, 26);
		Navbar.add(menuBar);
		JMenu mnMenu = new JMenu("Menu");
		mnMenu.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnMenu.setForeground(new Color(0, 0, 0));
		mnMenu.setBackground(new Color(255, 255, 255));
		menuBar.add(mnMenu);
		//
		JMenuItem mnt_stock = new JMenuItem("Stock");
		mnt_stock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GlobalData.CurrentUser_userType == null) {
					JOptionPane.showMessageDialog(mnt_stock, "You can't see the submission data for this form. Please login first.");
					CardLayout cl = (CardLayout) mainPanel.getLayout();
					cl.show(mainPanel, "Login");
					return;
				}
				if (!GlobalData.CurrentUser_userStatus.equals("yes")) {
					JOptionPane.showMessageDialog(null, "Waiting for admin approval.");
					return;
				} else {
					CardLayout cl = (CardLayout) mainPanel.getLayout();
					cl.show(mainPanel, "StockInfo");
					return;
				}
			}
		});
		mnt_stock.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnMenu.add(mnt_stock);
		JMenuItem mnt_order = new JMenuItem("My Test");
		mnt_order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GlobalData.CurrentUser_userStatus == null) {
					JOptionPane.showMessageDialog(null, "You can't see the submission data for this form. Please login first.");
					CardLayout cl = (CardLayout) mainPanel.getLayout();
					cl.show(mainPanel, "Login");
					return;
				}
				if (!GlobalData.CurrentUser_userStatus.equals("yes")) {
					JOptionPane.showMessageDialog(null, "Waiting for admin approval.");
					return;
				}
				TestPanel.testId = 0;
				JPanel Test = new TestPanel(mainPanel);
				mainPanel.add(Test, "Test");
				CardLayout cl = (CardLayout) mainPanel.getLayout();
				cl.show(mainPanel, "Test");
				return;
			}
		});
		mnt_order.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnMenu.add(mnt_order);
		JMenuItem mntExit = new JMenuItem("Exit");
		mntExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION) == 0) {
					System.exit(0);
				} ;
			}
		});
		mntExit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		mnMenu.add(mntExit);
		//
		JMenu mnAdmin = new JMenu("Admin");
		mnAdmin.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnAdmin.setForeground(new Color(0, 0, 0));
		mnAdmin.setBackground(new Color(255, 255, 255));
		if (GlobalData.CurrentUser_userType == null || !GlobalData.CurrentUser_userType.equals("admin")) {
			mnAdmin.setEnabled(false);
		} else {
			mnAdmin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JPanel Admin = new AdminPanel();
					mainPanel.add(Admin, "Admin");
					CardLayout cl = (CardLayout) mainPanel.getLayout();
					cl.show(mainPanel, "Admin");
					return;
				}
			});
		} ;
		menuBar.add(mnAdmin);

		//
		if (GlobalData.CurrentUser_userName != null) {
			JLabel lblNewLabel = new JLabel("Logout");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel.setBounds(894, 0, 90, 24);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			lblNewLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					GlobalData.CurrentUser_userID = 0;
					GlobalData.CurrentUser_userName = null;
					GlobalData.CurrentUser_userStatus = null;
					GlobalData.CurrentUser_userType = null;
					JPanel navbar = new NavBar(mainPanel, mainNavBar);
					mainNavBar.add(navbar, "Navbar");
					CardLayout clNav = (CardLayout) mainNavBar.getLayout();
					clNav.show(mainNavBar, "Navbar");
					CardLayout cl = (CardLayout) mainPanel.getLayout();
					cl.show(mainPanel, "Logout");
					return;
				}
			});

			Navbar.add(lblNewLabel);
		}
	}
}
