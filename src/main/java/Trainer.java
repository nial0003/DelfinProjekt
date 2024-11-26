import java.util.ArrayList;

public class Trainer {
    String name;
    ArrayList<Athlete> athletes;
    FileHandler fh = new FileHandler();

    public Trainer(String name){
        this.name = name;
        athletes = new ArrayList<>();
    }

    //Checks if a member is an Athlete and if it is, it'll add them to the special athletes ArrayList
    public void addAthleteToList(ArrayList<Member> members){
        for (Member member : members){
            if (member.getMembershipType() == MembershipType.ATLET){
                String[] name = member.getName().split(",\\s");
                athletes.add(new Athlete(name[1], name[0], member.getLd(), member.getGender(),
                        member.getAddress(), member.getPhoneNumber(), member.getMemberShipStatus().toString(), member.getMembershipType().toString(),
                        member.getHasPaid()));
            }
        }
    }

    public void setAthleteTimes(int x, double time, SwimmingDisciplines discipline){
        athletes.get(x).setDisciplineTimes(discipline, time);
    }

    public double getAthleteTime(int x, SwimmingDisciplines discipline){
        return athletes.get(x).getTime(discipline);
    }

    public void saveAthletesToFile(){
        fh.saveToAthleteFile(athletes);
    }
}
