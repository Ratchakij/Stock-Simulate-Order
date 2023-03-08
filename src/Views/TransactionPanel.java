package Views;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import Common.GlobalData;
import Controllers.TestController;
import Controllers.TransactionController;
import Models.StocksDB;
import Models.TestsDB;
import Models.TransactionsDB;
public class TransactionPanel extends JPanel {
	public boolean checkInput(JDateChooser date, JTextField qty) {
		// Check input
		if (date.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Choose date.");
			return false;
		}
		if (!tf_qty.getText().trim().matches("-?\\d+(\\d+)?")) {
			JOptionPane.showMessageDialog(null, "Input only number without decimal.");
			return false;
		}
		if (Integer.parseInt(tf_qty.getText().trim().toString()) <= 0) {
			JOptionPane.showMessageDialog(null, "Order must be more than 0.");
			return false;
		}
		return true;
	}
	public boolean checkDayOff(LocalDate dcOrder) {
		for (StocksDB stock : StockInfoPanel.stockList) {
			if (dcOrder.isEqual(stock.date)) return true;;
		} ;
		JOptionPane.showMessageDialog(null, "Date is day off.");
		return false;
	}
	public void updatePanel(JPanel mainPanel) {
		JPanel Transaction = new TransactionPanel(mainPanel);
		mainPanel.add(Transaction, "Transaction");
		CardLayout clTrans = (CardLayout) mainPanel.getLayout();
		clTrans.show(mainPanel, "Transaction");
	}
	private void checkBalance(String position, double amount, int qty) {
		try {
			if (position.equals("BUY")) {
				moneyBalance -= amount;
				stockBalance += qty;
			} else {
				moneyBalance += amount;
				stockBalance -= qty;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	public void loadTransaction(int testId) {
		// Sort 
		Collections.sort(transList, new Comparator<TransactionsDB>() {
			@Override
			public int compare(TransactionsDB o1, TransactionsDB o2) {
				return o1.date.compareTo(o2.date);
			}
		});
		transByTestId = new ArrayList<TransactionsDB>();
		for (TransactionsDB trans : transList) {
			if (trans.test_id == testId) {
				model.addRow(new Object[] { trans.test_id, trans.date.format(formatter), trans.position, trans.qty });
				transByTestId.add(trans);
			} ;
		}
		for (int i = 0; i < model.getRowCount(); i++) {
			for (StocksDB stock : StockInfoPanel.stockList) {
				if (transByTestId.get(i).date.isEqual(stock.date)) {
					double amount = transByTestId.get(i).qty * stock.open;
					model.setValueAt(stock.open, i, 4);
					model.setValueAt(df.format(amount), i, 5);
					checkBalance(transByTestId.get(i).position, amount, transByTestId.get(i).qty);
					model.setValueAt(df.format(moneyBalance), i, 6);
					model.setValueAt(stockBalance, i, 7);
				}
			} ;
		}
	}
	//
	private JTextField tf_qty;
	private JLabel lblMoneyBalance;
	private JComboBox<String> cb_order;
	DefaultTableModel model;
	String[] column = { "Test ID", "date", "position", "qty", "price", "amount", "money_balance", "stock_balance" };
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
	DecimalFormat df = new DecimalFormat("#,###.00");
	ArrayList<TransactionsDB> transList = new ArrayList<TransactionsDB>(TransactionController.getTrans());
	ArrayList<TransactionsDB> transByTestId;
	double moneyBalance = 100000000;
	int stockBalance = 0;
	public TransactionPanel(JPanel mainPanel) {
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 984, 536);
		add(panel);
		panel.setLayout(null);
		JLabel lblTransaction = new JLabel();
		if (TestPanel.testId == 0) {
			lblTransaction.setText("Transactions Create");
		} else {
			lblTransaction.setText("Transactions History");
		}
		lblTransaction.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTransaction.setBounds(10, 25, 188, 23);
		panel.add(lblTransaction);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 100, 900, 350);
		panel.add(scrollPane);
		JTable table = new JTable();
		table.setEnabled(false);
		scrollPane.setViewportView(table);
		//
		model = new DefaultTableModel();
		for (String c : column) {
			model.addColumn(c);
		}
		loadTransaction(TestPanel.testId);
		//		if (TestPanel.testId != 0) loadTransaction(TestPanel.testId);
		table.setModel(model);
		//
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(42, 75, 900, 25);
		if (TestPanel.testId == 0) {
			lblNewLabel.setText("สร้างรหัสการทดสอบ");
		} else {
			lblNewLabel.setText("การทดสอบรหัสที่: " + TestPanel.testId + "   สร้างเมื่อ: " + TestPanel.testCreateDate);
		}
		panel.add(lblNewLabel);
		JLabel lblNewLabel_1 = new JLabel("วันที่");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(42, 476, 46, 14);
		panel.add(lblNewLabel_1);
		JDateChooser dc_order = new JDateChooser();
		dc_order.setVerifyInputWhenFocusTarget(false);
		dc_order.setDateFormatString("dd-MMM-yy");
		dc_order.setBounds(98, 470, 100, 20);
		panel.add(dc_order);
		JLabel lblNewLabel_2 = new JLabel("คำสั่งซื้อ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(208, 476, 46, 14);
		panel.add(lblNewLabel_2);
		JLabel lblNewLabel_2_1 = new JLabel("จำนวน");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1.setBounds(360, 476, 46, 14);
		panel.add(lblNewLabel_2_1);
		tf_qty = new JTextField();
		tf_qty.setColumns(10);
		tf_qty.setBounds(416, 470, 86, 20);
		panel.add(tf_qty);
		//
		JButton btn_addOrder = new JButton("ส่งคำสั่งซื้อ-ขาย");
		btn_addOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String order = cb_order.getSelectedItem().toString();
				try {
					if (!checkInput(dc_order, tf_qty)) return;
					// Convert Date to LocalDate
					LocalDate dcOrder = dc_order.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (!checkDayOff(dcOrder)) return;
					// Set price order
					double priceOrder = 0;
					for (StocksDB stock : StockInfoPanel.stockList) {
						if (dcOrder.isEqual(stock.date)) {
							priceOrder = stock.open * Integer.parseInt(tf_qty.getText().trim().toString());
						}
					}
					// Create from old test ///////////////////////////////////////////////////////////////////////////////////////////////////////
					if (model.getRowCount() != 0) {
						// Check Date
						LocalDate lastDateTrans = LocalDate.parse(model.getValueAt(model.getRowCount() - 1, 1).toString(), DateTimeFormatter.ofPattern("dd-MMM-yy"));
						if (dcOrder.isBefore(lastDateTrans)) {
							JOptionPane.showMessageDialog(null, "Date is must after last order date.");
							return;
						}
						// Check Duplicate Date without last date
						for (TransactionsDB trans : transList) {
							if (trans.date.isEqual(lastDateTrans)) continue;
							if (trans.date.isEqual(dcOrder)) {
								JOptionPane.showMessageDialog(null, "Date is duplicate transaction.");
								return;
							} ;
						}
						// Check Money
						double priceRemain = Double.valueOf(model.getValueAt(model.getRowCount() - 1, 6).toString().replace(",", ""));
						if (order.equals("BUY")) {
							if (priceOrder > priceRemain) {
								JOptionPane.showMessageDialog(null, "Money not Enought.");
								return;
							} ;
						}
						// Check Stock
						int stockOrder = Integer.valueOf(tf_qty.getText().trim().toString());
						int stockRemain = Integer.valueOf(model.getValueAt(model.getRowCount() - 1, 7).toString());
						if (order.equals("SELL")) {
							if (stockOrder > stockRemain) {
								JOptionPane.showMessageDialog(null, "Stock not Enought.");
								return;
							} ;
						} ;
						if (dcOrder.isEqual(lastDateTrans)) {
							String position = model.getValueAt(model.getRowCount() - 1, 2).toString();
							int qty = Integer.valueOf(stockOrder + Integer.valueOf(model.getValueAt(model.getRowCount() - 1, 3).toString()));
							int id = transByTestId.get(transByTestId.size() - 1).id;
							if (!order.equals(position)) {
								JOptionPane.showMessageDialog(null, "Position not match last order.");
								return;
							} ;
							TransactionController.updateTrans(qty, id);
							updatePanel(mainPanel);
							return;
						} else {
							TransactionsDB trans = new TransactionsDB(0, cb_order.getSelectedItem().toString(), Integer.parseInt(tf_qty.getText().toString()), TestPanel.testId, dcOrder);
							TransactionController.createTrans(trans);
							updatePanel(mainPanel);
							return;
						}
					}
					// Create new test ///////////////////////////////////////////////////////////////////////////////////////////////////////
					if (model.getRowCount() == 0) {
						// Check Duplicate Date
						for (TransactionsDB trans : transList) {
							if (dcOrder.isEqual(trans.date)) {
								JOptionPane.showMessageDialog(null, "Date is duplicate transaction.");
								return;
							} ;
						}
						if (order.equals("BUY")) {
							if (priceOrder > moneyBalance) {
								JOptionPane.showMessageDialog(null, "Money not Enought.");
								return;
							} ;
						}
						if (order.equals("SELL")) {
							if (Integer.parseInt(tf_qty.getText().trim().toString()) > stockBalance) {
								JOptionPane.showMessageDialog(null, "Stock not Enought.");
								return;
							}
						}
						TestsDB test = new TestsDB(0, LocalDate.now(), GlobalData.CurrentUser_userID);
						TestController.createTest(test);
						ArrayList<TestsDB> newTest = new ArrayList<TestsDB>(TestController.getTest());
						TestPanel.testId = newTest.get(newTest.size() - 1).id;
						TestPanel.testCreateDate = newTest.get(newTest.size() - 1).date;
						TransactionsDB trans = new TransactionsDB(0, cb_order.getSelectedItem().toString(), Integer.parseInt(tf_qty.getText().toString()), TestPanel.testId, dcOrder);
						TransactionController.createTrans(trans);
						// Reset State
						updatePanel(mainPanel);
						return;
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					System.err.println(e2.getMessage());
				}
			}
		});
		btn_addOrder.setBounds(526, 470, 116, 23);
		panel.add(btn_addOrder);
		cb_order = new JComboBox<String>();
		cb_order.setModel(new DefaultComboBoxModel<String>(new String[] { "BUY", "SELL" }));
		cb_order.setBounds(264, 470, 86, 22);
		panel.add(cb_order);
		lblMoneyBalance = new JLabel();
		if (TestPanel.testId == 0) {
			lblMoneyBalance.setText("Money = " + df.format(moneyBalance));
		} else {
			lblMoneyBalance.setText("Money = " + model.getValueAt(model.getRowCount() - 1, 6).toString());
		}
		lblMoneyBalance.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMoneyBalance.setBounds(692, 465, 250, 25);
		panel.add(lblMoneyBalance);
	}
	public static void main(String[] args) {
	}
}
