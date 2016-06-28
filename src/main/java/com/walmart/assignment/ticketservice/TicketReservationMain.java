package com.walmart.assignment.ticketservice;

import static java.lang.System.out;

import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Ticket Reservation
 * 
 */
public class TicketReservationMain {
	public static void main(String[] args) {
		out.println("Welcome to Walmart Ticket Reservation System");
		out.println("Bekow Are options");
		out.println("1)Find the number of seats available within the venue, optionally by seating level");
		out.println("2)Find and hold the best available seats");
		out.println("3)Reserve Seats");
		out.println("4)Exit");
		TicketService ticketService = new TicketServiceImpl();
		Scanner sc = new Scanner(System.in);
		int optionEntered = 0;
		try {
			optionEntered = sc.nextInt();
			while (optionEntered != 4) {
				switch (optionEntered)

				{

				case 1:

					out.println("Please enter in which level you need to find the seats");
					try {
						int levelEnetered = sc.nextInt();

						Optional<Integer> levelEneteredOptionalType = Optional
								.ofNullable(levelEnetered);
						out.println(ticketService
								.numSeatsAvailable(levelEneteredOptionalType));
						out.println("please enter your opton again");

						optionEntered = sc.nextInt();
					} catch (InputMismatchException e) {
						out.println("Please enter valid Input");
						optionEntered = 4;

					}
					break;
				case 2:
					out.println("Please enter in which level you need to Hold the seats");
					SeatHold holdedSeat = holdInputData(sc, ticketService);
					if (holdedSeat != null)
						out.println(holdedSeat.toString());
					else
						out.println("No Seats Available");
					out.println("please enter your opton again");
					try {
						optionEntered = sc.nextInt();
					} catch (InputMismatchException e) {
						out.println("Please enter valid Input");
						optionEntered = 4;
					}
					break;
				case 3:
					out.println("Please enter Email Id the seat is held");
					String emailIdForReserve = sc.next();
					SeatHold seatHold = ticketService
							.findSeatHoldedWithEmail(emailIdForReserve);
					if (seatHold == null) {
						seatHold = holdInputData(sc, ticketService);

					}
					if (seatHold != null)
						System.out.println("Your reservation Number is"
								+ ticketService.reserveSeats(
										seatHold.getHoldId(),
										seatHold.getEmailId()));
					else
						out.println("No Seats Available");
					out.println("please enter your opton ");
					try {
						optionEntered = sc.nextInt();
					} catch (InputMismatchException e) {
						out.println("Please enter valid Input");
						optionEntered = 4;
					}
					break;
				}
			}

		} catch (InputMismatchException e) {
			out.println("Please enter valid Input");

		}

	}

	/**
	 * Method to take input from user and holds the available seats
	 * 
	 * @param sc
	 * @param ticketService
	 * @return SeatHold object which seat is holded
	 */
	public static SeatHold holdInputData(Scanner sc, TicketService ticketService) {
		SeatHold holdedSeat = null;
		try {
			out.println("Enter number of seats");
			int numberOfSeats = sc.nextInt();
			out.println("Enter minimum level");
			int minLevel = sc.nextInt();

			Optional<Integer> minLevelOptionalType = Optional
					.ofNullable(minLevel);
			out.println("Enter Maximum level");
			int maxLevel = sc.nextInt();
			Optional<Integer> maxLevelOptionalType = Optional
					.ofNullable(maxLevel);
			out.println("Enter EmailId ");
			String emailId = sc.next();

			holdedSeat = ticketService.findAndHoldSeats(numberOfSeats,
					minLevelOptionalType, maxLevelOptionalType, emailId);
		} catch (InputMismatchException e) {
			out.println("Please enter valid Input");

		}
		return holdedSeat;
	}
}
