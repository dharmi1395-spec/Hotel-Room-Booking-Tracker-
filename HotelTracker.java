
//Dharmi Rajendrabhai Gajera (3484039)

import java.util.Scanner;

public class HotelTracker 
{
     
   
    // CONSTANTS - these never change 
    // Nightly room prices
    private static final double STANDARD_PRICE  = 89.99;
    private static final double DELUXE_PRICE    = 129.99;
    private static final double SUITE_PRICE     = 219.99;

    // Add-on prices (per night)
    private static final double BREAKFAST_PRICE = 18.00;
    private static final double PARKING_PRICE   = 22.00;

    // GST is 10%, so we store it as 0.10
    private static final double GST_RATE        = 0.10;

   
    // GLOBAL STATISTICS - these used across ALL bookings
   

    private static double totalRevenue  = 0.0;  // sum of all booking costs
    private static int    totalBookings = 0;     // how many bookings have been made
    private static int    totalCheckIns = 0;     // check-ins across all guests

    // How many of each room type have been booked
    private static int standardCount = 0;
    private static int deluxeCount   = 0;
    private static int suiteCount    = 0;

    // How many bookings included each add-on
    private static int breakfastCount = 0;
    private static int parkingCount   = 0;

   
    // LAST BOOKING DETAILS  to show a summary or cancel it
   

    private static String  lastGuestName    = "";
    private static String  lastRoomType     = "";
    private static int     lastNights       = 0;
    private static boolean lastHadBreakfast = false;
    private static boolean lastHadParking   = false;
    private static double  lastTotalCost    = 0.0;
    private static int     lastCheckIns     = 0;   

    // hasLastBooking: true once at least one booking has been made
    private static boolean hasLastBooking = false;

    
    // stops user from cancelling same booking twice
    private static boolean lastWasCancelled = false;

   
    //  we save statistics BEFORE each booking
    

    private static double savedRevenue       = 0.0;
    private static int    savedBookings      = 0;
    private static int    savedCheckIns      = 0;
    private static int    savedStandardCount = 0;
    private static int    savedDeluxeCount   = 0;
    private static int    savedSuiteCount    = 0;
    private static int    savedBreakfastCount = 0;
    private static int    savedParkingCount  = 0;

   
    // Main Method - the program starts here
   

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Hotel Room Booking Tracker!");

        
        int choice = 0;
        while (choice != 8) {

            displayMenu();
            choice = readMenuChoice(scanner);

            
           switch (choice) {
    case 1:
        viewRoomTypes();
        break;

    case 2:
        bookRoom(scanner);
        break;

    case 3:
        recordCheckIn();
        break;

    case 4:
        viewBookingSummary();
        break;

    case 5:
        compareRoomTypes(scanner);
        break;

    case 6:
        simulatePromoOffer(scanner);
        break;

    case 7:
        cancelLastBooking();
        break;

    case 8:
        System.out.println("\nGoodbye! Thanks for using the Hotel Booking Tracker.");
        break;

    default:
        System.out.println("Please enter a number between 1 and 8.");
}
        }

