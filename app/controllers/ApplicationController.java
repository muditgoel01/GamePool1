package controllers;

import com.vividsolutions.jts.geom.Coordinate;
import neo4j.models.World;
import neo4j.services.GalaxyService;
import neo4j.services.Neo4JServiceProvider;
import neo4jplugin.Transactional;
import org.neo4j.collections.rtree.SpatialIndexReader;
import org.neo4j.gis.spatial.Layer;
import org.neo4j.gis.spatial.SimplePointLayer;
import org.neo4j.gis.spatial.SpatialDatabaseRecord;
import org.neo4j.gis.spatial.SpatialDatabaseService;
import org.neo4j.gis.spatial.filter.SearchIntersectWindow;
import org.neo4j.gis.spatial.pipes.GeoPipeFlow;
import org.neo4j.gis.spatial.pipes.processing.Envelope;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tuxburner
 */
public class ApplicationController extends Controller {

    private final static GalaxyService galaxyService = Neo4JServiceProvider.get().galaxyService;
    private final static GraphDatabaseService graphDatabaseService = Neo4JServiceProvider.get().graphDatabaseService;
    private static SpatialDatabaseService spatialDatabaseService = new SpatialDatabaseService(graphDatabaseService);
    private static SimplePointLayer simplePointLayer = spatialDatabaseService.createSimplePointLayer("neo-text");

    @Transactional
    public static Result index() {

        if (galaxyService.getNumberOfWorlds() == 0) {
            galaxyService.makeSomeWorldsAndRelations();
        }

        final List<World> allWorlds = galaxyService.getAllWorlds();
        final World first = allWorlds.get(0);
        final World last = allWorlds.get(allWorlds.size() - 1);
        final List<World> pathFromFirstToLast = galaxyService.getWorldPath(first, last);

        return ok(views.html.index.render(allWorlds, pathFromFirstToLast));
    }

    @Transactional
    public static Result galaxyIndex() {

        if (galaxyService.getNumberOfWorlds() == 0) {
            galaxyService.makeSomeWorldsAndRelations();
        }

        // Add locations
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
        //List<SpatialDatabaseRecord> results = simplePointLayer.findClosestPointsTo(myPosition, 10.0);
        List<GeoPipeFlow> results = simplePointLayer.findClosestPointsTo(myPosition, 10.0);
        Coordinate closest = results.get(0).getGeometry().getCoordinate();


        final List<World> allWorlds = galaxyService.getAllWorlds();
        final World first = allWorlds.get(0);
        final World last = allWorlds.get(allWorlds.size() - 1);
        final List<World> pathFromFirstToLast = galaxyService.getWorldPath(first, last);

        return ok(views.html.index.render(allWorlds, pathFromFirstToLast));
    }

}
