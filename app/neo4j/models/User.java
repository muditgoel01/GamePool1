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

    private Double lat;
    private Double lon;

    @Fetch
	@RelatedTo(type = "UserRelationships.POSTED", direction = Direction.OUTGOING)
	public Set<GamePost> gamesPosted;


	public User(String facebookId) {
		this.facebookId = facebookId;
	}

	public User() {
	}

	public void postGame(String gameId) {
        GamePost gamePost = new GamePost(facebookId, gameId);
		gamesPosted.add(gamePost);
	}

	@Override
	public String toString() {
		return "User{facebookId="+facebookId+",lat="+lat+",lon="+lon+"}";
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
        this.updateWkt();
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
        this.updateWkt();
    }

    public String getWkt() {
        return wkt;
    }

    private void updateWkt()
    {
        this.wkt = String.format("POINT( %.2f %.2f )", this.getLon(), this.getLat());
    }

    public void setWkt(double lon, double lat)
    {
        this.setLon(lon);
        this.setLat(lat);
        this.updateWkt();
    }

    public Set<GamePost> getGamesPosted() {
        return gamesPosted;
    }

    public void setGamesPosted(Set<GamePost> gamesPosted) {
        this.gamesPosted = gamesPosted;
    }


}




