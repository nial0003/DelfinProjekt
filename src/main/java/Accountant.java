import java.util.ArrayList;

public class Accountant {

    private FileHandler fh = new FileHandler();
    private ArrayList<Member> listOfMembers;

    public Accountant() {
        this.listOfMembers = fh.loadFromFile();
    }




    // Method to calculate the annual membership fees for all members combined
    public int calculateMembershipFees() {
        if (listOfMembers == null || listOfMembers.isEmpty()) {
            return 0;
        }
        int totalFees = 0;

        for (Member member : listOfMembers) {
            int fee = member.determineMembershipFee();
            totalFees += fee;
        }
        return totalFees;
    }

    // Method to format total membership fees as a string
    public String formatTotalMembershipFees() {
        int totalFees = calculateMembershipFees();
        return "Samlede kontingentindbetalinger: " + totalFees + " kr.\n";
    }

    // Method to filter members by their payment status
    public ArrayList<Member> filterMembersByPaymentStatus(boolean hasPaid) {
        ArrayList<Member> filteredMembers = new ArrayList<>();
        for (Member member : listOfMembers) {
            if (member.getHasPaid() == hasPaid) {
                filteredMembers.add(member);
            }
        }
        return filteredMembers;
    }

    // Method to format members payment status to a string
    public String formatMemberPaymentStatus(ArrayList<Member> members) {
        if (members == null || members.isEmpty()) {
            return "Ingen medlemmer fundet.";
        }

        String header = "Medlemsliste:\n" +
                "-------------------------------------------------\n";
        String footer = "-------------------------------------------------\n";

        String formattedMembers = header;

        for (Member member : members) {
            formattedMembers += "Navn: " + member.getName() + "\n" +
                    "Medlemsnummer: " + member.getMemberNumber() + "\n" +
                    "Betalt: " + (member.getHasPaid() ? "Ja" : "Nej") + "\n" +
                    footer;
        }
        return formattedMembers;
    }


    //TODO
    //Method to find members

    public ArrayList<Member> findMembers(String searchKeyword) {
        ArrayList<Member> matchingMembers = new ArrayList<>();

        for (Member member : listOfMembers) {
            if (String.valueOf(member.getMemberNumber()).equals(searchKeyword) ||
                    String.valueOf(member.getPhoneNumber()).equals(searchKeyword) ||
                    member.getFirstName().toLowerCase().startsWith(searchKeyword.toLowerCase()) ||
                    member.getLastName().toLowerCase().startsWith(searchKeyword.toLowerCase())) {
                matchingMembers.add(member);
            }
        }

        return matchingMembers;
    }


    //TODO
    //Method to update members payment status

    public boolean updateMemberPaymentStatus(int memberNumber, boolean hasPaid) {
        for (Member member : listOfMembers) {
            if (member.getMemberNumber() == memberNumber) {
                member.setHasPaid(hasPaid);
                fh.saveToFile(listOfMembers);
                return true;
            }
        }
        return false;
    }

    //Getter
    public ArrayList<Member> getListOfMembers() {
        return listOfMembers;
    }


}
