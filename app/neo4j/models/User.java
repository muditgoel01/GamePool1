package neo4j.models;


import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.Set;

@NodeEntity
@TypeAlias("User")
public class User extends AbstractNode {

    @Indexed(unique=true)
    private String facebookId;

    @Indexed
    private String name;

    @Indexed(indexName="userLocation", indexType= IndexType.POINT)
    private String wkt;

    private Double latitude;
    private Double longitude;

    @Fetch
	@RelatedTo(type = "UserEdge.POSTED", direction = Direction.OUTGOING)
	public Set<GamePost> gamesPosted;


	public User(String facebookId) {
		this.facebookId = facebookId;
	}

	public User() {
	}

	public void postGame(String gameId) {
        GamePost gamePost = new GamePost("FreshStatus");
		gamesPosted.add(gamePost);
	}

	@Override
	public String toString() {
		return "User{id="+id+",facebookId="+facebookId+",latitude="+latitude+",longitude="+longitude+"}";
	}



    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
        this.updateWkt();
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
        this.updateWkt();
    }

    public String getWkt() {
        return wkt;
    }

    private void updateWkt()
    {
        this.wkt = String.format("POINT( %.2f %.2f )", this.getLongitude(), this.getLatitude());
    }

    public void setWkt(double longitude, double latitude)
    {
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.updateWkt();
    }

    public Set<GamePost> getGamesPosted() {
        return gamesPosted;
    }

    public void setGamesPosted(Set<GamePost> gamesPosted) {
        this.gamesPosted = gamesPosted;
    }


}




