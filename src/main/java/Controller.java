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
       return accountant.formatMemberPaymentStatus(members);
    }

    public String getFormattedTotalMembershipFees() {
        return accountant.formatTotalMembershipFees();
    }

    public String getFoundMembers(String searchKeyword) {
        ArrayList<Member> foundMembers = accountant.findMembers(searchKeyword);

        if (foundMembers.isEmpty()) {
            return "Ingen medlemmer fundet.";
        }

        String result = "Fundne medlemmer:\n";
        for (Member member : foundMembers) {
            result += "Navn: " + member.getName() +
                    ", ID: " + member.getMemberNumber() +
                    ", Telefonnummer: " + member.getPhoneNumber() + "\n";
        }

        return result;
    }


    public boolean updateMemberPaymentStatus(String memberNumber, boolean hasPaid) {
        return accountant.updateMemberPaymentStatus(Integer.parseInt(memberNumber), hasPaid);
    }
    public ArrayList<Member> findMembers(String searchKeyword) {
        return accountant.findMembers(searchKeyword);
    }


}
