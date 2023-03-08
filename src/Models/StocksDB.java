package Models;

import java.time.LocalDate;

public class StocksDB {
	public LocalDate date;
	public double price;
	public double open;
	public double high;
	public double low;
	public String vol;
	public String change;
	public StocksDB() {
		// TODO Auto-generated constructor stub
	}
	// Date	Price	Open	High	Low	Vol	Change%
	public StocksDB(LocalDate xDate, double xPrice, double xOpen, double xHigh, double xLow, String xVol, String xChange) {
		date = xDate;
		price = xPrice;
		open = xOpen;
		high = xHigh;
		low = xLow;
		vol = xVol;
		change = xChange;
	}
}
