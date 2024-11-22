import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileHandler {
    private File file = new File("Members.txt");

    public ArrayList<Member> loadFromFile() {
        ArrayList<Member> listOfMembers = new ArrayList<>();


        try (FileReader fr = new FileReader(file)) {

            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            if (!line.isBlank()) {
                String[] data = line.split(",");
                String lastName = data[0];
                String firstName = data[1];
                int yearBorn = Integer.parseInt(data[2]);
                int monthBorn = Integer.parseInt(data[3]);
                int dayBorn = Integer.parseInt(data[4]);
                String gender = data[5];
                String address = data[6];
                int number = Integer.parseInt(data[7]);
                String memberType = data[8];
                boolean hasPaid = Boolean.parseBoolean(data[9]);

                listOfMembers.add(new Member(firstName, lastName, LocalDate.of(yearBorn, monthBorn, dayBorn), gender, address, number, memberType, hasPaid));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listOfMembers;
    }
}


