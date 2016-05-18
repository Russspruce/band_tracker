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
  public void equals_returnsTrueIfDescriptionsAretheSame_true() {
    Venue firstVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    Venue secondVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesVenuesCorrectly() {
    Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    testVenue.save();
    assertEquals(1, Venue.all().size());
  }

  @Test
  public void find_findsVenueInDatabase_true() {
    Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    testVenue.save();
    Venue savedVenue = Venue.find(testVenue.getId());
    assertTrue(testVenue.equals(savedVenue));
  }

  @Test
  public void update_updatesVenueDescription_true() {
    Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    testVenue.save();
    testVenue.update("Doug Fir Lounge", "A classic concert hall where it is great to hang out.");
    assertEquals("Doug Fir Lounge", Venue.find(testVenue.getId()).getVenueName());
  }


  @Test
  public void addBand_addsBandToVenue() {
    Band testBand = new Band("Imagine Dragons", "Indie Rock");
    testBand.save();
    Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    testVenue.save();
    testVenue.addBand(testBand);
    Band savedBand = testVenue.getBands().get(0);
    assertTrue(testBand.equals(savedBand));
  }

  @Test
  public void getBands_returnsAllBands_List() {
    Band testBand = new Band("Imagine Dragons", "Indie Rock");
    testBand.save();
    Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    testVenue.save();
    testVenue.addBand(testBand);
    List savedBands = testVenue.getBands();
    assertEquals(1, savedBands.size());
  }

}
