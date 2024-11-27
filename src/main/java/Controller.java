import java.util.ArrayList;

public class Controller {
    Chairman chairman = new Chairman();
    Accountant accountant = new Accountant();

    private ArrayList<Member> getListOfMembers() {
        return chairman.getListOfMembers();
    }


    public ArrayList<Member> getAllMembers() {
        return accountant.getListOfMembers();
    }


    public ArrayList<Member> getFilteredMembers(boolean hasPaid) {
        return accountant.filterMembersByPaymentStatus(hasPaid);
    }


    public String getFormatMembers(ArrayList<Member> members) {
       return accountant.formatMembers(members);
    }

    public int getCalculateMembershipFees() {
        return accountant.calculateMembershipFees();
    }



}
