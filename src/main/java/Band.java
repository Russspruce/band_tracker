import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Band {
  private int id;
  private String band_name;
  private String genre;

  public Band(String band_name, String genre) {
    this.band_name = band_name;
    this.genre = genre;
  }

  public String getBandName() {
    return band_name;
  }

  public String getGenre() {
    return genre;
  }

  public int getId() {
    return id;
  }

  
}
