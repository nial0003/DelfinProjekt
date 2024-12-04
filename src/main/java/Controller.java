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

    public String getCalculateTotalMembershipFees() {
        int totalFees = accountant.calculateTotalMembershipFees();
        return "Samlede kontingentindbetalinger: " + totalFees + " DKK";
    }

    public String getCalculateReceivedPayments (){
        int totalReceived = accountant.calculateReceivedPayments();
        return "Modtagene betalinger: " + totalReceived + " DKK";
    }

    public String getCalculateOutstandingPayments (){
        int totalOutstanding = accountant.calculateOutstandingPayments();
        return "Udestående betalinger: " + totalOutstanding + " DKK";
    }

    public String getCalculatePaidPercentage (){
        double paidPercentage = accountant.calculatePaidPercentage();
        return String.format("Procentdel af medlemmer, der har betalt: %.2f%%",paidPercentage);
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
}
