import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void venue_instantiatesCorrectly_true() {
    Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    assertEquals(true, testVenue instanceof Venue);
  }

  @Test
 public void getVenueName_taskInstantiatesWithVenueName_String() {
   Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
   assertEquals("Crystal Ballroom", testVenue.getVenueName());
 }

 @Test
 public void
 getLocation_venueInstantiatesWithLocation_String() {
   Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
   assertEquals("Portland, OR", testVenue.getLocation());
 }

 @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Venue.all().size());
  }
}