        scanner.close();
    }

   
    // METHOD: displayMenu
    
   
    public static void displayMenu() {
        System.out.println();
        System.out.println("+------------------------------------------+");
        System.out.println("|      HOTEL ROOM BOOKING TRACKER          |");
        System.out.println("+------------------------------------------+");
        System.out.println("|  1. View Room Types                      |");
        System.out.println("|  2. Book a Room                          |");
        System.out.println("|  3. Record a Guest Check-In              |");
        System.out.println("|  4. View Booking Summary                 |");
        System.out.println("|  5. Compare Two Room Types               |");
        System.out.println("|  6. Simulate Promotional Offer           |");
        System.out.println("|  7. Cancel Last Booking                  |");
        System.out.println("|  8. Exit                                 |");
        System.out.println("+------------------------------------------+");
    }

   
    // METHOD: readMenuChoice
    
    public static int readMenuChoice(Scanner scanner) {
        System.out.print("Enter your choice: ");

        //keep running until user enetr whole number
        while (!scanner.hasNextInt()) {
            System.out.print("That's not a valid option. Enter a number (1-8): ");
            scanner.next();
        }

        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

   
    // METHOD: viewRoomTypes
    
   
    public static void viewRoomTypes () {
    
    System.out.println("=================================================================\r\n"+//
                        "RoomType     Price       Includes\r\n"+//
                        "=================================================================\r\n"+//
                        "Standard     $89.99      Room access only\r\n" + //
                        "Deluxe       $129.99     Room + minibar + early check-in\r\n" + //
                        "Suite        $219.99     Room + minibar + lounge + spa access\r\n"+//
                        "=================================================================\r\n"+//
                        "Add-ons                Price\r\n"+//
                        "=================================================================\r\n"+//
                        "Breakfast Package      $18.00 per night\r\n"+//
                        "Car Parking            $22.00 per night\r\n"+//
                        "=================================================================\r\n");
    }
    

   
    // METHOD: bookRoom
    
    public static void bookRoom(Scanner scanner) {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("  BOOK A ROOM");
        System.out.println("=======================================================");

        //  Step 1: Get the guest's name
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        // Keep asking until the name is not empty
        while (guestName.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter guest name: ");
            guestName = scanner.nextLine();
        }

        //  Step 2: Get room type 
        String roomType = askForRoomType(scanner);

        // Step 3: Get number of nights 
        int nights = askForNights(scanner);

        //  Step 4: Ask about add-ons 
        boolean hasBreakfast = false;
        boolean hasParking   = false;

        boolean wantsExtras = askYesOrNo(scanner, "Would you like to add any extras? (Y/N): ");
        if (wantsExtras) {
            hasBreakfast = askYesOrNo(scanner, "  Add Breakfast Package ($18.00/night)? (Y/N): ");
            hasParking   = askYesOrNo(scanner, "  Add Car Parking ($22.00/night)? (Y/N): ");
        }

        // Step 5: get the costs 

        // Get the room's price base on chosen room type
        double roomPrice = getRoomPrice(roomType);

        // Total add-on cost per night
        double addOnCostPerNight = 0.0;
        if (hasBreakfast) {
            addOnCostPerNight = addOnCostPerNight + BREAKFAST_PRICE;
        }
        if (hasParking) {
            addOnCostPerNight = addOnCostPerNight + PARKING_PRICE;
        }

        //  costs before tax
        double nightlyTotal = roomPrice + addOnCostPerNight;
        double subtotal     = nightlyTotal * nights;

        // GST is 10% of the subtotal
        double gst       = subtotal * GST_RATE;
        double totalCost = subtotal + gst;

        // Step 6: Print the receipt
        printReceipt(guestName, roomType, nights, hasBreakfast, hasParking,
                     roomPrice, addOnCostPerNight, nightlyTotal, subtotal, gst, totalCost);

        // Step 7: Save the current statistics  
        
        savedRevenue        = totalRevenue;
        savedBookings       = totalBookings;
        savedCheckIns       = totalCheckIns;
        savedStandardCount  = standardCount;
        savedDeluxeCount    = deluxeCount;
        savedSuiteCount     = suiteCount;
        savedBreakfastCount = breakfastCount;
        savedParkingCount   = parkingCount;

        // Step 8: Update the global statistics 
        totalRevenue  = totalRevenue + totalCost;
        totalBookings = totalBookings + 1;

        if (hasBreakfast) {
            breakfastCount = breakfastCount + 1;
        }
        if (hasParking) {
            parkingCount = parkingCount + 1;
        }

        if (roomType.equalsIgnoreCase("Standard")) {
            standardCount = standardCount + 1;
        } else if (roomType.equalsIgnoreCase("Deluxe")) {
            deluxeCount = deluxeCount + 1;
        } else {
            suiteCount = suiteCount + 1;
        }

        // Step 9: Save the details of THIS booking 
        
        lastGuestName    = guestName;
        lastRoomType     = roomType;
        lastNights       = nights;
        lastHadBreakfast = hasBreakfast;
        lastHadParking   = hasParking;
        lastTotalCost    = totalCost;
        lastCheckIns     = 0;         
        hasLastBooking   = true;
        lastWasCancelled = false;
    }

   
    // METHOD: recordCheckIn
    
    public static void recordCheckIn() {
        
        if (!hasLastBooking) {
            System.out.println("\nNo active booking found. Please make a booking first (Option 2).");
            return;
        }

        
        lastCheckIns  = lastCheckIns + 1;
        totalCheckIns = totalCheckIns + 1;

        System.out.println("\nCheck-in recorded for " + lastGuestName + ".");
        System.out.println("Total check-ins for this guest: " + lastCheckIns);
    }

   
    // METHOD: viewBookingSummary
    
    public static void viewBookingSummary() {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("  BOOKING SUMMARY");
        System.out.println("=======================================================");

        if (!hasLastBooking) {
            System.out.println("  No bookings have been made yet.");
        } else {
            //  the most recent booking's details
            System.out.println("  MOST RECENT BOOKING");
           System.out.println("-------------------------------------------------------");
            System.out.printf("  %-24s %s\n",    "Guest Name:",        lastGuestName);
            System.out.printf("  %-24s %s\n",    "Room Type:",         lastRoomType);
            System.out.printf("  %-24s %d night(s)\n", "Duration:",    lastNights);
            System.out.printf("  %-24s %s\n",    "Breakfast Package:", lastHadBreakfast ? "Yes" : "No");
            System.out.printf("  %-24s %s\n",    "Car Parking:",       lastHadParking   ? "Yes" : "No");
            System.out.printf("  %-24s $%.2f\n", "Total Cost:",        lastTotalCost);
            System.out.printf("  %-24s %d\n",    "Check-Ins:",         lastCheckIns);

            
            if (lastWasCancelled) {
                System.out.println("  ** This booking has been cancelled **");
            }
        }

        
       System.out.println("-------------------------------------------------------");
        System.out.println("  OVERALL STATISTICS");
       System.out.println("-------------------------------------------------------");
        System.out.printf("  %-28s %d\n",    "Total Bookings Made:", totalBookings);
        System.out.printf("  %-28s $%.2f\n", "Total Revenue:",       totalRevenue);
        System.out.printf("  %-28s %d\n",    "Total Check-Ins:",     totalCheckIns);

        // Average booking value 
        if (totalBookings > 0) {
            double average = totalRevenue / totalBookings;
            System.out.printf("  %-28s $%.2f\n", "Average Booking Value:", average);
        } else {
            System.out.printf("  %-28s %s\n", "Average Booking Value:", "N/A");
        }

       System.out.println("-------------------------------------------------------");
        System.out.println("  BOOKINGS BY ROOM TYPE");
        System.out.printf("  %-14s %d\n", "Standard:", standardCount);
        System.out.printf("  %-14s %d\n", "Deluxe:",   deluxeCount);
        System.out.printf("  %-14s %d\n", "Suite:",    suiteCount);

       System.out.println("-------------------------------------------------------");
        System.out.println("  ADD-ON UPTAKE");
        System.out.printf("  %-22s %d\n", "Breakfast Package:", breakfastCount);
        System.out.printf("  %-22s %d\n", "Car Parking:",       parkingCount);
        System.out.println("=======================================================");
    }

   
    // METHOD: compareRoomTypes
    
    public static void compareRoomTypes(Scanner scanner) {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("  COMPARE TWO ROOM TYPES");
        System.out.println("=======================================================");

        // ask the first room type
        System.out.println("Choose the FIRST room type:");
        String type1 = askForRoomType(scanner);

        // Ask the second room type - should be different from the first
        System.out.println("Choose the SECOND room type (must be different):");
        String type2 = askForRoomType(scanner);
        while (type2.equalsIgnoreCase(type1)) {
            System.out.println("That's the same as the first. Please choose a different room type.");
            type2 = askForRoomType(scanner);
        }

        
        int nights = askForNights(scanner);

        
        double price1 = getRoomPrice(type1);
        double price2 = getRoomPrice(type2);

        
        double subtotal1 = price1 * nights;
        double total1    = subtotal1 + subtotal1 * GST_RATE;

        double subtotal2 = price2 * nights;
        double total2    = subtotal2 + subtotal2 * GST_RATE;

        // Display the comparison
       System.out.println("-------------------------------------------------------");
        System.out.printf("  %s  -  %d night(s): $%.2f (incl. GST)\n", type1, nights, total1);
        System.out.printf("  %s  -  %d night(s): $%.2f (incl. GST)\n", type2, nights, total2);
       System.out.println("-------------------------------------------------------");

        if (total1 < total2) {
            double saving = total2 - total1;
            System.out.printf("  %s is cheaper by $%.2f.\n", type1, saving);
        } else if (total2 < total1) {
            double saving = total1 - total2;
            System.out.printf("  %s is cheaper by $%.2f.\n", type2, saving);
        } else {
            System.out.println("  Both room types cost the same for " + nights + " night(s).");
        }

        System.out.println("=======================================================");
    }

   
    // METHOD: simulatePromoOffer
    
   
    public static void simulatePromoOffer(Scanner scanner) {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("  SIMULATE PROMOTIONAL OFFER");
        System.out.println("=======================================================");

        
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        while (guestName.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter guest name: ");
            guestName = scanner.nextLine();
        }

        String roomType = askForRoomType(scanner);
        int    nights   = askForNights(scanner);

        boolean hasBreakfast = false;
        boolean hasParking   = false;

        boolean wantsExtras = askYesOrNo(scanner, "Would you like to add any extras? (Y/N): ");
        if (wantsExtras) {
            hasBreakfast = askYesOrNo(scanner, "  Add Breakfast Package ($18.00/night)? (Y/N): ");
            hasParking   = askYesOrNo(scanner, "  Add Car Parking ($22.00/night)? (Y/N): ");
        }

        
        double roomPrice         = getRoomPrice(roomType);
        double addOnCostPerNight = 0.0;
        if (hasBreakfast) {
            addOnCostPerNight = addOnCostPerNight + BREAKFAST_PRICE;
        }
        if (hasParking) {
            addOnCostPerNight = addOnCostPerNight + PARKING_PRICE;
        }

        double nightlyTotal = roomPrice + addOnCostPerNight;
        double subtotal     = nightlyTotal * nights;

        // randomly pick one offer from any three promotional offer
        // Math.random() gives a random number 
        
        double randomNumber = Math.random();

        String promoName     = "";
        double promoDiscount = 0.0;

        if (randomNumber < 1.0 / 3.0) {
            // Offer 1: First Night Free
            // applies if  guest stays for 2 or more nights
            promoName = "First Night Free";
            if (nights >= 2) {
                promoDiscount = roomPrice; 
            } else {
                System.out.println("\n[Promo] \"First Night Free\" was selected, but requires 2+ nights.");
                System.out.println("        No discount applied.");
            }

        } else if (randomNumber < 2.0 / 3.0) {
            // Offer 2: 20% Off Total
            promoName     = "20% Off Total";
            promoDiscount = subtotal * 0.20;

        } else {
            // Offer 3: Free Parking for 2 Nights
            // applies if guest chosse parking 
            promoName = "Free Parking for 2 Nights";
            if (hasParking) {
                
                int freeParkingNights = 2;
                if (nights < 2) {
                    freeParkingNights = nights;
                }
                promoDiscount = PARKING_PRICE * freeParkingNights;
            } else {
                System.out.println("\n[Promo] \"Free Parking for 2 Nights\" was selected, but no parking was added.");
                System.out.println("        No discount applied.");
            }
        }

       
        double discountedSubtotal = subtotal - promoDiscount;

        //subtotal should not go below 0
        if (discountedSubtotal < 0.0) {
            discountedSubtotal = 0.0;
        }

        double gst       = discountedSubtotal * GST_RATE;
        double totalCost = discountedSubtotal + gst;

        // print receipt
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("         HOTEL ROOM BOOKING CONFIRMATION");
        System.out.println("         [PROMOTIONAL OFFER: " + promoName + "]");
        System.out.println("=======================================================");
        System.out.printf("  %-20s %s\n",            "Guest Name :",   guestName);
        System.out.printf("  %-20s %s ($%.2f/night)\n", "Room Type :", roomType, roomPrice);
        System.out.printf("  %-20s %d nights\n",     "Duration :",     nights);
        System.out.println("  Add-Ons :");
        System.out.printf("    - Breakfast Package : %s\n", hasBreakfast ? "Yes ($18.00/night)" : "No");
        System.out.printf("    - Car Parking       : %s\n", hasParking   ? "Yes ($22.00/night)" : "No");
       System.out.println("-------------------------------------------------------");
        System.out.printf("  %-22s $%.2f\n", "Nightly Total :",         nightlyTotal);
        System.out.printf("  %-22s $%.2f\n", "Subtotal (" + nights + " nts) :", subtotal);

        if (promoDiscount > 0.0) {
            System.out.printf("  %-22s -$%.2f\n", "Promo Discount :",    promoDiscount);
            System.out.printf("  %-22s $%.2f\n",  "After Discount :",    discountedSubtotal);
        }

        System.out.printf("  %-22s $%.2f\n", "GST (10%) :", gst);
       System.out.println("-------------------------------------------------------");
        System.out.printf("  %-22s $%.2f\n", "TOTAL :", totalCost);
        System.out.println("=======================================================");

    
        savedRevenue        = totalRevenue;
        savedBookings       = totalBookings;
        savedCheckIns       = totalCheckIns;
        savedStandardCount  = standardCount;
        savedDeluxeCount    = deluxeCount;
        savedSuiteCount     = suiteCount;
        savedBreakfastCount = breakfastCount;
        savedParkingCount   = parkingCount;

        totalRevenue  = totalRevenue + totalCost;
        totalBookings = totalBookings + 1;

        if (hasBreakfast) {
            breakfastCount = breakfastCount + 1;
        }
        if (hasParking) {
            parkingCount = parkingCount + 1;
        }

        if (roomType.equalsIgnoreCase("Standard")) {
            standardCount = standardCount + 1;
        } else if (roomType.equalsIgnoreCase("Deluxe")) {
            deluxeCount = deluxeCount + 1;
        } else {
            suiteCount = suiteCount + 1;
        }

        lastGuestName    = guestName;
        lastRoomType     = roomType;
        lastNights       = nights;
        lastHadBreakfast = hasBreakfast;
        lastHadParking   = hasParking;
        lastTotalCost    = totalCost;
        lastCheckIns     = 0;
        hasLastBooking   = true;
        lastWasCancelled = false;
    }

   
    // METHOD: cancelLastBooking
    
    public static void cancelLastBooking() {
        // Can't cancel if no booking has been made
        if (!hasLastBooking) {
            System.out.println("\nNo booking available to cancel.");
            return;
        }

        
        if (lastWasCancelled) {
            System.out.println("\nThe last booking has already been cancelled.");
            return;
        }

        // Restore all statistics 
        totalRevenue   = savedRevenue;
        totalBookings  = savedBookings;
        totalCheckIns  = savedCheckIns;
        standardCount  = savedStandardCount;
        deluxeCount    = savedDeluxeCount;
        suiteCount     = savedSuiteCount;
        breakfastCount = savedBreakfastCount;
        parkingCount   = savedParkingCount;

        
        lastWasCancelled = true;

        System.out.println();
        System.out.println("=======================================================");
        System.out.println("  BOOKING CANCELLED");
        System.out.println("-------------------------------------------------------");
        System.out.println("  The booking for " + lastGuestName + " has been cancelled.");
        System.out.println("  Room type: " + lastRoomType + " | Nights: " + lastNights);
        System.out.println("  All statistics have been reverted.");
        System.out.println("=======================================================");
    }

   
    // METHOD: printReceipt
    
   
    public static void printReceipt(String guestName, String roomType, int nights,
                                    boolean hasBreakfast, boolean hasParking,
                                    double roomPrice, double addOnCostPerNight,
                                    double nightlyTotal, double subtotal,
                                    double gst, double totalCost) {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println("         HOTEL ROOM BOOKING CONFIRMATION");
        System.out.println("=======================================================");
        System.out.printf("  %-20s %s\n",               "Guest Name :",   guestName);
        System.out.printf("  %-20s %s ($%.2f/night)\n", "Room Type :",    roomType, roomPrice);
        System.out.printf("  %-20s %d nights\n",         "Duration :",    nights);
        System.out.println("  Add-Ons :");
        System.out.printf("    - Breakfast Package : %s\n", hasBreakfast ? "Yes ($18.00/night)" : "No");
        System.out.printf("    - Car Parking       : %s\n", hasParking   ? "Yes ($22.00/night)" : "No");
       System.out.println("-------------------------------------------------------");
        System.out.printf("  %-22s $%.2f\n", "Nightly Total :",            nightlyTotal);
        System.out.printf("  %-22s $%.2f\n", "Subtotal (" + nights + " nts) :", subtotal);
        System.out.printf("  %-22s $%.2f\n", "GST (10%) :",                gst);
       System.out.println("-------------------------------------------------------");
        System.out.printf("  %-22s $%.2f\n", "TOTAL :", totalCost);
        System.out.println("=======================================================");
    }

   
    // METHOD: getRoomPrice
   
   
    public static double getRoomPrice(String roomType) {
        if (roomType.equalsIgnoreCase("Deluxe")) {
            return DELUXE_PRICE;
        } else if (roomType.equalsIgnoreCase("Suite")) {
            return SUITE_PRICE;
        } else {
            return STANDARD_PRICE; // set Standard as the default
        }
    }

   
    // METHOD: askForRoomType
    
   
    public static String askForRoomType(Scanner scanner) {
        System.out.print("Enter room type (Standard / Deluxe / Suite): ");
        String input = scanner.nextLine();

        // Keep asking until the input enters one of the three room types
        while (!input.equalsIgnoreCase("Standard")
            && !input.equalsIgnoreCase("Deluxe")
            && !input.equalsIgnoreCase("Suite")) {
            System.out.print("Invalid room type. Please enter Standard, Deluxe, or Suite: ");
            input = scanner.nextLine();
        }

        
        if (input.equalsIgnoreCase("Standard")) {
            return "Standard";
        } else if (input.equalsIgnoreCase("Deluxe")) {
            return "Deluxe";
        } else {
            return "Suite";
        }
    }

   
    // METHOD: askForNights
    
   
    public static int askForNights(Scanner scanner) {
        System.out.print("Enter number of nights (1-30): ");

        // ask for whole number 
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a whole number between 1 and 30: ");
            scanner.next(); 
        }

        int nights = scanner.nextInt();
        scanner.nextLine(); //clear lines

        // must enter between 1 to 30
        while (nights < 1 || nights > 30) {
            System.out.print("Number of nights must be between 1 and 30. Try again: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a whole number between 1 and 30: ");
                scanner.next();
            }
            nights = scanner.nextInt();
            scanner.nextLine();
        }

        return nights;
    }

   
    // METHOD: askYesOrNo
    
   
    public static boolean askYesOrNo(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();

        // Keep asking until user enter Y/N
        while (!input.equalsIgnoreCase("Y")   && !input.equalsIgnoreCase("N")
            && !input.equalsIgnoreCase("Yes") && !input.equalsIgnoreCase("No")) {
            System.out.print("Please enter Y or N: ");
            input = scanner.nextLine();
        }

        
        return input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("Yes");
    }

   
}
