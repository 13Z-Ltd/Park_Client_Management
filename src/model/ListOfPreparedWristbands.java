package model;

import java.util.ArrayList;

public class ListOfPreparedWristbands {
	private int numberOfWhites;
	private int numberOfYellows;
	private int numberOfReds;

	private ArrayList<PreparedWristband> whiteWristbands;
	private ArrayList<PreparedWristband> yellowWristbands;
	private ArrayList<PreparedWristband> redWristbands;

	private int discount;
	private int amontOfDiscount;
	private int total;
	private boolean discountTime;

	public ListOfPreparedWristbands() {
		createNewList();
	};

	public void createNewList() {
		whiteWristbands = new ArrayList<PreparedWristband>();
		yellowWristbands = new ArrayList<PreparedWristband>();
		redWristbands = new ArrayList<PreparedWristband>();
		numberOfWhites = 0;
		numberOfYellows = 0;
		numberOfReds = 0;
		discount = 0;
		amontOfDiscount = 0;
		discountTime = false;
		total = 0;
		countTotal();
	}

	public int increaseWristNumber(String color) {
		if (color == "WHITE" && numberOfWhites < 30) {
			numberOfWhites++;
			whiteWristbands.add(new PreparedWristband("WHITE"));
			countTotal();
			return 1;
		}
		if (color == "YELLOW" && numberOfYellows < 30) {
			numberOfYellows++;
			yellowWristbands.add(new PreparedWristband("YELLOW"));
			countTotal();
			return 1;
		}
		if (color == "RED" && numberOfReds < 30) {
			numberOfReds++;
			redWristbands.add(new PreparedWristband("RED"));
			countTotal();
			return 1;
		}		
		return 0;
	}

	public int decreaseWristNumber(String color) {
		if (color == "WHITE" && numberOfWhites > 0) {
			numberOfWhites--;
			whiteWristbands.remove(whiteWristbands.size() - 1);
			countTotal();
			return 1;
		}
		if (color == "YELLOW" && numberOfYellows > 0) {
			numberOfYellows--;
			yellowWristbands.remove(yellowWristbands.size() - 1);
			countTotal();
			return 1;
		}
		if (color == "RED" && numberOfReds > 0) {
			numberOfReds--;
			redWristbands.remove(redWristbands.size() - 1);
			countTotal();
			return 1;
		}
		return 0;
	}

	private void countTotal() {
		total = (numberOfWhites * 3200 + numberOfYellows * 4000 + numberOfReds * 5200);
		checkDiscount();
	}

	private void checkDiscount() {
		discount = 0;
		if (numberOfReds >= 2 && (numberOfWhites + numberOfYellows) >= 2) {
			discount = 10;
		}
		if (numberOfReds >= 1 && (numberOfWhites + numberOfYellows) >= 3) {
			discount = 10;
		}
		if (discountTime) {
			discount = 20;
		}
		
		if (discount > 0) {
			double x = (total / 100) * discount;
			//x = (x / 100) * discount;
			amontOfDiscount = (int) x;
			total = total - amontOfDiscount;
		}
	}

	public int getNumberOfAllWrists() {
		return (numberOfWhites + numberOfYellows + numberOfReds);
	}
	
	public int getNumberOfEmptyWrists() {
		int counter = 0;
		//ArrayList<PreparedWristband> allWrists = whiteWristbands; allWrists.addAll(yellowWristbands); allWrists.addAll(redWristbands);
		for (PreparedWristband wristband : whiteWristbands) {
			if(!wristband.isBarCodeOn())
				counter++;
		}
		for (PreparedWristband wristband : yellowWristbands) {
			if(!wristband.isBarCodeOn())
				counter++;
		}
		for (PreparedWristband wristband : redWristbands) {
			if(!wristband.isBarCodeOn())
				counter++;
		}
		return counter;
	}

	public int getNumberOfWhites() {
		return numberOfWhites;
	}

	public void setNumberOfWhites(int numberOfWhites) {
		this.numberOfWhites = numberOfWhites;
	}

	public int getNumberOfYellows() {
		return numberOfYellows;
	}

	public void setNumberOfYellows(int numberOfYellows) {
		this.numberOfYellows = numberOfYellows;
	}

	public int getNumberOfReds() {
		return numberOfReds;
	}

	public void setNumberOfReds(int numberOfReds) {
		this.numberOfReds = numberOfReds;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getAmontOfDiscount() {
		return amontOfDiscount;
	}

	public void setAmontOfDiscount(int amontOfDiscount) {
		this.amontOfDiscount = amontOfDiscount;
	}

	public ArrayList<PreparedWristband> getWhiteWristbands() {
		return whiteWristbands;
	}

	public ArrayList<PreparedWristband> getYellowWristbands() {
		return yellowWristbands;
	}

	public ArrayList<PreparedWristband> getRedWristbands() {
		return redWristbands;
	}

	public boolean isDiscountTime() {
		return discountTime;
	}

	public void setDiscountTime(boolean discountTime) {
		this.discountTime = discountTime;
	}
}
