import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

//TODO
// Implement SRP for chairman

public class UserInterface {
    private Controller controller;
    private Chairman chairman;
    private Scanner sc;

    //--------------------Constructor-----------------------------------------------------------------------------------
    public UserInterface() {
        chairman = new Chairman();
        sc = new Scanner(System.in);
        controller = new Controller();
    }

    //--------------------Start of program------------------------------------------------------------------------------
    public void startProgram() {
        System.out.println("Velkommen til Svømmeklubben Delfinen!");
        while (true) {
            System.out.println("""
                    Vælg din rolle ved at indtaste tilhørende nummer, eller afslut programmet:
                    1. Formand
                    2. Kassér
                    3. Træner
                    4. Afslut
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> {
                    if (authenticateRole("Formand", "1111")) {
                        chairmanMenu();
                    } else {
                        System.out.println("Ugyldig adgangskode. Prøv igen.");
                    }
                }
                case "2" -> {
                    if (authenticateRole("Kasser", "2222")) {
                        accountantMenu();
                    } else {
                        System.out.println("Ugyldig adgangskode. Prøv igen.");
                    }
                }
                case "3" -> {
                    if (authenticateRole("Træner", "3333")) {
                        trainerMenu();
                    } else {
                        System.out.println("Ugyldig adgangskode. Prøv igen.");
                    }
                }
                case "4" -> {
                    System.out.println("Tak for nu!");
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    private boolean authenticateRole(String role, String expectedCode) {
        System.out.println("Indtast adgangskode for " + role + ": ");
        String inputCode = sc.nextLine();
        return inputCode.equals(expectedCode);
    }

    private void chairmanMenu() {
        System.out.println("Velkommen Formand!");
        while (true) {
            System.out.println("""
                    Vælg en funktion:
                    1. Tilføj et nyt medlem
                    2. Opdater medlemsoplysninger
                    3. Medlemsliste
                    4. Tilbage til hovedmenu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> addMember();
                case "2" -> System.out.println("Opdater medlemsoplysninger (funktionalitet ikke implementeret endnu)");
                case "3" -> displayMemberList();
                case "4" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    private void trainerMenu() {
        System.out.println("Velkommen Træner!");
        controller.addAthletesToListForTraining();
        controller.saveAthleteMembersToAthleteTrainingFile();
        controller.rewriteFileWithNewData();
        while (true) {
            System.out.println("""
                    Vælg en funktion:
                    1. Opdater medlems trænings resultat
                    2. Tilføj konkurence resultat til atlet
                    3. Se bedste svømmer i disciplin
                    9. Tilbage til hovedmenuen""");

            String input = sc.nextLine();
            switch (input) {
                case "1" -> updateTrainingResult();
                case "2" -> addCompetitionToAthlete();
                case "3" -> System.out.println("(funktionalitet ikke implementeret endnu)");
                case "9" -> {
                    return;
                }
            }
        }
    }

    //--------------------Method to add member--------------------------------------------------------------------------
    private void addMember() {
        System.out.println("Udfyld venligst følgende oplysninger for det nye medlem:");
        System.out.print("Fornavn: ");
        String firstName = sc.nextLine();
        System.out.print("Efternavn: ");
        String lastName = sc.nextLine();
        System.out.print("Fødselsår: ");
        int year = Integer.parseInt(sc.nextLine());
        System.out.print("Fødselsmåned: ");
        int month = Integer.parseInt(sc.nextLine());
        System.out.print("Fødselsdag: ");
        int day = Integer.parseInt(sc.nextLine());// TODO try-catch
        System.out.print("Køn: ");
        String gender = sc.nextLine();
        System.out.print("Adresse: ");
        String address = sc.nextLine();
        System.out.print("Telefonnummer: ");
        int phoneNumber = Integer.parseInt(sc.nextLine()); // TODO try-catch
        System.out.print("Medlemsstatus (AKTIV/PASSIV): "); // TODO try-catch
        String membershipStatus = sc.nextLine();
        System.out.print("Medlemstype (HOBBY/ATLET): ");
        String membershipType = sc.nextLine();
        System.out.print("Har betalt (true/false): "); // TODO ja nej yallah
        boolean hasPaid = Boolean.parseBoolean(sc.nextLine());

        chairman.addMember(firstName, lastName, year, month, day, gender,
                address, phoneNumber, membershipStatus, membershipType, hasPaid);
        System.out.println("Medlem tilføjet!");
    }

    //--------------------Member list sub menu--------------------------------------------------------------------------
    private void displayMemberList() {
        while (true) {
            System.out.println("""
                    Vælg hvorledes du ønsker at sortere medlemmerne ved at indtaste tilhørende nummer:
                    1. Medlemsstatus (AKTIV/PASSIV)
                    2. Medlemstype (HOBBY/ATLET)
                    3. Aldersgruppe (JUNIOR/SENIOR/PENSIONIST)
                    4. Tilbage til formandsmenu
                    """);
            String input = sc.nextLine();

            switch (input) {
                case "1" -> {
                    Map<MembershipType, ArrayList<Member>> groupedByStatus = chairman.groupByMembershipStatus();
                    displayGroupedMembers(groupedByStatus);
                }
                case "2" -> {
                    Map<MembershipType, ArrayList<Member>> groupedByType = chairman.groupByMembershipType();
                    displayGroupedMembers(groupedByType);
                }
                case "3" -> {
                    Map<MembershipType, ArrayList<Member>> groupedByAge = chairman.groupByAgeGroup();
                    displayGroupedMembers(groupedByAge);
                }
                case "4" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    //--------------------Method to display grouped members-------------------------------------------------------------
    private void displayGroupedMembers(Map<MembershipType, ArrayList<Member>> groupedMembers) {
        for (Map.Entry<MembershipType, ArrayList<Member>> entry : groupedMembers.entrySet()) {
            System.out.println(entry.getKey());
            for (Member member : entry.getValue()) {
                System.out.println("- " + member.getName());
            }
            System.out.println();
        }
    }

    private void updateTrainingResult() {
        System.out.println("Navn på atlet:");
        String name = sc.nextLine();
        name = findCorrectAthlete(name);
        System.out.println("Navn på disciplin {Crawl, Butterfly, Rygcrawl, Brystsvømning}:");
        String discipline = sc.nextLine();
        System.out.println("Ny trænings tid:");
        double newTime = sc.nextDouble();
        sc.nextLine();
        controller.setAthleteTrainingTime(name.toLowerCase(), discipline, newTime);
        System.out.println(discipline + " for " + name + " er blevet ændret til: " + newTime);
    }

    private String findCorrectAthlete(String name){
        ArrayList<String> oneOrMoreAthletes = controller.findCorrectAthlete(name);
        int index = 1;
        if (oneOrMoreAthletes.size() == 1){
            return oneOrMoreAthletes.getFirst();
        } else {
            System.out.println("More than one Athlete of that name exists, please pick correct Athlete: ");
            for (int i = 0; i < oneOrMoreAthletes.size(); i++) {
                if (oneOrMoreAthletes.get(i) != null){
                    String[] athleteString = oneOrMoreAthletes.get(i).split(",");
                    String athleteName = athleteString[1] + ", " + athleteString[2];
                    System.out.println(index + ") " + athleteName);
                    index++;
                }
            }
            int choice = sc.nextInt() - 1;
            sc.nextLine();
            return oneOrMoreAthletes.get(choice);
        }
    }

    private void addCompetitionToAthlete() {
        System.out.println("Navn på atlet:");
        String name = sc.nextLine();
        System.out.println("Navn på stævne:");
        String competitionName = sc.nextLine();
        System.out.println("Hvilken disciplin deltog han i? {Crawl, Rygcrawl, Butterfly, Brystsvømning");
        String discipline = sc.nextLine();
        System.out.println("Hvad var deres svømmetid?");
        double swimmingResult = sc.nextDouble();
        sc.nextLine();
        System.out.println("Hvad blev deres placering?");
        int placement = sc.nextInt();
        sc.nextLine();
        controller.addCompetitionToAthlete(name, competitionName, discipline, swimmingResult, placement);
    }

    //--------------------Accountant menu-------------------------------------------------------------------------------
    private void accountantMenu() {
        System.out.println("Velkommen Kassér!");
        while (true) {
            System.out.println("""
                    Vælg en funktion:
                    1. Vis medlemmers betalingsstatus
                    2. Opdater betalingsstatus for medlem
                    3. Vis samlede kontigent indtægter
                    4. Søg efter medlem
                    5. Tilbage til hovedmenu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> showPaymentStatusSubMenu();
                case "2" -> updatePaymentStatus();
                case "3" -> System.out.println(controller.getFormattedTotalMembershipFees());
                case "4" -> searchForMember();
                case "5" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    //--------------------Method to search for members------------------------------------------------------------------
    private void searchForMember() {
        System.out.println("Indtast søgeord (navn, medlemsnummer eller telefonnummer): ");
        String searchKeyword = sc.nextLine();

        String result = controller.getFoundMembers(searchKeyword);

        System.out.println(result);
    }

    //--------------------Method to update payment status for members---------------------------------------------------
    private void updatePaymentStatus() {
        boolean searching = true;

        while (searching) {
            System.out.println("Indtast søgeord (fornavn, efternavn, medlemsnummer eller telefonnummer) for at opdatere betalingsstatus:");
            String searchKeyword = sc.nextLine();

            ArrayList<Member> foundMembers = controller.findMembers(searchKeyword);

            if (foundMembers.isEmpty()) {
                System.out.println("Ingen medlemmer fundet med søgeordet: " + searchKeyword);
                System.out.println("Vil du prøve igen? (ja/nej): ");
                String tryAgain = sc.nextLine();
                if (!tryAgain.equalsIgnoreCase("ja")) {
                    return;
                }
                continue;
            }

            System.out.println("Fundne medlemmer:");
            for (int i = 0; i < foundMembers.size(); i++) {
                Member member = foundMembers.get(i);
                System.out.println((i + 1) + ". Navn: " + member.getFirstName() + " " + member.getLastName() +
                        ", ID: " + member.getMemberNumber() +
                        ", Telefonnummer: " + member.getPhoneNumber());
            }

            boolean validSelection = false;
            Member selectedMember = null;

            while (!validSelection) {
                System.out.println("Vælg medlem ved at indtaste nummeret ud for navnet:");
                try {
                    int memberIndex = Integer.parseInt(sc.nextLine()) - 1;
                    if (memberIndex >= 0 && memberIndex < foundMembers.size()) {
                        selectedMember = foundMembers.get(memberIndex);
                        validSelection = true;
                    } else {
                        System.out.println("Ugyldigt valg. Prøv igen.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ugyldigt valg. Indtast et nummer.");
                }
            }

            System.out.println("Valgt medlem:");
            System.out.println("Navn: " + selectedMember.getFirstName() + " " + selectedMember.getLastName());
            System.out.println("Nuværende betalingsstatus: " + (selectedMember.getHasPaid() ? "Betalt" : "Ikke betalt"));

            boolean validStatus = false;
            boolean hasPaid = false;

            while (!validStatus) {
                System.out.println("Indtast ny betalingsstatus (betalt/ikke betalt): ");
                String statusInput = sc.nextLine();
                if (statusInput.equalsIgnoreCase("betalt")) {
                    hasPaid = true;
                    validStatus = true;
                } else if (statusInput.equalsIgnoreCase("ikke betalt")) {
                    hasPaid = false;
                    validStatus = true;
                } else {
                    System.out.println("Ugyldig indtastning. Skriv 'betalt' eller 'ikke betalt'.");
                }
            }

            boolean success = controller.updateMemberPaymentStatus(selectedMember.getMemberNumber(), hasPaid);

            if (success) {
                System.out.println("Betalingsstatus opdateret for medlem: " + selectedMember.getFirstName() + " " + selectedMember.getLastName());
                searching = false;
            } else {
                System.out.println("Kunne ikke opdatere betalingsstatus.");
            }
        }
    }

    //--------------------Payment status sub menu-----------------------------------------------------------------------
    private void showPaymentStatusSubMenu() {
        while (true) {
            System.out.println("""
                    Vis medlemmers betalingsstatus:
                    1. Vis alle medlemmer
                    2. Vis medlemmer, der har betalt
                    3. Vis medlemmer, der mangler at betale
                    4. Tilbage til kasser-menu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.println("Alle medlemmers betalingsstatus:");
                    System.out.println(controller.getFormatMembers(controller.getAllMembers()));
                }
                case "2" -> {
                    System.out.println("Medlemmer, der har betalt:");
                    System.out.println(controller.getFormatMembers(controller.getFilteredMembers(true)));
                }
                case "3" -> {
                    System.out.println("Medlemmer, der mangler at betale:");
                    System.out.println(controller.getFormatMembers(controller.getFilteredMembers(false)));
                }
                case "4" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }
}