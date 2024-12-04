import java.util.ArrayList;

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

    public String formatMemberPaymentStatus(ArrayList<Member> members) {
        if (members == null || members.isEmpty()) {
            return "Ingen medlemmer fundet.";
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

    public String getCalculateTotalMembershipFees() {
        int totalFees = accountant.calculateTotalMembershipFees();
        return "Samlede kontingent indtægter: " + totalFees + " DKK";
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
        return String.format("Procentdel af betalte kontingentgebyrer: %.2f%%",paidPercentage);
    }

    public String getCalculateOutstandingPercentage (){
        double outstandingPercentage = accountant.calculateOutstandingPercentage();
        return String.format("Procentdel af ubetalte kontingentgebyrer: %.2f%%",outstandingPercentage);
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


    //-------------------Methods for Trainer class----------------------------------------------------------------------
    public void saveAthleteMembersToAthleteTrainingFile(){
        fh.saveAthleteMembersToAthleteTrainingFile(trainer.getAthletes());
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


    //-------------------Methods for Chairman class---------------------------------------------------------------------
    public void rewriteFileWithNewData(){
        fh.saveToFile(chairman.getListOfMembers(), false);
    }

    public void addAthletesToList(){
        trainer.addAthletesToList(chairman.getListOfMembers());
    }

}
