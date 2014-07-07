package neo4j.services;


import neo4jplugin.Neo4JPlugin;
import neo4jplugin.ServiceProvider;
import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class Neo4JServiceProvider extends ServiceProvider {

    @Autowired
    public GraphDatabaseService graphDatabaseService;

    @Autowired
    public UserService userService;

    @Autowired
    public GameService gameService;

    @Autowired
    public GamePostService gamePostService;

    @Autowired
    public GameRequestService gameRequestService;

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

    public static Neo4JServiceProvider get() {
        return Neo4JPlugin.get();
    }
}