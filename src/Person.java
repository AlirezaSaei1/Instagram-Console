import java.util.*;


public class Person implements Comparable{
    String ID;
    String phoneNumber;
    Set<Person> followers = new HashSet<>();
    Set<Person> followings = new HashSet<>();
    ArrayList<Post> posts = new ArrayList<>();
    boolean PV;
    Person(String ID, String phoneNumber, boolean PV) throws IdExists {
        Main.instagram.Users.add(ID);
        this.ID = ID;
        this.phoneNumber = phoneNumber;
        this.PV = PV;
    }

    @Override
    public int compareTo(Object o) {
        double ratio1 = (this.followers.size()  / this.followings.size()) * 100;
        double ratio2 = (((Person) o).followers.size()  / ((Person) o).followings.size()) * 100;
        return (int) (ratio2- ratio1);
    }
}