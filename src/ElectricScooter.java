public class ElectricScooter {
    private int batteryPercent;
    private int maxSpeedKmh;


    public ElectricScooter(int batteryPercent, int maxSpeedKmh) {
        setBatteryPercent(batteryPercent);
        setMaxSpeedKmh(maxSpeedKmh);
    }

    // Getters

    public int getBatteryPercent() {
        return batteryPercent;
    }

    public int getMaxSpeedKmh() {
        return maxSpeedKmh;
    }

    // Setters with validation
    public void setBatteryPercent(int batteryPercent) {
        if (batteryPercent < 0 || batteryPercent > 100) {
            throw new IllegalArgumentException("Battery percent must be between 0 and 100.");
        }
        this.batteryPercent = batteryPercent;
    }

    public void setMaxSpeedKmh(int maxSpeedKmh) {
        if (maxSpeedKmh <= 0) {
            throw new IllegalArgumentException("Max speed must be greater than zero.");
        }
        this.maxSpeedKmh = maxSpeedKmh;
    }
}