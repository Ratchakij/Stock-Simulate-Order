package Views;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LogoutPanel extends JPanel {
	/**
	 * Create the panel.
	 */
	public LogoutPanel(JPanel mainPanel) {
		setLayout(null);
		JPanel logout = new JPanel();
		logout.setBounds(0, 0, 984, 536);
		add(logout);
		logout.setLayout(null);
		//
		JLabel lblNewLabel = new JLabel("Logout Successful");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(267, 100, 450, 66);
		logout.add(lblNewLabel);
		JLabel lblNewLabel_1 = new JLabel("กลับไปหน้าแรก");
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel Home = new HomePanel();
				mainPanel.add(Home, "Home");
				CardLayout cl = (CardLayout) mainPanel.getLayout();
				cl.show(mainPanel, "Home");
				return;
			}
		});
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(267, 175, 450, 66);
		logout.add(lblNewLabel_1);
	}
}
