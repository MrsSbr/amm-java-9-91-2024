import java.time.LocalDate;

public class Hotel {
    private int hotelId;
    private String hotelName;
    private String address;
    private String city;
    private String country;
    private double rating;
    private String description;

    // Конструкторы
    public Hotel() {}

    public Hotel(String hotelName, String address, String city, String country,
                 double rating, String description) {
        this.hotelName = hotelName;
        this.address = address;
        this.city = city;
        this.country = country;
        this.rating = rating;
        this.description = description;
    }

    // Геттеры и сеттеры
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }


}