import java.time.LocalDate;
import java.util.EnumMap;

public class Athlete extends Member {

    private EnumMap<SwimmingDisciplines, Double> disciplineTimes;

    public Athlete(String firstName, String lastName, LocalDate birthday, String gender, String address, int phoneNumber,
                   String membershipStatus, String membershipType, boolean hasPaid) {
        super(firstName, lastName, birthday, gender, address, phoneNumber, membershipStatus, membershipType, hasPaid);
        this.disciplineTimes = new EnumMap<>(SwimmingDisciplines.class);
    }

    public void setDisciplineTimes(SwimmingDisciplines discipline, double time) {
        disciplineTimes.put(discipline, time);
    }

    //Returns the time for the given discipline, if the given discipline doesn't have a time it'll return
    //-1. Which we can then use when deciding what times to print out in the trainer class.
    public double getTime(SwimmingDisciplines discipline){
        return disciplineTimes.getOrDefault(discipline, -1.0);
    }
}
