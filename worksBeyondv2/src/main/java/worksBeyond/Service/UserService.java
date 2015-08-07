package worksBeyond.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class UserService{
	
	@Autowired UserRepository userRepository;
	@Autowired CommentRepository commentRepository;
	@Autowired DocumentRepository documentRepository;
	@Autowired PostRepository postRepository;
	@Autowired WallRepository wallRepository;
	@Autowired Neo4jOperations template;
	
	
	public void save(User user){
		userRepository.save(user);
	}
	
	public void delete(String userId){
		User userToDelete = userRepository.findByUserId(userId);
	    userRepository.delete(userToDelete);
	}
	
	public Authentication getAuthenticatedUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}
	
	public List<User> getFriendsList(User user){
		
		Iterable<User> friends = null;
		List<User> friendsList = new ArrayList<User>();
		try{
			friends = userRepository.getUserFriendsList(user);
			for(User friend: friends){
				friendsList.add(friend);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	return friendsList;
	}
	public List<User> getFriendRequests(User user){
		Iterable<User> requests = null;
		List<User> friendRequests = new ArrayList<User>();
		try{
			requests = userRepository.getUserFriendRequests(user);
			for(User friend: requests){
				friendRequests.add(friend);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return friendRequests;
	}
	
	public User get(Long id){
		return userRepository.findOne(id);
	}
	
	public List<User> getAllUsers(){
		Iterable<User> u = null;
		List<User> users = new ArrayList<User>();
		try{
			u = userRepository.findAll();
			for(User user: u){
				users.add(user);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return users;
	}
	
	
	public List<User> getAllUsersExceptMeAndFriends(User me){
		Iterable<User> u = null;
		List<User> users = new ArrayList<User>();
		try{
			u = userRepository.getAllUsersExceptMe(me);
		//adds everyone except the currently logged in user
			for(User user: u){
				if(user.getUserId() != me.getUserId()){
					users.add(user);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return users;
	}
	
	
	
	
	
	
public void addStatus(User user, Post post){
	post.setUserFullName(user.getFirstName() + " " + user.getLastName());
	post.setUserName(user.getUsername());
	try{
		user.addStatusPost(post);
		userRepository.save(user);
	}catch(Exception e){
		e.printStackTrace();
	}

}
public void requestFriendship(String potentialFriendId, User me){
	try{
		User potentialfriend = userRepository.findByUserId(potentialFriendId);
		me.addStartNodeForUsersRequestingMe(potentialfriend, true);
		template.save(me);
	}catch(Exception e){
		e.printStackTrace();
	}
	
}
public void removeOldRequests(String friendId, User me){
	User friend = userRepository.findByUserId(friendId);
	try{
		template.deleteRelationshipBetween(friend, me, "HAS_FRIEND_REQUEST");
	}catch(Exception e){
		e.printStackTrace();
	}
	
}
public void approveFriendship(String friendId, User me){
	try{
		User friend = userRepository.findByUserId(friendId);
		me  = userRepository.findByUserId(me.getUserId());
		if(!me.equals(friend)){
			me.befriend(friend, true);
			userRepository.save(me);
		}
		}catch(Exception e){
		e.printStackTrace();
	}
	
	
	
}
	public User findByUserId(String userId){
		User user = userRepository.findByUserId(userId);
		if(user == null){
			System.out.println("User Doesnt Exist!");
			return null;
		}else{
			return user;
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
	
	public User findUserByFirstName(String value){
		
		User result = userRepository.findByFirstName(value);
		return result;
	}
	
public User findByEmail(String value){
		User result = userRepository.findByEmail(value);
		return result;
	}
}
