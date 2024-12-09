package Trainer;

import Membership.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class Athlete extends Member {

    private EnumMap<SwimmingDisciplines, List<Double>> disciplineTimes;
    private ArrayList<Competition> competitionTimes;

    public Athlete(String firstName, String lastName, LocalDate birthday, String gender, String address, int phoneNumber,
                   String membershipStatus, String membershipType, boolean hasPaid, boolean hasBeenAddedToAthletes) {
        super(firstName, lastName, birthday, gender, address, phoneNumber, membershipStatus, membershipType, hasPaid, hasBeenAddedToAthletes);

        this.disciplineTimes = new EnumMap<>(SwimmingDisciplines.class);

        for (SwimmingDisciplines disciplines : SwimmingDisciplines.values()) {
            disciplineTimes.put(disciplines, new ArrayList<>());
        }
        competitionTimes = new ArrayList<>();
    }

    public List<Double> getTimes(SwimmingDisciplines discipline) {
        return new ArrayList<>(disciplineTimes.get(discipline));
    }

    public ArrayList<Competition> getCompetitionTimes() {
        return competitionTimes;
    }

    //ToDo check if it can be moved to the fileHandler class.
    public String toCSVStyle(String name, boolean isCompetition) {
        StringBuilder finalString = new StringBuilder();
            String team = "Juniorhold";
            if (getAgeGroup() != MembershipType.JUNIOR) {
                team = "Seniorhold";
            }

        if (!isCompetition) {
            finalString.append(name).append(",").append(team).append(",").append("træning{");

            for (var entry : disciplineTimes.entrySet()) {
                SwimmingDisciplines discipline = entry.getKey();
                List<Double> times = entry.getValue();

                finalString.append(discipline);

                if (times != null && !times.isEmpty()) {
                    finalString.append("[");
                    for (int i = 0; i < times.size(); i++) {
                        finalString.append(times.get(i));
                        if (i < times.size() - 1) {
                            finalString.append(",");
                        }
                    }
                    finalString.append("],");
                } else {
                    finalString.append("[0.0],");
                }

            }
            if (finalString.charAt(finalString.length() - 1) == ',') {
                finalString.deleteCharAt(finalString.length() - 1);
            }
            finalString.append("}\n");
        } else {
            finalString.append(name).append(",").append(team).append(",");
            for (Competition competition : competitionTimes){
                finalString.append("Stævne").append(competition.toString()).append(",");
            }
            finalString.append("\n");
        }
        return finalString.toString();
    }
}
