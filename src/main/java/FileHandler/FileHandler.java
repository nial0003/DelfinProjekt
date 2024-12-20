package FileHandler;

import Trainer.*;
import Membership.*;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {
    private File members = new File("CSV-Files/Members.csv");
    private File AthletesTrainingResults = new File("CSV-Files/AthletesTrainingResults.csv");;
    private File athleteCompetitionResults = new File("CSV-Files/AthleteCompetitionResults.csv");

    //-------------------Use Paths.get() to create platform-independent paths-------------------------------------------
    public FileHandler() {
    }

    //-------------------Loads the Members.csv from the file and returns them in an ArrayList---------------------------
    public ArrayList<Member> loadFromFile() {
        ArrayList<Member> listOfMembers = new ArrayList<>();

        try (FileReader fr = new FileReader(members)) {

            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(",");

                // Parse fields in the same order as in the toCSVStyle method
                String lastName = data[0];
                String firstName = data[1];
                int yearBorn = Integer.parseInt(data[2]);
                int monthBorn = Integer.parseInt(data[3]);
                int dayBorn = Integer.parseInt(data[4]);
                String gender = data[5];
                String address = data[6];
                int phoneNumber = Integer.parseInt(data[7]);
                String membershipStatus = data[8];
                String membershipType = data[9];
                boolean hasPaid = Boolean.parseBoolean(data[11]);
                int memberNumber = Integer.parseInt(data[12]);
                boolean hasBeenAddedToAthletes = Boolean.parseBoolean(data[13]);

                // Use the constructor for file loading
                listOfMembers.add(new Member(firstName, lastName, LocalDate.of(yearBorn, monthBorn, dayBorn), gender, address,
                        phoneNumber, membershipStatus, membershipType, hasPaid, memberNumber, hasBeenAddedToAthletes));

                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listOfMembers;
    }

    //-------------------Method to save members to member file----------------------------------------------------------

    //Saves list of members to the file, by converting member details to CSV-style string.
    //Write true if you want to add the information to the end of the file
    //Write false if you want to create a new file with new information.
    public void saveToMemberFile(ArrayList<Member> listOfMembers, boolean appendFile) {
        try (FileWriter fw = new FileWriter(members, appendFile)) {
            for (Member member : listOfMembers) {
                fw.write(member.toCSVStyle());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //-------------------Method to save athletes to athlete file--------------------------------------------------------
    public void saveAthleteMembersToAthleteTrainingFile(ArrayList<Athlete> listOfAthletes) {
        try (FileWriter fw = new FileWriter(AthletesTrainingResults, true)) {
            for (Athlete athlete : listOfAthletes) {
                String name = athlete.getName();
                fw.write(athlete.toCSVStyle(name, false));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //-------------------Method to save competition result for athletes-------------------------------------------------
    public void saveCompetitionResultsToFile(ArrayList<Athlete> listOfAthletes) {
        try (FileWriter fw = new FileWriter(athleteCompetitionResults)) {
            for (Athlete athlete : listOfAthletes) {
                if (!athlete.getCompetitionTimes().isEmpty()) {
                    String name = athlete.getName();
                    fw.write(athlete.toCSVStyle(name, true));
                }
            }
            fw.write("end");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //-------------------Method to save competition results to results file---------------------------------------------

    // Goes through the file AthletecompetitionResults and checks if each line matches the pattern given
    // if it does it saves the specific information as a competition and adds it to the correct Trainer.Athlete based
    // on the name of the Trainer.Athlete in the file.
    public void addCompetitionToListFromFile(String name, Trainer trainer) {
        try (FileReader fr = new FileReader(athleteCompetitionResults)) {
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> athleteCompResults = new ArrayList<>();

            String line = br.readLine();
            while (!line.contentEquals("end")) {
                if (!line.isBlank() && !line.equalsIgnoreCase("end")) {
                    athleteCompResults.add(line);
                }
                line = br.readLine();
            }
            Pattern pattern = Pattern.compile("\\[(.*?)]");

            for (String athleteEntry : athleteCompResults) {
                // Split the line into athlete info and competitions
                String[] parts = athleteEntry.split(",Stævne\\{");
                String athleteInfo = parts[0];
                String[] competitions = Arrays.copyOfRange(parts, 1, parts.length);

                // Extract the athlete's name from athleteInfo
                int lastCommaIndex = athleteInfo.lastIndexOf(",");
                String nameOfAthlete = athleteInfo.substring(0, lastCommaIndex);

                for (String competition : competitions) {
                    // Add back the opening brace removed by split
                    competition = "Stævne{" + competition;

                    Matcher matcher = pattern.matcher(competition);
                    ArrayList<String> listResult = new ArrayList<>();
                    while (matcher.find()) {
                        listResult.add(matcher.group(1));
                    }

                    // Ensure listResult contains all expected parts
                    if (listResult.size() >= 4) {
                        String compName = listResult.get(0);
                        String disciplineName = listResult.get(1);
                        Double time = Double.valueOf(listResult.get(2));
                        int placement = Integer.parseInt(listResult.get(3));
                        trainer.addCompetitionToAthlete(nameOfAthlete, compName, disciplineName, time, placement, true);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //-------------------Method to save list of updated athletes--------------------------------------------------------
    public void saveUpdatedAthletesToFile(ArrayList<String> updatedListOfAthletes) {
        try (FileWriter fw = new FileWriter(AthletesTrainingResults)) {
            for (String str : updatedListOfAthletes) {
                fw.write(str + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Returns an arrayList of Strings from the athleteTrainingFile. This is done so we can search through it and update the times
    //of a given member.
    public ArrayList<String> getAthletesFromAthleteTrainingFile() {
        try (FileReader fr = new FileReader(AthletesTrainingResults)) {
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> result = new ArrayList<>();

            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                result.add(line);
                line = br.readLine();
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<String> junOrSen(String trainOrComp, String seniorOrJunior) {
        ArrayList<String> junOrSen = new ArrayList<>();
        if (trainOrComp.equalsIgnoreCase("training")) {
            ArrayList<String> listOfAthletes = getAthletesFromAthleteTrainingFile();
            for (String athlete : listOfAthletes) {
                String[] strs = athlete.split(",");
                String team = strs[2];
                if (team.equalsIgnoreCase(seniorOrJunior)) {
                    junOrSen.add(athlete);
                }
            }
        } else if (trainOrComp.equalsIgnoreCase("competition")) {
            try (FileReader fr = new FileReader(athleteCompetitionResults)) {
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (!line.equalsIgnoreCase("end")) {
                    String[] lineSplitted = line.split(",");
                    String team = lineSplitted[2];
                    if (team.equalsIgnoreCase(seniorOrJunior)) {
                        junOrSen.add(line);
                    }
                    line = br.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return junOrSen;
    }

    public ArrayList<String> showBestAthletes(String trainOrComp, String seniorOrJunior, String disciplin, int index) {
        ArrayList<String> junOrSen = junOrSen(trainOrComp, seniorOrJunior);
        ArrayList<String> fiveBestAthletes = new ArrayList<>();

        if (trainOrComp.equalsIgnoreCase("training")) {
            Map<String, Double> athleteTimes = new HashMap<>();

            // Regex to match disciplines and their times
            String regex = "([\\p{L}]+)\\[(\\d+\\.\\d+)]";
            Pattern pattern = Pattern.compile(regex);

            for (String athlete : junOrSen) {
                String[] parts = athlete.split(",");
                if (parts.length < 4) {
                    continue;
                }

                String name = parts[0] + " " + parts[1];
                String trainingData = athlete.contains("træning{") && athlete.contains("}") ?
                        athlete.substring(athlete.indexOf("træning{") + 8, athlete.lastIndexOf('}')) : "";


                Matcher matcher = pattern.matcher(trainingData);

                while (matcher.find()) {
                    String currentDiscipline = matcher.group(1);
                    double time = Double.parseDouble(matcher.group(2));

                    if (currentDiscipline.equalsIgnoreCase(disciplin) && time > 0.0) {
                        athleteTimes.put(name, time);
                    }
                }
            }

            if (athleteTimes.isEmpty()) {
                return fiveBestAthletes;
            }

            // Sort athletes by time
            List<Map.Entry<String, Double>> sortedAthletes = new ArrayList<>(athleteTimes.entrySet());
            sortedAthletes.sort(Map.Entry.comparingByValue());

            for (int i = 0; i < Math.min(5, sortedAthletes.size()); i++) {
                Map.Entry<String, Double> entry = sortedAthletes.get(i);
                fiveBestAthletes.add((i + 1) + ". " + entry.getKey() + " - " + entry.getValue() + " seconds");
            }
        } else if (trainOrComp.equalsIgnoreCase("competition")) {
            ArrayList<String> athleteData = junOrSen("competition", seniorOrJunior);
            Map<String, Double> bestTimes = new HashMap<>();

            for (String line : athleteData) {
                String[] parts = line.split(",Stævne\\{");
                if (parts.length < 2) continue;

                String name = parts[0];

                for (int i = 1; i < parts.length; i++) {
                    String event = parts[i].replaceAll("}", "");

                    // Add the extractBetween calls here
                    String evenDiscipline = extractBetween(event, "Disciplin[", "]");
                    String eventTime = extractBetween(event, "Tid[", "]");

                    if (evenDiscipline != null && evenDiscipline.equalsIgnoreCase(disciplin) && eventTime != null) {
                        double time = Double.parseDouble(eventTime);
                        bestTimes.put(name, Math.min(bestTimes.getOrDefault(name, Double.MAX_VALUE), time));
                    }
                }
            }


            List<Map.Entry<String, Double>> sortedResults = new ArrayList<>(bestTimes.entrySet());
            sortedResults.sort(Map.Entry.comparingByValue());

            for (int i = 0; i < Math.min(5, sortedResults.size()); i++) {
                Map.Entry<String, Double> entry = sortedResults.get(i);
                String name = entry.getKey();
                double time = entry.getValue();
                fiveBestAthletes.add((i + 1) + ", " + name + " - " + time + " sekunder");
            }
        }


        return fiveBestAthletes;
    }
    private String extractBetween(String source, String start, String end) {
        int startIndex = source.toLowerCase().indexOf(start.toLowerCase());
        if (startIndex == -1) return null;
        startIndex += start.length();
        int endIndex = source.indexOf(end, startIndex);
        if (endIndex == -1) return null;
        return source.substring(startIndex, endIndex);
    }
}


