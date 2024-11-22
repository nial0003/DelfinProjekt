import java.time.LocalDate;
import java.util.ArrayList;

public class Chairman {
    FileHandler fh = new FileHandler();
    ArrayList<Member> listOfMembers;
    ArrayList<Member> newMember;

    public Chairman(){
        listOfMembers = fh.loadFromFile();
        newMember = new ArrayList<>();
    }

    //adds a new member to the newMember Arraylist and then uploads the new Member to the file at the end of the file.
    public void addMember(String firstName, String lastName, int yearBorn, int monthBorn, int dayBorn, String gender,
                          String address, int number, String membershipStatus, String membershipType, boolean hasPaid){

        newMember.add(new Member(firstName, lastName, LocalDate.of(yearBorn, monthBorn, dayBorn), gender, address,
                number,membershipStatus, membershipType,  hasPaid));
        fh.saveToFile(newMember);
    }
}
