package ap.edu.bib;

/**
 * Created by Michael on 30/09/2016.
 */

public class Bibliotheek {
    private String naam;
    private String longitude;
    private String latitude;

    public Bibliotheek(String naam, String longitude, String latitude) {
        this.naam = naam;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getNaam(){return naam;}
    public String getLongitude() {return longitude;}
    public String getLatitude() {return latitude;}
}
