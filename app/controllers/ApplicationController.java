package controllers;

import com.vividsolutions.jts.geom.Coordinate;
import neo4j.models.World;
import neo4j.services.GalaxyService;
import neo4j.services.LocationService;
import neo4j.services.Neo4JServiceProvider;
import neo4j.services.UserService;
import neo4jplugin.Transactional;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.gis.spatial.*;
import org.neo4j.gis.spatial.pipes.GeoPipeFlow;
import org.neo4j.gis.spatial.pipes.GeoPipeline;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;


public class ApplicationController extends Controller {

    public final static GalaxyService galaxyService = Neo4JServiceProvider.get().galaxyService;
    public final static UserService userService = Neo4JServiceProvider.get().userService;
    public final static GraphDatabaseService graphDatabaseService = Neo4JServiceProvider.get().graphDatabaseService;
    public final static LocationService locationService = new LocationService(graphDatabaseService, "gamePoolSpatialIndex");

    public static String facebookId = "24"; // session("facebookId");
    public static Double userLat = 55.561; //Double.parseDouble(session("userLat"));
    public static Double userLon = 13.761; //Double.parseDouble(session("userLon"));
    public static Double radius = 0.2; //Double.parseDouble(session("radius"));


    @Transactional
    public static Result index() {

        userService.createUser("fb101");
        return ok(views.html.index.render("done"));



//        Node stadiumNode;
//
//        stadiumNode = graphDatabaseService.createNode();
//        stadiumNode.setProperty("name", "Stadium1");
//        locationService.addNode(stadiumNode, 55.561, 13.761);
//
//        stadiumNode = graphDatabaseService.createNode();
//        stadiumNode.setProperty("name", "Stadium2");
//        locationService.addNode(stadiumNode, 55.562, 13.762);
//
//        stadiumNode = graphDatabaseService.createNode();
//        stadiumNode.setProperty("name", "Stadium3");
//        locationService.addNode(stadiumNode, 55.563, 13.763);
//
//        IndexHits<Node> results = locationService.getUsersInRange(userLat, userLon, 0.9);
//
//        int numResults = results.size();
//        StringBuilder sb = new StringBuilder();
//        while(results.hasNext()){
//            Node node = results.next(); //hits.getSingle();
//            Double distance = locationService.getDistance(13.761, 55.561,
//                    (Double) node.getProperty("lat"), (Double) node.getProperty("lon"));
//
//            sb.append("#"+node.getId()+"("+node.getProperty("lat")+"-"+distance+") ");
//        }
//        String pointString = sb.toString();
//
//        return ok(views.html.index.render(pointString));
    }


    public static String getFacebookId(){
        return facebookId;
    }

    public static Double getUserLat() {
        return userLat;
    }

    public static Double getUserLon() {
        return userLon;
    }

    public static Double getRadius() {
        return radius;
    }


/*

    @Transactional
    public static Result indexGalaxy() {

        if (galaxyService.getNumberOfWorlds() == 0) {
            galaxyService.makeSomeWorldsAndRelations();
        }

        final List<World> allWorlds = galaxyService.getAllWorlds();
        final World first = allWorlds.get(0);
        final World last = allWorlds.get(allWorlds.size() - 1);
        final List<World> pathFromFirstToLast = galaxyService.getWorldPath(first, last);

        return ok(views.html.indexGalaxy.render(allWorlds, pathFromFirstToLast));
    }


    @Transactional
    public static Result indexPointLayer() {

        SpatialDatabaseService spatialDatabaseService = new SpatialDatabaseService(graphDatabaseService);
        String[] layerNames = spatialDatabaseService.getLayerNames();

        // Generic. Not specific to simplePointLayer
        EditableLayer simplePointLayer = spatialDatabaseService.getOrCreatePointLayer("gamePoolPointLayer", "lat", "lon");

        SpatialRecord record;
        record = simplePointLayer.add(simplePointLayer.getGeometryFactory().createPoint(new Coordinate(13.765, 55.565)));
        record = simplePointLayer.add(simplePointLayer.getGeometryFactory().createPoint(new Coordinate(13.761, 55.561)));
        record = simplePointLayer.add(simplePointLayer.getGeometryFactory().createPoint(new Coordinate(13.762, 55.562)));
        record = simplePointLayer.add(simplePointLayer.getGeometryFactory().createPoint(new Coordinate(13.763, 55.563)));
        record = simplePointLayer.add(simplePointLayer.getGeometryFactory().createPoint(new Coordinate(13.764, 55.564)));

//        List<Coordinate> coordinateList = new ArrayList<Coordinate>();
//        coordinateList.add(new Coordinate(13.766, 55.566));
//        coordinateList.add(new Coordinate(13.767, 55.567));
//        coordinateList.add(new Coordinate(13.768, 55.568));
//        coordinateList.add(new Coordinate(13.769, 55.569));
//        record = simplePointLayer.add(simplePointLayer.getGeometryFactory().createPoint(coordinateList));

        simplePointLayer.add((Node)new World("World1", 99));
        simplePointLayer.add((Node)new World("World2", 99));
        simplePointLayer.add((Node)new World("World3", 99));

        List<SpatialDatabaseRecord> results = GeoPipeline.startNearestNeighborSearch(simplePointLayer,
                new Coordinate(13.76, 55.56), 1000000.00).toSpatialDatabaseRecordList();

        return ok();
    }

    @Transactional
    public static Result indexSimplePointLayer() {

        SpatialDatabaseService spatialDatabaseService = new SpatialDatabaseService(graphDatabaseService);
        SimplePointLayer simplePointLayer = spatialDatabaseService.createSimplePointLayer("gamePoolSimplePointLayer");

        List<Coordinate> coordinateList = new ArrayList<Coordinate>();
        coordinateList.add(new Coordinate(13.766, 55.566));
        coordinateList.add(new Coordinate(13.761, 55.561));
        coordinateList.add(new Coordinate(13.762, 55.562));
        coordinateList.add(new Coordinate(13.763, 55.563));
        coordinateList.add(new Coordinate(13.764, 55.564));
        coordinateList.add(new Coordinate(13.765, 55.565));
        for (Coordinate coordinate : coordinateList) {
            simplePointLayer.add(coordinate);
        }

        // Search for nearby locations
        Coordinate myPosition = new Coordinate(13.76, 55.56);
        List<GeoPipeFlow> results = simplePointLayer.findClosestPointsTo(myPosition, 10.0);
        Coordinate closest = results.get(0).getGeometry().getCoordinate();

        return ok();
    }

*/

}
