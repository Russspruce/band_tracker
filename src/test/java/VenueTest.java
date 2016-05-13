import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void venue_instantiatesCorrectly_true() {
    Venue myVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    assertEquals(true, myVenue instanceof Venue);
  }

  @Test
 public void getVenueName_taskInstantiatesWithVenueName_String() {
   Venue myVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
   assertEquals("Crystal Ballroom", myVenue.getVenueName());
 }
}
