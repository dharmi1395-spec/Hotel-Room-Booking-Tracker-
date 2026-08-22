//Dharmi Rajendrabhai Gajera (c 3484039)
//Bhargav Dharmeshbhai Patadia (c3440219)



public class Booking
{
    private int bookingId;
    private String guestName;
    private String roomType;
    private int nights;

    private boolean hasBreakfast;
    private boolean hasParking;

    private double addOnsCost;
    private double subtotal;
    private double tax;
    private double totalCost;

    private int checkInCount;

    public Booking(int bookingId,
                   String guestName,
                   String roomType,
                   int nights,
                   boolean hasBreakfast,
                   boolean hasParking,
                   double addOnsCost,
                   double subtotal,
                   double tax,
                   double totalCost)
    {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.hasBreakfast = hasBreakfast;
        this.hasParking = hasParking;
        this.addOnsCost = addOnsCost;
        this.subtotal = subtotal;
        this.tax = tax;
        this.totalCost = totalCost;

        checkInCount = 0;
    }

    public int getBookingId()
    {
        return bookingId;
    }

    public String getGuestName()
    {
        return guestName;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public int getNights()
    {
        return nights;
    }

    public boolean getHasBreakfast()
    {
        return hasBreakfast;
    }

    public boolean getHasParking()
    {
        return hasParking;
    }

    public double getAddOnsCost()
    {
        return addOnsCost;
    }

    public double getSubtotal()
    {
        return subtotal;
    }

    public double getTax()
    {
        return tax;
    }

    public double getTotalCost()
    {
        return totalCost;
    }

    public int getCheckInCount()
    {
        return checkInCount;
    }

    public void setRoomType(String roomType)
    {
        this.roomType = roomType;
    }

    public void setNights(int nights)
    {
        this.nights = nights;
    }

    public void setAddOnsCost(double addOnsCost)
    {
        this.addOnsCost = addOnsCost;
    }

    public void setSubtotal(double subtotal)
    {
        this.subtotal = subtotal;
    }

    public void setCheckInCount(int checkInCount)
    {
        this.checkInCount = checkInCount;
    }

    public void setTax(double tax)
    {
        this.tax = tax;
    }

    public void setTotalCost(double totalCost)
    {
        this.totalCost = totalCost;
    }

    public void recordCheckIn()
    {
        checkInCount++;
    }

    public void displayBooking()
    {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Nights: " + nights);
        System.out.println("Breakfast: " + hasBreakfast);
        System.out.println("Parking: " + hasParking);
        System.out.println("Total Cost: $" + totalCost);
        System.out.println("Check-Ins: " + checkInCount);
    }
}