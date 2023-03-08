package Views;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import Common.GlobalData;
public class HomePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	public HomePanel() {
		setLayout(null);
		//
		JPanel home = new JPanel();
		home.setBounds(0, 0, 984, 536);
		add(home);
		home.setLayout(null);
		//
		JLabel lblWelcome = new JLabel();
		if (GlobalData.CurrentUser_userName != null) {
			lblWelcome.setText("Welcome, " + GlobalData.CurrentUser_userName);
		} else {
			lblWelcome.setText("Welcome, Guest");
		}
		lblWelcome.setBounds(10, 50, 964, 22);
		home.add(lblWelcome);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("C:\\Users\\Asus-UEFI\\eclipse-workspace\\EXAM_1.3_Stock\\src\\SET.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel lblNewLabel = new JLabel(new ImageIcon(myPicture));
		lblNewLabel.setBounds(10, 83, 964, 442);
		home.add(lblNewLabel);
	}
}
