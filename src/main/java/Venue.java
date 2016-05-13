
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
}
