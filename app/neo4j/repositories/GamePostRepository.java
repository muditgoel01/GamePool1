package neo4j.repositories;

import neo4j.models.Game;
import neo4j.models.GamePost;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamePostRepository extends GraphRepository<GamePost> {

    @Query("MATCH (n:GamePost) WHERE n.id={id} RETURN distinct(n) as gamePost")
    GamePost findGamePostById(@Param("id") Long id);

    @Query("MATCH (n:GamePost) WHERE n.status={status} RETURN distinct(n) as gamePost")
    List<GamePost> findGamePostsByStatus(@Param("status") String status);

    @Query("MATCH (u:User)-[:POSTED]->(p:GamePost) WHERE id(u)={userId} RETURN distinct(p) as gamePost")
    List<GamePost> findGamePostsByUser(@Param("userId") long userId);

//    @Query("MATCH (g:Game)<-[:POSTED]-(p:GamePost) WHERE id(g)={gameId} RETURN distinct(p) as gamePost")
//    List<GamePost> findGamePostsByGame(@Param("gameId") long gameId);


}