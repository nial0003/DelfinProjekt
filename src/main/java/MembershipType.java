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

    //Method that returns the agegroup - used in editMember
    public static Enum<MembershipType> membershipAgeGroup(String ageGroup){
        return switch (ageGroup) {
            case "JUNIOR" -> {
                yield JUNIOR;
            }
            case "SENIOR" -> {
                yield SENIOR;
            }
            case "PENSIONIST" -> {
                yield PENSIONIST;
            }
            default -> throw new IllegalStateException("Unexpected value: " + ageGroup);
        };
    }

    //Method that returns active or passive for a member - Used in editMember
    public static Enum<MembershipType> activePassiveMember(String membershipStatus) {
        if (membershipStatus.toUpperCase() == "AKTIV") {
            return AKTIV;
        } else {
            return PASSIV;
        }

    }

    //Method that returns if the member is an athlete or hobby - Used in editMember
    public static Enum<MembershipType> memberHobbyAthlete(String membershipType) {
        if (membershipType.toUpperCase() == "HOBBY") {
            return HOBBY;
        } else {
            return ATLET;
        }
    }


}