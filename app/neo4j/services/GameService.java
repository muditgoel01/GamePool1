package neo4j.services;


import neo4j.models.User;
import neo4j.repositories.GameRepository;
import neo4j.repositories.UserRepository;
import neo4jplugin.Transactional;
import org.apache.commons.collections.IteratorUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    @Autowired
    public GameRepository gameRepository;

    @Autowired
    public GraphDatabaseService graphDatabaseService;

    @Autowired
    public Neo4jTemplate neo4jTemplate;


    @Transactional
    public Node saveNewGame(Map<String, Object> gameParams){

        Collection<String> labels = new ArrayList<String>();
        labels.add("Game");
        labels.add("_Game"); // Needed for Spring Data Neo4j - To prevent IllegalStateException: No primary SDN label exists .. (i.e one with starting  with _)

        // Add node to neo4j graph and (automatically) to repository
        Node gameNode = neo4jTemplate.createNode(gameParams, labels);

        return gameNode;
    }





}





