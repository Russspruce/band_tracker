
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
      String sql = "INSERT INTO venues(veneu_name, location, description) VALUES (:veneu_name, :location, :description)";
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
      String sql = "DELETE FROM venues WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM bands_venues WHERE band_id = :band_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("band_id", this.getId())
          .executeUpdate();
    }
  }

  public void addVenue(Venue venue) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id)";
    con.createQuery(sql)
      .addParameter("band_id", this.getId())
      .addParameter("venue_id", venue.getId())
      .executeUpdate();
  }
}

public List<Venue> getVenues() {
  try(Connection con = DB.sql2o.open()){
    String joinQuery = "SELECT venue_id FROM bands_venues WHERE band_id = :band_id";
    List<Integer> venueIds = con.createQuery(joinQuery)
      .addParameter("band_id", this.getId())
      .executeAndFetch(Integer.class);

    List<Venue> venues = new ArrayList<Venue>();

    for (Integer venueId : venueIds) {
      String venueQuery = "SELECT * FROM venues WHERE id = :venue_id";
      Venue venue = con.createQuery(venueQuery)
        .addParameter("venue_id", venueId)
        .executeAndFetchFirst(Venue.class);
      venues.add(venue);
    }
    return venues;
  }
}

}
