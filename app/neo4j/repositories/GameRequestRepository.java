package neo4j.repositories;

import neo4j.models.GameRequest;
import neo4j.models.World;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface GameRequestRepository extends GraphRepository<GameRequest> {
}