package neo4j.repositories;

import neo4j.models.Game;
import neo4j.models.World;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface GameRepository extends GraphRepository<Game> {
}