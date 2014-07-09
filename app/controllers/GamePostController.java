package controllers;

import neo4j.models.GameEdge;
import neo4j.models.GamePost;
import neo4j.models.GameRequest;
import neo4j.models.UserEdge;
import neo4j.services.*;
import neo4jplugin.Transactional;
import org.apache.commons.collections.IteratorUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GamePostController extends Controller{

    public final static GamePostService gamePostService = Neo4JServiceProvider.get().gamePostService;
    //public final static GameRequestService gameRequestService = Neo4JServiceProvider.get().gameRequestService;
    public final static Neo4jTemplate neo4jTemplate = Neo4JServiceProvider.get().neo4jTemplate;


    @Transactional
    public static Result getGamePost(long gamePostId)
    {
        GamePost gamePost = gamePostService.gamePostRepository.findOne(gamePostId);
        return Results.ok(Json.stringify(Json.toJson(gamePost)));
    }

    @Transactional
    public static Result getGamePostsByStatus(String status)
    {
        List<GamePost> gamePosts = gamePostService.gamePostRepository.findGamePostsByStatus(status);
        return Results.ok(Json.stringify(Json.toJson(gamePosts)));
    }

    @Transactional
    public static Result getGamePostsByUser(Long userId)
    {
        List<GamePost> gamePosts = gamePostService.gamePostRepository.findGamePostsByUser(userId);
        return Results.ok(Json.stringify(Json.toJson(gamePosts)));
    }

    @Transactional
    public static Result getAllGamePosts()
    {
        List<GamePost> gamePosts = IteratorUtils.toList(gamePostService.gamePostRepository.findAll().iterator());
        return Results.ok(Json.stringify(Json.toJson(gamePosts)));
    }

    @Transactional
    public static Result addGamePost()
    {
        DynamicForm requestData = Form.form().bindFromRequest();
        String status = requestData.get("status");
        String userId = requestData.get("postedByUserId");
        String gameId = requestData.get("forGameId");

        Long postedByUserId = Long.parseLong(userId);
        Long forGameId = Long.parseLong(gameId);

        Node gamePostNode = addGamePost(status, postedByUserId, forGameId);

        return Results.ok(Json.stringify(Json.toJson(gamePostNode.getId())));
    }

    @Transactional
    public static Node addGamePost(String status, Long postedByUserId, Long forGameId)
    {
        GamePost gamePost = new GamePost();
        gamePost.setStatus(status);

        Map<String, Object> gamePostParams = new HashMap<String, Object>();
        gamePostParams.put("status", gamePost.getStatus());

        Node gamePostNode = gamePostService.saveNewGamePost(gamePostParams);

        Node userNode = neo4jTemplate.getNode(postedByUserId);
        Node gameNode = neo4jTemplate.getNode(forGameId);

        userNode.createRelationshipTo(gamePostNode, UserEdge.POSTED);
        gamePostNode.createRelationshipTo(gameNode, GameEdge.POSTED);

        return gamePostNode;
    }

//    @Transactional
//    public static Result deleteGamePost(Long gamePostId)
//    {
//        // TODO Just mark as 'deleted'. Do not actually delete.
//        // TODO Update associated relationships too. (Mark them as 'deleted' too?)
//        // User > GamePost > GameRequest
//        List<GameRequest> gameRequests = gameRequestService.gameRequestRepository.findGameRequestsByGamePost(gamePostId);
//        for(GameRequest gameRequest : gameRequests){
//            GameRequestController.deleteGameRequest(gameRequest.getId());
//        }
//        gamePostService.gamePostRepository.delete(gamePostId);
//        return Results.ok("GamePost has been removed.");
//    }

}




