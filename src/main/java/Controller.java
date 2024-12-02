import java.util.ArrayList;

public class Controller {
    Chairman chairman = new Chairman();
    Accountant accountant = new Accountant();
    Trainer trainer = new Trainer();
    FileHandler fh = new FileHandler();

    private ArrayList<Member> getListOfMembers() {
        return chairman.getListOfMembers();
    }

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

    public void saveAthleteMembersToAthleteFile(){
        fh.saveAthleteMembersToAthleteFile(trainer.getAthletes());
    }

    public void saveCompetitionResultsToFile(){
        fh.saveCompetitionResultsToFile(trainer.getAthletes());
    }
}
