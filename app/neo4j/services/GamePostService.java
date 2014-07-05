package neo4j.services;


import neo4j.repositories.GamePostRepository;
import neo4j.repositories.GameRepository;
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
public class GamePostService {

    @Autowired
    public GamePostRepository gamePostRepository;

    @Autowired
    public GraphDatabaseService graphDatabaseService;

    @Autowired
    public Neo4jTemplate neo4jTemplate;


    @Transactional
    public Node saveNewGamePost(Map<String, Object> gamePostParams){

        Collection<String> labels = new ArrayList<String>();
        labels.add("GamePost");
        labels.add("_GamePost"); // Needed for Spring Data Neo4j - To prevent IllegalStateException: No primary SDN label exists .. (i.e one with starting  with _)

        // Add node to neo4j graph and (automatically) to repository
        Node gamePostNode = neo4jTemplate.createNode(gamePostParams, labels);

        return gamePostNode;
    }





}





