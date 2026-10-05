import java.util.ArrayList;

/**
 * Holds the fleet of vehicles and contains all rental business logic.
 * Contains no input/output: errors are reported by throwing exceptions.
 */
public class RentalService {

    private final ArrayList<Vehicle> vehicles = new ArrayList<>();

    /**
     * Adds a vehicle to the fleet.
     *
     * @throws IllegalArgumentException if the vehicle is null or its ID already exists
     */
    public void addVehicle(Vehicle v) {
        if (v == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }
        for (Vehicle existing : vehicles) {
            if (existing.getId().equalsIgnoreCase(v.getId())) {
                throw new IllegalArgumentException("Vehicle with ID " + v.getId() + " already exists.");
            }
        }
        vehicles.add(v);
    }

    /**
     * Returns a copy of the fleet list.
     */
    public ArrayList<Vehicle> getAll() {
        return new ArrayList<>(vehicles);
    }
}