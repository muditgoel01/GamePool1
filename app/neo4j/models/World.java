package neo4j.models;

import org.geotools.data.shapefile.indexed.IndexType;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

@NodeEntity
public class World extends AbstractNode {

	@Indexed
	public String name;

	@Indexed
	public int moons;

    @Indexed
    public Double lat;

    @Indexed
    public Double lon;

	@Fetch
	@RelatedTo(type = "REACHABLE_BY_ROCKET", direction = Direction.OUTGOING)
	public Set<World> reachableByRocket;

	public World(String name, int moons) {
		this.name = name;
		this.moons = moons;
        this.lat = 13.76;
        this.lon = 55.56;
	}

	public World() {
	}

	public void addRocketRouteTo(World otherWorld) {
		reachableByRocket.add(otherWorld);
	}

	@Override
	public String toString() {
		return String.format("World{name='%s', moons=%d}", name, moons);
	}

	public enum RelTypes implements RelationshipType {
		REACHABLE_BY_ROCKET
	}
}