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

    public String getFoundMembers(ArrayList<Member> members, String keyword) {
        ArrayList<Member> foundMembers = accountant.findMembers(members, keyword);

        if (foundMembers.isEmpty()) {
            return "Ingen medlemmer fundet, der matcher søgeordet: " + keyword;
        }

        String formattedResult = "Medlemmer fundet:\n";
        for (Member member : foundMembers) {
            formattedResult += "Navn: " + member.getName() +
                    ", ID: " + member.getMemberNumber() +
                    ", Telefon: " + member.getPhoneNumber() + "\n";
        }

        return formattedResult;
    }


}
