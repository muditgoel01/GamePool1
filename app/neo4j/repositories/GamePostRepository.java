package neo4j.repositories;

import neo4j.models.GamePost;
import neo4j.models.World;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface GamePostRepository extends GraphRepository<GamePost> {
}