package neo4j.repositories;

import neo4j.models.GamePost;
import neo4j.models.GameRequest;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRequestRepository extends GraphRepository<GameRequest> {

    @Query("MATCH (n:GameRequest) WHERE n.id={id} RETURN distinct(n) as gameRequest")
    GameRequest findGameRequestById(@Param("id") Long id);

    @Query("MATCH (n:GameRequest) WHERE n.status={status} RETURN distinct(n) as gameRequest")
    List<GameRequest> findGameRequestsByStatus(@Param("status") String status);

    @Query("MATCH (u:User)-[:REQUESTED]->(r:GameRequest) WHERE id(u)={userId} RETURN distinct(r) as gameRequest")
    List<GameRequest> findGameRequestsByUser(@Param("userId") long userId);

//    @Query("MATCH (r:GameRequest)-[:REQUESTED]->(p:GamePost) WHERE id(p)={gamePostId} RETURN distinct(r) as gameRequest")
//    List<GameRequest> findGameRequestsByGamePost(@Param("gamePostId") long gamePostId);

//    @Query("MATCH (r:GameRequest)-[rel:REQUESTED]->(p:GamePost) WHERE id(r)={gameRequestId} RETURN distinct(r) as gameRequest")
//    List<GameRequest> deleteGameRequestAndRelationships(@Param("gamePostId") long gamePostId);
}