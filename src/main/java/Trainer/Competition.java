package Trainer;

import Membership.SwimmingDisciplines;

public class Competition {
    private String competitionName;
    private SwimmingDisciplines discipline;
    private double time;
    private int placement;

    public Competition(String competitionName, String discipline, double result, int placement){
        this.competitionName = competitionName;
        this.discipline = SwimmingDisciplines.getSwimmingDiscipline(discipline);
        this.time = result;
        this.placement = placement;
    }

    @Override
    public String toString() {
        return "{[" + competitionName + "]Disciplin[" + discipline + "]Tid[" + time + "]Placering[" + placement +"]}";
    }
}
