package Views;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import Controllers.StockController;
import Models.StocksDB;
public class StockInfoPanel extends JPanel {
	private JTable table;
	private JDateChooser dc_start;
	private JDateChooser dc_end;
	DefaultTableModel model = new DefaultTableModel();
	/**
	 * Create the panel.
	 */
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
	public static ArrayList<StocksDB> stockList = new ArrayList<StocksDB>(StockController.getAllStock());
	//
	public void loadSet() {
		model.setColumnCount(0);
		model.setRowCount(0);
		model.addColumn("Date");
		model.addColumn("Price");
		model.addColumn("Open");
		model.addColumn("High");
		model.addColumn("Low");
		model.addColumn("Volume");
		model.addColumn("Change%");
		for (StocksDB stock : stockList) {
			model.addRow(new Object[] { stock.date.format(formatter), stock.price, stock.open, stock.high, stock.low, stock.vol, stock.change });
		}
		table.setModel(model);
	}

	public void loadSetByDate(LocalDate startDate, LocalDate endDate) {
		//		ArrayList<StocksDB> stockListDate = StockController.getStockByDate(startDate, endDate);
		ArrayList<StocksDB> stockListByDate = new ArrayList<StocksDB>();
		if (startDate.isBefore(stockList.get(stockList.size() - 1).date) || startDate.isAfter(stockList.get(0).date)) {
			startDate = stockList.get(stockList.size() - 1).date;
		} ;
		if (endDate.isBefore(stockList.get(stockList.size() - 1).date) || endDate.isAfter(stockList.get(0).date)) {
			endDate = stockList.get(0).date;
		} ;
		for (int i = 0; i < stockList.size(); i++) {
			if (stockList.get(i).date.isEqual(endDate)) {
				if (endDate.equals(startDate)) {
					stockListByDate.add(stockList.get(i));
					break;
				}
				while (true) {
					stockListByDate.add(stockList.get(i));
					if (stockList.get(i).date.isEqual(startDate)) {
						break;
					}
					i++;
				}
			} ;
		}
		model.setRowCount(0);
		for (StocksDB stock : stockListByDate) {
			model.addRow(new Object[] { stock.date.format(formatter), stock.price, stock.open, stock.high, stock.low, stock.vol, stock.change });
		}
		table.setModel(model);
	}
	public boolean checkDayOff(LocalDate startDate, LocalDate endDate) {
		int checkStart = 0;
		int checkEnd = 0;
		for (int i = 0; i < stockList.size(); i++) {
			if (stockList.get(i).date.isEqual(startDate)) checkStart = 1;
			if (stockList.get(i).date.isEqual(endDate)) checkEnd = 1;
		}
		// Out of range
		if (startDate.isBefore(stockList.get(stockList.size() - 1).date) || startDate.isAfter(stockList.get(0).date)) return true;
		if (endDate.isBefore(stockList.get(stockList.size() - 1).date) || endDate.isAfter(stockList.get(0).date)) return true;
		// Day off
		if (checkStart == 0) {
			JOptionPane.showMessageDialog(null, "Start date is day off, Choose other date.");
			return false;
		}
		if (checkEnd == 0) {
			JOptionPane.showMessageDialog(null, "End date is day off, Choose other date.");
			return false;
		}
		return true;
	}
	public StockInfoPanel() {
		setLayout(null);
		JPanel info = new JPanel();
		info.setBounds(0, 0, 984, 536);
		add(info);
		info.setLayout(null);
		JLabel lblSet = new JLabel("แสดงข้อมูล SET");
		lblSet.setHorizontalAlignment(SwingConstants.CENTER);
		lblSet.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblSet.setBounds(407, 20, 175, 29);
		info.add(lblSet);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 100, 900, 400);
		info.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		//
		dc_start = new JDateChooser();
		dc_start.setDateFormatString("dd-MMM-yy");
		dc_start.setBounds(302, 69, 100, 20);
		info.add(dc_start);
		dc_end = new JDateChooser();
		dc_end.setDateFormatString("dd-MMM-yy");
		dc_end.setBounds(433, 69, 100, 20);
		info.add(dc_end);
		//
		JButton btnChDate = new JButton("แสดงตามวันที่");
		btnChDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (dc_start.getDate() == null || dc_end.getDate() == null) {
						JOptionPane.showMessageDialog(btnChDate, "The date is invalid.");
						return;
					}
					LocalDate dcStart = dc_start.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					LocalDate dcEnd = dc_end.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					if (dc_start.getDate().after(dc_end.getDate())) {
						JOptionPane.showMessageDialog(btnChDate, "The start date should be before end date.");
						return;
					}
					if (!checkDayOff(dcStart, dcEnd)) return;

					loadSetByDate(dcStart, dcEnd);
				} catch (Exception e2) {
					e2.printStackTrace();
					System.err.println(e2.getMessage());
				}
			}
		});
		btnChDate.setBounds(543, 66, 100, 23);
		info.add(btnChDate);
		//
		JLabel lblNewLabel = new JLabel("เริ่ม");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(265, 75, 30, 14);
		info.add(lblNewLabel);
		JLabel lblNewLabel_1 = new JLabel("ถึง");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(412, 75, 12, 14);
		info.add(lblNewLabel_1);
		JButton btnChAll = new JButton("แสดงทั้งหมด");
		btnChAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				model.setRowCount(0);
				for (StocksDB stock : stockList) {
					model.addRow(new Object[] { stock.date.format(formatter), stock.price, stock.open, stock.high, stock.low, stock.vol, stock.change });
				}
				table.setModel(model);
				return;
			}
		});
		btnChAll.setBounds(842, 66, 100, 23);
		info.add(btnChAll);
		loadSet();
	}
	public static void main(String[] args) {
	}
}
