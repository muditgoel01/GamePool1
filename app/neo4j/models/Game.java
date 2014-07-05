package neo4j.models;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.expression.spel.ast.StringLiteral;

import java.util.Set;

@NodeEntity
@TypeAlias("Game")
public class Game extends AbstractNode {

    @Indexed
    private String title;

    @Indexed
    private String console;

    @Indexed
    private int year;


//	@Fetch
//	@RelatedTo(type = "GameEdge.POSTED", direction = Direction.INCOMING)
//	public Set<GamePost> postsForGame;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}