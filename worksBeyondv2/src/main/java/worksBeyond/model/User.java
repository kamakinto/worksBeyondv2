package worksBeyond.model;


import java.util.Date;
import java.util.Set;

//import org.neo4j.cypherdsl.query.Direction;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.neo4j.graphdb.Direction;


@NodeEntity
public class User {
	
	@GraphId Long nodeId;
	@Indexed
	String userId;
	String username;
	String firstName;
	String lastName;
	String email;
	String aboutMe;
	String Quote;
	String favoriteBook;
	int age;
	Date userCreation;
	String sex;
	String password;
	Role role;
	byte[] picture;
	

	@RelatedTo(type="FRIENDS_WITH", direction=Direction.BOTH)
	@Fetch
	Set<User> friends;
	

	@RelatedTo(type="HAS_FRIEND_REQUEST")
	@Fetch
	Set<User> startNodeForUsersRequestingMe;
	
	@RelatedTo(type="HAS_FRIEND_REQUEST", direction=Direction.INCOMING)
	@Fetch
	Set<User> UsersWhoHaveRequestedMe;
	
	
	public void addStartNodeForUsersRequestingMe(User user, boolean flag){
		if(flag){
			this.startNodeForUsersRequestingMe.add(user);
		}else{
			this.startNodeForUsersRequestingMe.remove(user);
		}
		
	}
	public void addUsersWhoHaveRequestedMe(User user, boolean flag){
		if(flag){
			this.UsersWhoHaveRequestedMe.add(user);
		}else{
			this.UsersWhoHaveRequestedMe.remove(user);
		}
		
	}
	

	
	//list of the user's status updates
	@RelatedTo(type="WROTE_STATUS", direction= Direction.OUTGOING)
	@Fetch
	Set<Post> statusPosts;
	
	//list of the user's favorited documents
	@RelatedTo(type="HAS_FAVORITE_DOCUMENT", direction=Direction.OUTGOING)
	@Fetch
	Set<Document> favorites;
	
	//List of comments the user has written
	@RelatedTo(type="WROTE_COMMENT", direction=Direction.OUTGOING)
	@Fetch
	Set<Comment> userComments;
	
	//List of Documents the user has written
	@RelatedTo(type="AUTHORED_DOCUMENT", direction=Direction.OUTGOING)
	@Fetch
	Set<Document> authoredDocuments;
	
	//List of Posts the user has added to documents
	@RelatedTo(type="WROTE_DOCUMENT_POST",direction=Direction.OUTGOING)
	@Fetch
	Set<Post> userDocumentPosts;
	
	//List of Posts the user has written to another User
	@RelatedTo(type="WROTE_USER_POST",direction=Direction.OUTGOING)
	@Fetch
	Set<Post> userToUserPost;
	
	//List of Posts written to the User
	@RelatedTo(type="HAS_USER_POST", direction=Direction.INCOMING)
	@Fetch
	Set<Post> postsUserRecieves;
	
	//List of Documents the User has liked
	@RelatedTo(type="HAS_DOCUMENT_LIKES",direction=Direction.OUTGOING)
	@Fetch
	Set<Document> likesDocuments;
	
	//List of Comments the User has liked
	@RelatedTo(type="HAS_COMMENT_LIKES",direction=Direction.OUTGOING)
	@Fetch
	Set<Comment> likesComment;
	
	//List of Posts the User has liked
	@RelatedTo(type="HAS_POST_LIKES",direction=Direction.OUTGOING)
	@Fetch
	Set<Post> likesPost;
	
	public Date getUserCreation() {
		return userCreation;
	}
	public void setUserCreation(Date userCreation) {
		this.userCreation = userCreation;
	}

	public User(String firstName, String LastName){
		setFirstName(firstName);
		setLastName(LastName);
	}
	
