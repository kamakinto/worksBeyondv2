package worksBeyond.model;

import java.util.Date;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
@NodeEntity
public class Post {
	@GraphId Long nodeId;
	@Indexed
	String postId;
	String userName;
	Byte[] userPicture;
	String userFullName;
	
	
	public Byte[] getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(Byte[] userPicture) {
		this.userPicture = userPicture;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	Date postCreation;
	String content;
	User user; 
	
	//A Post will be able to have User generated comments connected to them
	@RelatedTo(type="HAS_COMMENTS", direction=Direction.OUTGOING)
	Set<Comment> postComments;
	
	//The User who wrote the status
	@RelatedTo(type="WROTE_STATUS", direction=Direction.INCOMING)
	User statusPost;
	
	//The User who wrote the Post (refers to Posts associated with users)
	@RelatedTo(type="WROTE_USER_POST", direction=Direction.INCOMING)
	User userPostToUser;
	
	@RelatedTo(type="HAS_USER_POST", direction=Direction.INCOMING)
	User userRecievingPost;
	
	@RelatedTo(type="WROTE_DOCUMENT_POST", direction=Direction.INCOMING)
	User userPostToDocument;
	
	
	@RelatedTo(type="HAS_DOCUMENT_POST", direction=Direction.INCOMING)
	Document documentsPost;
	
	@RelatedTo(type="HAS_POST_LIKES", direction=Direction.INCOMING)
	Set<User> userLikes;
	
	public void addPostComments(Comment comment){
		this.postComments.add(comment);
	}
	public void addUserLikes(User user){
		this.userLikes.add(user);
	}
	public Set<Comment> getPostComments() {
		return postComments;
	}
	public void setPostComments(Set<Comment> postComments) {
		this.postComments = postComments;
	}

	public User getStatusPost() {
		return statusPost;
	}
	public void setStatusPost(User statusPost) {
		this.statusPost = statusPost;
	}
	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public Date getPostCreation() {
		return postCreation;
	}
	public void setPostCreation(Date postCreation) {
		this.postCreation = postCreation;
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
	
	public User getUserPostToUser() {
		return userPostToUser;
	}
	public void setUserPostToUser(User userPostToUser) {
		this.userPostToUser = userPostToUser;
	}
	public User getUserRecievingPost() {
		return userRecievingPost;
	}
	public void setUserRecievingPost(User userRecievingPost) {
		this.userRecievingPost = userRecievingPost;
	}
	public User getUserPostToDocument() {
		return userPostToDocument;
	}
	public void setUserPostToDocument(User userPostToDocument) {
		this.userPostToDocument = userPostToDocument;
	}
	public Document getDocumentsPost() {
		return documentsPost;
	}
	public void setDocumentsPost(Document documentsPost) {
		this.documentsPost = documentsPost;
	}
	public Set<User> getUserLikes() {
		return userLikes;
	}
	public void setUserLikes(Set<User> userLikes) {
		this.userLikes = userLikes;
	}
	public void addComment(Comment comment){
		this.postComments.add(comment);
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

}
