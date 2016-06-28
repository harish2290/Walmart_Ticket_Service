package com.walmart.assignment.ticketservice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class TicketServiceImpl implements TicketService {

	private VenueLevels[] venueLevelArray;
	private int maxLevel;
	private int minLevel;
	private Map<String, SeatHold> holdedSeats;
	private Map<String, SeatHold> reservedSeats;

	public TicketServiceImpl() {
		this.venueLevelArray = VenueLevels.values();
		findMaxLevelNumber();
		findMinLevelNumber();
		this.holdedSeats = new HashMap<String, SeatHold>();
		reservedSeats = new HashMap<String, SeatHold>();

	}

	public Map<String, SeatHold> getReservedSeats() {
		return reservedSeats;
	}

	public void setReservedSeats(Map<String, SeatHold> reservedSeats) {
		this.reservedSeats = reservedSeats;
	}

	/**
	 * The number of seats in the requested level that are neither held nor
	 * reserved
	 * 
	 * @param venueLevel
	 *            a numeric venue level identifier to limit the search
	 * @return the number of tickets available on the provided level
	 */
	public synchronized int numSeatsAvailable(Optional<Integer> venueLevel) {
		// TODO Auto-generated method stub
		if (!venueLevel.isPresent()) {
			clearSeatsAfterTimeOut();
			return findSeatsAllLevels();

		}
		if (venueLevel.isPresent()
				&& (venueLevel.get() < this.getMinLevel() || venueLevel.get() > this
						.getMaxLevel())) {
			return -1;
		} else {
			clearSeatsAfterTimeOut();
			return findAvailableSeatsForLevel(venueLevel.get());
		}

	}

	/**
	 * Find and hold the best available seats for a customer
	 * 
	 * @param numSeats
	 *            the number of seats to find and hold
	 * @param minLevel
	 *            the minimum venue level
	 * @param maxLevel
	 *            the maximum venue level
	 * @param customerEmail
	 *            unique identifier for the customer
	 * @return a SeatHold object identifying the specific seats and related
	 *         information
	 */
	public synchronized SeatHold findAndHoldSeats(int numSeats,
			Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		SeatHold seatHold = null;
		// TODO Auto-generated method stub
		int minLevelInt = minLevel.get();
		int maxLevelInt = maxLevel.get();

		if (minLevelInt > maxLevelInt) {
			int tempLevel = minLevelInt;
			minLevelInt = maxLevelInt;
			maxLevelInt = tempLevel;

		}
		clearSeatsAfterTimeOut();
		int noOfSeatsAvailableBtwLevels = findNoOfSeatsBtwLevels(minLevelInt,
				maxLevelInt);

		if (numSeats > noOfSeatsAvailableBtwLevels) {

			return null;
		} else {
			for (int i = minLevelInt; i <= maxLevelInt; i++) {
				int noOfSeatsForLevel = findAvailableSeatsForLevel(i);
				if (noOfSeatsForLevel >= numSeats) {
					this.getVenueLevelArray()[i - 1]
							.setAvailableSeats(noOfSeatsForLevel - numSeats);
					int holdId = this.randomNum();
					seatHold = new SeatHold(customerEmail, System.nanoTime(),
							numSeats, getVenueLevelArray()[i - 1], holdId);
					break;
				}

			}
			if (seatHold != null) {
				holdedSeats.put(customerEmail, seatHold);
			}
		}
		return seatHold;
	}

	/**
	 * Commit seats held for a specific customer
	 * 
	 * @param seatHoldId
	 *            the seat hold identifier
	 * @param customerEmail
	 *            the email address of the customer to which the seat hold is
	 *            assigned
	 * @return a reservation confirmation code
	 */
	public synchronized String reserveSeats(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		SeatHold seatHold = this.getHoldedSeats().get(customerEmail);
		removeReservedFromHold(seatHold);
		int levelNo = seatHold.getLevel().getLevel();
		int nofSeatsInThatLevel = this.getVenueLevelArray()[levelNo - 1]
				.getAvailableSeats();
		int totalNoOfSeatsinThatLevel = nofSeatsInThatLevel
				- seatHold.getNoOfSeats();
		this.getVenueLevelArray()[levelNo - 1]
				.setAvailableSeats(totalNoOfSeatsinThatLevel);
		String reserVationNumber = String.valueOf(randomNum());
		getReservedSeats().put(reserVationNumber, seatHold);
		return reserVationNumber;
	}

	public void findMinLevelNumber() {
		setMinLevel(VenueLevels.values()[0].getLevel());

	}

	public void findMaxLevelNumber() {
		int length = getVenueLevelArray().length;
		setMaxLevel(getVenueLevelArray()[length - 1].getLevel());
	}

	public int findAvailableSeatsForLevel(int levelNo) {
		return this.getVenueLevelArray()[levelNo - 1].getAvailableSeats();

	}

	public int findSeatsAllLevels() {
		int count = 0;
		for (int i = 0; i < getVenueLevelArray().length; i++) {
			count = count + this.getVenueLevelArray()[i].getAvailableSeats();
		}
		return count;
	}

	public VenueLevels[] getVenueLevelArray() {

		return venueLevelArray;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public Map<String, SeatHold> getHoldedSeats() {
		return holdedSeats;
	}

	public void setHoldedSeats(Map<String, SeatHold> holdedSeats) {
		this.holdedSeats = holdedSeats;
	}

	public int findNoOfSeatsBtwLevels(int minLevel, int maxlevel) {
		int count = 0;
		for (int i = minLevel; i <= maxlevel; i++) {
			count = count
					+ this.getVenueLevelArray()[i - 1].getAvailableSeats();
		}
		return count;

	}

	public void clearSeatsAfterTimeOut() {
		Iterator<Map.Entry<String, SeatHold>> it = this.getHoldedSeats()
				.entrySet().iterator();
		while (it.hasNext()) {
			SeatHold seatHoldValue = it.next().getValue();
			long timeDiff = System.nanoTime()
					- seatHoldValue.getTimeOfSeatHold();
			// System.out.println("Time Diff" +
			// NANOSECONDS.toSeconds(timeDiff));

			if (NANOSECONDS.toSeconds(timeDiff) > 180) {
				updateSeats(seatHoldValue);
				it.remove();
			}
		}
	}

	/**
	 * removes the seats from Map and updates the Seats
	 * 
	 * @paramSeatHolderValue seatHold object whose time is greater than 3 mins
	 */

	public void removeReservedFromHold(SeatHold seatHold) {
		Iterator<Map.Entry<String, SeatHold>> it = this.getHoldedSeats()
				.entrySet().iterator();
		while (it.hasNext()) {
			SeatHold seatHoldValue = it.next().getValue();
			if (seatHoldValue.equals(seatHold)) {
				updateSeats(seatHoldValue);
				it.remove();
			}

		}
	}

	/**
	 * find the hold seats before reservation
	 * 
	 * @param email
	 *            the seat hold identifier
	 * 
	 * @return a SeatHold Object
	 */

	public SeatHold findSeatHoldedWithEmail(String email) {
		SeatHold seatHolded = null;
		clearSeatsAfterTimeOut();
		if (this.getHoldedSeats().get(email) == null) {
			return null;
		} else {
			seatHolded = this.getHoldedSeats().get(email);
		}
		return seatHolded;
	}

	/**
	 * generates random Number
	 * 
	 * @return a Integer random number
	 */

	public int randomNum() {

		int val = (int) (Math.random() * 9000 + 1000);
		return val;
	}

	/**
	 * Updates the seats when time is greater than 3 mins
	 * 
	 * @paramSeatHolderValue seatHold object whose time is greater than 3 mins
	 */

	public synchronized void updateSeats(SeatHold seatHoldValue) {
		int levelNo = seatHoldValue.getLevel().getLevel();
		int nofSeatsInThatLevel = this.getVenueLevelArray()[levelNo - 1]
				.getAvailableSeats();
		int totalNoOfSeatsinThatLevel = nofSeatsInThatLevel
				+ seatHoldValue.getNoOfSeats();
		this.getVenueLevelArray()[levelNo - 1]
				.setAvailableSeats(totalNoOfSeatsinThatLevel);
	}
}
