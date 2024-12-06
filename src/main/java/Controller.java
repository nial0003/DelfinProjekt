import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Controller {
    Chairman chairman = new Chairman();
    Accountant accountant = new Accountant();
    Trainer trainer = new Trainer();
    FileHandler fh = new FileHandler();

    //-------------------Methods for Accountant class-------------------------------------------------------------------
    public ArrayList<Member> getAllMembers() {
        return accountant.getListOfMembers();
    }

    public ArrayList<Member> getFilteredMembers(boolean hasPaid) {
        return accountant.filterMembersByPaymentStatus(hasPaid);
    }

//    public String getFormatMembers(ArrayList<Member> members) {
//        return accountant.formatMemberPaymentStatus(members);
//    }

    public String formatMemberPaymentStatus(ArrayList<Member> members) {
        if (members == null || members.isEmpty()) {
            return "\nIngen medlemmer fundet.\n";
        }

        String header = "-------------------------------------------------\n";
        String footer = "-------------------------------------------------\n";

        String formattedMembers = header;

        for (Member member : members) {
            formattedMembers += "Navn: " + member.getName() + "\n" +
                    "Medlemsnummer: " + member.getMemberNumber() + "\n" +
                    "Betalt: " + (member.getHasPaid() ? "Ja" : "Nej") + "\n" +
                    "Kontingentgebyr: " + member.determineMembershipFee() + " DKK\n" +
                    footer;
        }
        return formattedMembers;
    }

    public void addAthletesToListForTraining() {
        trainer.addAthletesToList(chairman.getListOfMembers(), true);
    }

    private void addAthletesToListForCompetition() {
        trainer.addAthletesToList(chairman.getListOfMembers(), false);
    }

    public String getCalculateTotalMembershipFees() {
        int totalFees = accountant.calculateTotalMembershipFees();
        return "Samlede kontingent indtægter: " + totalFees + " DKK";
    }

    public String getCalculateReceivedPayments() {
        int totalReceived = accountant.calculateReceivedPayments();
        return "Modtagene betalinger: " + totalReceived + " DKK";
    }

//    public String getFoundMembers(String searchKeyword) {
//        ArrayList<Member> foundMembers = accountant.findMembers(searchKeyword);
//    }

    public String getCalculateOutstandingPayments() {
        int totalOutstanding = accountant.calculateOutstandingPayments();
        return "Udestående betalinger: " + totalOutstanding + " DKK";
    }

    public String getCalculatePaidPercentage() {
        double paidPercentage = accountant.calculatePaidPercentage();
        return String.format("Procentdel af betalte kontingentgebyrer: %.2f%%", paidPercentage);
    }

    public String getCalculateOutstandingPercentage() {
        double outstandingPercentage = accountant.calculateOutstandingPercentage();
        return String.format("Procentdel af ubetalte kontingentgebyrer: %.2f%%", outstandingPercentage);
    }

    public boolean updateMemberPaymentStatus(int memberNumber, boolean hasPaid) {
        return accountant.updateMemberPaymentStatus(memberNumber, hasPaid);
    }

    public ArrayList<Member> findMembers(String searchKeyword) {
        return accountant.findMembers(searchKeyword);
    }

    //-------------------Methods for Trainer class----------------------------------------------------------------------
    public void saveAthleteMembersToAthleteTrainingFile() {
        fh.saveAthleteMembersToAthleteTrainingFile(trainer.getAthletes());
    }

    public void setAthleteTrainingTime(String name, String discipline, double newTime) {
        ArrayList<String> updatedTrainingTimes = trainer.setAthleteTrainingTime(name, fh.getAthletesFromAthleteTrainingFile(), discipline, newTime);
        fh.saveUpdatedAthletesToFile(updatedTrainingTimes);
    }

    public void addCompetitionToAthlete(String name, String competitionName, String discipline, double time,
                                        int placement) {
        addAthletesToListForCompetition();
        fh.addCompetitionToListFromFile(trainer);
        trainer.addCompetitionToAthlete(name, competitionName, discipline, time, placement, false);
        fh.saveCompetitionResultsToFile(trainer.getAthletes());
        trainer.clearAthleteList();
    }

    public ArrayList<String> showBestAthletes(String trainOrComp, String seniorOrJunior, String disciplin,
                                              int index) {
        return fh.showBestAthletes(trainOrComp, seniorOrJunior, disciplin, index);
    }


    //-------------------Methods for Chairman class---------------------------------------------------------------------
    public void rewriteFileWithNewData() {
        fh.saveToFile(chairman.getListOfMembers(), false);
    }

    public ArrayList<String> findCorrectAthlete(String name) {
        ArrayList<String> athletes = fh.getAthletesFromAthleteTrainingFile();
        ArrayList<String> moreThanOneAthlete = new ArrayList<>();
        for (String athlete : athletes) {
            if (athlete.toLowerCase().contains(name)) {
                moreThanOneAthlete.add(athletes.indexOf(athlete) + "," + athlete);
            }
        }
        return moreThanOneAthlete;
    }


    public void setFirstNameForMemberAtIndex(int index, String newName) {
        chairman.setNameForMemberAtIndex(index, newName);
    }

    public void setLastNameForMemberAtIndex(int index, String newName) {
        chairman.setLastNameForMemberAtIndex(index, newName);
    }

    public void setGender(int index, String gender) {
        chairman.setGenderForMemberAtIndex(index, gender);
    }

    public void setAdress(int index, String adress) {
        chairman.setAdressForMemberAtIndex(index, adress);
    }

//    public void addAthletesToList() {
//        trainer.addAthletesToList(chairman.getListOfMembers());
//    }

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

    public int getMemberIndex(String memberName) {
        return chairman.getMemberIndex(memberName);
    }


    public void addMember(Member member, int indexInList) {
        chairman.addMember(member, indexInList);
    }

    public String getName(Member member) {
        return member.getName();
    }

    public Member getMemberFromIndex(int choice, ArrayList<Member> foundMembers) {
        return chairman.getMemberFromIndex(choice, foundMembers);
    }

}



