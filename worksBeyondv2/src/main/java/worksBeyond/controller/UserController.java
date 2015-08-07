package worksBeyond.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import worksBeyond.Service.PostService;
import worksBeyond.Service.UserService;
import worksBeyond.model.Post;
import worksBeyond.model.User;
import worksBeyond.model.User.Role;
import worksBeyond.model.Wall;
import worksBeyond.neo4jRepository.PostRepository;
import worksBeyond.neo4jRepository.WallRepository;



@Controller
@Transactional
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private WallRepository wallRepository;
	
	@RequestMapping(value= "/registerUser")
	public String index(Map<String, Object> map){
		
		try{
			map.put("user", new User());
			map.put("userList", userService.getAllUsers());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "registerUser";
	}
	
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String saveUser(
			@ModelAttribute(value="user") User user){
		
		//Set user Id
		String userId = UUID.randomUUID().toString() ;
		userId = "usr" + userId;
		user.setUserId(userId);
		//Adjust Role
		if(user.getEmail().contains("ADMIN")){
			user.setRole(Role.ROLE_ADMIN);
		}else{
			user.setRole(Role.ROLE_USER);
		}
		//Set Node Creation timestamp
		Date nodeCreation = new Date();
		user.setUserCreation(nodeCreation);
		
		//encrypt password
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
			try{
			userService.save(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/registerUser";
	}
	
	@RequestMapping(value = "/remove/user/{userId}")
	public String removeUser(@PathVariable("userId") String userId){
		userService.delete(userId);
		return "redirect:/registerUser";
	}
	
	//TODO: ADD @PreAuthorization(@currentUserAccess.canEditUser(principal, #id)")
	@RequestMapping(value = "/remove/post/{postId}")
	public String removePost(@PathVariable("postId") String postId){
		postService.delete(postId);
		return "redirect:/Profile";
	}
	@RequestMapping(value="/approve/friend/{friendId}")
	public String approveFriend(@PathVariable("friendId") String friendId){
		User me = userService.findByEmail(userService.getAuthenticatedUser().getName());
		userService.removeOldRequests(friendId, me);
		userService.approveFriendship(friendId, me);
		return "redirect:/friends";
	}
	
	@RequestMapping(value="/remove/friend/{friendId}")
	public String removePendingFriend(@PathVariable("friendId") String friendId){
		User me = userService.findByEmail(userService.getAuthenticatedUser().getName());
		userService.removeOldRequests(friendId, me);
		return "redirect:/friends";
	}
	@RequestMapping(value = "/request/friend/{userId}")
	public String addFriend(@PathVariable("userId") String potentialFriendId){
		 User me = userService.findByEmail(userService.getAuthenticatedUser().getName());
			userService.requestFriendship(potentialFriendId, me);
		return "redirect:/friends";
	}
	
	 @RequestMapping(value= "/UserProfile")
	    public ModelAndView Userprofilepage(){
		 ModelAndView mav = new ModelAndView();
		 User user = userService.findByEmail(userService.getAuthenticatedUser().getName());
		 mav.addObject("user", user);
		// mav.addObject("isAuthenticated", userService.getAuthenticatedUser().isAuthenticated());
		// mav.addObject("friendsList", friendsList);
		// mav.addObject("wallPosts", wallPosts);
		 mav.setViewName("UserProfile");
		 return mav;
	    }  
	 
	
	 @RequestMapping(value = "/postStatus", method = RequestMethod.POST)
		public String postStatus(
				@ModelAttribute(value="status") Post status){
			
			//Set status Id
			String postId = UUID.randomUUID().toString() ;
			postId = "stat" + postId;
			status.setPostId(postId);;
		
			//Set Node Creation timestamp
			Date nodeCreation = new Date();
			status.setPostCreation(nodeCreation);
			
			//Set Name
			try{
				postRepository.save(status);
			}catch(Exception e){
				e.printStackTrace();
			}
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userService.findByEmail(auth.getName());
			userService.addStatus(user, status);
			return "redirect:/Profile";
	 }
	
	 @RequestMapping(value= "/Profile")
	    public ModelAndView profilee(ModelMap model){
		 ModelAndView mav = new ModelAndView();
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 User user = userService.findByEmail(auth.getName());
		 
		 
		 mav.addObject("user", user);
		 mav.addObject("status", new Post());
		// mav.addObject("friendsList", friendsList);
		 mav.addObject("profileWallPosts", userService.getUserWall(user));
		 mav.setViewName("Profile");
		 return mav;
	    }  
	 
	 @RequestMapping(value= "/NewsFeed")
	    public ModelAndView newsFeed(){
		 ModelAndView mav = new ModelAndView();
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 User user = userService.findByEmail(auth.getName());
		 List<Post> miniFeed = postService.getUserMiniFeed(user);
		 mav.addObject("user", user);
		// mav.addObject("friendsList", friendsList);
		// mav.addObject("wallPosts", wallPosts);
		 mav.addObject("miniFeed", miniFeed);
		 mav.setViewName("NewsFeed");
		 return mav;
	    }  
	 
	 @RequestMapping(value= "/userPublications")
	    public ModelAndView userPublications(){
		 ModelAndView mav = new ModelAndView();
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 User user = userService.findByEmail(auth.getName());
		 mav.addObject("user", user);
		// mav.addObject("friendsList", friendsList);
		// mav.addObject("wallPosts", wallPosts);
		 mav.setViewName("userPublications");
		 return mav;
	 }
	 
	 @RequestMapping(value= "/friends")
	    public ModelAndView friends(){
		 ModelAndView mav = new ModelAndView();
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 User user = userService.findByEmail(auth.getName());
		 mav.addObject("user", user);
		 mav.addObject("friendsList",  userService.getFriendsList(user));
		 mav.addObject("friendRequests", userService.getFriendRequests(user));
		 mav.addObject("allUsers", userService.getAllUsersExceptMeAndFriends(user));
		// mav.addObject("wallPosts", wallPosts);
		 mav.setViewName("friends");
		 return mav;
	    }  
	 
	 
	 @RequestMapping(value= "/messages")
	    public ModelAndView messages(){
		 ModelAndView mav = new ModelAndView();
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 User user = userService.findByEmail(auth.getName());
		 mav.addObject("user", user);
		// mav.addObject("friendsList", friendsList);
		// mav.addObject("wallPosts", wallPosts);
		 mav.setViewName("messages");
		 return mav;
	    }  
	
			
	
	
	
	

}
