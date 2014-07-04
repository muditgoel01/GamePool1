package neo4j.services;


import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.operation.distance.DistanceOp;
import neo4j.models.User;
import neo4j.repositories.UserRepository;
import neo4jplugin.Transactional;
import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;

public class UserLocationService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> getUsersWithinDistance(double longitude, double latitude, double distanceInKm)
    {
        return IteratorUtils.toList(
                this.userRepository.findWithinDistance(
                        UserRepository.USER_GEOSPATIAL_INDEX, longitude, latitude, distanceInKm
                ).iterator());
    }


}





