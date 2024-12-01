import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trainer {
    String name;
    ArrayList<Athlete> athletes;
    FileHandler fh = new FileHandler();

    public Trainer(String name) {
        this.name = name;
        athletes = new ArrayList<>();
    }

    //Checks if a member is an Athlete and if it is, it'll add them to the special athletes ArrayList
    public void addAthletesToList(ArrayList<Member> members) {
        for (Member member : members) {
            if (member.getMembershipType() == MembershipType.ATLET) {
                String[] name = member.getName().split(",");
                athletes.add(new Athlete(name[1], name[0], member.getLd(), member.getGender(),
                        member.getAddress(), member.getPhoneNumber(), member.getMemberShipStatus().toString(), member.getMembershipType().toString(),
                        member.getHasPaid()));
            }
        }
    }

    public ArrayList<String> setAthleteTrainingTime(String name, ArrayList<String> athletesFromFile, String discipline, Double newTime) {
        String regex = "([\\p{L}]+)(,\\[(\\d+\\.\\d+)])?";

        Pattern pattern = Pattern.compile(regex);

        String athlete = null;
        int athleteIndex = -1;

        for (int i = 0; i < athletesFromFile.size(); i++) {
            if (athletesFromFile.get(i).contains(name)){
                athleteIndex = i;
                athlete = athletesFromFile.get(i);
                break;
            }
        }

        if (athleteIndex == -1 || athlete == null){
            throw new NullPointerException("Atlet ikke fundet i filen");
        }

        int trainingStart = athlete.indexOf("træning{");
        if (trainingStart == -1){
            throw new IllegalArgumentException("Træningstider ikke fundet i atletens data");
        }

        String prefix = athlete.substring(0, trainingStart + "træning{".length());
        String trainingTimes = athlete.substring(trainingStart + "træning{".length(), athlete.lastIndexOf('}'));

        Matcher matcher = pattern.matcher(trainingTimes);
        StringBuilder updatedString = new StringBuilder();

        while (matcher.find()) {
            String currentDiscipline = matcher.group(1);
            String currentTime = matcher.group(3);

            if (currentDiscipline.equalsIgnoreCase(discipline)) {
                updatedString.append(currentDiscipline).append(",[").append(newTime).append("]");
            } else if (currentTime != null) {
                updatedString.append(currentDiscipline).append(",[").append(currentTime).append("]");
            }
        }

        LocalDate dateOfTraining = LocalDate.now();

        String updatedAthlete = prefix + updatedString +"},DayOfTraining[" + dateOfTraining +"]";

        athletesFromFile.set(athleteIndex, updatedAthlete);
        return athletesFromFile;
    }


    public double getAthleteTime(int x, SwimmingDisciplines discipline) {
        Athlete athlete = athletes.get(x);

        List<Double> times = athlete.getTimes(discipline);

        if (times == null || times.isEmpty()) {
            throw new IllegalArgumentException("No times recorded for the discipline: " + discipline);
        }

        return times.getFirst();
    }

    public void saveAthleteMembersToAthleteFile() {
        fh.saveAthleteMembersToAthleteFile(athletes);
    }
}
