/**
 * Interface representing vehicles that can be insured.
 * Implemented by Car, ElectricCar and Truck. Electric scooters are not insured.
 */
public interface Insurable {

    /**
     * Calculates the total insurance cost for a rental period.
     * days number of rental days (must be greater than zero)
     *
     * @return total insurance cost in SEK
     */
    double calculateInsuranceCost(int days);
}