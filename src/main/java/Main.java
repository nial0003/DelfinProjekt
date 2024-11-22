import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileHandler fh = new FileHandler();
        Chairman chairman = new Chairman();
        try {
            chairman.addMember("Frida", "Sørensen", 1900, 10, 28, "kvinde", "Gulberbsgade 28 2650 København", 55668855, "ACTIVE", "ATHLETE", false);
            ArrayList<Member> list = fh.loadFromFile();
            for (Member member : list) {
                System.out.println(member);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}