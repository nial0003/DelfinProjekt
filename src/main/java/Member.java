import java.time.LocalDate;
import java.time.Period;

public class Member {
    private String firstName;
    private String lastName;
    private String name;
    private int age;
    private String gender;
    private String address;
    private int phoneNumber;
    private int memberNumber;
    static int memberCounter = 0;
    private Enum memberShipStatus;
    private Enum membershipType;
    private Enum ageGroup;
    private boolean hasBeenAddedToAthletes;
    private boolean hasPaid;
    private LocalDate ld;

    //-------------------Constructor for new members--------------------------------------------------------------------
    public Member(String firstName, String lastName, LocalDate birthday, String gender, String address, int phoneNumber,
                  String membershipStatus, String membershipType, boolean hasPaid, boolean hasBeenAddedToAthletes) {
        this.firstName = firstName;
        this.lastName = lastName;
        name = lastName + "," + firstName;
        age = calculateAge(birthday);
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.memberShipStatus = MembershipType.membershipStatus(membershipStatus);
        this.membershipType = MembershipType.membershipType(membershipType);
        this.ageGroup = MembershipType.ageGroup(age);
        this.hasPaid = hasPaid;
        this.memberNumber = ++memberCounter; // Increment for new members
        this.ld = birthday;
        this.hasBeenAddedToAthletes = hasBeenAddedToAthletes;
    }

    //-------------------Constructor for members loaded from file-------------------------------------------------------
    public Member(String firstName, String lastName, LocalDate birthday, String gender, String address, int phoneNumber,
                  String membershipStatus, String membershipType, boolean hasPaid, int memberNumber, boolean hasBeenAddedToAthletes) {
        this.firstName = firstName;
        this.lastName = lastName;
        name = lastName + "," + firstName;
        age = calculateAge(birthday);
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.memberShipStatus = MembershipType.membershipStatus(membershipStatus);
        this.membershipType = MembershipType.membershipType(membershipType);
        this.ageGroup = MembershipType.ageGroup(age);
        this.hasPaid = hasPaid;
        this.memberNumber = memberNumber; // Use the number from the file
        this.ld = birthday;
        this.hasBeenAddedToAthletes = hasBeenAddedToAthletes;

        // Update memberCounter to ensure it stays ahead
        if (memberNumber > memberCounter) {
            memberCounter = memberNumber;
        }
    }

    //-------------------Method to calculate age------------------------------------------------------------------------
    //Calculates the age based on the birthday provided and subtracts it from the current date.
    //Return year in between.
    public int calculateAge(LocalDate birthday) {
        LocalDate todaysDate = LocalDate.now();
        Period period = Period.between(birthday, todaysDate);
        return period.getYears();
    }

    //--------------------Method to determine the annual membership fee-------------------------------------------------
    public int determineMembershipFee() {
        int fee = 0;
        int age = calculateAge(ld);

        if (getMemberShipStatus().equals(MembershipType.AKTIV)) {
            if (getAgeGroup().equals(MembershipType.JUNIOR)) {
                fee = 1000;
            } else if (getAgeGroup().equals(MembershipType.SENIOR)) {
                fee = 1600;
            } else {
                fee = 1200;
            }

        } else if (getMemberShipStatus().equals(MembershipType.PASSIV)) {
            if (getAgeGroup().equals(MembershipType.PENSIONIST)) {
                fee = 375;
            } else {
                fee = 500;
            }
        }
        return fee;
    }

    //-------------------Converts the member details into a CSV-style string--------------------------------------------
    public String toCSVStyle() {
        return name + "," +
                ld.getYear() + "," +
                ld.getMonthValue() + "," +
                ld.getDayOfMonth() + "," +
                gender + "," +
                address + "," +
                phoneNumber + "," +
                memberShipStatus + "," +
                membershipType + "," +
                ageGroup + "," +
                hasPaid + "," +
                memberNumber + "," +
                hasBeenAddedToAthletes + "\n";
    }

    //-------------------Formats the member details as a user-friendly string-------------------------------------------
    @Override
    public String toString() {
        return "Medlemsdetaljer:\n" +
                "-----------------------------\n" +
                "Navn: " + name + "\n" +
                "Fødselsdato: " + ld + "\n" +
                "Alder: " + age + "\n" +
                "Køn: " + gender + "\n" +
                "Adresse: " + address + "\n" +
                "Telefonnummer: " + phoneNumber + "\n" +
                "Medlemsstatus: " + memberShipStatus + "\n" +
                "Medlemstype: " + membershipType + "\n" +
                "Aldersgruppe: " + ageGroup + "\n" +
                "Betalt: " + (hasPaid ? "Ja" : "Nej") + "\n" +
                "Medlemsnummer: " + memberNumber + "\n" +
                "-----------------------------";
    }

    //-------------------Getters & Setters------------------------------------------------------------------------------
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public Enum<MembershipType> getMemberShipStatus() {
        return memberShipStatus;
    }

    public Enum<MembershipType> getMembershipType() {
        return membershipType;
    }

    public Enum<MembershipType> getAgeGroup() {
        return ageGroup;
    }

    public LocalDate getLd() {
        return ld;
    }

    public boolean getHasPaid() {
        return hasPaid;
    }

    

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAgeGroup (Enum<MembershipType> ageGroup){
        this.ageGroup = ageGroup;
    }

    public void setMembershipStatus(Enum<MembershipType> memberShipStatus) {
        this.memberShipStatus = memberShipStatus;
    }

    public void setMembershipType(Enum<MembershipType> membershipType) {
        this.membershipType = membershipType;
    }



    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public boolean getHasBeenAddedToAthletes(){
        return this.hasBeenAddedToAthletes;
    }

    public void setHasBeenAddedToAthletes(boolean hasBeenAddedToAthletes) {
        this.hasBeenAddedToAthletes = hasBeenAddedToAthletes;
    }
}
