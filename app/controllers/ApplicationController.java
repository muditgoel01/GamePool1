package controllers;

import neo4j.models.User;
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

    @Transactional
    public static Result index()
    {

        if(userService.userRepository.count() <= 0){

            UserController.addUser("fb1005", 37.31, -122.01);
            UserController.addUser("fb1006", 37.32, -122.02);
            UserController.addUser("fb1007", 37.33, -122.03);
            UserController.addUser("fb1008", 37.34, -122.04);
        }

        List<User> users = IteratorUtils.toList(userService.userRepository.findAll().iterator());
        return Results.ok(Json.stringify(Json.toJson(users)));

        //return ok(views.html.index.render("done"));
    }



}
