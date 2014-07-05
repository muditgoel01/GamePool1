package controllers;

import neo4j.models.Game;
import neo4j.models.User;
import neo4j.services.GamePostService;
import neo4j.services.GameService;
import neo4j.services.Neo4JServiceProvider;
import neo4j.services.UserService;
import neo4jplugin.Transactional;
import org.apache.commons.collections.IteratorUtils;
import org.neo4j.gis.spatial.indexprovider.LayerNodeIndex;
import org.neo4j.gis.spatial.indexprovider.SpatialIndexProvider;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class ApplicationController extends play.mvc.Controller {

    //public final static GraphDatabaseService graphDatabaseService = Neo4JServiceProvider.get().graphDatabaseService;
    //public final static Neo4jTemplate neo4jTemplate = Neo4JServiceProvider.get().neo4jTemplate;
    public final static UserService userService = Neo4JServiceProvider.get().userService;
    public final static GameService gameService = Neo4JServiceProvider.get().gameService;
    public final static GamePostService gamePostService = Neo4JServiceProvider.get().gamePostService;

    @Transactional
    public static Result index()
    {

        if(userService.userRepository.count() <= 0){
            UserController.addUser("fb1001", 37.31, -122.01);
            UserController.addUser("fb1002", 37.32, -122.02);
            UserController.addUser("fb1003", 37.33, -122.03);
            UserController.addUser("fb1004", 37.34, -122.04);
        }

        if(gameService.gameRepository.count() <= 0){
            GameController.addGame("Age Of Empires", "PS1", 2001);
            GameController.addGame("Spider Man", "PS2", 2002);
            GameController.addGame("Roller Coaster Tycoon", "PS3", 2003);
            GameController.addGame("FIFA World Cup", "PS4", 2004);
        }

        if(gamePostService.gamePostRepository.count() <= 0){
            GamePostController.addGamePost("Fresh1", 4L, 12L);
            GamePostController.addGamePost("Fresh2", 4L, 13L);
            GamePostController.addGamePost("Fresh3", 6L, 12L);
            GamePostController.addGamePost("Fresh4", 8L, 14L);
        }

        List<User> users = IteratorUtils.toList(userService.userRepository.findAll().iterator());
        List<Game> games = IteratorUtils.toList(gameService.gameRepository.findAll().iterator());
        List<Game> gamePosts = IteratorUtils.toList(gamePostService.gamePostRepository.findAll().iterator());

        return Results.ok("USERS = "+Json.stringify(Json.toJson(users))
                + " GAMES = " + Json.stringify(Json.toJson(games))
                + " GAME_POSTS = " + Json.stringify(Json.toJson(gamePosts))
        );

        //return ok(views.html.index.render("done"));
    }



}
