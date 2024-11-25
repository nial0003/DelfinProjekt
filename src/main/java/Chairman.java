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

    //adds a new member to the newMember and listOfMembers Arraylist and then uploads the new Member to the file at the end of the file.
    //This is done so that other classes will have access to the new member via the listOfMembers arraylist before it's
    //reloaded from the file.
    public void addMember(String firstName, String lastName, int yearBorn, int monthBorn, int dayBorn, String gender,
                          String address, int number, String membershipStatus, String membershipType, boolean hasPaid){

        Member member = new Member(firstName, lastName, LocalDate.of(yearBorn, monthBorn, dayBorn), gender, address,
                number,membershipStatus, membershipType,  hasPaid);
        newMember.add(member);
        listOfMembers.add(member);
        fh.saveToFile(newMember);
    }

    public ArrayList<Member> getListOfMembers() {
        return listOfMembers;
    }
}
