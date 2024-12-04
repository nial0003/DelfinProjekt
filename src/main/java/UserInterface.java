import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

//TODO
// Implement SRP for chairman
//TODO remove case 4 - Søg efter medlem in accountant menu

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
                    9. Afslut
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
                case "9" -> {
                    System.out.println("Tak for nu!");
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    //--------------------Method to authenticate user role--------------------------------------------------------------
    private boolean authenticateRole(String role, String expectedCode) {
        System.out.println("Indtast adgangskode for " + role + ": ");
        String inputCode = sc.nextLine();
        return inputCode.equals(expectedCode);
    }

    //--------------------Chairman menu---------------------------------------------------------------------------------
    private void chairmanMenu() {
        System.out.println("Velkommen Formand!");
        while (true) {
            System.out.println("""
                    Vælg en funktion:
                    1. Tilføj et nyt medlem
                    2. Opdater medlemsoplysninger
                    3. Medlemsliste
                    9. Tilbage til hovedmenu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> addMember();
                case "2" -> System.out.println("Opdater medlemsoplysninger (funktionalitet ikke implementeret endnu)");
                case "3" -> displayMemberList();
                case "9" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
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
        System.out.println("Fødselsdato: (format: YYYY-MM-DD)");
        LocalDate birthDate = null;
        while (true) {
            try {
                String input = sc.nextLine();
                birthDate = LocalDate.parse(input);
                int currentYear = java.time.Year.now().getValue();
                if (birthDate.getYear() < 1900 || birthDate.getYear() > currentYear) {
                    throw new IllegalArgumentException("Årstallet skal være mellem 1900 og " + currentYear + ".");
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Fejl: Indtast venligst en gyldig dato i formatet YYYY-MM-DD.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.print("Køn: ");
        String gender = sc.nextLine();
        System.out.print("Adresse: (format: gadenavn husnummer postnummer by)");
        String address = sc.nextLine();
        System.out.print("Telefonnummer: ");
        int phoneNumber = 0;
        while (true) {
            try {
                System.out.print("(8 cifre) ");
                String input = sc.nextLine();
                if (input.length() != 8 || !input.matches("\\d+")) {
                    throw new IllegalArgumentException("Telefonnummer skal være præcis 8 cifre.");
                }
                phoneNumber = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Fejl: Telefonnummeret skal kun indeholde tal.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.print("Medlemsstatus (AKTIV/PASSIV): ");
        String membershipStatus = sc.nextLine();
        System.out.print("Medlemstype (HOBBY/ATLET): ");
        String membershipType = sc.nextLine();
        System.out.print("Har betalt (ja/nej): ");
        boolean hasPaid = Boolean.parseBoolean(sc.nextLine());

        chairman.addMember(firstName, lastName, birthDate.getYear(), birthDate.getMonthValue(),
                birthDate.getDayOfMonth(), gender, address, phoneNumber, membershipStatus, membershipType, hasPaid);
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
                    9. Tilbage til formandsmenu
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
                case "9" -> {
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

    //--------------------Trainer menu----------------------------------------------------------------------------------
    private void trainerMenu() {
        System.out.println("Velkommen Træner!");
        controller.addAthletesToList();
        controller.saveAthleteMembersToAthleteTrainingFile();
        controller.rewriteFileWithNewData();
        while (true) {
            System.out.println("""
                    Vælg en funktion:
                    1. Opdater medlems trænings resultat
                    2. Tilføj konkurrence resultat til atlet
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

    //--------------------Method to update training results-------------------------------------------------------------
    private void updateTrainingResult() {
        System.out.println("Navn på atlet:");
        String name = sc.nextLine();
        System.out.println("Navn på disciplin {Crawl, Butterfly, Rygcrawl, Brystsvømning}:");
        String discipline = sc.nextLine();
        System.out.println("Ny trænings tid:");
        double newTime = sc.nextDouble();
        controller.setAthleteTrainingTime(name, discipline, newTime);
        System.out.println(discipline + " for " + name + " er blevet ændret til: " + newTime);
    }

    //--------------------Method to add a competition type to an athlete------------------------------------------------
    private void addCompetitionToAthlete() {
        System.out.println("Navn på atlet:");
        String name = sc.nextLine();
        System.out.println("Navn på stævne:");
        String competitionName = sc.nextLine();
        System.out.println("Hvilken disciplin deltog han i? {Crawl, Rygcrawl, Butterfly, Brystsvømning");
        String discipline = sc.nextLine();
        System.out.println("Hvad var deres svømmetid?");
        double swimmingResult = sc.nextDouble();
        System.out.println("Hvad blev deres placering?");
        int placement = sc.nextInt();
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
                    3. Vis statistik
                    9. Tilbage til hovedmenu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> showPaymentStatusSubMenu();
                case "2" -> updatePaymentStatus();
                case "3" -> showStatisticsSubMenu();
                case "9" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
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
                    System.out.println("Ugyldigt valg. Skriv 'betalt' eller 'ikke betalt' for at angive betalingsstatus.");
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
                    9. Tilbage til kasser-menu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.println("Alle medlemmers betalingsstatus:");
                    System.out.println(controller.formatMemberPaymentStatus(controller.getAllMembers()));
                }
                case "2" -> {
                    System.out.println("Medlemmer, der har betalt:");
                    System.out.println(controller.formatMemberPaymentStatus(controller.getFilteredMembers(true)));
                }
                case "3" -> {
                    System.out.println("Medlemmer, der mangler at betale:");
                    System.out.println(controller.formatMemberPaymentStatus(controller.getFilteredMembers(false)));
                }
                case "9" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    private void showStatisticsSubMenu() {
        while (true) {
            System.out.println("""
                    Vis statistik:
                    1. Samlede kontingent indtægter
                    2. Antal medlemmer
                    3. Procentdel af betalte kontingentgebyrer
                    4. Procentdel af ubetalte kontingentgebyrer
                    9. Tilbage til kasser-menu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.println("Finansielt overblik:");
                    System.out.println(controller.getCalculateTotalMembershipFees());
                    System.out.println(controller.getCalculateReceivedPayments());
                    System.out.println(controller.getCalculateOutstandingPayments() + "\n");
                }
                case "2" -> {
                    System.out.println("Antal medlemmer: " + controller.getAllMembers().size() + "\n");
                }
                case "3" -> {
                    System.out.println(controller.getCalculatePaidPercentage() + "\n");
                }
                case "4" -> {
                    System.out.println(controller.getCalculateOutstandingPercentage() + "\n");
                }
                case "9" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }
}