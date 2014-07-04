package neo4j.models;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.neo4j.annotation.GraphId;


public abstract class AbstractNode {

	@GraphId
	public Long id;

    @CreatedDate
    private Long createdDate;

    @LastModifiedDate
    private Long lastModifiedDate;


}