public class Product {
    private String Name;
    private String ID;
    private String Location;
    private double Price;
    private String Producer;
    private String Retailer;

    public Product(String name, String ID, String location, double price, String producer, String retailer) {
        Name = name;
        this.ID = ID;
        Location = location;
        Price = price;
        Producer = producer;
        Retailer = retailer;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getProducer() {
        return Producer;
    }

    public void setProducer(String producer) {
        Producer = producer;
    }

    public String getRetailer() {
        return Retailer;
    }

    public void setRetailer(String retailer) {
        Retailer = retailer;
    }
}
