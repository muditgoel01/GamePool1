package neo4j.repositories;

import neo4j.models.User;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends GraphRepository<User>, SpatialRepository<User> {

    public static final String USER_GEOSPATIAL_INDEX = "userLocation";

    @Query("MATCH (u:User) WHERE u.id={id} RETURN distinct(u) as user")
    User findUserById(@Param("id") Long id);

    @Query("MATCH (u:User) WHERE u.facebookId={facebookId} RETURN distinct(u) as user")
    User findUserByFacebookId(@Param("facebookId") String facebookId);

    @Query("MATCH (u:User) WHERE u.name={name} RETURN distinct(u) as user")
    List<User> findUsersByName(@Param("name") String name);

    // @Query("MATCH (a:ARTIST)<-[:AUTHOR]-(p:ARTWORK)-[:OFFICIAL_LOCATION]->(m:MUSEUM) WHERE id(a)={artistId} RETURN distinct(m) as museum")
    // List<User> findMuseumByArtist(@Param("artistId") long artistId);


}