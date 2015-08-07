package worksBeyond.model;

import java.sql.Date;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;


@NodeEntity
public class Document {
	@GraphId Long nodeId;
	@Indexed
	private Integer id;
	private String name;
    private String author;
    private String excerpt;
    private String description;
    private String filename;
    private byte[] content;
    private String contentType;
    private Date created;
    
 
    @RelatedTo(type="HAS_FAVORITE_DOCUMENT", direction=Direction.INCOMING)
    Set<User> userFavorites;
    
    @RelatedTo(type="AUTHORED_DOCUMENT",direction=Direction.INCOMING)
    Set<User> documentAuthors;
    
   @RelatedTo(type="HAS_DOCUMENT_LIKES",direction=Direction.INCOMING)
   Set<User> userLikes;
    
   @RelatedTo(type="HAS_DOCUMENT_POSTS", direction=Direction.OUTGOING)
	Set<Post> documentsPosts;
   
   public void addUserFavorites(User user){
	   this.userFavorites.add(user);
   }
   public void addDocumentAuthor(User user){
	   this.documentAuthors.add(user);
   }
   public void addUserLikes(User user){
	   this.userLikes.add(user);
   }
   public void addDocumentPost(Post post){
	   this.documentsPosts.add(post);
   }
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public Set<User> getUserFavorites() {
		return userFavorites;
	}

	public void setUserFavorites(Set<User> userFavorites) {
		this.userFavorites = userFavorites;
	}

	public Set<User> getDocumentAuthors() {
		return documentAuthors;
	}

	public void setDocumentAuthors(Set<User> documentAuthors) {
		this.documentAuthors = documentAuthors;
	}

	public Set<User> getUserLikes() {
		return userLikes;
	}

	public void setUserLikes(Set<User> userLikes) {
		this.userLikes = userLikes;
	}

	public Set<Post> getDocumentsPosts() {
		return documentsPosts;
	}

	public void setDocumentsPosts(Set<Post> documentsPosts) {
		this.documentsPosts = documentsPosts;
	}

}
