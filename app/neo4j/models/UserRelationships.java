package neo4j.models;

import org.neo4j.graphdb.RelationshipType;

/**
 * Created by muditgoel on 7/1/14.
 */
public enum UserRelationships implements RelationshipType {
    POSTED,
    REQUESTED
}
