package neo4j.services;


import neo4j.models.Game;
import neo4j.models.User;
import neo4j.models.World;
import neo4j.repositories.UserRepository;
import neo4j.repositories.WorldRepository;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private GraphDatabaseService graphDatabaseService;

    private ExecutionEngine engine = new ExecutionEngine(graphDatabaseService);

    private LocationService locationService = new LocationService(graphDatabaseService, "gamePoolSpatialIndex");


	public long getNumberOfUsers() {
		return userRepository.count();
	}
	public List<User> getAllUsers() {
		return new ArrayList<User>(IteratorUtil.asCollection(userRepository.findAll()));
	}

    public List<Game> getPostedGames(){
        // https://github.com/neo4j-contrib/spatial/blob/master/src/test/java/org/neo4j/gis/spatial/IndexProviderTest.java
        // ExecutionEngine engine = new ExecutionEngine(graphDatabaseService);
        // result = engine.execute( "start n=node(*) where n.name = 'my node' return n, n.name" );
        // result = engine.execute("start malmo=node:layer1('withinDistance:[56.0, 15.0,1000.0]') match p=malmo--other return malmo, other");
        // result.iterator().hasNext()
        // engine.execute("start n=node:layer3('withinDistance:[33.32, 44.44, 5.0]') return n").columnAs("n").hasNext()

        return null;
    }

	public List<User> makeSomeUsersAndRelations() {
		List<User> users = new ArrayList<User>();

		users.add(createUser("fb1"));
		users.add(createUser("fb2"));
		users.add(createUser("fb3"));
		users.add(createUser("fb4"));

		return users;
	}
	
	public User createUser(String facebookId) {
        Node userNode = graphDatabaseService.createNode();
        locationService.addNode(userNode, 55.561, 13.761);

        User user = new User(facebookId);
        user.setLat(55.561);
        user.setLon(13.761);

		return userRepository.save(user);
	}

}