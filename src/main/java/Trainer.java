import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Trainer {
    ArrayList<Athlete> athletes;

    public Trainer() {
        athletes = new ArrayList<>();
    }

    //Checks if a member is an Athlete and if it is, it'll add them to the special athletes ArrayList
    public void addAthletesToList(ArrayList<Member> members, boolean isItForTraining) {
        for (Member member : members) {
            if (isItForTraining) {
                if (!member.getHasBeenAddedToAthletes() && member.getMembershipType() == MembershipType.ATLET) {
                    String[] name = member.getName().split(",");
                    athletes.add(new Athlete(name[1], name[0], member.getLd(), member.getGender(),
                            member.getAddress(), member.getPhoneNumber(), member.getMemberShipStatus().toString(), member.getMembershipType().toString(),
                            member.getHasPaid(), member.getHasBeenAddedToAthletes()));
                    member.setHasBeenAddedToAthletes(true);
                }

            } else {
                if (member.getMembershipType() == MembershipType.ATLET) {
                    String[] name = member.getName().split(",");
                    athletes.add(new Athlete(name[1], name[0], member.getLd(), member.getGender(),
                            member.getAddress(), member.getPhoneNumber(), member.getMemberShipStatus().toString(), member.getMembershipType().toString(),
                            member.getHasPaid(), member.getHasBeenAddedToAthletes()));
                }
            }
        }
    }

    /*Takes the name of the person you wish to find from the AthletesTrainingResults.csv. If said person exists in the
     * file it will isolate the discipline you wish to update from said athlete so that you can update it using the newTime.*/
    public ArrayList<String> setAthleteTrainingTime(String name, ArrayList<String> athletesFromFile, String
            discipline, Double newTime) {
        // ([\p{L}]+) : This part isolates the Discipline of the Athlete so we can run it against the Discipline names
        //              in the file of the correct person and update the training time
        // (,\[(\d+\.\d+)]) : Isolates the value of the correct discipline so we can update it to the newTime that we've given.
        String regex = "([\\p{L}]+)(\\[(\\d+\\.\\d+)])?";

        // takes the regex we have made and compiles it into a pattern which we can be used to correctly find the
        // discipline and it's value from a String so we can update the value.
        Pattern pattern = Pattern.compile(regex);

        String athlete = null;
        int athleteIndex = -1;

        //Finds the athlete from the ArrayList athletesFromFile by comparing the name we've given to the Strings in the
        //List. It then gives us the index in which it was found so we can replace it with the correct data later
        //and it gives us the athlete from the File as a String
        String[] athleteNameSplitted = name.split(",");
        athleteIndex = Integer.parseInt(athleteNameSplitted[0]);
        athlete = athletesFromFile.get(athleteIndex);

        if (athleteIndex == -1 || athlete == null) {
            throw new NullPointerException("Atlet ikke fundet i filen");
        }

        //From here we isolate the training times from the Athlete if the Athlete was indeed in the file.
        //This is done by giving the first index of "træning{" and creating a subString, trainingTimes, between
        //the first indexOf "træning{" and lastiIndexOf '}'. We also isolate the name and training team of the
        //Athlete in the prefix String.
        int trainingStart = athlete.indexOf("træning{");
        if (trainingStart == -1) {
            throw new IllegalArgumentException("Træningstider ikke fundet i atletens data");
        }

        String prefix = athlete.substring(0, trainingStart + "træning{".length());
        String trainingTimes = athlete.substring(trainingStart + "træning{".length(), athlete.lastIndexOf('}'));

        //Takes the pattern we created earlier and gives it to the matcher which will then run through each line
        //in the trainingTimes ArrayList<String> to see if anything in it matches the given pattern.
        //If it does we can store the part of the String which contains the discipline and run it against the given
        //discipline whose time we want to update.
        //For all the disciplines which doesn't match the given discipline it'll set the discipline and time to the one
        //already in the file.
        Matcher matcher = pattern.matcher(trainingTimes);
        StringBuilder updatedString = new StringBuilder();

        while (matcher.find()) {
            String currentDiscipline = matcher.group(1);
            String currentTime = matcher.group(3);

            if (currentDiscipline.equalsIgnoreCase(discipline)) {
                updatedString.append(currentDiscipline).append("[").append(newTime).append("],");
            } else if (currentTime != null) {
                updatedString.append(currentDiscipline).append("[").append(currentTime).append("],");
            }
        }

        if (!updatedString.isEmpty() && updatedString.charAt(updatedString.length() - 1) == ',') {
            updatedString.deleteCharAt(updatedString.length() - 1);
        }

        //Adds the date to show when the trainingTime was updated.
        LocalDate dateOfTraining = LocalDate.now();

        String updatedAthlete = prefix + updatedString + "},DayOfTraining[" + dateOfTraining + "]";

        //Replaces the Athlete we have updated at the place where we got the original Athlete.
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

    public ArrayList<Athlete> getAthletes() {
        return athletes;
    }

    //adds a competition to a given Athlete at the end
    public void addCompetitionToAthlete(String name, String competitionName, String discipline,
                                        double time, int placement, boolean alreadyOnList) {
        if (alreadyOnList) {
            for (Athlete athlete : athletes) {
                if (athlete.getName().contains(name)) {
                    athlete.getCompetitionTimes().add(new Competition(competitionName, discipline, time, placement));
                }
            }
        } else {
            String[] athleteToAddCompetition = name.split(",");
            name = athleteToAddCompetition[1] + "," + athleteToAddCompetition[2];
            for (Athlete athlete : athletes) {
                if (athlete.getName().contains(name)) {
                    athlete.getCompetitionTimes().add(new Competition(competitionName, discipline, time, placement));
                }
            }
        }
    }
    public void clearAthleteList() {
        athletes.clear();
    }
}