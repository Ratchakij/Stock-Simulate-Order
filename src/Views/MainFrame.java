package Views;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
	public JPanel contentPane;
	public JPanel mainNavbar;
	public JPanel mainPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainFrame frame = new MainFrame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Asking before closing
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//		this.addWindowListener(new WindowAdapter() {
		//			@Override
		//			public void windowClosing(WindowEvent e) {
		//				if (JOptionPane.showConfirmDialog(null, "Are you sure to close?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
		//					dispose();
		//				} ;
		//
		//			}
		//		});
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//////////// Main Navbar ////////////
		mainNavbar = new JPanel();
		mainNavbar.setBackground(new Color(255, 255, 255));
		mainNavbar.setBounds(0, 0, 984, 25);
		contentPane.add(mainNavbar);
		mainNavbar.setLayout(new CardLayout(0, 0));
		//////////// Main Panel ////////////
		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(255, 255, 255));
		mainPanel.setBounds(0, 25, 984, 536);
		contentPane.add(mainPanel);
		mainPanel.setLayout(new CardLayout(0, 0));
		// Navbar 1
		JPanel Navbar = new NavBar(mainPanel, mainNavbar);
		mainNavbar.add(Navbar, "Navbar");
		Navbar.setLayout(null);
		// Home
		JPanel Home = new HomePanel();
		mainPanel.add(Home, "Home");
		Home.setLayout(null);
		// Login
		JPanel Login = new LoginPanel(mainPanel, mainNavbar);
		mainPanel.add(Login, "Login");
		Login.setLayout(null);
		// Register
		JPanel Register = new RegisterPanel(mainPanel, mainNavbar);
		mainPanel.add(Register, "Register");
		Register.setLayout(null);
		// InfoStock
		JPanel StockInfo = new StockInfoPanel();
		mainPanel.add(StockInfo, "StockInfo");
		StockInfo.setLayout(null);
		// Test
		JPanel Test = new TestPanel(mainPanel);
		mainPanel.add(Test, "Test");
		Test.setLayout(null);
		// Transaction
		JPanel Transaction = new TransactionPanel(mainPanel);
		mainPanel.add(Transaction, "Transaction");
		Transaction.setLayout(null);
		// Admin
		JPanel Admin = new AdminPanel();
		mainPanel.add(Admin, "Admin");
		Admin.setLayout(null);
		// Logout
		JPanel Logout = new LogoutPanel(mainPanel);
		mainPanel.add(Logout, "Logout");
		Logout.setLayout(null);

	}
}
