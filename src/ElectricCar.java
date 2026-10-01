public class ElectricCar {
    private int batteryPercent;
    private int rangeKm;


    public ElectricCar(int batteryPercent, int rangeKm) {
        setBatteryPercent(batteryPercent);
        setRangeKm(rangeKm);
    }

    // Getters
    public int getBatteryPercent() {
        return batteryPercent;
    }

    public int getRangeKm() {
        return rangeKm;
    }

    // Setters with validation
    public void setBatteryPercent(int batteryPercent) {
        if (batteryPercent < 0 || batteryPercent > 100) {
            throw new IllegalArgumentException("Battery percent must be between 0 and 100.");
        }
        this.batteryPercent = batteryPercent;
    }

    public void setRangeKm(int rangeKm) {
        if (rangeKm <= 0) {
            throw new IllegalArgumentException("Range must be greater than zero.");
        }
        this.rangeKm = rangeKm;
    }
}