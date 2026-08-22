//Dharmi Rajendrabhai Gajera (c 3484039)
//Bhargav Dharmeshbhai Patadia (c3440219)


import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class HotelSystemUI
{
    private Scanner scanner;

    private HotelProperty[] properties;

    private int propertyCount;

    private int currentPropertyIndex;

    private String[] roomNames = new String[3];
    private double[] roomPrices = new double[3];
    private String[] roomFeatures = new String[3];

    private int roomCount;

//CONSTRUCTOR

    public HotelSystemUI()
    {
        scanner = new Scanner(System.in);

        properties = new HotelProperty[3];

        propertyCount = 0;

        currentPropertyIndex = 0;
    }




//START SYSTEM
public void startSystem()
{
    // Create the first property automatically
    properties[0] = new HotelProperty(101, "Main Hotel");
    propertyCount = 1;
    currentPropertyIndex = 0;

    loadRoomTypes();

    int choice = 0;

    do
    {
        displayMainMenu();

        try
        {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice)
            {
                case 1:
                    manageProperties();
                    break;

                case 2:
                    viewRoomTypes();
                    break;

                case 3:
                    bookRoom();
                    break;

                case 4:
                    recordGuestCheckIn();
                    break;

                case 5:
                    viewBookingSummary();
                    break;

                case 6:
                    compareRoomTypes();
                    break;

                case 7:
                    simulatePromotionalOffer();
                    break;

                case 8:
                    modifyLastBooking();
                    break;

                case 9:
                    cancelLastBooking();
                    break;

                case 10:
                    viewAllBookings();
                    break;

                case 11:
                    savePropertyData();
                    break;

                case 12:
                    loadPropertyData();
                    break;

                case 13:
                    System.out.println("Thank you for using the Hotel Booking System.");
                    break;

                default:
                    System.out.println("Invalid menu choice.");
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }

    } while (choice != 13);
}

//DISPLAY MENU 
public void displayMainMenu()
{
    System.out.println();
    System.out.println("========================================");
    System.out.println("      HOTEL ROOM BOOKING SYSTEM");
    System.out.println("========================================");

    if (propertyCount > 0)
    {
        System.out.println("Current Property: "
                + properties[currentPropertyIndex].getPropertyName());
    }

    System.out.println("----------------------------------------");
    System.out.println("1. Manage Properties");
    System.out.println("2. View Room Types");
    System.out.println("3. Book a Room");
    System.out.println("4. Record a Guest Check-In");
    System.out.println("5. View Booking Summary");
    System.out.println("6. Compare Two Room Types");
    System.out.println("7. Simulate Promotional Offer");
    System.out.println("8. Modify Last Booking");
    System.out.println("9. Cancel Last Booking");
    System.out.println("10. View All Bookings");
    System.out.println("11. Save Property Data");
    System.out.println("12. Load Property Data");
    System.out.println("13. Exit");
    System.out.println("----------------------------------------");
}

//METHOD: MANAGE PROPERTIES

public void manageProperties()
{
    int choice = 0;

    do
    {
        System.out.println();
        System.out.println("========== Manage Properties ==========");
        System.out.println("1. Create New Property");
        System.out.println("2. Select Property");
        System.out.println("3. View All Properties");
        System.out.println("4. Return to Main Menu");

        try
        {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice)
            {
                case 1:
                    createProperty();
                    break;

                case 2:
                    selectProperty();
                    break;

                case 3:
                    viewProperties();
                    break;

                case 4:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }

    } while (choice != 4);
}

//METHOD : CREATE PROPERTY

public void createProperty()
{
    if (propertyCount >= properties.length)
    {
        System.out.println("Maximum number of properties reached.");
        return;
    }

    System.out.print("Enter Property ID: ");
    int propertyId = scanner.nextInt();
    scanner.nextLine();

    System.out.print("Enter Property Name: ");
    String propertyName = scanner.nextLine();

    Random random = new Random();

    // Check for duplicate ID
     boolean duplicate;

        do
        {
            duplicate = false;

            for(int i = 0; i < propertyCount; i++)
            {
                if(properties[i].getPropertyId() == propertyId)
                {
                    duplicate = true;
                    propertyId = random.nextInt(900) + 100;
                }
            }

        } while(duplicate);

        properties[propertyCount] =
                new HotelProperty(propertyId, propertyName);

        propertyCount++;

        System.out.println("Property created successfully.");
    }

// METHOD: SELECT PROPERTY

public void selectProperty()
{
    if (propertyCount == 0)
    {
        System.out.println("No properties available.");
        return;
    }

    System.out.print("Enter Property ID: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    for (int i = 0; i < propertyCount; i++)
    {
        if (properties[i].getPropertyId() == id)
        {
            currentPropertyIndex = i;

            System.out.println("Current property changed to: "
                    + properties[i].getPropertyName());

            return;
        }
    }

    System.out.println("Property not found.");
}

//METHOD: VIEW PROPERTY

public void viewProperties()
{
    if (propertyCount == 0)
    {
        System.out.println("No properties available.");
        return;
    }

    System.out.println();
    System.out.println("========== Properties ==========");

    for (int i = 0; i < propertyCount; i++)
    {
        System.out.print("Property ID: "
                + properties[i].getPropertyId());

        System.out.print(" | Name: "
                + properties[i].getPropertyName());

        if (i == currentPropertyIndex)
        {
            System.out.print("  <-- Current");
        }

        System.out.println();
    }
}

//METHOD: LOAD ROOM TYPE

public void loadRoomTypes()
{
    roomCount = 0;

    try
    {
        Scanner file = new Scanner(new File("rooms.txt"));

        while (file.hasNextLine() && roomCount < roomNames.length)
        {
            String line = file.nextLine();

            String[] data = line.split(",");

            roomNames[roomCount] = data[0].trim();
            roomPrices[roomCount] = Double.parseDouble(data[1].trim());
            roomFeatures[roomCount] = data[2].trim();

            roomCount++;
        }

        file.close();

        System.out.println("Room types loaded successfully.");
    }
    catch (FileNotFoundException e)
    {
        System.out.println("rooms.txt not found.");
        System.out.println("Loading default room types...");

        roomNames[0] = "Standard";
        roomPrices[0] = 89.99;
        roomFeatures[0] = "Room access only";

        roomNames[1] = "Deluxe";
        roomPrices[1] = 129.99;
        roomFeatures[1] = "Room + minibar + early check-in";

        roomNames[2] = "Suite";
        roomPrices[2] = 219.99;
        roomFeatures[2] = "Room + minibar + lounge + spa access";

        roomCount = 3;
    }
}

// METHOD: VIEW ROOM TYPE
public void viewRoomTypes()
{
    System.out.println();
    System.out.println("========== Room Types ==========");

    for (int i = 0; i < roomCount; i++)
    {
        System.out.println("Room Type : " + roomNames[i]);
        System.out.printf("Price     : $%.2f per night%n", roomPrices[i]);
        System.out.println("Features  : " + roomFeatures[i]);
        System.out.println("--------------------------------------");
    }
}

//METHOD: BOOK ROOM

public void bookRoom()
{
    if (propertyCount == 0)
    {
        System.out.println("No property available.");
        return;
    }

    try
    {
        System.out.println("\n========== Book a Room ==========");

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        // Check duplicate booking ID
        HotelProperty currentProperty = properties[currentPropertyIndex];

        if (currentProperty.bookingIdExists(bookingId))
        {
            bookingId = (int)(Math.random() * 9000) + 1000;

            System.out.println("Duplicate Booking ID.");
            System.out.println("New Booking ID: " + bookingId);
        }

        System.out.print("Enter Guest Name: ");
        String guestName = scanner.nextLine();

        System.out.println("\nAvailable Room Types");

        for (int i = 0; i < roomCount; i++)
        {
            System.out.println((i + 1) + ". "
                    + roomNames[i]
                    + " ($"
                    + roomPrices[i]
                    + ")");
        }

        int roomChoice;

        do
        {
            System.out.print("Choose Room Type (1-3): ");
            roomChoice = scanner.nextInt();

        } while (roomChoice < 1 || roomChoice > roomCount);

        scanner.nextLine();

        String roomType = roomNames[roomChoice - 1];

        double roomPrice = roomPrices[roomChoice - 1];

        System.out.print("Enter Number of Nights: ");
        int nights = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Breakfast (true/false): ");
        boolean breakfast = scanner.nextBoolean();

        System.out.print("Parking (true/false): ");
        boolean parking = scanner.nextBoolean();

        scanner.nextLine();

        double addOnsCost = 0;

        if (breakfast)
        {
            addOnsCost += nights * 20;
        }

        if (parking)
        {
            addOnsCost += nights * 15;
        }

        double subtotal =
                (roomPrice * nights) + addOnsCost;

        double tax = subtotal * 0.10;

        double totalCost = subtotal + tax;

        Booking booking =
                new Booking(
                        bookingId,
                        guestName,
                        roomType,
                        nights,
                        breakfast,
                        parking,
                        addOnsCost,
                        subtotal,
                        tax,
                        totalCost);

        boolean added =
                properties[currentPropertyIndex].addBooking(booking);

        if (added)
        {
            System.out.println("\n========== Booking Successful ==========");
            System.out.println("Booking ID : " + bookingId);
            System.out.println("Guest      : " + guestName);
            System.out.println("Room       : " + roomType);
            System.out.println("Nights     : " + nights);
            System.out.println("Breakfast  : " + breakfast);
            System.out.println("Parking    : " + parking);
            System.out.println("Add-ons    : $" + addOnsCost);

            System.out.printf("Subtotal   : $%.2f%n", subtotal);
            System.out.printf("Tax        : $%.2f%n", tax);
            System.out.printf("Total      : $%.2f%n", totalCost);
        }
        else
        {
            System.out.println("Booking limit reached.");
        }

    }
    catch (InputMismatchException e)
    {
        System.out.println("Invalid input.");
        scanner.nextLine();
    }
}

//RECORD GUEST CHECK IN

public void recordGuestCheckIn()
{
    if (propertyCount == 0)
    {
        System.out.println("No property available.");
        return;
    }

    if (properties[currentPropertyIndex].getBookingCount() == 0)
    {
        System.out.println("No bookings available.");
        return;
    }

    try
    {
        System.out.println("\n========== Record Guest Check-In ==========");

        System.out.print("Enter Booking ID: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        properties[currentPropertyIndex].recordGuestCheckIn(bookingId);
    }
    catch (InputMismatchException e)
    {
        System.out.println("Invalid Booking ID.");
        scanner.nextLine();
    }
}

//VIEW BOOKING SUMMARY

public void viewBookingSummary()
{
    if (propertyCount == 0)
    {
        System.out.println("No properties available.");
        return;
    }

    System.out.println("\n========== Booking Summary ==========");

    // Display summary for every property
    for (int i = 0; i < propertyCount; i++)
    {
        properties[i].displaySummary();
        System.out.println();
    }

    // Display last booking for current property
    Booking lastBooking = properties[currentPropertyIndex].getLastBooking();

    if (lastBooking != null)
    {
        System.out.println("===== Most Recent Booking =====");

        System.out.println("Booking ID : "
                + lastBooking.getBookingId());

        System.out.println("Guest Name : "
                + lastBooking.getGuestName());

        System.out.println("Room Type  : "
                + lastBooking.getRoomType());

        System.out.println("Nights     : "
                + lastBooking.getNights());

        System.out.println("Breakfast  : "
                + lastBooking.getHasBreakfast());

        System.out.println("Parking    : "
                + lastBooking.getHasParking());

        System.out.printf("Total Cost : $%.2f%n",
                lastBooking.getTotalCost());

        System.out.println("Check-Ins  : "
                + lastBooking.getCheckInCount());
    }
    else
    {
        System.out.println("No bookings for current property.");
    }
}

//COMPARE ROOM TYPE

public void compareRoomTypes()
{
    if (roomCount < 2)
    {
        System.out.println("Not enough room types available.");
        return;
    }

    try
    {
        System.out.println("\n========== Compare Room Types ==========");

        for (int i = 0; i < roomCount; i++)
        {
            System.out.println((i + 1) + ". "
                    + roomNames[i]
                    + " ($"
                    + roomPrices[i]
                    + ")");
        }

        System.out.print("Enter First Room (name or number): ");
        String firstInput = scanner.nextLine();

        System.out.print("Enter Second Room (name or number): ");
        String secondInput = scanner.nextLine();

        int first = -1;
        int second = -1;

        for (int i = 0; i < roomCount; i++)
        {
            if (firstInput.equalsIgnoreCase(roomNames[i]) || firstInput.equals(String.valueOf(i + 1)))
            {
                first = i;
            }

            if (secondInput.equalsIgnoreCase(roomNames[i]) || secondInput.equals(String.valueOf(i + 1)))
            {
                second = i;
            }
        }

        if (first == -1 || second == -1)
        {
            System.out.println("Invalid room selection.");
            return;
        }

        System.out.println();

        System.out.println("Room 1 : " + roomNames[first - 1]);
        System.out.printf("Price  : $%.2f%n",roomPrices[first - 1]);
        System.out.println("Features : " + roomFeatures[first - 1]);

        System.out.println();

        System.out.println("Room 2 : " + roomNames[second - 1]);
        System.out.printf("Price  : $%.2f%n", roomPrices[second - 1]);
        System.out.println("Features : " + roomFeatures[second - 1]);

        System.out.println();

        if (roomPrices[first - 1] > roomPrices[second - 1])
        {
            System.out.println(roomNames[first - 1]+ " is more expensive.");
        }
        else if (roomPrices[first - 1] < roomPrices[second - 1])
        {
            System.out.println(roomNames[second - 1] + " is more expensive.");
        }
        else
        {
            System.out.println("Both room types have the same price.");
        }
    }
    catch (InputMismatchException e)
    {
        System.out.println("Invalid input.");
        scanner.nextLine();
    }
}


// METHOD: SIMULATE PROMOTIONAL OFFER
public void simulatePromotionalOffer()
{
    try
    {
        System.out.println("\n========== Promotional Offer ==========");

        for (int i = 0; i < roomCount; i++)
        {
            System.out.println((i + 1)
                    + ". "
                    + roomNames[i]
                    + " ($"
                    + roomPrices[i]
                    + ")");
        }

        System.out.print("Choose Room Type: ");
        int roomChoice = scanner.nextInt();

        if (roomChoice < 1 || roomChoice > roomCount)
        {
            System.out.println("Invalid room selection.");
            return;
        }

        System.out.print("Enter Number of Nights: ");
        int nights = scanner.nextInt();

        scanner.nextLine();

        double originalPrice =
                roomPrices[roomChoice - 1] * nights;

        double discount =
                originalPrice * 0.15;

        double promotionalPrice =
                originalPrice - discount;

        System.out.println();

        System.out.println("Room Type : "
                + roomNames[roomChoice - 1]);

        System.out.printf("Original Price      : $%.2f%n",
                originalPrice);

        System.out.printf("Discount (15%%)      : $%.2f%n",
                discount);

        System.out.printf("Promotional Price   : $%.2f%n",
                promotionalPrice);
    }
    catch (InputMismatchException e)
    {
        System.out.println("Invalid input.");
        scanner.nextLine();
    }
}

// METHOD: MODIFY LAST BOOKING

public void modifyLastBooking()
{
    if (properties[currentPropertyIndex].getBookingCount() == 0)
    {
        System.out.println("No bookings available.");
        return;
    }

    try
    {
        System.out.println("\n========== Modify Last Booking ==========");

        System.out.println("Available Room Types");

        for (int i = 0; i < roomCount; i++)
        {
            System.out.println((i + 1) + ". "
                    + roomNames[i]
                    + " ($"
                    + roomPrices[i]
                    + ")");
        }

        System.out.print("Choose New Room Type: ");
        int roomChoice = scanner.nextInt();

        if (roomChoice < 1 || roomChoice > roomCount)
        {
            System.out.println("Invalid room type.");
            return;
        }

        System.out.print("Enter New Number of Nights: ");
        int nights = scanner.nextInt();

        scanner.nextLine();

        String roomType = roomNames[roomChoice - 1];
        double roomPrice = roomPrices[roomChoice - 1];

        double subtotal = roomPrice * nights;
        double tax = subtotal * 0.10;
        double totalCost = subtotal + tax;

        properties[currentPropertyIndex].modifyLastBooking(
                roomType,
                nights,
                subtotal,
                tax,
                totalCost);

        Booking updated = properties[currentPropertyIndex].getLastBooking();

        System.out.println("\n========== Booking Updated ==========");
        System.out.println("Booking ID : " + updated.getBookingId());
        System.out.println("Guest      : " + updated.getGuestName());
        System.out.println("Room       : " + updated.getRoomType());
        System.out.println("Nights     : " + updated.getNights());
        System.out.println("Breakfast  : " + updated.getHasBreakfast());
        System.out.println("Parking    : " + updated.getHasParking());

        System.out.printf("Subtotal   : $%.2f%n", updated.getSubtotal());
        System.out.printf("Tax        : $%.2f%n", updated.getTax());
        System.out.printf("Total      : $%.2f%n", updated.getTotalCost());
    }
    catch (InputMismatchException e)
    {
        System.out.println("Invalid input.");
        scanner.nextLine();
    }
}

//METHOD: CANCEL LAST BOOKING

public void cancelLastBooking()
{
    if (propertyCount == 0)
    {
        System.out.println("No properties available.");
        return;
    }

    if (properties[currentPropertyIndex].getBookingCount() == 0)
    {
        System.out.println("No bookings available.");
        return;
    }

    properties[currentPropertyIndex].cancelLastBooking();
}

// METHOD: VIEW ALL BOOKING

public void viewAllBookings()
{
    if (propertyCount == 0)
    {
        System.out.println("No properties available.");
        return;
    }

    properties[currentPropertyIndex].viewAllBookings();
}

//METHOD: SAVE PROPERTY DATA

public void savePropertyData()
{
    if (propertyCount == 0)
    {
        System.out.println("No properties available.");
        return;
    }

    System.out.print("Enter file name to save: ");
    String fileName = scanner.nextLine();

    properties[currentPropertyIndex].savePropertyData(fileName);
}

//METHOD: LOAD PROPERTY DATA

public void loadPropertyData()
{
    if (propertyCount == 0)
    {
        System.out.println("No properties available.");
        return;
    }

    System.out.print("Enter file name to load: ");
    String fileName = scanner.nextLine();

    properties[currentPropertyIndex].loadPropertyData(fileName);
}


    public static void main(String[] args)
    {
        HotelSystemUI hotel = new HotelSystemUI();

        hotel.startSystem();
    }

}    