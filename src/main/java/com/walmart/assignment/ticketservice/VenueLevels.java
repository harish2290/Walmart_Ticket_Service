package com.walmart.assignment.ticketservice;

public enum VenueLevels {
	ORCHESTRA(1, "ORCHESTRA", 100.00, 25, 50), MAIN(2, "MAIN", 75.00, 20, 100), BALCONY1(
			3, "BALCONY 1", 50.00, 15, 100), BALCONY2(4, "BALCONY 2", 40.00,
			15, 100);
	private final int level;
	private final String levelName;
	private final double price;
	private final int rows;
	private final int numberOfSeatsPerRow;
	private int availableSeats;

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public int getLevel() {
		return level;
	}

	public String getLevelName() {
		return levelName;
	}

	public double getPrice() {
		return price;
	}

	public int getRows() {
		return rows;
	}

	public int getNumberOfSeatsPerRow() {
		return numberOfSeatsPerRow;
	}

	VenueLevels(int level, String levelName, double price, int rows,
			int numberOfSeatsPerRow) {
		this.level = level;
		this.levelName = levelName;
		this.price = price;
		this.rows = rows;
		this.numberOfSeatsPerRow = numberOfSeatsPerRow;
		this.availableSeats = rows * numberOfSeatsPerRow;

	}
}
