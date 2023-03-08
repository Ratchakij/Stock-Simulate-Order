package Controllers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import Common.GlobalData;
import Models.StocksDB;
public class StockController {
	static ArrayList<StocksDB> stockList = new ArrayList<StocksDB>();
	//
	private static StocksDB parse(String line) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
		StocksDB stock = new StocksDB();
		String[] ss = line.split("\t");
		stock.date = LocalDate.parse(ss[0], formatter);
		stock.price = Double.parseDouble(ss[1].replace(",", ""));
		stock.open = Double.parseDouble(ss[2].replace(",", ""));
		stock.high = Double.parseDouble(ss[3].replace(",", ""));
		stock.low = Double.parseDouble(ss[4].replace(",", ""));
		stock.vol = ss[5];
		stock.change = ss[6];
		return stock;
	};
	public static void setStock() {
		ArrayList<StocksDB> list = new ArrayList<StocksDB>();
		File file = new File("D:\\EPT_Download\\set_index_history_2000_to_2021.csv");
		try (BufferedReader br = new BufferedReader(new FileReader(file));
		    Scanner fileSc = new Scanner(br)) {
			fileSc.nextLine();
			while (fileSc.hasNextLine()) {
				String s = fileSc.nextLine();
				list.add(parse(s));
			} ;
			// System.out.println("list.size() = " + list.size());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		// Insert Stock to Database
		String query = "INSERT INTO set_data VALUES (?,?,?,?,?,?,?)";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query)) {
			for (StocksDB stock : list) {
				java.sql.Date sqlDate = java.sql.Date.valueOf(stock.date);
				pstmt.setDate(1, sqlDate);
				pstmt.setDouble(2, stock.price);
				pstmt.setDouble(3, stock.open);
				pstmt.setDouble(4, stock.high);
				pstmt.setDouble(5, stock.low);
				pstmt.setString(6, stock.vol);
				pstmt.setString(7, stock.change);
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	};
	public static ArrayList<StocksDB> getAllStock() {
		String query = "SELECT * FROM set_data ORDER BY Date DESC";
		try (Connection con = GlobalData.getConnection();
		    PreparedStatement pstmt = con.prepareStatement(query);
		    ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				LocalDate date = rs.getDate(1).toLocalDate();
				double price = rs.getDouble(2);
				double open = rs.getDouble(3);
				double high = rs.getDouble(4);
				double low = rs.getDouble(5);
				String vol = rs.getString(6);
				String change = rs.getString(7);
				StocksDB stock = new StocksDB(date, price, open, high, low, vol, change);
				stockList.add(stock);
				// print the results
				//				System.out.format("%s, %f, %f, %f, %f, %s, %s, \n", date.format(formatter), price, open, high, low, vol, change);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return stockList;
	};
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// getStockByDate From DB
	//	public static ArrayList<StocksDB> getStockByDate() {
	//		// Convert Date to LocalDate
	//		LocalDate dateStart = StockInfoPanel.dc_start.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	//		LocalDate dateEnd = StockInfoPanel.dc_end.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	//		System.out.println("dateStart = " + dateStart);
	//		ArrayList<StocksDB> stockListByDate = new ArrayList<StocksDB>();
	//		String query = "SELECT * FROM set_data WHERE Date BETWEEN '" + dateStart + "' AND '" + dateEnd + "' ORDER BY Date DESC";
	//		try (Connection con = GlobalData.getConnection();
	//			PreparedStatement pstmt = con.prepareStatement(query);
	//		    ResultSet rs = pstmt.executeQuery();) {
	//			while (rs.next()) {
	//				LocalDate date = rs.getDate(1).toLocalDate();
	//				double price = rs.getDouble(2);
	//				double open = rs.getDouble(3);
	//				double high = rs.getDouble(4);
	//				double low = rs.getDouble(5);
	//				String vol = rs.getString(6);
	//				String change = rs.getString(7);
	//				StocksDB stock = new StocksDB(date, price, open, high, low, vol, change);
	//				stockListByDate.add(stock);
	//				// print the results
	//				//				System.out.format("%s, %f, %f, %f, %f, %s, %s, \n", date.format(formatter), price, open, high, low, vol, change);
	//			}
	//
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//			System.err.println(e.getMessage());
	//			}
	//		return stockListByDate;
	//		}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		//		getAllStock();
		//		getStockByDate();
	}
}
