package neo4j.models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.expression.spel.ast.StringLiteral;

import java.util.Set;

@NodeEntity
public class Game extends AbstractNode {

    @Indexed
    private Long id;

    @Indexed
    private String title;

    @Indexed
    private String console;

    @Indexed
    private int year;


	@Fetch
	@RelatedTo(type = "UserRelationships.POSTED", direction = Direction.OUTGOING)
	public Set<GamePost> gamesPosted;

	public Game(String title, String console, int year) {
		this.title = title;
        this.console = console;
        this.year = year;
	}

	public Game() {
	}

	@Override
	public String toString() {
		return "Game{id="+id+",title="+title+",console="+console+",year="+year+"}";
	}

}