	public enum Role{
		GUEST, ROLE_USER, ROLE_ADMIN
	}
	public User(){
		
	}
	public void befriend(User friend, boolean flag){
		if(flag){
			this.friends.add(friend);	
		}else{
			this.friends.remove(friend);
		}
		
	}
	public void addStatusPost(Post post){
		this.statusPosts.add(post);
		System.out.println("list of posts on user: "+ this.statusPosts.toString());
		
	}
	public Set<Post> getStatusPosts() {
		return statusPosts;
	}
	public void setStatusPosts(Set<Post> statusPosts) {
		this.statusPosts = statusPosts;
	}
	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
	
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		
		this.role = role;
	
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getQuote() {
		return Quote;
	}
	public void setQuote(String quote) {
		Quote = quote;
	}
	public String getFavoriteBook() {
		return favoriteBook;
	}
	public void setFavoriteBook(String favoriteBook) {
		this.favoriteBook = favoriteBook;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
	public Set<User> getFriends() {
		return friends;
	}
	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	
	public Set<Document> getFavorites() {
		return favorites;
	}
	public void setFavorites(Set<Document> favorites) {
		this.favorites = favorites;
	}
	public Set<Comment> getUserComments() {
		return userComments;
	}
	public void setUserComments(Set<Comment> userComments) {
		this.userComments = userComments;
	}
	public Set<Document> getAuthoredDocuments() {
		return authoredDocuments;
	}
	public void setAuthoredDocuments(Set<Document> authoredDocuments) {
		this.authoredDocuments = authoredDocuments;
	}
	public Set<Post> getUserDocumentPosts() {
		return userDocumentPosts;
	}
	public void setUserDocumentPosts(Set<Post> userDocumentPosts) {
		this.userDocumentPosts = userDocumentPosts;
	}
	public Set<Post> getUserToUserPost() {
		return userToUserPost;
	}
	public void setUserToUserPost(Set<Post> userToUserPost) {
		this.userToUserPost = userToUserPost;
	}
	
	public void addFavorites(Document doc){
		this.favorites.add(doc);
	}
	public void addUserComments(Comment comment){
		this.userComments.add(comment);
	}
	public void addAuthoredDocuments(Document doc){
		this.authoredDocuments.add(doc);
	}
	public void addUserDocumentPosts(Post post){
		this.userDocumentPosts.add(post);
	}
	public void addUserToUserPost(Post post){
		this.userToUserPost.add(post);
	}
	public void addLikesDocuments(Document doc){
		this.likesDocuments.add(doc);
	}
	public void addPostsUserRecieves(Post post){
		this.postsUserRecieves.add(post);
	}
	public void addLikesPost(Post post){
		this.likesPost.add(post);
	}
	public Set<Document> getLikesDocuments() {
		return likesDocuments;
	}
	public void setLikesDocuments(Set<Document> likesDocuments) {
		this.likesDocuments = likesDocuments;
	}
	public Set<Comment> getLikesComment() {
		return likesComment;
	}
	public void setLikesComment(Set<Comment> likesComment) {
		this.likesComment = likesComment;
	}
	public Set<Post> getLikesPost() {
		return likesPost;
	}
	public Set<Post> getPostsUserRecieves() {
		return postsUserRecieves;
	}
	public void setPostsUserRecieves(Set<Post> postsUserRecieves) {
		this.postsUserRecieves = postsUserRecieves;
	}
	public void setLikesPost(Set<Post> likesPost) {
		this.likesPost = likesPost;
	}
	
	public Set<User> getStartNodeForUsersRequestingMe() {
		return startNodeForUsersRequestingMe;
	}
	public void setStartNodeForUsersRequestingMe(
			Set<User> startNodeForUsersRequestingMe) {
		this.startNodeForUsersRequestingMe = startNodeForUsersRequestingMe;
	}
	public Set<User> getUsersWhoHaveRequestedMe() {
		return UsersWhoHaveRequestedMe;
	}
	public void setUsersWhoHaveRequestedMe(Set<User> usersWhoHaveRequestedMe) {
		UsersWhoHaveRequestedMe = usersWhoHaveRequestedMe;
	}
	@Override
	public String toString() {
		return "User [nodeId=" + nodeId + ", username=" + username
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", age=" + age + "]";
	}
	

}
