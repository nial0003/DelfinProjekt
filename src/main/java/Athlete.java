import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Athlete extends Member {

    private EnumMap<SwimmingDisciplines, List<Double>> disciplineTimes;

    public Athlete(String firstName, String lastName, LocalDate birthday, String gender, String address, int phoneNumber,
                   String membershipStatus, String membershipType, boolean hasPaid) {
        super(firstName, lastName, birthday, gender, address, phoneNumber, membershipStatus, membershipType, hasPaid);

        this.disciplineTimes = new EnumMap<>(SwimmingDisciplines.class);

        for (SwimmingDisciplines disciplines : SwimmingDisciplines.values()) {
            disciplineTimes.put(disciplines, new ArrayList<>());
        }
    }

    public void setDisciplineTimes(SwimmingDisciplines discipline, Double time) {
        disciplineTimes.get(discipline).add(time);
    }

    public List<Double> getTimes(SwimmingDisciplines discipline) {
        return new ArrayList<>(disciplineTimes.get(discipline));
    }


    public String toCSVStyle(String name) {
        StringBuilder disciplinTimeString = new StringBuilder();
        disciplinTimeString.append(name).append(",").append(getAgeGroup()).append(",");

        for (var entry : disciplineTimes.entrySet()) {
            SwimmingDisciplines discipline = entry.getKey();
            List<Double> times = entry.getValue();

            disciplinTimeString.append(discipline).append(",");

            if (times != null && !times.isEmpty()) {
                disciplinTimeString.append("[");
                for (int i = 0; i < times.size(); i++) {
                    disciplinTimeString.append(times.get(i));
                    if (i < times.size() - 1) {
                        disciplinTimeString.append(",");
                    }
                }
                disciplinTimeString.append("],");
            }

        }
        if (disciplinTimeString.charAt(disciplinTimeString.length() - 1) == ',') {
            disciplinTimeString.deleteCharAt(disciplinTimeString.length() - 1);
        }
        disciplinTimeString.append("\n");
        return disciplinTimeString.toString();
    }
}
