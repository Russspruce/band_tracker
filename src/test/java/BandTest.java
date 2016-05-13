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
 public void getBandName_categoryInstantiatesWithBandName_String() {
   Band testBand = new Band("Imagine Dragons", "Indie Rock");
   assertEquals("Imagine Dragons", testBand.getBandName());
 }
}
