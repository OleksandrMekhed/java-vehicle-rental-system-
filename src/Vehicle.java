/**
 * Abstract superclass representing a general Vehicle in the rental system.
 * Demonstrates Encapsulation (private fields) and abstraction.
 */
public abstract class Vehicle {
    private String id;
    private String brand;
    private String model;
    private double pricePerDay;
    private boolean rented;

    /**
     * Constructor initializing vehicle details with validation.
     * A new vehicle is always available (not rented).
     */
    public Vehicle(String id, String brand, String model, double pricePerDay) {
        setId(id);
        setBrand(brand);
        setModel(model);
        setPricePerDay(pricePerDay);
        this.rented = false;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isRented() {
        return rented;
    }

    // Setters with validation (final: safe to call from the constructor)
    public final void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty.");
        }
        this.id = id.trim();
    }

    public final void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be null or empty.");
        }
        this.brand = brand.trim();
    }

    public final void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be null or empty.");
        }
        this.model = model.trim();
    }

    public final void setPricePerDay(double pricePerDay) {
        if (pricePerDay <= 0) {
            throw new IllegalArgumentException("Price per day must be greater than zero.");
        }
        this.pricePerDay = pricePerDay;
    }

    /**
     * Validates the number of rental days. Reused by all subclasses.
     */
    protected static void validateDays(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be greater than zero.");
        }
    }

    /**
     * Abstract method to calculate the total rental cost.
     * Must be overridden by subclasses (Polymorphism).
     */
    public abstract double calculateRentalCost(int days);

    /**
     * Marks the vehicle as rented.
     * Can be extended by subclasses (battery check).
     *
     * @throws IllegalStateException if the vehicle is already rented
     */
    public void rent() {
        if (rented) {
            throw new IllegalStateException("Vehicle " + id + " is already rented.");
        }
        rented = true;
    }

    /**
     * Marks the vehicle as available again.
     *
     * @throws IllegalStateException if the vehicle is not currently rented
     */
    public void returnVehicle() {
        if (!rented) {
            throw new IllegalStateException("Vehicle " + id + " is not rented.");
        }
        rented = false;
    }

    /**
     * Returns general details about the vehicle.
     * Can be extended by subclasses.
     */
    public String getDetails() {
        return "ID: " + getId()
                + " || Brand: " + getBrand()
                + " || Model: " + getModel()
                + " || Price per day: " + String.format("%.2f", getPricePerDay()) + " SEK"
                + " || Status: " + (isRented() ? "Rented" : "Available");
    }
}