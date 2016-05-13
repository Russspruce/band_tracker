
import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Venue {
  private int id;
  private String venue_name;
  private String location;
  private String description;

  public Venue(String venue_name, String location, String description) {
    this.venue_name = venue_name;
    this.location = location;
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public String getVenueName() {
    return venue_name;
  }

  public String getLocation() {
    return location;
  }

  public String getDescription() {
    return description;
  }
  public static List<Venue> all() {
    String sql = "SELECT id, description FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getVenueName().equals(newVenue.getVenueName()) &&
      this.getLocation().equals(newVenue.getLocation()) && this.getDescription().equals(newVenue.getDescription()) &&
      this.getId() == newVenue.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (venue_name, location, description) VALUES (:venue_name, :location, :description)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("venue_name", this.venue_name)
      .addParameter("location", this.location)
      .addParameter("description", this.description)
      .executeUpdate()
      .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues where id=:id";
      Venue venue = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  public void update(String newVenueName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE venues SET venue_name = :venue_name WHERE id = :id";
      con.createQuery(sql)
      .addParameter("venue_name", newVenueName)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM venues WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", this.getId())
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM bands_venues WHERE band_id = :band_id";
      con.createQuery(joinDeleteQuery)
      .addParameter("venue_id", this.getId())
      .executeUpdate();
    }
  }

  public void addBand(Band band) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
      .addParameter("band_id", band.getId())
      .addParameter("venue_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Band> getBands() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT band_id FROM bands_venues WHERE venue_id = :venue_id";
      List<Integer> band_ids = con.createQuery(joinQuery)
      .addParameter("venue_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Band> bands = new ArrayList<Band>();

      for (Integer band_id : band_ids) {
        String venueQuery = "SELECT * FROM bands WHERE id = :band_id";
        Band band = con.createQuery(venueQuery)
        .addParameter("band_id", band_id)
        .executeAndFetchFirst(Band.class);
        bands.add(band);
      }
      return bands;
    }
  }

}
