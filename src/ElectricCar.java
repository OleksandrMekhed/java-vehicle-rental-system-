/**
 * Subclass representing an Electric Car in the rental system.
 * Extends Vehicle and adds battery level and driving range.
 */
public class ElectricCar extends Vehicle {
    private static final double ECO_DISCOUNT = 0.10;
    private static final int MIN_BATTERY_TO_RENT = 20;

    private int batteryPercent;
    private int rangeKm;

    /**
     * Constructor initializing ElectricCar attributes along with inherited Vehicle fields.
     */
    public ElectricCar(String id, String brand, String model, double pricePerDay, int batteryPercent, int rangeKm) {
        super(id, brand, model, pricePerDay);
        setBatteryPercent(batteryPercent);
        setRangeKm(rangeKm);
    }

    // Getters
    public int getBatteryPercent() {
        return batteryPercent;
    }

    public int getRangeKm() {
        return rangeKm;
    }

    // Setters with validation
    public void setBatteryPercent(int batteryPercent) {
        if (batteryPercent < 0 || batteryPercent > 100) {
            throw new IllegalArgumentException("Battery percent must be between 0 and 100.");
        }
        this.batteryPercent = batteryPercent;
    }

    public void setRangeKm(int rangeKm) {
        if (rangeKm <= 0) {
            throw new IllegalArgumentException("Range must be greater than zero.");
        }
        this.rangeKm = rangeKm;
    }

    /**
     * Calculates the rental cost: price per day multiplied by days,
     * with a 10% eco discount applied.
     */
    @Override
    public double calculateRentalCost(int days) {
        validateDays(days);
        return getPricePerDay() * days * (1 - ECO_DISCOUNT);
    }

    /**
     * Rents the car only if the battery is at least 20%.
     * The battery is checked first, then the common rent logic in Vehicle runs.
     *
     * @throws IllegalStateException if the battery is too low or the car is already rented
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
     * Extends general Vehicle details with electric car specifics.
     */
    @Override
    public String getDetails() {
        return super.getDetails()
                + " || Battery: " + getBatteryPercent() + "%"
                + " || Range: " + getRangeKm() + " km";
    }
}