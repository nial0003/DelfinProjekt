import java.time.LocalDate;
import java.time.Period;

//TODO
//Fix it so that the memberNumber increments correctly.


public class Member {
    private String firstName;
    private String lastName;
    private String name;
    private int age;
    private String gender;
    private String address;
    private int phoneNumber;
    private int memberNumber;
    private static int memberCounter = 0;
    private Enum<MembershipType> memberShipStatus;
    private Enum<MembershipType> membershipType;
    private Enum<MembershipType> ageGroup;
    private boolean hasPaid;
    private LocalDate ld;


    public Member(String firstName, String lastName, LocalDate birthday, String gender, String address, int phoneNumber,
                  String membershipStatus, String membershipType, boolean hasPaid) {
        this.name = lastName + ", " + firstName;
        this.age = calculateAge(birthday);
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.memberShipStatus = MembershipType.membershipStatus(membershipStatus);
        this.membershipType = MembershipType.membershipType(membershipType);
        this.ageGroup = MembershipType.ageGroup(age);
        this.hasPaid = hasPaid;
        this.memberNumber = ++memberCounter;
        this.ld = birthday;
    }

    //Calculates the age based on the birthday provided and subtracts it from the current date.
    //Return year in between.
    public int calculateAge(LocalDate birthday) {
        LocalDate todaysDate = LocalDate.now();
        Period period = Period.between(birthday, todaysDate);
        return period.getYears();
    }


    //Converts the member details into a CSV-style string
    public String toCSVStyle() {
        return "\n" + name + "," + ld.getYear() + "," + ld.getMonthValue() + "," + ld.getDayOfMonth() + "," + gender + "," + address + "," + phoneNumber +
                "," + memberShipStatus + "," + membershipType + "," + ageGroup + "," + hasPaid + "," + memberNumber;
    }


    //GETTERS

    public Enum<MembershipType> getMembershipType() {
        return membershipType;
    }

    public LocalDate getLd () {
        return ld;
    }

    public String getName () {
        return name;
    }



    //TODO
    //Change language of output to Danish

    // Formats the member details as a user-friendly string
    @Override
    public String toString() {
        return "Member Details:\n" +
                "-----------------------------\n" +
                "Name: " + name + "\n" +
                "Birthday: " + ld + "\n" +
                "Age: " + age + "\n" +
                "Gender: " + gender + "\n" +
                "Address: " + address + "\n" +
                "Phone Number: " + phoneNumber + "\n" +
                "Membership Status: " + memberShipStatus + "\n" +
                "Membership Type: " + membershipType + "\n" +
                "Age Group: " + ageGroup + "\n" +
                "Has Paid: " + (hasPaid ? "Yes" : "No") + "\n" +
                "Member Number: " + memberNumber + "\n" +
                "-----------------------------";
    }


}
