public class NoPostToDelete extends RuntimeException{
    public NoPostToDelete(){
        super("There Are No Posts To Delete");
    }
}
