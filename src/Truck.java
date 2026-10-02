/**
 * Subclass representing a Truck in the rental system.
 * Extends Vehicle and adds load capacity.
 */
public class Truck extends Vehicle implements Insurable {
    private static final double SURCHARGE_PER_KG_PER_DAY = 0.02;
    private static final double INSURANCE_PER_DAY = 120.0;

    private double loadCapacityKg;

    /**
     * Constructor initializing Truck attributes along with inherited Vehicle fields.
     */
    public Truck(String id, String brand, String model, double pricePerDay, double loadCapacityKg) {
        super(id, brand, model, pricePerDay);
        setLoadCapacityKg(loadCapacityKg);
    }

    // Getters
    public double getLoadCapacityKg() {
        return loadCapacityKg;
    }

    // Setters with validation
    public void setLoadCapacityKg(double loadCapacityKg) {
        if (loadCapacityKg <= 0) {
            throw new IllegalArgumentException("Load capacity must be greater than zero.");
        }
        this.loadCapacityKg = loadCapacityKg;
    }

    /**
     * Calculates the rental cost: (price per day + daily load surcharge) multiplied by days.
     * The surcharge is 0.02 SEK per kg of load capacity per day.
     */
    @Override
    public double calculateRentalCost(int days) {
        validateDays(days);
        double dailySurcharge = loadCapacityKg * SURCHARGE_PER_KG_PER_DAY;
        return (getPricePerDay() + dailySurcharge) * days;
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
     * Extends general Vehicle details with truck specifics.
     */
    @Override
    public String getDetails() {
        return super.getDetails()
                + " || Load Capacity: "
                + String.format("%.0f", getLoadCapacityKg()) + " kg";
    }
}