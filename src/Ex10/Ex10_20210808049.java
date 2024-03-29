package Ex10;

import java.util.*;

public class Ex10_20210808049 {

}
class User{
    private int id;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private Map<User, Queue<Message>> messages;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.id = hashCode();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.likedPosts = new HashSet<>();
        this.messages = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Set<User> getFollowers() {
        return followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }
    public void message(User recipient, String content){
        if (!messages.containsKey(recipient)) {
            messages.put(recipient, new LinkedList<>());
            recipient.messages.put(this,new LinkedList<>());
        }
        Message newMessage = new Message(this, content);
        messages.get(recipient).add(newMessage);
        recipient.messages.get(this).add(newMessage);
        read(recipient);
    }

    public void read(User user) {
        if (messages.containsKey(user)) {
            Queue<Message> userMessages = messages.get(user);
            while (!userMessages.isEmpty()) {
                Message message = userMessages.poll();
                System.out.println(message);
            }
        }
    }
    public void follow(User user) {
        if (following.contains(user)) {
            following.remove(user);
            user.followers.remove(this);
        } else {
            following.add(user);
            user.followers.add(this);
        }
    }
    public void like(Post post) {
        if (likedPosts.contains(post)) {
            likedPosts.remove(post);
            post.likedBy(this);
        } else {
            likedPosts.add(post);
            post.likedBy(this);
        }
    }
    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return id == user.id;
    }

    public Post post(String content) {
        Post newPost = new Post(content);
        return newPost;
    }
    public Comment comment(Post post, String content){
        Comment newComment = new Comment(content);
        post.commentBy(this, newComment);
        return newComment;
    }
    public int hashCode() {
        return Objects.hash(email);
    }
}
class Message{
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.dateSent = new Date();
        this.seen = false;
    }
    public boolean hasRead() {
        return seen;
    }
    public String read(User reader) {
        if (!sender.equals(reader)) {
            seen = true;
        }
        System.out.println("Sent at: " + dateSent);
        return content;
    }

    public String toString(){
        return content;
    }
}
class Post{
    private Date datePosted;
    private Set<User> likes;
    private Map<User, ArrayList<Comment>> comments;
    private String content;
    Post(String content) {
        this.content = content;
        this.datePosted = new Date();
        this.likes = new HashSet<>();
        this.comments = new HashMap<>();
    }
    public boolean likedBy(User user) {
        if (likes.contains(user)) {
            likes.remove(user);
            return false;
        } else {
            likes.add(user);
            return true;
        }
    }
    public boolean commentBy(User user, Comment comment) {
        if (!comments.containsKey(user)) {
            comments.put(user, new ArrayList<>());
        } else
            comments.get(user).add(comment);
        return true;
    }
    public String getContent() {
        System.out.println("Posted at: " + datePosted);
        return content;
    }
    public Comment getComment(User user, int index){
        if(comments.containsKey(user)&& index < comments.get(user).size() && index>=0)
            return comments.get(user).get(index);
        return null;
    }
    public int getCommentCount() {
        int count = 0;
        for (ArrayList<Comment> userComments : comments.values()) {
            count += userComments.size();
        }
        return count;
    }
    public int getCommentCountByUser(User user){
        if(comments.containsKey(user))
            return comments.get(user).size();
        else
            return 0;
    }
}
class Comment extends Post{
    Comment(String content){
        super(content);
    }
}
class SocialNetwork{
    private static Map<User, ArrayList<Post>> postsByUsers = new HashMap<>();

    public static User register(String username, String email) {
        User newUser = new User(username, email);
        if (!postsByUsers.containsKey(newUser)) {
            postsByUsers.put(newUser, new ArrayList<>());
            return newUser;
        }else
            return null;
    }
    public static Post post(User user, String content) {
        if (postsByUsers.containsKey(user)) {
            Post newPost = new Post(content);
            postsByUsers.get(user).add(newPost);
            return newPost;
        }
        return null;
    }
    public static User getUser(String email) {
        int hashCode = Objects.hash(email);
        for (User user : postsByUsers.keySet()) {
            if (user.hashCode() == hashCode) {
                return user;
            }
        }
        return null;
    }
    public static Set<Post> getFeed(User user) {
        HashSet<Post> feed = new HashSet<>();
        Set<User> followedUsers = user.getFollowing();

        for (User followedUser : followedUsers){
            feed.addAll(postsByUsers.get(followedUser));
        }
        return feed;
    }

    public static Map<User, String> search(String keyword) {
        Map<User, String> result = new HashMap<>();
        for (User user : postsByUsers.keySet()) {
            if (user.getUsername().contains(keyword)) {
                result.put(user, user.getUsername());
            }
        }
        return result;
    }
    public static <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map) {
        Map<V, Set<K>> reversedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            reversedMap.put(value, new HashSet<>());
            reversedMap.get(value).add(key);
        }
        return reversedMap;
    }
}