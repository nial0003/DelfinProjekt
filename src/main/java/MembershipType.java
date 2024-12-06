public enum MembershipType {
    JUNIOR,
    SENIOR,
    PENSIONIST,
    AKTIV,
    PASSIV,
    ATLET,
    HOBBY;

    // Assigns their age group based on their age
    public static Enum<MembershipType> ageGroup(int age) {
        if (age < 18) {
            return JUNIOR;
        } else if (age < 60) {
            return SENIOR;
        } else {
            return PENSIONIST;
        }
    }

    // Assigns their status by returning an Enum type that's determined by the String input which is either "ACTIVE"
    // or "PASSIVE.
    public static Enum<MembershipType> membershipStatus(String membershipStatus) {
        if (membershipStatus.toUpperCase().contentEquals("AKTIV")) {
            return AKTIV;
        } else if (membershipStatus.toUpperCase().contentEquals("PASSIV")) {
            return PASSIV;
        } else {
            return null;
        }
    }

    // Assigns their membership type, by choosing "HOBBY" or "ATHLETE"
    public static Enum<MembershipType> membershipType(String membershipType) {
        if (membershipType.toUpperCase().contentEquals("HOBBY")) {
            return HOBBY;
        } else if (membershipType.toUpperCase().contentEquals("ATLET")) {
            return ATLET;
        } else {
            return null;
        }
    }


}