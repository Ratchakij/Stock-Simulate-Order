package Views;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Common.GlobalData;
import Controllers.TestController;
import Models.TestsDB;
public class TestPanel extends JPanel {
	//
	public void loadTest() {
		ArrayList<TestsDB> testList = new ArrayList<TestsDB>(TestController.getTest());
		// Sort 
		Collections.sort(testList, new Comparator<TestsDB>() {
			@Override
			public int compare(TestsDB o1, TestsDB o2) {
				return o1.date.compareTo(o2.date);
			}
		});
		model.setRowCount(0);
		int i = 1;
		for (TestsDB test : testList) {
			if (test.user_id == GlobalData.CurrentUser_userID) {
				model.addRow(new Object[] { i, test.id, test.date, "รายละเอียด", "ลบ" });
				i++;
			} ;
		}
		//		table.setModel(model);
	}
	private JTable table;
	static DefaultTableModel model;
	String[] column = { "ลำดับ", "รหัสการทดสอบ", "สร้างเมื่อ", "รายละเอียด", "ลบรายการ" };
	static int testId;
	static LocalDate testCreateDate;
	static int selectRow;
	static int selectCol;
	public TestPanel(JPanel mainPanel) {
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 984, 536);
		add(panel);
		panel.setLayout(null);
		JLabel lblTestOrderDetail = new JLabel("หน้าจัดการการทดสอบ");
		lblTestOrderDetail.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTestOrderDetail.setBounds(10, 25, 180, 23);
		panel.add(lblTestOrderDetail);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(192, 120, 600, 300);
		panel.add(scrollPane);
		table = new JTable();
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (table.columnAtPoint(e.getPoint()) > 2) {
					table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					table.setCursor(Cursor.getDefaultCursor());
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					selectRow = table.getSelectedRow();
					selectCol = table.getSelectedColumn();
					System.out.println("selectRow = " + selectRow);
					System.out.println("selectCol = " + selectCol);
					testId = Integer.valueOf(model.getValueAt(selectRow, 1).toString());
					testCreateDate = LocalDate.parse(model.getValueAt(selectRow, 2).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					if (selectCol == 3) {
						JPanel Transaction = new TransactionPanel(mainPanel);
						mainPanel.add(Transaction, "Transaction");
						CardLayout cl = (CardLayout) mainPanel.getLayout();
						cl.show(mainPanel, "Transaction");
						return;
					}
					if (selectCol == 4) {
						if (JOptionPane.showConfirmDialog(null, "Confirm delete?", "Delete", JOptionPane.YES_NO_OPTION) == 0) {
							TestController.deleteTest(testId);
							JOptionPane.showMessageDialog(null, "Delete complete.");
							model.removeRow(selectRow);
							loadTest();
							return;
						} ;
					} ;
				} catch (Exception e2) {
					e2.printStackTrace();
					System.err.println(e2.getMessage());
				}
			}
		});
		model = new DefaultTableModel();
		for (String c : column) {
			model.addColumn(c);
		}
		loadTest();
		scrollPane.setViewportView(table);
		table.setModel(model);
		//
		JTextField tf = new JTextField();
		tf.setEditable(false);
		DefaultCellEditor editor = new DefaultCellEditor(tf);
		table.setDefaultEditor(Object.class, editor);
		JButton btnNewOrder = new JButton("สร้างการทดสอบ");
		btnNewOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				testId = 0;
				JPanel Transaction = new TransactionPanel(mainPanel);
				mainPanel.add(Transaction, "Transaction");
				CardLayout cl = (CardLayout) mainPanel.getLayout();
				cl.show(mainPanel, "Transaction");
				return;
			}
		});
		btnNewOrder.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewOrder.setBounds(657, 86, 135, 23);
		panel.add(btnNewOrder);
		JLabel lblNewLabel = new JLabel("ID: " + GlobalData.CurrentUser_userID + "   Name: " + GlobalData.CurrentUser_userName + "   จำนวนการทดสอบทั้งหมด: " + model.getRowCount());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(192, 86, 370, 23);
		panel.add(lblNewLabel);
	}
}
