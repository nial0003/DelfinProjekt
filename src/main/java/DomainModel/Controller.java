package DomainModel;

import Accounting.Accountant;
import Chairman.Chairman;
import Membership.*;
import Trainer.*;
import FileHandler.*;

import java.util.ArrayList;
import java.util.Map;

public class Controller {
    Chairman chairman = new Chairman();
    Accountant accountant = new Accountant();
    Trainer trainer = new Trainer();
    FileHandler fh = new FileHandler();

    //-------------------Methods for Accounting.Accountant class-------------------------------------------------------------------
    public ArrayList<Member> getAllMembers() {
        return accountant.getListOfMembers();
    }

    public ArrayList<Member> getFilteredMembers(boolean hasPaid) {
        return accountant.filterMembersByPaymentStatus(hasPaid);
    }

    public String getFormattedMemberPaymentStatus(ArrayList<Member> members) {
        return accountant.formatMemberPaymentStatus(members);
  }

    public void addAthletesToListForTraining() {
        trainer.addAthletesToList(chairman.getListOfMembers(), true);
    }

    private void addAthletesToListForCompetition() {
        trainer.addAthletesToList(chairman.getListOfMembers(), false);
    }

    public int getCalculateTotalMembershipFees() {
        return accountant.calculateTotalMembershipFees();
    }

    public int getCalculateReceivedPayments() {
        return accountant.calculateReceivedPayments();
    }

    public int getCalculateOutstandingPayments() {
        return accountant.calculateOutstandingPayments();
    }

    public double getCalculatePaidPercentage() {
        return accountant.calculatePaidPercentage();
    }

    public double getCalculateOutstandingPercentage() {
        return accountant.calculateOutstandingPercentage();
    }

    public boolean updateMemberPaymentStatus(int memberNumber, boolean hasPaid) {
        return accountant.updateMemberPaymentStatus(memberNumber, hasPaid);
    }

    public ArrayList<Member> findMembers(String searchKeyword) {
        return accountant.findMembers(searchKeyword);
    }

    //-------------------Methods for Trainer.Trainer class----------------------------------------------------------------------
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


    //-------------------Methods for Chairman.Chairman class---------------------------------------------------------------------
    public void rewriteFileWithNewData() {
        fh.saveToMemberFile(chairman.getListOfMembers(), false);
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

    public String getName(Member member) {
        return member.getName();
    }

    public Member getMemberFromIndex(int choice, ArrayList<Member> foundMembers) {
        return chairman.getMemberFromIndex(choice, foundMembers);
    }

    public ArrayList<Member> findMember(String search) {
        return chairman.findMembers(search);
    }

    public Map<MembershipType, ArrayList<Member>> groupByMembershipStatus() {
        return chairman.groupByMembershipStatus();
    }

    public Map<MembershipType, ArrayList<Member>> groupByMembershipType() {
        return chairman.groupByMembershipType();
    }

    public Map<MembershipType, ArrayList<Member>> groupByAgeGroup() {
        return chairman.groupByAgeGroup();
    }

    public void addMember(String firstName, String lastName, int year, int month, int day, String gender, String address, int phoneNumber, String membershipStatus, String membershipType, boolean hasPaid) {
        chairman.addMember(firstName, lastName, year, month, day, gender, address, phoneNumber, membershipStatus, membershipType, hasPaid);
        rewriteFileWithNewData();
    }


}



