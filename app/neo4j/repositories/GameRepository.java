package neo4j.repositories;

import neo4j.models.Game;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends GraphRepository<Game> {

    @Query("MATCH (n:Game) WHERE n.title=~ '.*{title}.*' RETURN distinct(n) as game")
    List<Game> findGamesByTitle(@Param("title") String title);

    @Query("MATCH (n:Game) WHERE n.console={console} RETURN distinct(n) as game")
    List<Game> findGamesByConsole(@Param("console") String console);

    @Query("MATCH (n:Game) WHERE n.year={year} RETURN distinct(n) as game")
    List<Game> findGamesByYear(@Param("year") int year);

    @Query("MATCH (u:User)-[:POSTED]->(p:GamePost)-[:POSTED]->(g:Game) WHERE id(u)={userId} RETURN distinct(g) as game")
    List<Game> findGamesByUser(@Param("userId") long userId);

    @Query("MATCH (u:User)-[:POSTED]->(p:GamePost)-[:POSTED]->(g:Game) WHERE id(u) IN {userIds} RETURN distinct(g) as game")
    List<Game> findGamesByUsers(@Param("userIds") List<Long> userIds);

}