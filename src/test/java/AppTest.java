import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Jam Trek");
  }

  @Test
  public void allBandsPageIsDisplayed() {
    goTo("http://localhost:4567/");
    click("a", withText("Bands on Tour"));
    assertThat(pageSource().contains("Bands on Tour!"));
  }

  @Test
  public void individualBandPageIsDisplayed() {
    Band testBand = new Band("Cage the Elephant", "Alternative Rock");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("Cage the Elephant", "Alternative Rock");
  }

  @Test
  public void allVenuesPageIsDisplayed() {
    goTo("http://localhost:4567/");
    click("a", withText("Available Venues"));
    assertThat(pageSource().contains("Available Venues!"));
  }

  @Test
  public void individualVenuePageIsDisplayed() {
    Venue testVenue = new Venue("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(url);
    assertThat(pageSource()).contains("Crystal Ballroom", "Portland, OR", "A great place with all the latest bands");


  }
}
