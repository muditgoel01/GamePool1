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
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import org.apache.commons.collections.IteratorUtils;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public GraphDatabaseService graphDatabaseService;

    @Autowired
    public Neo4jTemplate neo4jTemplate;


    private Index<Node> _index;
    public Index<Node> getIndex(){
        if(_index == null){
            IndexManager indexManager = graphDatabaseService.index();
            Map<String, String> config = Collections.unmodifiableMap(
                    MapUtil.stringMap(SpatialIndexProvider.GEOMETRY_TYPE,
                            LayerNodeIndex.POINT_GEOMETRY_TYPE,
                            IndexManager.PROVIDER, SpatialIndexProvider.SERVICE_NAME,
                            LayerNodeIndex.WKT_PROPERTY_KEY,
                            "wkt"));
            _index = indexManager.forNodes("userLocation", config);
        }
        return _index;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<User> getUsersWithinDistance(double latitude, double longitude, double distanceInKm)
    {
        // Note the order of latitude and longitude has been reversed below!

        return IteratorUtils.toList(
                this.userRepository.findWithinDistance(
                        UserRepository.USER_GEOSPATIAL_INDEX, longitude, latitude, distanceInKm
                ).iterator());
    }

    @Transactional
    public Node saveNewUser(Map<String, Object> userParams){
        // user must have values for wkt and facebookId.

//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("facebookId", user.getFacebookId());
//        Iterator<Node> users = neo4jTemplate.query("MATCH (u:User) WHERE u.facebookId={facebookId} RETURN u", params)
//                .to(Node.class).iterator();
//        if(users.hasNext()){
//            Node userNode = users.next();
//            if (userNode.hasProperty("wkt")){
//                System.out.println("ADDING " + userNode.getProperty("name") + " to userLocation index.");
//                getIndex().add(userNode, "dummy", "value");
//            }else{
//                System.out.println(userNode.getProperty("name") + " NOT ADDED to userLocation index.");
//            }
//        }

        Collection<String> labels = new ArrayList<String>();
        labels.add("User");
        labels.add("_User"); // Needed for Spring Data Neo4j - To prevent IllegalStateException: No primary SDN label exists .. (i.e one with starting  with _)

        // Add node to neo4j graph and (automatically) to User repository
        Node userNode = neo4jTemplate.createNode(userParams, labels);

        // Add node to spatial index. Node must have 'wkt' property set.
        getIndex().add(userNode, "dummy", "value");

        return userNode;
    }





}





