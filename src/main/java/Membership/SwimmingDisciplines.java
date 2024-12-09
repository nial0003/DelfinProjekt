package Membership;
import UserInterface.*;

public enum SwimmingDisciplines {
    BUTTERFLY,
    CRAWL,
    RYGCRAWL,
    BRYSTSVØMNING;

    public static SwimmingDisciplines getSwimmingDiscipline(String discipline) {
        return switch (discipline.toUpperCase()) {
            case "BUTTERFLY" -> BUTTERFLY;
            case "CRAWL" -> CRAWL;
            case "RYGCRAWL" -> RYGCRAWL;
            case "BRYSTSVØMNING" -> BRYSTSVØMNING;
            default -> throw new IllegalStateException("Unexpected value: " + discipline.toUpperCase());
        };
    }
}
