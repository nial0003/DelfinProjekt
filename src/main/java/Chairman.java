import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
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

    //-------------------Method to add a new member---------------------------------------------------------------------

    //adds a new member to the newMember and listOfMembers Arraylist and then uploads the new Member to the file at the end of the file.
    //This is done so that other classes will have access to the new member via the listOfMembers arraylist before it's
    //reloaded from the file.
    public void addMember(String firstName, String lastName, int yearBorn, int monthBorn, int dayBorn, String gender,
                          String address, int number, String membershipStatus, String membershipType, boolean hasPaid){

        Member member = new Member(firstName, lastName, LocalDate.of(yearBorn, monthBorn, dayBorn), gender, address,
                number,membershipStatus, membershipType,  hasPaid, false);
        newMember.add(member);
        listOfMembers.add(member);
        fh.saveToFile(newMember, true);
    }

    public void addMember(Member member, int indexInList){
        listOfMembers.set(indexInList, member);
    }

    public int getMemberIndex(String memberName) {
        int index = 0;
        for (int i = 0; i < listOfMembers.size(); i++) {
            if (listOfMembers.get(i).getName().equalsIgnoreCase(memberName)){
                index = i;
            }
        }
        return index;
    }

    public void setNameForMemberAtIndex(int index, String newName){
        listOfMembers.get(index).setFirstName(newName);
    }

    public void setLastNameForMemberAtIndex(int index, String newName) {
        listOfMembers.get(index).setLastName(newName);
    }

    public void setGenderForMemberAtIndex(int index, String newGender) {
        listOfMembers.get(index).setGender(newGender);
    }

    public void setAdressForMemberAtIndex(int index, String newAdress) {
        listOfMembers.get(index).setAddress(newAdress);
    }

    public void setPhonenumberForMemberAtIndex(int index, int number) {
            listOfMembers.get(index).setPhoneNumber(number);
    }

    public void setActivePassiveForMemberAtIndex(int index) {
        if (listOfMembers.get(index).getMemberShipStatus() == MembershipType.AKTIV) {
            listOfMembers.get(index).setMembershipStatus(MembershipType.PASSIV);
        } else {
            listOfMembers.get(index).setMembershipStatus(MembershipType.AKTIV);
        }
    }

    public void setHobbyAthleteForMemberAtIndex(int index) {
        if (listOfMembers.get(index).getMembershipType() == MembershipType.HOBBY) {
            listOfMembers.get(index).setMembershipType(MembershipType.ATLET);
        } else {
            listOfMembers.get(index).setMembershipType(MembershipType.HOBBY);
        }
    }

    public Enum<MembershipType> getMembershipStatus(int index) {
        return listOfMembers.get(index).getMemberShipStatus();
    }

    public Enum<MembershipType> getMembershipType(int index) {
        return listOfMembers.get(index).getMembershipType();
    }

    public ArrayList<Member> getListOfMembers() {
        return listOfMembers;
    }

    //-------------------Methods to group members by different parameters-----------------------------------------------

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


    //------------------Used for editMember ----------------------------------------------------------------------------
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
    private int takeIntUserInput(int minimumValue, int maximumValue) {
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
    public void setHobbyAthleteForMember(Enum<MembershipType> memberType, Member selectedMember) {
        selectedMember.setMembershipType(memberType);
    }






}
