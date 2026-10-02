/**
 * Interface representing vehicles with a rechargeable battery.
 * Implemented by ElectricCar and ElectricScooter.
 */
public interface Chargeable {
    int MIN_BATTERY_TO_RENT = 20;
    int FULL_BATTERY = 100;

    /**
     * Returns the current battery level.
     *
     * @return battery level from 0 to 100
     */
    int getBatteryPercent();

    /**
     * Charges the battery to full (100%).
     */
    void charge();
}

