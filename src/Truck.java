public class Truck {
    private double loadCapacityKg;


    public Truck(double loadCapacityKg) {
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
}