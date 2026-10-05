public class Main {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        RentalMenu menu = new RentalMenu(rentalService);
        menu.run();
    }
}