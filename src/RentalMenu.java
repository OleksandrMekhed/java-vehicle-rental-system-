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
}