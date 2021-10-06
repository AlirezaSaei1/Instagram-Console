import java.sql.SQLOutput;
import java.util.*;

public class Main {
    static Scanner s = new Scanner(System.in);
    static Instagram instagram = new Instagram();
    static boolean logOut = false;
    static boolean back = false;

    public static void main(String[] args) {
        try {
            //If a page is private you can only observe ID, nothing more!
            instagram.allUsers.put("Helen", new Person("Helen", "0999", false));
            instagram.allUsers.put("Frank", new Person("Frank", "0999", false));
            instagram.allUsers.put("Alex", new Person("Alex", "0998", false));
            instagram.allUsers.put("Samantha", new Person("Samantha", "0991", false));
            instagram.allUsers.put("John", new Person("John", "0936", false));
            instagram.allUsers.put("Derek", new Person("Derek", "0914", false));
            instagram.allUsers.put("Arthur", new Person("Arthur", "0922", false));
            instagram.allUsers.put("Russel", new Person("Russel", "0944", false));
        } catch (IdExists e) {
            System.out.println(e.getMessage());
        }
        Person user = null;
        while (true) {
            if (!back) {
                Menu1();
                user = getMenu1Input(s.nextInt());
            }
            if (user != null && !logOut) {
                Menu2();
                getMenu2Input(user, s.nextInt());
            }
            if (logOut) {
                break;
            }
        }
    }

    //First Menu
    static void Menu1() {
        System.out.println("\033[0;31m" + "Main Menu");
        System.out.println("\033[0;34m" + "1: Search By ID");
        System.out.println("2: Search Phone Number");
        System.out.println("3: Add New Person");
        System.out.println("4: Delete A User");
        System.out.println("5: Select A User");
        System.out.println("6: Show All Users");
        System.out.println("7: Quit");
        System.out.println("\033[0m" + "Enter A Number: ");
    }

    //After Login Menu
    static void Menu2() {
        System.out.println("\033[0;35m" + "(1) Home");
        System.out.println("(2) Show Account Information");
        System.out.println("(3) Follow Sb.");
        System.out.println("(4) UnFollow Sb.");
        System.out.println("(5) Create new Post");
        System.out.println("(6) My Posts");
        System.out.println("(7) Post Details");
        System.out.println("(8) Delete Post");
        System.out.println("(9) Back");
        System.out.println("\033[0m");
    }

