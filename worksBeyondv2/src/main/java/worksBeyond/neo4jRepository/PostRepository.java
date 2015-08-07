package worksBeyond.neo4jRepository;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import worksBeyond.model.Post;
import worksBeyond.model.User;

@Repository
public interface PostRepository extends GraphRepository<Post>{
	
	Post findByPostId(String postId);
	
	
	@Query("START user=node({0}) "
			+"MATCH (user)-[:FRIENDS_WITH]-(friend)-[:WROTE_STATUS]->(fpost)"
			+ "RETURN fpost as allPosts"
			+ " ORDER BY allPosts.postCreation desc"
			+ " UNION "
			+ "START user=node({0}) "
			+ "MATCH (user)-[:WROTE_STATUS]->(post)"
			+ " RETURN post as allPosts"
			+ " ORDER BY allPosts.postCreation desc;")
	Iterable<Post> getMiniFeed(User user);

}
