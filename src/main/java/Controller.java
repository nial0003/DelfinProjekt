import java.util.ArrayList;

public class Controller {
    Chairman chairman = new Chairman();
    private Member member;
    private FileHandler fh;



    private ArrayList<Member> getListOfMembers() {
        return chairman.getListOfMembers();
    }

    public void setAgeGroupForMember (Enum<MembershipType> ageGroup, Member selectedMember){
        chairman.setAgeGroupForMember(ageGroup, selectedMember);
    }

    public void setActivePassiveForMember(Enum<MembershipType> memberStatus, Member selectedMember) {
        chairman.setActivePassiveForMember(memberStatus, selectedMember);
    }

    public void setHobbyAthleteForMember (Enum<MembershipType> memberType, Member selectedMember) {
        chairman.setHobbyAthleteForMember(memberType, selectedMember);
    }

    public String getName(Member member) {
        return member.getName();
    }

    public Member getMemberFromIndex(int choice, ArrayList<Member> foundMembers) {
        return chairman.getMemberFromIndex(choice,foundMembers);
    }

}



