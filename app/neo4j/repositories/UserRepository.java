package neo4j.repositories;

import neo4j.models.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRepository extends GraphRepository<User>, SpatialRepository<User> {

    public static final String USER_GEOSPATIAL_INDEX = "userLocation";

    @Query("MATCH (m:MUSEUM) WHERE m.name={name} RETURN distinct(m) as museum")
    User findUserByName(@Param("name") String name);

    @Query("MATCH (a:ARTIST)<-[:AUTHOR]-(p:ARTWORK)-[:OFFICIAL_LOCATION]->(m:MUSEUM) WHERE id(a)={artistId} RETURN distinct(m) as museum")
    List<User> findUserByArtist(@Param("artistId") long artistId);
}