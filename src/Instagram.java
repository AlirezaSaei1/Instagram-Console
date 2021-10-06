import java.util.*;

public class Instagram{
    public HashMap< String, Person> allUsers = new HashMap<>();
    public Set<String> Users = new HashSet<>();
    public ArrayList<Post> allPosts = new ArrayList<>();

    void showUsers(){
        ArrayList<Person> pList = new ArrayList<>();
        for (String id : Users){
            pList.add(allUsers.get(id));
        }
        try {
            pList.sort(Person::compareTo);
        }catch (ArithmeticException e){
            System.out.println(e.getMessage());
            System.out.println("Could Not Sort\nUnsorted List: ");
        }
        for (int i=0; i<pList.size(); i++){
            System.out.printf("(%d) ID: %8s        Followers: %3d          Followings: %3d", i + 1, pList.get(i).ID, pList.get(i).followers.size(), pList.get(i).followings.size());
            System.out.println();
        }
        if(pList.size()==0){
            System.out.println("No Users!");
        }
    }
}
