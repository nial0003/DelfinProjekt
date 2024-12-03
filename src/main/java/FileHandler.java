import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileHandler {
    private File file = new File("Members");
    private File athletesTrainingFile = new File("AthletesTrainingResults");
    private File athleteCompetitionResults = new File("AthleteCompetitionResults");

    //-------------------Loads the Members from the file and returns them in an ArrayList-------------------------------
    public ArrayList<Member> loadFromFile() {
        ArrayList<Member> listOfMembers = new ArrayList<>();

        try (FileReader fr = new FileReader(file)) {

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
                String ageGroup = data[10]; // Ignored since it's recalculated
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


    //Saves list of members to the file, by converting member details to CSV-style string.
    //Write true if you want to add the information to the end of the file
    //Write false if you want to create a new file with new information.
    public void saveToFile(ArrayList<Member> listOfMembers, boolean appendFile) {
        try (FileWriter fw = new FileWriter(file, appendFile)) {
            for (Member member : listOfMembers) {
                fw.write(member.toCSVStyle());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //-------------------Method to save athletes to athlete file--------------------------------------------------------
    public void saveAthleteMembersToAthleteTrainingFile(ArrayList<Athlete> listOfAthletes) {
        try (FileWriter fw = new FileWriter(athletesTrainingFile)) {
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
    // if it does it saves the specific information as a competition and adds it to the correct Athlete based
    // on the name of the Athlete in the file.
    public void addCompetitionToListFromFile(Trainer trainer) {
        try (FileReader fr = new FileReader(athleteCompetitionResults)) {
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> athleteCompResults = new ArrayList<>();
            ArrayList<String> listResult = new ArrayList<>();

            String line = br.readLine();
            while (!line.contentEquals("end")) {
                if (!line.isBlank() && !line.equalsIgnoreCase("end")) {
                    athleteCompResults.add(line);
                }
                line = br.readLine();
            }
            Pattern pattern = Pattern.compile("\\[(.*?)]");
            String nameOfAthlete = "";
            for (int i = 0; i < athleteCompResults.size(); i++) {

                if (!athleteCompResults.get(i).contains("Stævne")) {
                    if (!athleteCompResults.get(i).isBlank()){
                        int lastCommaIndex = athleteCompResults.get(i).lastIndexOf(",");
                        nameOfAthlete = athleteCompResults.get(i).substring(0, lastCommaIndex);
                    }
                    i++;
                }
                Matcher matcher = pattern.matcher(athleteCompResults.get(i));
                while (matcher.find()){
                    listResult.add(matcher.group(1));
                }
                String compName = listResult.get(0);
                String disciplineName = listResult.get(1);
                Double time = Double.valueOf(listResult.get(2));
                int placement = Integer.parseInt(listResult.get(3));
                trainer.addCompetitionToAthlete(nameOfAthlete, compName, disciplineName, time, placement);
                listResult.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //-------------------Method to save list of updated athletes--------------------------------------------------------
    public void saveUpdatedAthletesToFile(ArrayList<String> updatedListOfAthletes) {
        try (FileWriter fw = new FileWriter(athletesTrainingFile)) {
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
        try (FileReader fr = new FileReader(athletesTrainingFile)) {
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

    //------------------------------------------------------------------------------------------------------------------
    public String printFromAthleteFile() {
        String output = "";
        try (FileReader fr = new FileReader(athletesTrainingFile)) {
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(",");
                for (int i = 0; i < data.length; i++) {
                    output += data[i] + " ";
                }
                output += "\n";
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return output;
    }







}


