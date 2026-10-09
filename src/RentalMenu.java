import java.util.ArrayList;
import java.util.Scanner;

/**
 * Console menu for the rental system.
 * Contains interaction logic only: asks the user, calls RentalService and prints results.
 */
public class RentalMenu {
    private final RentalService rentalService;
    private final Scanner scanner;
    private boolean running;

    public RentalMenu(RentalService rentalService) {
        this.rentalService = rentalService;
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    /**
     * Reads an integer from the user. Repeats the question until the input is valid.
     */
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Reads a non-empty text line from the user. Repeats until the input is not empty.
     */
    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Invalid input. Please enter some text.");
        }
    }

    /**
     * Reads a decimal number from the user. Repeats until the input is a valid number.
     */
    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (Double.isNaN(value) || Double.isInfinite(value)) {
                    System.out.println("Invalid input. Please enter a finite number.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (use for example: 49.5).");
            }
        }
    }

    /**
     *  Runs the main menu loop until the user chooses to exit.
     **/
    public void run() {
        while (running) {
            System.out.println("\n--- Rental Menu ---");
            System.out.println("1. Show all vehicles");
            System.out.println("2. Add vehicle");
            System.out.println("3. Rent / return vehicle");
            System.out.println("4. Search vehicles");
            System.out.println("5. Remove vehicle");
            System.out.println("0. Exit");

            int choice = readInt("Enter your choice: ");


            switch (choice) {
                case 1:
                    showAllVehicles();
                    break;
                case 2:
                    addVehicleFlow();
                    break;
                case 3:
                    rentReturnFlow();
                    break;
                case 4:
                    searchFlow();
                    break;
                case 5:
                    removeVehicleFlow();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Prints details of every vehicle in the fleet.
     */
    private void showAllVehicles() {
        ArrayList<Vehicle> vehicles = rentalService.getAll();
        if (vehicles.isEmpty()) {
            System.out.println("The fleet is empty.");
            return;
        }
        printVehicles(vehicles);
    }

    /**
     * Prints details of each vehicle in the given list.
     * The correct getDetails() version runs for each object (polymorphism).
     */
    private void printVehicles(ArrayList<Vehicle> list) {
        for (Vehicle v : list) {
            System.out.println(v.getDetails());
        }
    }

    /**
     * Asks for the vehicle type and its data, creates the vehicle and adds it to the fleet.
     * Validation errors from constructors and duplicate IDs are shown to the user.
     */
    private void addVehicleFlow() {
        int type = readInt("1 = Car, 2 = ElectricCar, 3 = Truck, 4 = ElectricScooter\nEnter vehicle type: ");
        if (type < 1 || type > 4) {
            System.out.println("Invalid choice.");
            return;
        }
        String id = readText("Enter ID: ");
        String brand = readText("Enter brand: ");
        String model = readText("Enter model: ");
        double pricePerDay = readDouble("Enter price per day: ");

        try {
            Vehicle vehicle;
            switch (type) {
                case 1: {
                    int seats = readInt("Enter number of seats: ");
                    vehicle = new Car(id, brand, model, pricePerDay, seats);
                    break;
                }
                case 2: {
                    int batteryPercent = readInt("Enter battery percentage: ");
                    int rangeKm = readInt("Enter range in kilometers: ");
                    vehicle = new ElectricCar(id, brand, model, pricePerDay, batteryPercent, rangeKm);
                    break;
                }
                case 3: {
                    double loadCapacityKg = readDouble("Enter load capacity in kilograms: ");
                    vehicle = new Truck(id, brand, model, pricePerDay, loadCapacityKg);
                    break;
                }
                case 4: {
                    int batteryPercent = readInt("Enter battery percentage: ");
                    int maxSpeedKmh = readInt("Enter maximum speed in kilometers per hour: ");
                    vehicle = new ElectricScooter(id, brand, model, pricePerDay, batteryPercent, maxSpeedKmh);
                    break;
                }
                default:
                    System.out.println("Invalid vehicle type.");
                    return;
            }
            rentalService.addVehicle(vehicle);
            System.out.println("Vehicle added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("[ ERROR ]: " + e.getMessage());
        }
    }

    /**
     * Asks whether to rent or return a vehicle, then asks for its ID.
     * Domain errors (unknown ID, already rented, battery too low) are shown to the user.
     */
    private void rentReturnFlow() {
        int choice = readInt("Enter 1 to rent a vehicle, 2 to return a vehicle: ");
        if (choice != 1 && choice != 2) {
            System.out.println("Invalid choice.");
            return;
        }
        String id = readText("Enter vehicle ID: ");
        try {
            if (choice == 1) {
                rentalService.rentVehicle(id);
                System.out.println("Vehicle rented successfully.");
            } else {
                rentalService.returnVehicle(id);
                System.out.println("Vehicle returned successfully.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[ ERROR ]: " + e.getMessage());
        }
    }

    /**
     * Asks whether to search by brand or by type, then asks for the search term
     * and prints the matching vehicles.
     */
    private void searchFlow() {
        int choice = readInt("Enter 1 to search by brand, 2 to search by type: ");
        if (choice != 1 && choice != 2) {
            System.out.println("Invalid choice.");
            return;
        }
        String search = readText("Enter search term: ");
        try {
            ArrayList<Vehicle> results;
            if (choice == 1) {
                results = rentalService.searchByBrand(search);
            } else {
                results = rentalService.searchByType(search);
            }
            if (results.isEmpty()) {
                System.out.println("No vehicles found.");
            } else {
                printVehicles(results);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("[ ERROR ]: " + e.getMessage());
        }
    }

    /**
     * Asks for a vehicle ID and removes that vehicle from the fleet.
     * Domain errors (unknown ID, vehicle is rented) are shown to the user.
     */
    private void removeVehicleFlow() {
        String id = readText("Enter vehicle ID: ");
        try {
            rentalService.removeVehicle(id);
            System.out.println("Vehicle removed successfully.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[ ERROR ]: " + e.getMessage());
        }
    }
}