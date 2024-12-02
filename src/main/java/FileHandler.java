import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileHandler {
    private File file = new File("Members");
    private File atheleteFile = new File("Athletes");

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

                // Use the constructor for file loading
                listOfMembers.add(new Member(firstName, lastName, LocalDate.of(yearBorn, monthBorn, dayBorn), gender, address,
                        phoneNumber, membershipStatus, membershipType, hasPaid, memberNumber));

                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listOfMembers;
    }

    //-------------------Saves list of members to the file, by converting member details to CSV-style string------------
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
    public void saveToAthleteFile(ArrayList<Athlete> listOfAthletes) {
        try (FileWriter fw = new FileWriter(atheleteFile)) {
            for (Athlete athlete : listOfAthletes) {
                String name = athlete.getName();
                fw.write(athlete.toCSVStyle(name));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public String printFromAthleteFile() {
        String output = "";
        try (FileReader fr = new FileReader(atheleteFile)) {
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(",");
                for (int i = 0; i<data.length;i++){
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


