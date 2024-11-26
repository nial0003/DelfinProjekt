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


    // Method to show members annual fee, as well as providing the accountant with a total fee amount for all members combined
    public String calculateMembershipFees() {
        String feeDetails = "";
        int totalFees = 0;

        for (Member member : listOfMembers) {
            int fee = determineMembershipFee(member);
            feeDetails += formatFeeDetails(member, fee) + "\n";
            totalFees += fee;
        }

        feeDetails += "\nSamlede konigentindtægter: " + totalFees + " kr";

        return feeDetails;
    }

    // Format membership fee details for display
    private String formatFeeDetails(Member member, int fee) {
        return "Navn: " + member.getName() +
                ", Alder: " + member.calculateAge(member.getLd()) +
                ", Medlemstype: " + member.getMembershipType() +
                ", Kontigent: " + fee + " kr";
    }

    // Method to show if members have paid for their membership yet
    public String membershipPaymentStatus() {
        String paymentDetails = "";

        for (Member member : listOfMembers) {
            paymentDetails += "Navn: " + member.getName() +
                    ", Alder: " + member.calculateAge(member.getLd()) +
                    ", Betalt: " + member.getHasPaid()+"\n";
        }
        return paymentDetails;
    }

}
