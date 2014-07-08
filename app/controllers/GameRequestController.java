package controllers;

import neo4j.models.GameEdge;
import neo4j.models.GamePost;
import neo4j.models.GameRequest;
import neo4j.models.UserEdge;
import neo4j.services.*;
import neo4jplugin.Transactional;
import org.apache.commons.collections.IteratorUtils;
import org.neo4j.graphdb.Node;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameRequestController extends Controller{

    public final static GameRequestService gameRequestService = Neo4JServiceProvider.get().gameRequestService;
    public final static GameService gameService = Neo4JServiceProvider.get().gameService;
    public final static UserService userService = Neo4JServiceProvider.get().userService;
    public final static Neo4jTemplate neo4jTemplate = Neo4JServiceProvider.get().neo4jTemplate;


    @Transactional
    public static Result getGameRequest(long gameRequestId)
    {
        GameRequest gameRequest = gameRequestService.gameRequestRepository.findOne(gameRequestId);
        return Results.ok(Json.stringify(Json.toJson(gameRequest)));
    }

    @Transactional
    public static Result getGameRequestsByStatus(String status)
    {
        List<GameRequest> gameRequests = gameRequestService.gameRequestRepository.findGameRequestsByStatus(status);
        return Results.ok(Json.stringify(Json.toJson(gameRequests)));
    }

    @Transactional
    public static Result getGameRequestsByUser(Long userId)
    {
        List<GameRequest> gameRequests = gameRequestService.gameRequestRepository.findGameRequestsByUser(userId);
        return Results.ok(Json.stringify(Json.toJson(gameRequests)));
    }

    @SuppressWarnings("unchecked")
    public static Result getAllGameRequests()
    {
        List<GameRequest> gameRequests = IteratorUtils.toList(gameRequestService.gameRequestRepository.findAll().iterator());
        return Results.ok(Json.stringify(Json.toJson(gameRequests)));
    }

    @Transactional
    public static Result addGameRequest()
    {
        DynamicForm requestData = Form.form().bindFromRequest();
        String status = requestData.get("status");
        String userId = requestData.get("requestedByUserId");
        String gamePostId = requestData.get("forGamePostId");

        Long requestedByUserId = Long.parseLong(userId);
        Long forGamePostId = Long.parseLong(gamePostId);

        Node gameRequestNode = addGameRequest(status, requestedByUserId, forGamePostId);

        return Results.ok(Json.stringify(Json.toJson(gameRequestNode)));
    }

    @Transactional
    public static Node addGameRequest(String status, Long requestedByUserId, Long forGamePostId)
    {
        GameRequest gameRequest = new GameRequest();
        gameRequest.setStatus(status);

        Map<String, Object> gameRequestParams = new HashMap<String, Object>();
        gameRequestParams.put("status", gameRequest.getStatus());

        Node gameRequestNode = gameRequestService.saveNewGameRequest(gameRequestParams);

        Node userNode = neo4jTemplate.getNode(requestedByUserId);
        Node gamePostNode = neo4jTemplate.getNode(forGamePostId);

        userNode.createRelationshipTo(gameRequestNode, UserEdge.REQUESTED);
        gameRequestNode.createRelationshipTo(gamePostNode, GameEdge.REQUESTED);

        return gameRequestNode;
    }


}




