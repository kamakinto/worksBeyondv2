package worksBeyond.neo4jRepository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import worksBeyond.model.Document;

@Repository
public interface CommentRepository extends GraphRepository<Document> {
	
	@Query("START document=node(*) "
			+ "WHERE document.name=~ {0} "
			+ "RETURN document;")
	List<Document> findByNames(String publicationName);	
	
	//List<Document> findByNameLike(String publicationName);

}
