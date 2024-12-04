import java.util.ArrayList;

public class Accountant {
    private FileHandler fh;
    private ArrayList<Member> listOfMembers;

    //-------------------Constructor------------------------------------------------------------------------------------
    public Accountant() {
        fh = new FileHandler();
        listOfMembers = fh.loadFromFile();
    }

    //--------------------Helper method to check for an empty member list-----------------------------------------------
    private boolean isMemberListEmpty() {
        return listOfMembers == null || listOfMembers.isEmpty();
    }

    //--------------------Method to calculate the annual membership fees for all members combined-----------------------
    public int calculateTotalMembershipFees() {
        if (isMemberListEmpty()) {
            return 0;
        }
        int totalFees = 0;
        for (Member member : listOfMembers) {
            totalFees += member.determineMembershipFee();
        }
        return totalFees;
    }

    //-------------------Method to calculate received payments----------------------------------------------------------
    public int calculateReceivedPayments() {
        if (isMemberListEmpty()) {
            return 0;
        }
        int totalReceived = 0;
        for (Member member : listOfMembers) {
            if (member.getHasPaid()) {
                totalReceived += member.determineMembershipFee();
            }
        }
        return totalReceived;
    }

    //-------------------Method to calculate outstanding payments-------------------------------------------------------
    public int calculateOutstandingPayments() {
        if (isMemberListEmpty()) {
            return 0;
        }
        int totalOutstanding = 0;
        for (Member member : listOfMembers) {
            if (!member.getHasPaid()) {
                totalOutstanding += member.determineMembershipFee();
            }
        }
        return totalOutstanding;
    }

    //-------------------Method to calculate paid percentage------------------------------------------------------------
    public double calculatePaidPercentage() {
        int totalFees = calculateTotalMembershipFees();
        if (totalFees == 0) {
            return 0.0;
        }

        int receivedPayments = calculateReceivedPayments();
        return (double) receivedPayments / totalFees * 100;
    }


    //-------------------Method to calculate outstanding percentage------------------------------------------------------------
    public double calculateOutstandingPercentage() {
        int totalFees = calculateTotalMembershipFees();
        if (totalFees == 0) {
            return 0.0;
        }

        int receivedPayments = calculateReceivedPayments();
        return 100 - ((double) receivedPayments / totalFees * 100);
    }



    //-------------------Method to filter members by their payment status-----------------------------------------------
    public ArrayList<Member> filterMembersByPaymentStatus(boolean hasPaid) {
        ArrayList<Member> filteredMembers = new ArrayList<>();
        for (Member member : listOfMembers) {
            if (member.getHasPaid() == hasPaid) {
                filteredMembers.add(member);
            }
        }
        return filteredMembers;
    }

    //-------------------Method to format members payment status to a string--------------------------------------------
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

    //-------------------Method to find members-------------------------------------------------------------------------

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

    //-------------------Method to update members payment status--------------------------------------------------------

    public boolean updateMemberPaymentStatus(int memberNumber, boolean hasPaid) {
        for (Member member : listOfMembers) {
            if (member.getMemberNumber() == memberNumber) {
                member.setHasPaid(hasPaid);
                fh.saveToFile(listOfMembers, false);
                return true;
            }
        }
        return false;
    }

    //-------------------Getter-----------------------------------------------------------------------------------------
    public ArrayList<Member> getListOfMembers() {
        return listOfMembers;
    }

}
