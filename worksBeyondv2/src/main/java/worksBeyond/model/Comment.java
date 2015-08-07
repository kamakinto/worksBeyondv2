package worksBeyond.model;

import java.util.Date;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;


@NodeEntity
public class Comment {
	@GraphId Long nodeId;
	
	@Indexed
	String commentId;
	
	Date commentCreation;
	String content;
	User user;
	
	@RelatedTo(type="HAS_COMMENTS", direction=Direction.INCOMING)
	Post userPost;
	
	@RelatedTo(type="WROTE_COMMENT", direction=Direction.INCOMING)
	User UserComment;
	
	@RelatedTo(type="HAS_COMMENT_LIKES",direction=Direction.INCOMING)
	Set<User> userLikes;
	
	public void addUserLike(User user){
		this.userLikes.add(user);
	}
	
	public Post getUserPost() {
		return userPost;
	}
	public void setUserPost(Post userPost) {
		this.userPost = userPost;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public Date getCommentCreation() {
		return commentCreation;
	}
	public void setCommentCreation(Date commentCreation) {
		this.commentCreation = commentCreation;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUserComment() {
		return UserComment;
	}
	public void setUserComment(User userComment) {
		UserComment = userComment;
	}
	public Set<User> getUserLikes() {
		return userLikes;
	}
	public void setUserLikes(Set<User> userLikes) {
		this.userLikes = userLikes;
	} 
	

}
