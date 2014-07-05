package neo4j.services;


import neo4jplugin.Neo4JPlugin;
import neo4jplugin.ServiceProvider;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

@Component
public class Neo4JServiceProvider extends ServiceProvider {

    @Autowired
    public GraphDatabaseService graphDatabaseService;

    @Autowired
    public UserService userService;

    @Autowired
    public Neo4jTemplate neo4jTemplate;

    public static Neo4JServiceProvider get() {
        return Neo4JPlugin.get();
    }
}