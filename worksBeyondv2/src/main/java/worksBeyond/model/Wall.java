package worksBeyond.model;

import java.util.Set;
import worksBeyond.model.Comment;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;


@NodeEntity
public class Wall {
	
	@GraphId Long nodeId;
	@Indexed
	String wallId;
	String wallName;
	
	@RelatedTo(type="HAS_WALL", direction=Direction.INCOMING)
	User user;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@RelatedTo(type = "HAS_STATUS", direction=Direction.OUTGOING)
	@Fetch
	Set<Post> ProfileStatusPosts;
	
	
	@RelatedTo(type = "HAS_POST", direction=Direction.OUTGOING)
	@Fetch
	Set<Post> UserPosts;
	
	@RelatedTo(type = "HAS_DOCUMENT_COMMENT", direction=Direction.OUTGOING)
	@Fetch
	Set<Comment> documentComments;
	


	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getWallId() {
		return wallId;
	}

	public void setWallId(String wallId) {
		this.wallId = wallId;
	}

	public String getWallName() {
		return wallName;
	}

	public void setWallName(String wallName) {
		this.wallName = wallName;
	}

	public Set<Post> getProfileStatusPosts() {
		return ProfileStatusPosts;
	}

	public void setProfileStatusPosts(Set<Post> profileStatusPosts) {
		ProfileStatusPosts = profileStatusPosts;
	}

	public Set<Post> getUserPosts() {
		return UserPosts;
	}

	public void setUserPosts(Set<Post> userPosts) {
		UserPosts = userPosts;
	}

	public Set<Comment> getDocumentComments() {
		return documentComments;
	}

	public void setDocumentComments(Set<Comment> documentComments) {
		this.documentComments = documentComments;
	}
	
	public void addStatusPost(Post status){
		this.ProfileStatusPosts.add(status);
	}
	
	public void addUserPost(Post post){
		this.UserPosts.add(post);
	}
	public void addDocumentComment(Comment comment){
		this.documentComments.add(comment);
	}

}
