package worksBeyond.neo4jRepository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import worksBeyond.model.Document;
import worksBeyond.model.Wall;

@Repository
public interface WallRepository extends GraphRepository<Wall> {
	
	
	//List<Document> findByNameLike(String publicationName);

}
