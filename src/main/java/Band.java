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
  public static List<Band> all() {
    String sql = "SELECT * FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getBandName().equals(newBand.getBandName()) &&
             this.getGenre().equals(newBand.getGenre()) &&
             this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(band_name, genre) VALUES (:band_name, :genre)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("band_name", this.band_name)
      .addParameter("genre", this.genre)
      .executeUpdate()
      .getKey();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM bands WHERE id = :id; DELETE FROM bands_venues WHERE band_id = :id";
      con.createQuery(sql)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands where id = :id";
      Band category = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Band.class);
      return category;
    }
  }

}
