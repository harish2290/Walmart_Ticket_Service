# Walmart Ticket Service Assignment

This ticket service allows user to create multiple holds and reservations for one customer. Also this service will hold any available seat within levels user provided. If available seats are not enough in one level, then hold available seats in next level. There is no limit on number of holding nor on number of seat to hold. This service will return seat hold details like email id, level & number of seats.

#1. Build Project By Maven

Run below command inside of this project folder, Walmart-ticket-service.jar will be created under ./target inside of project directory

$ mvn clean install

#2. Run the Application

Once the jar is created please run the below command 

$ java -cp Walmart-ticket-service.jar com.walmart.assignment.ticketservice.TicketReservationMain

#3. Assumptions 

Once the above command is executed we will display 4 options.

    1)Find the number of seats available within the venue, optionally by seating level
    2)Find and hold the best available seats
    3)Reserve Seats
    4)Exit
    
On Entering option 1 we will prompt the user in which level they need to check the availability i.e., from 1 to 4.

Once the level is entered we will display number of seats available in that particular level which are neither held nor reserved.

If option 2 is entered details like number of seats to hold, minimum, maximum levels and email id are entered. 
Once all the details are captured we will first check the requested number of seats are available in minimum level & if available we will proceed further to hold seats.If not we will continue the process to the next level until we reach to the maximum level entered by the user. These seats are held for 3mins.

If option 3 is entered we will go ahead and reserve the seats based on the email id entered by the user. If entered email id is not found in the holded seats we will again request the user to provide the details to reserve the seats.If the email id matches with the held email id then seats are reserved and reservation number is generated.

If option 4 is entered we will exit the user from the application.


