package controllers;

import neo4j.models.User;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserController extends Controller{

    public final static UserService userService = Neo4JServiceProvider.get().userService;


    @Transactional
    public static Result getUser(long userId)
    {
        User user = userService.userRepository.findOne(userId);
        return Results.ok(Json.stringify(Json.toJson(user)));
    }

    @Transactional
    public static Result getUserByFacebookId(String facebookId)
    {
        User user = userService.userRepository.findUserByFacebookId(facebookId);
        return Results.ok(Json.stringify(Json.toJson(user)));
    }

    @Transactional
    public static Result getUserByName(String name)
    {
        List<User> users = userService.userRepository.findUsersByName(name);
        return Results.ok(Json.stringify(Json.toJson(users)));
    }

    @SuppressWarnings("unchecked")
    public static Result getAllUsers()
    {
        List<User> users = IteratorUtils.toList(userService.userRepository.findAll().iterator());
        return Results.ok(Json.stringify(Json.toJson(users)));
    }

    @Transactional
    public static Result getUsersWithinDistance(double latitude, double longitude, double distanceInKm)
    {
        List<User> users = userService.getUsersWithinDistance(latitude, longitude, distanceInKm);
        return Results.ok(Json.stringify(Json.toJson(users)));
    }

    @Transactional
    public static Result addUser()
    {
        DynamicForm requestData = Form.form().bindFromRequest();
        String facebookId = requestData.get("fid");

        String lat = requestData.get("lat");
        String lon = requestData.get("lon");
        Double latitude = Double.parseDouble(lat);
        Double longitude = Double.parseDouble(lon);

        Node userNode = addUser(facebookId, latitude, longitude);

        return Results.ok(Json.stringify(Json.toJson(userNode)));
    }

    @Transactional
    public static Node addUser(String facebookId, double latitude, double longitude)
    {
        User user = new User();
        user.setFacebookId(facebookId);
        user.setLatitude(latitude);
        user.setLongitude(longitude);

        Map<String, Object> userParams = new HashMap<String, Object>();
        userParams.put("facebookId", user.getFacebookId());
        userParams.put("wkt", user.getWkt());

        Node userNode = userService.saveNewUser(userParams);
        return userNode;
    }

}




