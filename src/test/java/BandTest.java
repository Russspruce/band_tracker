import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly_true() {
    Band testBand = new Band("Imagine Dragons", "Indie Rock");
    assertEquals(true, testBand instanceof Band);
  }

  @Test
 public void getBandName_bandInstantiatesWithBandName_String() {
   Band testBand = new Band("Imagine Dragons", "Indie Rock");
   assertEquals("Imagine Dragons", testBand.getBandName());
 }

 @Test
 public void
 getGenre_bandInstantiatesWithGenre_String() {
   Band testBand = new Band("Imagine Dragons", "Indie Rock");
   assertEquals("Indie Rock", testBand.getGenre());
 }

 @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Band.all().size());
  }
  @Test
  public void equals_returnsTrueIfNamesAretheSame_true() {
    Band firstBand = new Band("Imagine Dragons", "Indie Rock");
    Band secondBand = new Band("Imagine Dragons", "Indie Rock");;
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Band testBand = new Band("Imagine Dragons", "Indie Rock");
    testBand.save();
    assertTrue(Band.all().get(0).equals(testBand));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Band testBand = new Band("Imagine Dragons", "Indie Rock");
    testBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(testBand.getId(), savedBand.getId());
  }

  @Test
  public void find_findBandInDatabase_true() {
    Band testBand = new Band("Imagine Dragons", "Indie Rock");
    testBand.save();
    Band savedBand = Band.find(testBand.getId());
    assertTrue(testBand.equals(savedBand));
  }


    @Test
    public void delete_deletesBand_true() {
      Band testBand = new Band("Imagine Dragons", "Indie Rock");
      testBand.save();
      int testBandId = testBand.getId();
      testBand.delete();
      assertEquals(null, Band.find(testBandId));
    }

  }
