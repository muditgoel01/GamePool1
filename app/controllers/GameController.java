package controllers;

import neo4j.models.Game;
import neo4j.models.User;
import neo4j.services.GameService;
import neo4j.services.Neo4JServiceProvider;
import neo4j.services.UserService;
import neo4jplugin.Transactional;
import org.apache.commons.collections.IteratorUtils;
import org.neo4j.graphdb.Node;
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


public class GameController extends Controller{

    public final static GameService gameService = Neo4JServiceProvider.get().gameService;
    public final static UserService userService = Neo4JServiceProvider.get().userService;


    @Transactional
    public static Result getGame(long gameId)
    {
        Game game = gameService.gameRepository.findOne(gameId);
        return Results.ok(Json.stringify(Json.toJson(game)));
    }

    @Transactional
    public static Result getGamesByTitle(String title)
    {
        List<Game> games = gameService.gameRepository.findGamesByTitle(title);
        return Results.ok(Json.stringify(Json.toJson(games)));
    }

    @Transactional
    public static Result getGamesByConsole(String console)
    {
        List<Game> games = gameService.gameRepository.findGamesByConsole(console);
        return Results.ok(Json.stringify(Json.toJson(games)));
    }

    @Transactional
    public static Result getGamesByYear(int year)
    {
        List<Game> games = gameService.gameRepository.findGamesByYear(year);
        return Results.ok(Json.stringify(Json.toJson(games)));
    }

    @Transactional
    public static Result getGamesByUser(long userId)
    {
        List<Game> games = gameService.gameRepository.findGamesByUser(userId);
        return Results.ok(Json.stringify(Json.toJson(games)));
    }

    @SuppressWarnings("unchecked")
    public static Result getAllGames()
    {
        List<Game> games = IteratorUtils.toList(gameService.gameRepository.findAll().iterator());
        return Results.ok(Json.stringify(Json.toJson(games)));
    }

    @Transactional
    public static Result getGamesWithinDistance(double latitude, double longitude, double distanceInKm)
    {
        List<User> users = userService.getUsersWithinDistance(latitude, longitude, distanceInKm);
        List<Long> userIds = new ArrayList<Long>();
        for(User user: users){
            userIds.add(user.getId());
        }
        List<Game> games = gameService.gameRepository.findGamesByUsers(userIds);
        return Results.ok(Json.stringify(Json.toJson(games)));
    }

    @Transactional
    public static Result addGame()
    {
        DynamicForm requestData = Form.form().bindFromRequest();
        String title = requestData.get("title");
        String console = requestData.get("console");
        String yearStr = requestData.get("year");
        Integer year = Integer.parseInt(yearStr);

        Node gameNode = addGame(title, console, year);

        return Results.ok(Json.stringify(Json.toJson(gameNode)));
    }

    @Transactional
    public static Node addGame(String title, String console, int year)
    {
        Game game = new Game();
        game.setTitle(title);
        game.setConsole(console);
        game.setYear(year);

        Map<String, Object> gameParams = new HashMap<String, Object>();
        gameParams.put("title", game.getTitle());
        gameParams.put("console", game.getConsole());
        gameParams.put("year", game.getYear());

        Node gameNode = gameService.saveNewGame(gameParams);
        return gameNode;
    }


}




