package neo4j.models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
@TypeAlias("GameRequest")
public class GameRequest extends AbstractNode {

    @Indexed
    private String status;

    @Fetch
    @RelatedTo(type = "REQUESTED", direction = Direction.INCOMING)
    public User requestedBy;

    @Fetch
    @RelatedTo(type = "REQUESTED", direction = Direction.OUTGOING)
    public GamePost forGamePost;

	public GameRequest(String status) {
        this.status = status;
	}

	public GameRequest() {
	}

	@Override
	public String toString() {
		return "GameRequest{id="+id+",status="+status+"}";
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public GamePost getForGamePost() {
        return forGamePost;
    }

    public void setForGamePost(GamePost forGamePost) {
        this.forGamePost = forGamePost;
    }
}