package com.walmart.assignment.ticketservice;

public class SeatHold { 
	private String emailId;
	private VenueLevels level;

	private int noOfSeats;
	private Long timeOfSeatHold;
	private int holdId;

	SeatHold(String emailId, Long timeOfSeatHold, int noOfSeats,
			VenueLevels level, int holdId) {
		this.emailId = emailId;
		this.timeOfSeatHold = timeOfSeatHold;
		this.noOfSeats = noOfSeats;
		this.level = level;
		this.holdId = holdId;

	}

	public int getHoldId() {
		return holdId;
	}

	public void setHoldId(int holdId) {
		this.holdId = holdId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public VenueLevels getLevel() {
		return level;
	}

	public void setLevel(VenueLevels level) {
		this.level = level;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public Long getTimeOfSeatHold() {
		return timeOfSeatHold;
	}

	public void setTimeOfSeatHold(Long timeOfSeatHold) {
		this.timeOfSeatHold = timeOfSeatHold;
	}

	public boolean equals(SeatHold seatHold) {
		return this.getEmailId().equals(seatHold.getEmailId());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EmailId=").append(getEmailId()).append("no Of Seats=")
				.append(getNoOfSeats()).append("Level In Venue=")
				.append(getLevel().getLevelName());
		return sb.toString();
	}

}
