import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.startProgram();
        FileHandler fh = new FileHandler();
        Chairman chairman = new Chairman();
        Accountant accountant = new Accountant();
        try {
            chairman.addMember("Frida", "Sørensen", 1900, 10, 28, "kvinde", "Gulberbsgade 28 2650 København", 55668855, "ACTIVE", "ATHLETE", false);
            ArrayList<Member> list = fh.loadFromFile();
            for (Member member : list) {
                System.out.println(member);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        System.out.print(accountant.calculateMembershipFees());

    }
}