import java.time.LocalDate;
import java.time.Period;

public class Member {
    private String firstName;
    private String lastName;
    private String name;
    private int age;
    private String gender;
    private String address;
    private int phoneNumber;
    private static int memberNumber;
    private String memberType;
    private boolean hasPaid;

    public Member (String firstName, String lastName, LocalDate birthday, String gender, String address, int phoneNumber, String memberType, boolean hasPaid){
        this.name = lastName + firstName;
        this.age = calculateAge(birthday);
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.memberType = memberType;
        this.hasPaid = hasPaid;
        memberNumber++;
    }

    //Calculates the age based on the birthday provided and subtracts it from the current date.
    //Return year in between.
    public int calculateAge (LocalDate birthday){
        LocalDate todaysDate = LocalDate.now();
        Period period = Period.between(birthday, todaysDate);
        return period.getYears();
    }

}
