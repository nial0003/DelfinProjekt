import java.util.ArrayList;

public class Controller {
    Chairman chairman = new Chairman();

    private ArrayList<Member> getListOfMembers() {
        return chairman.getListOfMembers();
    }
}
