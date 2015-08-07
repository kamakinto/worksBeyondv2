package worksBeyond.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import worksBeyond.model.Post;
import worksBeyond.model.User;
import worksBeyond.model.Wall;
import worksBeyond.neo4jRepository.CommentRepository;
import worksBeyond.neo4jRepository.DocumentRepository;
import worksBeyond.neo4jRepository.PostRepository;
import worksBeyond.neo4jRepository.UserRepository;
import worksBeyond.neo4jRepository.WallRepository;

@Service
public class PostService{
	
	@Autowired UserRepository userRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired DocumentRepository documentRepository;
	@Autowired PostRepository postRepository;
	@Autowired WallRepository wallRepository;
	@Autowired Neo4jOperations template;
	
	
	public void save(Post post){
		postRepository.save(post);
	}
	
	public void delete(String postId){
		Post postToDelete = postRepository.findByPostId(postId);
		postRepository.delete(postToDelete);
	}
		
public void addStatus(User user, Post post){
	try{
		user.addStatusPost(post);
		userRepository.save(user);
	}catch(Exception e){
		e.printStackTrace();
	}
}
	
	public List<Post> getUserWall(User user){
		Iterable<Post> p = null;
			List<Post> posts = new ArrayList<Post>();
			try{
				p = userRepository.getUserWall(user);
				for(Post post: p){
					posts.add(post);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		return posts;
	}
	
	
	public List<Post> getUserMiniFeed(User user){
		Iterable<Post> p = null;
			List<Post> posts = new ArrayList<Post>();
			try{
				p = postRepository.getMiniFeed(user);
				for(Post post: p){
					posts.add(post);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			Collections.sort(posts, Collections.reverseOrder(new Comparator<Post>() {
				public int compare(Post p1, Post p2){
					return p1.getPostCreation().compareTo(p2.getPostCreation());
				}
				
			}));
			
		return posts;
	}

}
