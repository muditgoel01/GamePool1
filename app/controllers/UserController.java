package controllers;

import neo4j.repositories.UserRepository;
import neo4j.services.GalaxyService;
import neo4j.services.LocationService;
import neo4jplugin.Transactional;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;
import org.springframework.beans.factory.annotation.Autowired;
import play.mvc.Controller;
import play.mvc.Result;


public class UserController extends Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLocationService userLocationService;

    public final static GalaxyService galaxyService = ApplicationController.galaxyService;
    public final static GraphDatabaseService graphDatabaseService = ApplicationController.graphDatabaseService;
    public final static LocationService locationService = ApplicationController.locationService;


    public @ResponseBody Museum getMuseum(@PathVariable long museumId)
    {
        return this.museumRepository.findOne(museumId);
    }

    /**
     * Retrieve museum by name
     *
     * @param name museum's name
     * @return
     */
    @RequestMapping(value="/name/{name}", method=RequestMethod.GET, produces={"application/xml", "application/json"})
    public @ResponseBody Museum getMuseumByName(@PathVariable String name)
    {
        return this.museumRepository.findMuseumByName(name);
    }

    /**
     * Find museums hosting selected artist's artworks
     *
     * @param artistId
     * @return
     */
    @RequestMapping(value="/artist/{artistId}", method=RequestMethod.GET, produces={"application/xml", "application/json"})
    public @ResponseBody List<Museum> getMuseumsForArtist(@PathVariable long artistId)
    {
        return this.museumRepository.findMuseumByArtist(artistId);
    }

    /**
     * Find museums hosting selected artist's artworks
     *
     * @param artistId
     * @return
     */
    @Transactional
    @RequestMapping(value="/lon/{lon}/lat/{lat}/distanceInKm/{distanceInKm}", method=RequestMethod.GET, produces={"application/xml", "application/json"})
    public @ResponseBody List<Museum> getMuseumsWithinDistance(@PathVariable("lon") double longitude, @PathVariable("lat") double latitude, @PathVariable double distanceInKm)
    {
        return this.museumService.getMuseumsWithinDistance(longitude, latitude, distanceInKm);
    }

    /**
     * Retrieve all museums
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method=RequestMethod.GET, produces={"application/xml", "application/json"})
    public @ResponseBody List<Museum> getAllMuseums()
    {
        return IteratorUtils.toList(this.museumRepository.findAll().iterator());
    }

}




