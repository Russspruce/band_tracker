import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.ArrayList;
import static spark.Spark.*;

public class App {

  public static void main (String[] args){
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/submit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/band-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id", (request, reponse) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band", band);
      model.put("venues", band.getVenues());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/bands/:id", (request, response) -> {
      String band_name = request.queryParams("band_name");
      String genre = request.queryParams("genre");
      Band newBand = new Band(band_name, genre);
      newBand.save();
      response.redirect("/bands/" + newBand.getId());
      return null;
    });

    post("bands/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      band.delete();
      response.redirect("/bands");
      return null;
    });

    get("/venues", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("venue", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/submit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/venue-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request, reponse) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params(":id")));
      model.put("venue", venue);
      model.put("allBands", Band.all());
      model.put("venues", venue.getBands());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/:id", (request, response) -> {
      String venue_name = request.queryParams("venue_name");
      String location = request.queryParams("location");
      String description = request.queryParams("description");
      Venue newVenue = new Venue(venue_name, location, description);
      newVenue.save();
      response.redirect("/venues/" + newVenue.getId());
      return null;
    });

    get("venues/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params("id")));
      model.put("venue", venue);
      model.put("description", venue.getDescription());
      model.put("template", "templates/venue-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params("id")));
      String description = request.queryParams("description");
      venue.update(description);
      String url = String.format("/venues/%d", venue.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add_bands", (request, response) -> {
      int bandId = Integer.parseInt(request.queryParams("band_id"));
      int venueId = Integer. parseInt(request.queryParams("venue_id"));
      Venue venue = Venue.find(venueId);
      Band band = Band.find(bandId);
      venue.addBand(band);
      response.redirect("/venues/" + venueId);
      return null;
    });

  }
}
