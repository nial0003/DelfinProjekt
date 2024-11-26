import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileHandler {
    private File file = new File("Members");
    private File atheleteFile = new File("Athletes");

    //Loads the Members from the file and returns them in an ArrayList which can then be updated and used.
    public ArrayList<Member> loadFromFile() {
        ArrayList<Member> listOfMembers = new ArrayList<>();

        try (FileReader fr = new FileReader(file)) {

            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(",");
                String lastName = data[0];
                String firstName = data[1];
                int yearBorn = Integer.parseInt(data[2]);
                int monthBorn = Integer.parseInt(data[3]);
                int dayBorn = Integer.parseInt(data[4]);
                String gender = data[5];
                String address = data[6];
                int number = Integer.parseInt(data[7]);
                String membershipStatus = data[8];
                String membershipType = data[9];
                boolean hasPaid = Boolean.parseBoolean(data[10]);

                listOfMembers.add(new Member(firstName, lastName, LocalDate.of(yearBorn, monthBorn, dayBorn), gender, address,
                        number, membershipStatus, membershipType, hasPaid));
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listOfMembers;
    }


    //Saves list of members to the file, by converting member details to CSV-style string.
    public void saveToFile(ArrayList<Member> listOfMembers) {
        try (FileWriter fw = new FileWriter(file, true)) {
            for (Member member : listOfMembers) {
                fw.write(member.toCSVStyle());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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


