import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Chairman {
    FileHandler fh = new FileHandler();
    ArrayList<Member> listOfMembers;
    ArrayList<Member> newMember;
    Scanner sc = new Scanner(System.in);


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

    //Takes a member from an indexpoint in the ArrayList
    public Member getMemberFromIndex(int choice, ArrayList<Member> listOfMembers) {
        Member selectedMember = null;
        if (0 < choice && choice <= listOfMembers.size()) {
            selectedMember =listOfMembers.get(choice-1);
        }
        return selectedMember;
    }

    //Finds a member and adds it to a new ArrayList called foundMembers
    public ArrayList<Member> findMembers(String search) {
    ArrayList<Member> foundMembers = new ArrayList<>();
    for (Member member : listOfMembers) {
        if (member.getName().toLowerCase().contains(search.toLowerCase())) {
            foundMembers.add(member);
        }
    }
    return foundMembers;
    }

    //Takes an integer userinput to use when needed an integer with a minimum and a maximum value
    public int takeIntUserInput(int minimumValue, int maximumValue) {
        String input = sc.next();
        int inputInt;

        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ugyldigt input. Indsæt et tal");
            inputInt = takeIntUserInput(minimumValue,maximumValue);
        }

        while (!(inputInt >= minimumValue && inputInt <= maximumValue )) {
            System.out.println("Ugyldigt telefonnummer. Prøv igen");
            inputInt = takeIntUserInput(minimumValue,maximumValue);
        }
            return inputInt;
    }

    //Sets which agegroup a member belongs to
    public void setAgeGroupForMember (Enum<MembershipType> ageGroup, Member selectedMember){
        selectedMember.setAgeGroup(ageGroup);
    }

    //Sets active or passive for a member
    public void setActivePassiveForMember(Enum<MembershipType> memberStatus, Member selectedMember) {
        selectedMember.setMembershipStatus(memberStatus);
    }

    //Sets if a member is hobby or athlete
    public void setHobbyAthleteForMember(Enum<MembershipType> memmberType, Member selectedMember) {
        selectedMember.setMembershipType(memmberType);
    }





}
