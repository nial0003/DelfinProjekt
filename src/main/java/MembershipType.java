public enum MembershipType {
    JUNIOR,
    SENIOR,
    ELDER,
    ACTIVE,
    PASSIVE,
    ATHLETE,
    HOBBY;

    //Assigns their age group based on their age.
    public static Enum<MembershipType> ageGroup(int age){
        if (age < 18){
            return JUNIOR;
        } else if (age < 60) {
            return SENIOR;
        } else {
            return ELDER;
        }
    }

    //Assigns their status by returning an Enum type that's determined by the String input which is either "ACTIVE"
    //or "PASSIVE.
    public static Enum<MembershipType> membershipStatus(String membershipStatus){
        if (membershipStatus.contentEquals("ACTIVE")){
            return ACTIVE;
        } else if (membershipStatus.contentEquals("PASSIVE")){
            return PASSIVE;
        } else {
            return null;
        }
    }

    // Assigns their membership type, by choosing "HOBBY" or "ATHLETE"
    public static Enum<MembershipType> membershipType(String membershipType){
        if (membershipType.contentEquals("HOBBY")){
            return HOBBY;
        } else if (membershipType.contentEquals("ATHLETE")) {
            return ATHLETE;
        } else {
            return null;
        }
    }
}
