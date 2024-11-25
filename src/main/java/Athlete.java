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
    public double getTime(SwimmingDisciplines discipline) {
        return disciplineTimes.getOrDefault(discipline, -1.0);
    }

    //Overrides the toCSVStyle from the super class so that it's more inline with the format we want from this class in the file
    @Override
    public String toCSVStyle() {
        String disciplineTimesString = "";
        for (var disciplines : disciplineTimes.entrySet()) {
            if (disciplines.getValue() != -1.0) {
                disciplineTimesString += "," + disciplines.getKey() + ":" + disciplines.getValue();
            }
        }
        return getName() + disciplineTimesString + "\n";
    }
}