    //Getting Input From Menu 1
    static Person getMenu1Input(int x) {
        Person p;
        try {
            //------------------------------Search By ID-----------------------------
            if (x == 1) {
                System.out.println("Enter A Part Of ID To Begin Search: ");
                showResultByID(s.next());
                //--------------------------Search Phone Number---------------------------
            } else if (x == 2) {
                System.out.println("Enter A Phone Number To Begin Search");
                String number = s.next();
                int exist = 1;
                System.out.println("All Account With Number : " + number);
                for (String key : instagram.Users) {
                    if (instagram.allUsers.get(key).phoneNumber.equals(number)) {
                        System.out.println("(" + exist++ + ") ID: " + instagram.allUsers.get(key).ID);
                    }
                }
                if (exist == 1) {
                    System.out.println("No Users");
                }
                //------------------------------New User---------------------------------
            } else if (x == 3) {
                try {
                    System.out.println("Enter ID: ");
                    String id = s.next();
                    if (!instagram.Users.add(id)) {
                        throw new IdExists("The ID You Entered Already Exists");
                    }
                    System.out.println("Enter Phone Number: ");
                    String number = s.next();
                    System.out.println("Is It Private: (y/n)");
                    if (s.next().equals("y")) {
                        Person person = new Person(id, number, true);
                        instagram.allUsers.put(id, person);
                    } else {
                        Person person = new Person(id, number, false);
                        instagram.allUsers.put(id, person);
                    }
                } catch (IdExists e) {
                    System.out.println(e.getMessage());
                }
                //--------------------------Delete User----------------------------------
            } else if (x == 4) {
                instagram.showUsers();
                System.out.println("Enter User ID to Confirm Delete: ");
                String confirmation = s.next();
                instagram.Users.remove(instagram.allUsers.get(confirmation).ID);
                instagram.allUsers.remove(confirmation);
                //-------------------------Select A User----------------------------------
            } else if (x == 5) {
                instagram.showUsers();
                System.out.println("Enter A User ID: ");
                p = instagram.allUsers.get(s.next());
                if (p == null) {
                    System.out.println("No Users Exist With That ID");
                } else {
                    return p;
                }
                //---------------------------Show All Users-------------------------------
            } else if (x == 6) {
                instagram.showUsers();
                //---------------------------------EXIT-----------------------------------
            } else if (x == 7) {
                logOut = true;
            } else {
                System.out.println("Invalid Input");
            }
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Getting Input From Menu 1
    static void getMenu2Input(Person p, int command) {
        //------------------------------Home-------------------------------------
        if (command == 1) {
            Home(p);
            back = true;
        }
        //---------------------------Account Detail------------------------------
        if (command == 2) {
            boolean out = true;
            System.out.printf("ID: %6s\tPhone Number: %6s\tFollowers:%2d\tFollowings:%2d\tPosts:%2d\n", p.ID, p.phoneNumber, p.followers.size(), p.followings.size(), p.posts.size());
            while (out) {
                System.out.println("To Show:");
                System.out.println("(1) Followers");
                System.out.println("(2) Followings");
                System.out.println("(3) Back");
                switch (s.nextInt()) {
                    //-----------------Followers----------------------
                    case 1: {
                        StringBuilder list = new StringBuilder();
                        list.append("[ ");
                        int i = 0;
                        for (Person person : p.followers) {
                            list.append(person.ID);
                            if (i != p.followers.size() - 1) {
                                list.append(", ");
                            }
                            i++;
                        }
                        list.append(" ]");
                        System.out.println(list);
                        break;
                    }
                    //----------------Followers------------------------
                    case 2: {
                        StringBuilder list = new StringBuilder();
                        list.append("[ ");
                        int i = 0;
                        for (Person person : p.followings) {
                            list.append(person.ID);
                            if (i != p.followings.size() - 1) {
                                list.append(", ");
                            }
                            i++;
                        }
                        list.append(" ]");
                        System.out.println(list);
                        break;
                    }
                    //--------------------Back-------------------------
                    default: {
                        out = false;
                        System.out.println("Returning To Menu...");
                    }
                }
                back = true;
            }
        }
        //----------------------------Follow---------------------------------
        if (command == 3) {
            try {
                System.out.println("1: Search by ID");
                System.out.println("2: Show All Users");
                int input = s.nextInt();
                if (input == 1) {
                    System.out.println("Enter A Part Of ID To Begin Search: ");
                    showResultByID(s.next());
                    System.out.println("Enter Complete ID From List To Enter: \nNOTE: If Nothing Was Found, Press Any Key To Continue");
                    String fullID = s.next();
                    if (instagram.allUsers.get(fullID) != null && !p.equals(instagram.allUsers.get(fullID))) {
                        follow(p, instagram.allUsers.get(fullID));
                    } else {
                        throw new UnfollowYourself();
                    }
                } else if (input == 2) {
                    instagram.showUsers();
                    System.out.println("Enter Complete ID From List To Apply:\nNOTE: You Can't Enter Your ID");
                    String fullID = s.next();
                    if (p.equals(instagram.allUsers.get(fullID)) || instagram.allUsers.get(fullID) == null) {
                        throw new UnfollowYourself();
                    } else {
                        follow(p, instagram.allUsers.get(fullID));
                    }
                } else {
                    throw new InvalidInput();
                }
            } catch (InvalidInput | UnfollowYourself | NullPointerException e) {
                System.out.println(e.getMessage());
            } finally {
                back = true;
            }
        }
        //--------------------------Unfollow-----------------------------------
        if (command == 4) {
            try {
                if (p.followings.size() == 0) {
                    throw new NoFollowers();
                }
                System.out.println("Your Followings : " + p.followings.size());
                int counter = 1;
                for (String key : instagram.Users) {
                    if (p.followings.contains(instagram.allUsers.get(key))) {
                        System.out.printf("(%d) ID: %7s      Private: %5s", counter++, instagram.allUsers.get(key).ID, instagram.allUsers.get(key).PV);
                        System.out.println();
                    }
                }
                System.out.println("Enter An Id To Unfollow: ");
                String fullID = s.next();
                if (instagram.allUsers.get(fullID) != null) {
                    unfollow(p, instagram.allUsers.get(fullID));
                } else {
                    throw new InvalidInput();
                }
            } catch (NullPointerException | NoFollowers | InvalidInput e) {
                System.out.println(e.getMessage());
            } finally {
                back = true;
            }
        }
        //--------------------------New Post-------------------------------------
        if (command == 5) {
            createPost(p);
            back = true;
        }
        //---------------------------All Posts------------------------------------
        if (command == 6) {
            try {
                if (p.posts.size() != 0) {
                    for (int i = 0; i < p.posts.size(); i++) {
                        System.out.println("Post No." + (i + 1));
                        System.out.println(p.posts.get(i).post);
                        System.out.println("Likes: " + p.posts.get(i).liked.size() + "   Comments: " + p.posts.get(i).comments.size());
                    }
                } else {
                    throw new NoPost();
                }
            } catch (NoPost e) {
                System.out.println(e.getMessage());
            } finally {
                back = true;
            }
        }
        //---------------------------Post Details---------------------------------
        if (command == 7) {
            try {
                if (p.posts.size() == 0) {
                    throw new NoPost();
                }
                for (int i = 0; i < p.posts.size(); i++) {
                    System.out.println("Post No." + (i + 1));
                    System.out.println(p.posts.get(i).post);
                }
                System.out.println("Select A Post Number For More Details: ");
                try {
                    postDetails(p, s.nextInt() - 1);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            } catch (NoPost e) {
                System.out.println(e.getMessage());
            } finally {
                back = true;
            }
        }
        //---------------------------Delete A Post--------------------------------
        if (command == 8) {
            try {
                deletePost(p);
            } catch (ArrayIndexOutOfBoundsException | NoPostToDelete e) {
                System.out.println(e.getMessage());
            }
            back = true;
        }
        //-------------------------------Back------------------------------------
        if (command == 9) {
            back = false;
        }
    }

    //----------Function for deleting a post from all posts and user's posts-----------
    static void deletePost(Person p) {
        if (p.posts.size() == 0) {
            throw new NoPostToDelete();
        }
        int number = 1;
        for (Post post : p.posts) {
            System.out.println("( Post " + number++ + ") : " + post.post);
        }
        System.out.println("Select Number Of A Post To Remove: ");
        int c = s.nextInt();
        Post post = p.posts.get(c - 1);
        p.posts.remove(post);
        instagram.allPosts.remove(post);
    }

    //---------------------Show Details Of Posts (Comments)---------------------------
    static void postDetails(Person person, int num) {
        Post post = person.posts.get(num);
        System.out.println("Post : " + person.posts.get(num).post);
        System.out.println("Comments : ");
        if (post.comments.size() == 0) {
            System.out.println("No Comments");
        } else {
            for (String str : instagram.Users) {
                if (post.comments.get(str) != null) {
                    for (String comment : post.comments.get(str)) {
                        System.out.println(str + " : " + comment);
                    }
                }
            }
        }
        System.out.println("Likes : ");
        if (post.liked.size() == 0) {
            System.out.println("No Likes");
        } else {
            StringBuilder likes = new StringBuilder();
            likes.append("[ ");
            for (int i = 0; i < post.liked.size(); i++) {
                likes.append(post.liked.get(i).ID);
                if (i != post.liked.size() - 1) {
                    likes.append(", ");
                }
            }
            likes.append(" ]");
            System.out.println(likes);
        }
    }

    //-------------------Result Of Searching By ID-----------------------
    static void showResultByID(String id) {
        int listCounter = 1;
        System.out.println("Searched : " + id);
        for (String key : instagram.Users) {
            if (key.contains(id)) {
                System.out.printf("(%d) ID: %7s      Private: %5s", listCounter++, instagram.allUsers.get(key).ID, instagram.allUsers.get(key).PV);
                System.out.println();
            }
        }
        if (listCounter == 1) {
            System.out.println("No Search Result");
        }
    }

    //------------------------------Follow Sb.--------------------------
    static void follow(Person p1, Person p2) {
        p1.followings.add(p2);
        p2.followers.add(p1);
    }

    //----------------------------Unfollow Sb.---------------------------
    static void unfollow(Person p1, Person p2) {
        p1.followings.remove(p2);
        p2.followers.remove(p1);
    }

    //----------------------------All Posts-------------------------------
    static void Home(Person p) {
        boolean back = true;
        Queue<Post> posts = new ArrayDeque<>();
        for (int i = instagram.allPosts.size() - 1; i >= 0; i--) {
            posts.add(instagram.allPosts.get(i));
        }
        while(back) {
            Post post = posts.poll();
            while (true) {
                if (post == null) {
                    System.out.println("No New Posts");
                    back = false;
                    break;
                }
                System.out.println(post.poster.ID + ":\n" + post.post);
                System.out.println("1: Like\n2: Write Your Comment\n3: Scroll\n4: Back\nEnter A Number: ");
                int input = s.nextInt();
                if (input == 1) {
                    if (!post.liked.contains(p)) {
                        post.liked.add(p);
                    } else {
                        System.out.println("you can't like twice");
                    }
                } else if (input == 2) {
                    System.out.println("Write Your Comment: ");
                    s.nextLine();
                    String comment = s.nextLine();
                    if(post.comments.get(p.ID)!=null) {
                        post.comments.get(p.ID).add(comment);
                    }else{
                        ArrayList<String> per = new ArrayList<>();
                        per.add(comment);
                        post.comments.put(p.ID, per);
                    }

                } else if (input == 3) {
                    break;
                } else if (input == 4) {
                    back = false;
                    break;
                }
            }
        }
    }

    //----------------------------Creating A Post-----------------------
    static void createPost(Person p) {
        System.out.println("Enter Post Body: ");
        s.nextLine();
        String post = s.nextLine();
        Post newPost = new Post(post, p);
        instagram.allPosts.add(newPost);
        p.posts.add(newPost);
    }
}

