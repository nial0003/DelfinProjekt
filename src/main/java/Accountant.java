import java.util.ArrayList;

public class Accountant {

    private static final int JUNIOR_FEE = 1000;
    private static final int SENIOR_FEE = 1600;
    private static final int ELDER_FEE = 1200;
    private static final int PASSIVE_FEE = 500;
    private static final int PASSIVE_ELDER_FEE = 375;

    private FileHandler fh = new FileHandler();
    private ArrayList<Member> listOfMembers;

    public Accountant() {
        this.listOfMembers = fh.loadFromFile();
    }

    // Method to determine annual fee based on age group and membership type
    private int determineMembershipFee(Member member) {
        if (member.getMembershipType() == null) {
            System.out.println("Fejl: Medlemstype mangler for medlem: " + member.getName());
            return 0;
        }
        String membershipType = member.getMembershipType().toString();
        int age = member.calculateAge(member.getLd());

        if (membershipType.equals("HOBBY") || membershipType.equals("ATLET")) {
            if (age < 18) {
                return JUNIOR_FEE;
            } else if (age < 60) {
                return SENIOR_FEE;
            } else {
                return ELDER_FEE;
            }
        } else if (membershipType.equals("PASSIV")) {
            if (age < 60) {
                return PASSIVE_FEE;
            } else {
                return PASSIVE_ELDER_FEE;
            }
        }
        return 0;
    }


    // Method to calculate the annual membership fees for all members combined
    public int calculateMembershipFees() {
        if (listOfMembers == null || listOfMembers.isEmpty()) {
            return 0;
        }
        int totalFees = 0;

        for (Member member : listOfMembers) {
            int fee = determineMembershipFee(member);
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

    //Getter
    public ArrayList<Member> getListOfMembers() {
        return listOfMembers;
    }


}
