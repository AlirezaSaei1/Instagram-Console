import java.util.*;

public class Post{
    String post;
    Person poster;
    ArrayList<Person> liked = new ArrayList<>();
    HashMap<String, ArrayList<String>> comments = new HashMap<>();

    Post(String post, Person poster){
        this.post = post;
        this.poster = poster;
    }
}
