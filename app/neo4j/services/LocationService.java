package neo4j.services;


import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.operation.distance.DistanceOp;
import neo4j.models.World;
import neo4j.repositories.WorldRepository;
import neo4j.services.Neo4JServiceProvider;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.gis.spatial.pipes.GeoPipeline;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.impl.core.NodeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

public class LocationService{

    private Index<Node> index;

    public LocationService(GraphDatabaseService graphDatabaseService, String indexLayerName){
        //GraphDatabaseService graphDatabaseService2 = Neo4JServiceProvider.get().graphDatabaseService;
        Map<String, String> config = SpatialIndexProvider.SIMPLE_WKT_CONFIG;
        IndexManager indexMan = graphDatabaseService.index();
        index = indexMan.forNodes(indexLayerName, config);
    }

    public void addNode(Node node, Double lat, Double lon){
        node.setProperty("wkt", String.format("POINT(%s %s)", lat, lon));
        node.setProperty("lat", lat);
        node.setProperty("lon", lon);
        index.add(node, "dummyA", "dummyB");
    }

    public void addUser(String facebookId, Double lat, Double lon){

        Node node = null;
        node.setProperty("facebookId", facebookId);
        node.setProperty("wkt", String.format("POINT(%s %s)", lat, lon));
        node.setProperty("lat", lat);
        node.setProperty("lon", lon);
        index.add(node, "dummyA", "dummyB");
    }

    public IndexHits<Node> getGamesInRange(Double centerLat, Double centerLon, Double radiusInKm){
        IndexHits<Node> users = getUsersInRange(centerLat, centerLon, radiusInKm);
        IndexHits<Node> games;

        return users;
    }

    public IndexHits<Node> getUsersInRange(Double centerLat, Double centerLon, Double radiusInKm){
        Double[] center = {centerLon, centerLat}; // Note the order is reversed for lat and lon

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LayerNodeIndex.POINT_PARAMETER, center);
        params.put(LayerNodeIndex.DISTANCE_IN_KM_PARAMETER, radiusInKm);

        IndexHits<Node> users = index.query(LayerNodeIndex.WITHIN_DISTANCE_QUERY, params);
        return users;
    }

    public Double getDistance(Double fromLat, Double fromLon, Double toLat, Double toLon){
        WKTReader wktRdr = new WKTReader();
        Geometry A = null;
        Geometry B = null;
        try {
            A = wktRdr.read(String.format("POINT(%s %s)", fromLon, fromLat));
            B = wktRdr.read(String.format("POINT(%s %s)", toLon, toLat));
            DistanceOp distOp = new DistanceOp(A, B);
            return distOp.distance();
        } catch (ParseException e) {
            return null;
        }
    }

    public Index<Node> getIndex(){
        return index;
    }


}





