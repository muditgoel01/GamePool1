package controllers;

import com.vividsolutions.jts.geom.Coordinate;
import neo4j.models.World;
import neo4j.services.GalaxyService;
import neo4j.services.LocationService;
import neo4j.services.Neo4JServiceProvider;
import neo4jplugin.Transactional;
import org.neo4j.gis.spatial.*;
import org.neo4j.gis.spatial.pipes.GeoPipeFlow;
import org.neo4j.gis.spatial.pipes.GeoPipeline;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;


public class GameController extends Controller {

    public final static GalaxyService galaxyService = ApplicationController.galaxyService;
    public final static GraphDatabaseService graphDatabaseService = ApplicationController.graphDatabaseService;
    public final static LocationService locationService = ApplicationController.locationService;


    @Transactional
    public static Result getInRange(Double radius) {

        if(radius == null || radius <= 0){
            radius = getRadius();
        }

        IndexHits<Node> results = locationService.getGamesInRange(getUserLat(), getUserLon(), radius);

        int numResults = results.size();
        StringBuilder sb = new StringBuilder();
        while(results.hasNext()){
            Node node = results.next(); //hits.getSingle();
            Double distance = locationService.getDistance(13.761, 55.561,
                    (Double) node.getProperty("lat"), (Double) node.getProperty("lon"));

            sb.append("#"+node.getId()+"("+node.getProperty("lat")+"-"+distance+") ");
        }
        String pointString = sb.toString();

        //Json.stringify(Json.toJson(business));
        return ok(views.html.index.render(pointString));
    }


    public static String getFacebookId(){
        return ApplicationController.getFacebookId();
    }

    public static Double getUserLat() {
        return ApplicationController.getUserLat();
    }

    public static Double getUserLon() {
        return ApplicationController.getUserLon();
    }

    public static Double getRadius() {
        return ApplicationController.getRadius();
    }

}




