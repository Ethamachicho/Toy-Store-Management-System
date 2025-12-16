public class Store {

    private String city;
    private String hours;

    public Store(String city, String hours) {
        this.city = city;
        this.hours = hours;
    }

    public String getCity() { return city; }
    public String getHours() { return hours; }

    // Setters
    public void setCity(String city) { this.city = city; }
    public void setHours(String hours) { this.hours = hours; }

    @Override
    public String toString() {
        return city + " (" + hours + ")";
    }
}