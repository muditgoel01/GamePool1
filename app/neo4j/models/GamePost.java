package neo4j.models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class GamePost extends AbstractNode {

    @Indexed
    private Long id;

    @Indexed
    private String byFacebookId;

    @Indexed
    private String gameId;



	@Fetch
	@RelatedTo(type = "UserRelationships.POSTED", direction = Direction.OUTGOING)
	public Set<GamePost> gamesPosted;

	public GamePost(String userFacebookId, String gameId) {
        this.byFacebookId = userFacebookId;
        this.gameId = gameId;
	}

	public GamePost() {
	}

	@Override
	public String toString() {
		return "GamePost{id="+id+",byFacebookId="+byFacebookId+",gameId="+gameId+"}";
	}

}