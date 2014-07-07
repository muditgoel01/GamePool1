package neo4j.services;


import neo4j.repositories.GamePostRepository;
import neo4j.repositories.GameRequestRepository;
import neo4jplugin.Transactional;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class GameRequestService {

    @Autowired
    public GameRequestRepository gameRequestRepository;

    @Autowired
    public GraphDatabaseService graphDatabaseService;

    @Autowired
    public Neo4jTemplate neo4jTemplate;


    @Transactional
    public Node saveNewGameRequest(Map<String, Object> gameRequestParams){

        Collection<String> labels = new ArrayList<String>();
        labels.add("GameRequest");
        labels.add("_GameRequest"); // Needed for Spring Data Neo4j - To prevent IllegalStateException: No primary SDN label exists .. (i.e one with starting  with _)

        // Add node to neo4j graph and (automatically) to repository
        Node gameRequestNode = neo4jTemplate.createNode(gameRequestParams, labels);

        return gameRequestNode;
    }





}





