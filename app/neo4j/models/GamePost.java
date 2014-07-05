package neo4j.models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
@TypeAlias("GamePost")
public class GamePost extends AbstractNode {

    @Indexed
    private String status;

	@Fetch
	@RelatedTo(type = "UserEdge.POSTED", direction = Direction.INCOMING)
	public Set<User> postedBy;

    @Fetch
    @RelatedTo(type = "GameEdge.POSTED", direction = Direction.OUTGOING)
    public Set<Game> forGame;


	public GamePost(String status) {
        this.status = status;
	}

	public GamePost() {
	}

	@Override
	public String toString() {
		return "GamePost{id="+id+",status="+status+"}";
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<User> getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Set<User> postedBy) {
        this.postedBy = postedBy;
    }

    public Set<Game> getForGame() {
        return forGame;
    }

    public void setForGame(Set<Game> forGame) {
        this.forGame = forGame;
    }
}