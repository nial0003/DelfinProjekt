import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Trainer trainer = new Trainer("nikolaj");
        Chairman chairman = new Chairman();
        FileHandler fh = new FileHandler();

        trainer.addAthletesToList(chairman.getListOfMembers());
        trainer.saveAthleteMembersToAthleteFile();

        ArrayList<String> athletesFromFile = fh.getAthletesFromAthleteFile();
        ArrayList<String> updatedAthletesFromFile = trainer.setAthleteTrainingTime("Nana", athletesFromFile, "CRAWL", 42.0);
        updatedAthletesFromFile = trainer.setAthleteTrainingTime("Nana", athletesFromFile, "brystsvømning", 55.0);
        fh.saveUpdatedAthletesToFile(updatedAthletesFromFile);
    }
}