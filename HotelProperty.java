//Dharmi Rajendrabhai Gajera (c 3484039)
//Bhargav Dharmeshbhai Patadia (c3440219)


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HotelProperty
{
    private int propertyId;
    private String propertyName;

    private Booking[] bookings;
    private int bookingCount;

    private double totalRevenue;
    private int totalCheckIns;

    private int standardCount;
    private int deluxeCount;
    private int suiteCount;

    private int shortStayCount;
    private int midStayCount;
    private int longStayCount;

    private int breakfastCount;
    private int parkingCount;
    private int checkInCount;

    public HotelProperty(int propertyId, String propertyName)
    {
        this.propertyId = propertyId;
        this.propertyName = propertyName;

        bookings = new Booking[15];
        bookingCount = 0;

        totalRevenue = 0;
        totalCheckIns = 0;

        standardCount = 0;
        deluxeCount = 0;
        suiteCount = 0;

        shortStayCount = 0;
        midStayCount = 0;
        longStayCount = 0;

        breakfastCount = 0;
        parkingCount = 0;
    }

    public int getPropertyId()
    {
        return propertyId;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public int getBookingCount()
    {
        return bookingCount;
    }

    public double getTotalRevenue()
    {
        return totalRevenue;
    }

    public int getTotalCheckIns()
    {
        return totalCheckIns;
    }

    public boolean addBooking(Booking booking)
    {
        if (bookingCount >= bookings.length)
        {
            return false;
        }

        bookings[bookingCount] = booking;
        bookingCount++;

        totalRevenue = totalRevenue + booking.getTotalCost();

        if (booking.getRoomType().equalsIgnoreCase("Standard"))
        {
            standardCount++;
        }
        else if (booking.getRoomType().equalsIgnoreCase("Deluxe"))
        {
            deluxeCount++;
        }
        else if (booking.getRoomType().equalsIgnoreCase("Suite"))
        {
            suiteCount++;
        }

        if (booking.getNights() >= 1 && booking.getNights() <= 3)
        {
            shortStayCount++;
        }
        else if (booking.getNights() >= 4 && booking.getNights() <= 7)
        {
            midStayCount++;
        }
        else
        {
            longStayCount++;
        }

        if (booking.getHasBreakfast())
        {
            breakfastCount++;
        }

        if (booking.getHasParking())
        {
            parkingCount++;
        }

        return true;
    }

    public void recordGuestCheckIn(int bookingId)
    {
        for (int i = 0; i < bookingCount; i++)
        {
            if (bookings[i].getBookingId() == bookingId)
            {
                bookings[i].recordCheckIn();
                totalCheckIns++;
                System.out.println("Check-in recorded.");
                return;
            }
        }

        System.out.println("Booking ID not found.");
    }

    public void viewAllBookings()
    {
        if (bookingCount == 0)
        {
            System.out.println("No bookings recorded.");
            return;
        }

        System.out.println("\nBookings for " + propertyName);

        System.out.printf("%-10s %-20s %-12s %-10s %-12s %-10s%n",
                "ID",
                "Guest",
                "Room",
                "Nights",
                "Total",
                "CheckIns");

        for (int i = 0; i < bookingCount; i++)
        {
            Booking booking = bookings[i];

            System.out.printf("%-10d %-20s %-12s %-10d $%-11.2f %-10d%n",
                    booking.getBookingId(),
                    booking.getGuestName(),
                    booking.getRoomType(),
                    booking.getNights(),
                    booking.getTotalCost(),
                    booking.getCheckInCount());
        }
    }

    public void modifyLastBooking(String roomType,
                                  int nights,
                                  double subtotal,
                                  double tax,
                                  double totalCost)
    {
        if (bookingCount == 0)
        {
            System.out.println("No booking available to modify.");
            return;
        }

        Booking booking = bookings[bookingCount - 1];

        totalRevenue = totalRevenue - booking.getTotalCost();

        if (booking.getRoomType().equalsIgnoreCase("Standard"))
        {
            standardCount--;
        }
        else if (booking.getRoomType().equalsIgnoreCase("Deluxe"))
        {
            deluxeCount--;
        }
        else if (booking.getRoomType().equalsIgnoreCase("Suite"))
        {
            suiteCount--;
        }

        if (booking.getNights() >= 1 && booking.getNights() <= 3)
        {
            shortStayCount--;
        }
        else if (booking.getNights() >= 4 && booking.getNights() <= 7)
        {
            midStayCount--;
        }
        else
        {
            longStayCount--;
        }

        booking.setRoomType(roomType);
        booking.setNights(nights);
        booking.setSubtotal(subtotal);
        booking.setTax(tax);
        booking.setTotalCost(totalCost);
        booking.setCheckInCount(checkInCount);

        totalRevenue = totalRevenue + booking.getTotalCost();

        if (booking.getRoomType().equalsIgnoreCase("Standard"))
        {
            standardCount++;
        }
        else if (booking.getRoomType().equalsIgnoreCase("Deluxe"))
        {
            deluxeCount++;
        }
        else if (booking.getRoomType().equalsIgnoreCase("Suite"))
        {
            suiteCount++;
        }

        if (booking.getNights() >= 1 && booking.getNights() <= 3)
        {
            shortStayCount++;
        }
        else if (booking.getNights() >= 4 && booking.getNights() <= 7)
        {
            midStayCount++;
        }
        else
        {
            longStayCount++;
        }

        System.out.println("Last booking modified successfully.");
    }

    public void cancelLastBooking()
    {
        if (bookingCount == 0)
        {
            System.out.println("No booking available to cancel.");
            return;
        }

        Booking booking = bookings[bookingCount - 1];

        totalRevenue = totalRevenue - booking.getTotalCost();

        totalCheckIns =
                totalCheckIns - booking.getCheckInCount();

        if (booking.getRoomType().equalsIgnoreCase("Standard"))
        {
            standardCount--;
        }
        else if (booking.getRoomType().equalsIgnoreCase("Deluxe"))
        {
            deluxeCount--;
        }
        else if (booking.getRoomType().equalsIgnoreCase("Suite"))
        {
            suiteCount--;
        }

        if (booking.getNights() >= 1 && booking.getNights() <= 3)
        {
            shortStayCount--;
        }
        else if (booking.getNights() >= 4 && booking.getNights() <= 7)
        {
            midStayCount--;
        }
        else
        {
            longStayCount--;
        }

        if (booking.getHasBreakfast())
        {
            breakfastCount--;
        }

        if (booking.getHasParking())
        {
            parkingCount--;
        }

        bookings[bookingCount - 1] = null;
        bookingCount--;

        System.out.println("Last booking cancelled.");
    }

    public void displaySummary()
    {
        System.out.println("\nProperty ID: " + propertyId);
        System.out.println("Property Name: " + propertyName);
        System.out.println("Bookings: " + bookingCount);
        System.out.printf("Revenue: $%.2f%n", totalRevenue);
        System.out.println("Check-Ins: " + totalCheckIns);

        if (bookingCount > 0)
        {
            System.out.printf("Average Booking Value: $%.2f%n",
                    totalRevenue / bookingCount);
        }
        else
        {
            System.out.println("Average Booking Value: $0.00");
        }

        System.out.println("\nRoom Types");
        System.out.println("Standard: " + standardCount);
        System.out.println("Deluxe: " + deluxeCount);
        System.out.println("Suite: " + suiteCount);

        System.out.println("\nStay Categories");
        System.out.println("1-3 Nights: " + shortStayCount);
        System.out.println("4-7 Nights: " + midStayCount);
        System.out.println("8-30 Nights: " + longStayCount);

        System.out.println("\nAdd-Ons");
        System.out.println("Breakfast: " + breakfastCount);
        System.out.println("Parking: " + parkingCount);
    }

    public boolean bookingIdExists(int bookingId)
    {
        for (int i = 0; i < bookingCount; i++)
        {
            if (bookings[i].getBookingId() == bookingId)
            {
                return true;
            }
        }

        return false;
    }

    public void savePropertyData(String fileName)
    {
        try
        {
            PrintWriter writer =
                    new PrintWriter(fileName);

            writer.println(propertyId + ","
                    + propertyName + ","
                    + totalRevenue + ","
                    + bookingCount + ","
                    + totalCheckIns + ","
                    + standardCount + ","
                    + deluxeCount + ","
                    + suiteCount + ","
                    + shortStayCount + ","
                    + midStayCount + ","
                    + longStayCount + ","
                    + breakfastCount + ","
                    + parkingCount);

            for (int i = 0; i < bookingCount; i++)
            {
                Booking booking = bookings[i];

                writer.println(
                        booking.getBookingId() + ","
                                + booking.getGuestName() + ","
                                + booking.getRoomType() + ","
                                + booking.getNights() + ","
                                + booking.getHasBreakfast() + ","
                                + booking.getHasParking() + ","
                                + booking.getAddOnsCost() + ","
                                + booking.getSubtotal() + ","
                                + booking.getTax() + ","
                                + booking.getTotalCost() + ","
                                + booking.getCheckInCount());
            }

            writer.close();

            System.out.println("Property saved successfully.");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Unable to save file.");
        }
    }

    public Booking getLastBooking()
    {
        if (bookingCount == 0)
        {
            return null;
        }

    return bookings[bookingCount - 1];
}

    

    public void loadPropertyData(String fileName)
    {
        try
        {
            Scanner file =
                    new Scanner(new File(fileName));

            if (file.hasNextLine())
            {
                file.nextLine();
            }

            bookingCount = 0;

            totalRevenue = 0;
            totalCheckIns = 0;

            standardCount = 0;
            deluxeCount = 0;
            suiteCount = 0;

            shortStayCount = 0;
            midStayCount = 0;
            longStayCount = 0;

            breakfastCount = 0;
            parkingCount = 0;

            while (file.hasNextLine())
            {
                String line = file.nextLine();

                String[] data = line.split(",");

                int bookingId =
                        Integer.parseInt(data[0]);

                String guestName =
                        data[1];

                String roomType =
                        data[2];

                int nights =
                        Integer.parseInt(data[3]);

                boolean hasBreakfast =
                        Boolean.parseBoolean(data[4]);

                boolean hasParking =
                        Boolean.parseBoolean(data[5]);

                double addOnsCost =
                        Double.parseDouble(data[6]);

                double subtotal =
                        Double.parseDouble(data[7]);

                double tax =
                        Double.parseDouble(data[8]);

                double totalCost =
                        Double.parseDouble(data[9]);
                
                int checkInCount =
                        Integer.parseInt(data[10]);

                Booking booking =
                        new Booking(
                                bookingId,
                                guestName,
                                roomType,
                                nights,
                                hasBreakfast,
                                hasParking,
                                addOnsCost,
                                subtotal,
                                tax,
                                totalCost);

                booking.setCheckInCount(checkInCount);

                addBooking(booking);

                totalCheckIns += checkInCount;
            }

            file.close();

            System.out.println("Property loaded successfully.");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found.");
        }
    }
}