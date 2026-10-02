/**
 * Subclass representing a regular Car in the rental system.
 * Extends the abstract Vehicle class and adds the number of seats.
 */
public class Car extends Vehicle implements Insurable {
    private static final double INSURANCE_PER_DAY = 50.0;

    private int seats;

    /**
     * Constructor initializing Car attributes along with inherited Vehicle fields.
     */
    public Car(String id, String brand, String model, double pricePerDay, int seats) {
        super(id, brand, model, pricePerDay);
        setSeats(seats);
    }

    // Getters
    public int getSeats() {
        return seats;
    }

    // Setters with validation
    public void setSeats(int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Seats must be greater than zero.");
        }
        this.seats = seats;
    }

    /**
     * Calculates the rental cost: price per day multiplied by the number of days.
     */
    @Override
    public double calculateRentalCost(int days) {
        validateDays(days);
        return getPricePerDay() * days;
    }

    /**
     * Calculates insurance: fixed daily rate multiplied by days.
     */
    @Override
    public double calculateInsuranceCost(int days) {
        validateDays(days);
        return INSURANCE_PER_DAY * days;
    }

    /**
     * Extends general Vehicle details with car specifics.
     */
    @Override
    public String getDetails() {
        return super.getDetails() + " || Seats: " + getSeats();
    }
}