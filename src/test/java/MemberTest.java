import Membership.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;


class MemberTest {


    @Test
    void determineMembershipFeeJunior() {
        //Arrange
        ArrayList<Member> members = new ArrayList<>();
        Member member = new Member("Daniel", "Platz JR", LocalDate.of(2018, 7, 26), "Mand", "Frøstjernestræde 25 2640 Hedehusene", 28969955, "AKTIV", "HOBBY", true, false);
        members.add(member);

        //Act
        int fee = member.determineMembershipFee();

        //Assert
        int expectedFee = 1000;
        Assertions.assertEquals(expectedFee, fee);
    }

    @Test
    void determineMembershipFeeSenior() {
        //Arrange
        ArrayList<Member> members = new ArrayList<>();
        Member member = new Member("Daniel", "Platz", LocalDate.of(2002, 7, 26), "Mand", "Frøstjernestræde 25 2640 Hedehusene", 28969955, "AKTIV", "HOBBY", true, false);
        members.add(member);

        //Act
        int fee = member.determineMembershipFee();

        //Assert
        int expectedFee = 1600;
        Assertions.assertEquals(expectedFee,fee);
    }

    @Test
    void determineMembershipFeeElder() {
        //Arrange
        ArrayList<Member> members = new ArrayList<>();
        Member member = new Member("Daniel", "Platz SR", LocalDate.of(1902, 7, 26), "Mand", "Frøstjernestræde 25 2640 Hedehusene", 28969955, "AKTIV", "HOBBY", true, false);
        members.add(member);

        //Act
        int fee = member.determineMembershipFee();

        //Assert
        int expectedFee = 1200;
        Assertions.assertEquals(expectedFee,fee);
    }

    @Test
    void determineMembershipFeePassive() {
        //Arrange
        ArrayList<Member> members = new ArrayList<>();
        Member member = new Member("Daniel", "Platz", LocalDate.of(2002, 7, 26), "Mand", "Frøstjernestræde 25 2640 Hedehusene", 28969955, "PASSIV", "HOBBY", true, false);
        members.add(member);

        //Act
        int fee = member.determineMembershipFee();

        //Assert
        int expectedFee = 500;
        Assertions.assertEquals(expectedFee,fee);
    }

    @Test
    void determineMembershipFeePassiveElder() {
        //Arrange
        ArrayList<Member> members = new ArrayList<>();
        Member member = new Member("Daniel", "Platz SR", LocalDate.of(1902, 7, 26), "Mand", "Frøstjernestræde 25 2640 Hedehusene", 28969955, "PASSIV", "HOBBY", true, false);
        members.add(member);

        //Act
        int fee = member.determineMembershipFee();

        //Assert
        int expectedFee = 375;
        Assertions.assertEquals(expectedFee,fee);
    }
}
