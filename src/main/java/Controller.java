import java.util.ArrayList;

public class Controller {
    Chairman chairman = new Chairman();
    Accountant accountant = new Accountant();
    Trainer trainer = new Trainer();
    FileHandler fh = new FileHandler();

    public ArrayList<Member> getAllMembers() {
        return accountant.getListOfMembers();
    }

    public ArrayList<Member> getFilteredMembers(boolean hasPaid) {
        return accountant.filterMembersByPaymentStatus(hasPaid);
    }

    public String getFormatMembers(ArrayList<Member> members) {
       return accountant.formatMemberPaymentStatus(members);
    }

    public String getFormattedTotalMembershipFees() {
        return accountant.formatTotalMembershipFees();
    }

    public void addAthletesToList(){
        trainer.addAthletesToList(chairman.getListOfMembers());
    }

    public void saveAthleteMembersToAthleteTrainingFile(){
        fh.saveAthleteMembersToAthleteTrainingFile(trainer.getAthletes());
    }
    public String getFoundMembers(String searchKeyword) {
        ArrayList<Member> foundMembers = accountant.findMembers(searchKeyword);

        if (foundMembers.isEmpty()) {
            return "Ingen medlemmer fundet.";
        }

        String result = "Fundne medlemmer:\n";
        for (Member member : foundMembers) {
            result += "Navn: " + member.getName() +
                    ", ID: " + member.getMemberNumber() +
                    ", Telefonnummer: " + member.getPhoneNumber() + "\n";
        }

        return result;
    }

    public boolean updateMemberPaymentStatus(int memberNumber, boolean hasPaid) {
        return accountant.updateMemberPaymentStatus(memberNumber, hasPaid);
    }

    public ArrayList<Member> findMembers(String searchKeyword) {
        return accountant.findMembers(searchKeyword);
    }

    public void setAthleteTrainingTime(String name, String discipline, double newTime){
        ArrayList<String> updatedTrainingTimes = trainer.setAthleteTrainingTime(name, fh.getAthletesFromAthleteTrainingFile(), discipline, newTime);
        fh.saveUpdatedAthletesToFile(updatedTrainingTimes);
    }

    public void addCompetitionToAthlete(String name, String competitionName, String discipline, double time, int placement){
        fh.addCompetitionToListFromFile(trainer);
        trainer.addCompetitionToAthlete(name, competitionName, discipline, time, placement);
        fh.saveCompetitionResultsToFile(trainer.getAthletes());
    }

    public void rewriteFileWithNewData(){
        fh.saveToFile(chairman.getListOfMembers(), false);
    }

    public void setFirstNameForMemberAtIndex(int index, String newName){
        chairman.setNameForMemberAtIndex(index, newName);
    }

    public void setLastNameForMemberAtIndex(int index, String newName) {
        chairman.setLastNameForMemberAtIndex(index, newName);
    }

    public void setGender(int index, String gender) {
        chairman.setGenderForMemberAtIndex(index, gender);
    }

    public void setAdress(int index, String adress) {
        chairman.setAdressForMemberAtIndex(index,adress);
    }

    public void setPhonenumber(int index, int number) {
        chairman.setPhonenumberForMemberAtIndex(index, number);
    }

    public void setActivePassive(int index) {
        chairman.setActivePassiveForMemberAtIndex(index);
    }

    public void setHobbyAthlete(int index) {
        chairman.setHobbyAthleteForMemberAtIndex(index);
    }

    public Enum<MembershipType> getMembershipStatus(int index) {
        return chairman.getMembershipStatus(index);
    }

    public Enum<MembershipType> getMembershipType(int index) {
        return chairman.getMembershipType(index);
    }

    public int getMemberIndex(String memberName){
        return chairman.getMemberIndex(memberName);
    }


    public void addMember(Member member, int indexInList){
        chairman.addMember(member, indexInList);
    }

    public String getName(Member member) {
        return member.getName();
    }

    public Member getMemberFromIndex(int choice, ArrayList<Member> foundMembers) {
        return chairman.getMemberFromIndex(choice,foundMembers);
    }

}



