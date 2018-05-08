public class Car {

    private String manufacturer;
    private String modelName;
    private int modelYear = -1;
    private String licensePlate = "";

    public Car(String manufacturer, String modelName) {
        this.manufacturer = manufacturer;
        this.modelName = modelName;
    }

    public Car(String manufacturer, String modelName, int modeYear, String licensePlate) {
        this(manufacturer, modelName);
        this.modelYear = modeYear;
        this.licensePlate = licensePlate;
    }

    public Car(String manufacturer, String modelName, int modeYear) {
        this(manufacturer, modelName);
        this.modelYear = modeYear;
    }

    public Car(String manufacturer, String modelName, String licensePlate) {
        this(manufacturer, modelName);
        this.licensePlate = licensePlate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public int getModelYear() {
        return modelYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setModelYear(int modeYear) {
        this.modelYear = modeYear;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String toString() {
        return manufacturer + " manufactured " + modelName + " in " + modelYear + ", license plate is " + licensePlate;
    }
}
