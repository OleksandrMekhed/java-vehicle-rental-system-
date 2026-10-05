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
                System.out.println("Invalid input. Please enter a number (use a dot, e.g. 49.5).");
            }
        }
    }
}