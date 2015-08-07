package worksBeyond.neo4jRepository;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;



import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.stereotype.Repository;

import worksBeyond.model.Post;
import worksBeyond.model.User;

@Repository
public interface UserRepository extends GraphRepository<User>,RelationshipOperationsRepository<User>{
	User findByFirstName(String name);
	
	User findByUserId(String userid);
	 
	User findByEmail(String username);
	
	
	
	
	@Query("START user=node({0})"
			+ "MATCH (user)-[r]->(p)"
			+ "return p "
			+ "ORDER BY p.postCreation desc;")
Iterable<Post> getUserWall(User user);

	
@Query("START user=node({0})"
		+ "MATCH (user)-[:FRIENDS_WITH]-(friend)"
		+ "return friend;")
Iterable<User> getUserFriendsList(User user);


@Query("START user=node({0})"
		+"MATCH (user)<-[:HAS_FRIEND_REQUEST]-(friend)"
		+ "return friend;")
Iterable<User> getUserFriendRequests(User user);

@Query("START user=node({0})"
		+ "MATCH (n:User)"
		+ "WHERE NOT (user)-[:FRIENDS_WITH]-(n) "
		+ "return n;")
Iterable<User> getAllUsersExceptMe(User user);




}
