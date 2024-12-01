public class Competition {
    private String competitionName;
    private SwimmingDisciplines discipline;
    private double result;

    public Competition(String competitionName, String discipline, double result){
        this.competitionName = competitionName;
        this.discipline = SwimmingDisciplines.getSwimmingDiscipline(discipline);
        this.result = result;
    }
}
