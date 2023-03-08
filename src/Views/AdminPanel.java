package Views;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import Controllers.UserController;
import Models.UsersDB;
public class AdminPanel extends JPanel {
	private JTable table;
	private JLabel lblPrivilege;
	/**
	 * Create the panel.
	 */
	ArrayList<UsersDB> list;
	int selectRow;
	public AdminPanel() {
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 984, 536);
		add(panel);
		panel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 97, 844, 412);
		panel.add(scrollPane);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectRow = table.getSelectedRow();
				if (!table.isRowSelected(selectRow)) return;
				lblPrivilege.setText(table.getValueAt(selectRow, 6).toString());
				if (table.getValueAt(selectRow, 6).toString().equals("admin")) {
					lblPrivilege.setBackground(new Color(10, 220, 250));
				} else {
					lblPrivilege.setBackground(Color.ORANGE);
				} ;
			}
		});
		scrollPane.setViewportView(table);
		JLabel lblAdmin = new JLabel("Admin Management");
		lblAdmin.setBounds(10, 25, 181, 23);
		panel.add(lblAdmin);
		lblAdmin.setFont(new Font("Tahoma", Font.BOLD, 18));
		JButton btnApprove = new JButton("Approve");
		btnApprove.setBounds(884, 97, 90, 40);
		panel.add(btnApprove);
		//
		lblPrivilege = new JLabel("");
		lblPrivilege.setBounds(30, 73, 844, 23);
		panel.add(lblPrivilege);
		lblPrivilege.setOpaque(true);
		lblPrivilege.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrivilege.setForeground(Color.BLACK);
		lblPrivilege.setFont(new Font("Tahoma", Font.BOLD, 18));
		JButton btnUnapprove = new JButton("Unapprove");
		btnUnapprove.setBounds(885, 148, 89, 40);
		panel.add(btnUnapprove);
		JButton btnDelete = new JButton("Delete User");
		btnDelete.setBounds(885, 199, 89, 40);
		panel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRow = table.getSelectedRow();
				if (!table.isRowSelected(selectRow)) {
					JOptionPane.showMessageDialog(AdminPanel.this, "Please select row.");
					return;
				}
				if (JOptionPane.showConfirmDialog(null, "Confirm Delete?", "Delete", JOptionPane.YES_NO_OPTION) == 0) {
					UserController.deleteUser(Integer.valueOf(table.getValueAt(selectRow, 0).toString()));
					JOptionPane.showMessageDialog(AdminPanel.this, "Delete complete.");
					load();
					return;
				} ;
			}
		});
		btnUnapprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRow = table.getSelectedRow();
				if (!table.isRowSelected(selectRow)) {
					JOptionPane.showMessageDialog(AdminPanel.this, "Please select row.");
					return;
				}
				if (JOptionPane.showConfirmDialog(null, "Confirm Unpprove?", "Unpprove", JOptionPane.YES_NO_OPTION) == 0) {
					UserController.unapproveStatus(Integer.valueOf(table.getValueAt(selectRow, 0).toString()));
					JOptionPane.showMessageDialog(AdminPanel.this, "Unapprove complete.");
					load();
					return;
				} ;
			}
		});
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRow = table.getSelectedRow();
				if (!table.isRowSelected(selectRow)) {
					JOptionPane.showMessageDialog(AdminPanel.this, "Please select row.");
					return;
				}
				if (JOptionPane.showConfirmDialog(null, "Confirm Approve?", "Approve", JOptionPane.YES_NO_OPTION) == 0) {
					UserController.approveStatus(Integer.valueOf(table.getValueAt(selectRow, 0).toString()));
					JOptionPane.showMessageDialog(AdminPanel.this, "Approve complete.");
					load();
					return;
				} ;
			}
		});
		load();
	}
	public void load() {
		list = UserController.getAllUser();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("username");
		model.addColumn("name");
		model.addColumn("surname");
		model.addColumn("email");
		model.addColumn("approval_status");
		model.addColumn("privilege");
		for (UsersDB c : list) {
			model.addRow(new Object[] { c.id, c.username, c.name, c.surname, c.email, c.approval_status, c.privilege });
		}
		table.setModel(model);
	}
}
