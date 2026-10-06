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

    /**
     * Finds a vehicle by its ID.
     *
     * @throws IllegalArgumentException if the ID is empty or no vehicle has this ID
     */
    public Vehicle findById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or empty.");
        }
        for (Vehicle v : vehicles) {
            if (v.getId().equalsIgnoreCase(id.trim())) {
                return v;
            }
        }
        throw new IllegalArgumentException("No vehicle found with ID " + id.trim() + ".");
    }

    /**
     * Removes a vehicle from the fleet.
     *
     * @throws IllegalArgumentException if the ID is empty or no vehicle has this ID
     * @throws IllegalStateException if the vehicle is currently rented
     */
    public void removeVehicle(String id) {
        Vehicle v = findById(id);
        if (v.isRented()) {
            throw new IllegalStateException("Vehicle " + v.getId() + " is currently rented and cannot be removed.");
        }
        vehicles.remove(v);
    }

    /**
     * Searches vehicles whose brand contains the given text.
     *
     * @throws IllegalArgumentException if the search text is empty
     */
    public ArrayList<Vehicle> searchByBrand(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Search text cannot be null or empty.");
        }
        String query = text.trim().toLowerCase();
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getBrand().toLowerCase().contains(query)) {
                result.add(v);
            }
        }
        return result;
    }

    /**
     * Searches vehicles by type, e.g. "Car", "ElectricCar", "Truck" or "ElectricScooter"
     * (case-insensitive, spaces are ignored).
     *
     * @throws IllegalArgumentException if the type is empty
     */
    public ArrayList<Vehicle> searchByType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type cannot be null or empty.");
        }
        String query = type.replace(" ", "");
        ArrayList<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getClass().getSimpleName().equalsIgnoreCase(query)) {
                result.add(v);
            }
        }
        return result;
    }

    /**
     * Rents out the vehicle with the given ID.
     *
     * @throws IllegalArgumentException if the ID is empty or not found
     * @throws IllegalStateException if the vehicle is already rented or its battery is too low
     */
    public void rentVehicle(String id) {
        Vehicle v = findById(id);
        v.rent();
    }

    /**
     * Returns the vehicle with the given ID to the fleet.
     *
     * @throws IllegalArgumentException if the ID is empty or not found
     * @throws IllegalStateException if the vehicle is not currently rented
     */
    public void returnVehicle(String id) {
        Vehicle v = findById(id);
        v.returnVehicle();
    }
}