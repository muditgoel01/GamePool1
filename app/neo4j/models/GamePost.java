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

    private Integer views;

	@Fetch
	@RelatedTo(type = "POSTED", direction = Direction.INCOMING)
	public User postedBy; //UserEdge.POSTED

    @Fetch
    @RelatedTo(type = "POSTED", direction = Direction.OUTGOING)
    public Game forGame; //GameEdge.POSTED


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

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    public Game getForGame() {
        return forGame;
    }

    public void setForGame(Game forGame) {
        this.forGame = forGame;
    }
}