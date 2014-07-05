package neo4j.models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class GameRequest extends AbstractNode {

    @Indexed
    private Long id;

    @Indexed
    private String byFacebookId;

    @Indexed
    private Long forPostId;

    @Indexed
    private GameRequestStatus status;


	@Fetch
	@RelatedTo(type = "....REQUESTED", direction = Direction.OUTGOING)
	public Set<GameRequest> gamesPosted;

	public GameRequest(String byFacebookId, Long forPostId) {
        this.byFacebookId = byFacebookId;
        this.forPostId = forPostId;
        status = GameRequestStatus.REQUESTED;
	}

	public GameRequest() {
	}

	@Override
	public String toString() {
		return "GameRequest{id="+id+",byFacebookId="+byFacebookId+",forPostId="+forPostId+",status="+status+"}";
	}

}