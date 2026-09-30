/**
 * Subclass representing a regular Car in the rental system.
 * Add the number of seats.
 */
public class Car {
    private int seats;

    /**
     * Constructor initializing Car.
     */
    public Car(int seats) {
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
}