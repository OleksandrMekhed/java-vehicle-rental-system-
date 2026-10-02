/**
 * Subclass representing an Electric Scooter in the rental system.
 * Extends Vehicle and adds battery level and maximum speed.
 */
public class ElectricScooter extends Vehicle {
    private static final int MIN_BATTERY_TO_RENT = 20;
    private static final int MAX_RENTAL_DAYS = 7;


    private int batteryPercent;
    private int maxSpeedKmh;

    /**
     * Constructor initializing ElectricScooter attributes along with inherited Vehicle fields.
     */
    public ElectricScooter(String id, String brand, String model, double pricePerDay, int batteryPercent, int maxSpeedKmh) {
        super(id, brand, model, pricePerDay);
        setBatteryPercent(batteryPercent);
        setMaxSpeedKmh(maxSpeedKmh);
    }

    // Getters
    public int getBatteryPercent() {
        return batteryPercent;
    }

    public int getMaxSpeedKmh() {
        return maxSpeedKmh;
    }

    // Setters with validation
    public void setBatteryPercent(int batteryPercent) {
        if (batteryPercent < 0 || batteryPercent > 100) {
            throw new IllegalArgumentException("Battery percent must be between 0 and 100.");
        }
        this.batteryPercent = batteryPercent;
    }

    public void setMaxSpeedKmh(int maxSpeedKmh) {
        if (maxSpeedKmh <= 0) {
            throw new IllegalArgumentException("Max speed must be greater than zero.");
        }
        this.maxSpeedKmh = maxSpeedKmh;
    }

    /**
     * Calculates the rental cost: price per day multiplied by days.
     * Scooters can be rented for a maximum of 7 days.
     *
     * @throws IllegalArgumentException if days is not positive or exceeds 7
     */
    @Override
    public double calculateRentalCost(int days) {
        validateDays(days);
        if (days > MAX_RENTAL_DAYS) {
            throw new IllegalArgumentException("Scooters can be rented for at most "
                    + MAX_RENTAL_DAYS + " days (requested: " + days + ").");
        }
        return getPricePerDay() * days;
    }

    /**
     * Rents the scooter only if the battery is at least 20%.
     * The battery is checked first, then the common rent logic in Vehicle runs.
     *
     * @throws IllegalStateException if the battery is too low or the scooter is already rented
     */
    @Override
    public void rent() {
        if (batteryPercent < MIN_BATTERY_TO_RENT) {
            throw new IllegalStateException("Battery too low to rent (" + batteryPercent
                    + "%, minimum is " + MIN_BATTERY_TO_RENT + "%).");
        }
        super.rent();
    }

    /**
     * Extends general Vehicle details with scooter specifics.
     */
    @Override
    public String getDetails() {
        return super.getDetails()
                + " || Battery: " + getBatteryPercent() + "%"
                + " || Max Speed: " + getMaxSpeedKmh() + " km/h";
    }
}