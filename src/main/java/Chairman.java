import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

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
        listOfMembers.add(member);
        fh.saveToFile(newMember);
    }

    public Map<MembershipType, ArrayList<Member>> groupByMembershipStatus() {
        return listOfMembers.stream()
                .collect(Collectors.groupingBy(member -> (MembershipType) member.getMemberShipStatus(),
                        Collectors.toCollection(ArrayList::new)));
    }

    public Map<MembershipType, ArrayList<Member>> groupByMembershipType() {
        return listOfMembers.stream()
                .collect(Collectors.groupingBy(member -> (MembershipType) member.getMembershipType(),
                        Collectors.toCollection(ArrayList::new)));
    }

    public Map<MembershipType, ArrayList<Member>> groupByAgeGroup() {
        return listOfMembers.stream()
                .collect(Collectors.groupingBy(member -> (MembershipType) member.getAgeGroup(),
                        Collectors.toCollection(ArrayList::new)));
    }
}
