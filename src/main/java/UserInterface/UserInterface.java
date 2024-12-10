package UserInterface;

import DomainModel.Controller;
import Membership.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private final Controller controller;
    private final Scanner sc;

    //--------------------Constructor-----------------------------------------------------------------------------------
    public UserInterface() {
        controller = new Controller();
        sc = new Scanner(System.in);
    }

    //--------------------Start of program------------------------------------------------------------------------------
    public void startProgram() {
        System.out.println("Velkommen til Svømmeklubben Delfinen!");

        while (true) {
            System.out.println("=================================");
            System.out.println("      Svømmeklubben Delfinen     ");
            System.out.println("=================================");
            System.out.println("| [1] [ Formand ]              |");
            System.out.println("| [2] [ Kassér  ]              |");
            System.out.println("| [3] [ Træner  ]              |");
            System.out.println("| [9] [ Afslut  ]              |");
            System.out.println("=================================");
            System.out.print("Vælg din rolle (1-3 eller 9): ");

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
                    if (authenticateRole("Kassér", "2222")) {
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
                    System.out.println("Tak for nu! Programmet afsluttes.");
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

    //--------------------Chairman.Chairman menu---------------------------------------------------------------------------------
    private void chairmanMenu() {
        System.out.println("Velkommen Formand!");

        while (true) {
            System.out.println("=================================");
            System.out.println("           Formandsmenu          ");
            System.out.println("=================================");
            System.out.println("| [1] [ Tilføj medlem  ]       |");
            System.out.println("| [2] [ Opdater medlem ]       |");
            System.out.println("| [3] [ Medlemsliste   ]       |");
            System.out.println("| [9] [ Hovedmenu      ]       |");
            System.out.println("=================================");
            System.out.print("Vælg en funktion (1-3 eller 9): ");

            String input = sc.nextLine();
            switch (input) {
                case "1" -> addMember();
                case "2" -> editMember();
                case "3" -> displayMemberList();
                case "9" -> {
                    System.out.println("Tilbage til hovedmenuen.");
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
        System.out.print("Vejnavn: ");
        String streetName = sc.nextLine();
        System.out.print("Husnummer: ");
        String houseNumber = sc.nextLine();
        System.out.print("Postnummer: ");
        String postalCode = sc.nextLine();
        System.out.print("By: ");
        String city = sc.nextLine();
        String address = streetName + " " + houseNumber + " " + postalCode + " " + city;
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
        boolean hasPaid = sc.nextLine().equalsIgnoreCase("ja");

        controller.addMember(firstName, lastName, birthDate.getYear(), birthDate.getMonthValue(), birthDate.getDayOfMonth(),
                gender, address, phoneNumber, membershipStatus, membershipType, hasPaid);

        System.out.println("=================================");
        System.out.println("         Medlem tilføjet!        ");
        System.out.println("=================================");
        System.out.println("  Returnerer til Formandsmenuen  ");

    }

    //--------------------Method to edit member--------------------------------------------------------------------------
    public void editMember() {
        int index = findMemberIndex();
        System.out.println("""
                Hvad vil du gerne ændre?
                1) Fornavn
                2) Efternavn
                3) Køn
                4) Adresse
                5) Telefonnummer
                6) Aktiv/Passiv
                7) Atlet/Hobby
                """);
        switch (takeIntUserInput()) {
            case 1 -> {
                System.out.println("Indtast nyt fornavn");
                String newName = sc.nextLine();
                controller.setFirstNameForMemberAtIndex(index, newName);
                System.out.println("Fornavn er ændret til " + newName + "\n");
            }
            case 2 -> {
                System.out.println("Indtast nyt efternavn");
                String newName = sc.nextLine();
                controller.setLastNameForMemberAtIndex(index, newName);
                System.out.println("Efternavn er ændret til " + newName + "\n");
            }
            case 3 -> {
                System.out.println("Vælg køn: Kvinde/Mand\n");
                String newGender = sc.nextLine();
                controller.setGender(index, newGender);
            }
            case 4 -> {
                System.out.println("Indtast ny adresse (Vej Nr PostNr By\n");
                String newAdress = sc.nextLine();
                controller.setAdress(index, newAdress);
            }
            case 5 -> {
                System.out.println("Indtast nyt telefonnummer\n");
                int newNumber = sc.nextInt();
                controller.setPhonenumber(index, newNumber);
            }
            case 6 -> {
                controller.setActivePassive(index);
                if (controller.getMembershipStatus(index) == MembershipType.AKTIV) {
                    System.out.println("Medlemsstatus sat til AKTIV\n");
                } else {
                    System.out.println("Medlemsstatus sat til PASSIV\n");
                }
            }
            case 7 -> {
                controller.setHobbyAthlete(index);
                if (controller.getMembershipType(index) == MembershipType.HOBBY) {
                    System.out.println("Medlemstype sat til HOBBY\n");
                } else {
                    System.out.println("Medlemstype sat til ATLET\n");
                }
            }
            default -> System.out.println("Ugyldigt valg");
        }
        controller.rewriteFileWithNewData();
    }

    //Finds a member by taking a string input from the chairman and checks if the input matches a name in the arrayList
    public int findMemberIndex() {
        System.out.println("Vælg navnet på det medlem du gerne vil ændre");
        ArrayList<Member> foundMembers = new ArrayList<>();
        boolean indexIsFound = false;

        while (!indexIsFound) {
            String nameOfMemberToFind = sc.nextLine();
            foundMembers = controller.findMember(nameOfMemberToFind);
            if (!foundMembers.isEmpty()) {
                int index = 1;
                for (Member member : foundMembers) {
                    if (member != null) {
                        System.out.println(index + ") " + controller.getName(member));
                    } else {
                        System.out.println("Fejl. Ukendt medlem");
                    }
                    index++;
                    indexIsFound = true;
                }
            } else {
                System.out.println("Der blev ikke fundet nogle medlemmer med dette navn. Prøv igen");
            }
        }
        System.out.println("Vælg venligst et medlem");
        int choice = takeIntUserInput();

        Member selectedMember = controller.getMemberFromIndex(choice, foundMembers);
        System.out.println("Du har valgt " + selectedMember);
        return controller.getMemberIndex(selectedMember.getName());
    }

    //Takes an integer input from the user and throws an exception if the input is not an integer
    public int takeIntUserInput() {
        String input = sc.nextLine();
        int inputInt;
        try {
            inputInt = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            System.out.println("Ugyldigt input. Prøv igen");
            inputInt = takeIntUserInput();
        }
        return inputInt;
    }

    //--------------------Membership.Member list sub menu--------------------------------------------------------------------------
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
                    Map<MembershipType, ArrayList<Member>> groupedByStatus = controller.groupByMembershipStatus();
                    displayGroupedMembers(groupedByStatus);
                }
                case "2" -> {
                    Map<MembershipType, ArrayList<Member>> groupedByType = controller.groupByMembershipType();
                    displayGroupedMembers(groupedByType);
                }
                case "3" -> {
                    Map<MembershipType, ArrayList<Member>> groupedByAge = controller.groupByAgeGroup();
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

    //--------------------Trainer.Trainer menu----------------------------------------------------------------------------------
    private void trainerMenu() {
        System.out.println("Velkommen Træner!");

        controller.saveAthleteMembersToAthleteTrainingFile();
        controller.rewriteFileWithNewData();

        while (true) {
            System.out.println("===================================");
            System.out.println("            Trænermenu             ");
            System.out.println("===================================");
            System.out.println("| [1] [ Opdater træningsresultat ] |");
            System.out.println("| [2] [ Tilføj konkurrence       ] |");
            System.out.println("| [3] [ Se bedste svømmer        ] |");
            System.out.println("| [9] [ Hovedmenu                ] |");
            System.out.println("===================================");
            System.out.print("Vælg en funktion (1-3 eller 9): ");

            String input = sc.nextLine();
            switch (input) {
                case "1" -> updateTrainingResult();
                case "2" -> addCompetitionToAthlete();
                case "3" -> showBestAthletes();
                case "9" -> {
                    System.out.println("Tilbage til hovedmenuen.");
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    //--------------------Method to update training results-------------------------------------------------------------
    private void showBestAthletes() {
        System.out.println("""
                Indenfor hvilken disciplin vil du gerne se de bedste svømmere?
                1. Crawl
                2. Butterfly
                3. Rygcrawl
                4. Brystsvømning""");
        int input = sc.nextInt();
        String disciplin = switch (input) {
            case 1 -> "CRAWL";
            case 2 -> "BUTTERFLY";
            case 3 -> "RYGCRAWL";
            case 4 -> "BRYSTSVØMNING";
            default -> "Forkert disciplin";
        };
        System.out.println("""
                Vil du se seniorhold eller juniorhold?
                1. Juniorhold
                2. Seniorhold""");
        input = sc.nextInt();
        String team = switch (input) {
            case 1 -> "Juniorhold";
            case 2 -> "Seniorhold";
            default -> "Forkert hold valg";
        };
        sc.nextLine();
        System.out.println("""
                Træning eller konkurence?
                1. træning
                2. konkurence
                """);
        input = sc.nextInt();
        String trainingOrComp = switch (input) {
            case 1 -> "training";
            case 2 -> "competition";
            default -> "Forkert valg af træning eller konkurence";
        };
        sc.nextLine();
        int index = getDisciplinIndex(disciplin);
        ArrayList<String> fiveBestAthletes = controller.showBestAthletes(trainingOrComp, team, disciplin, index);
        System.out.println("-------------- Bedste " + disciplin.toLowerCase() + " svømmere i træning for " + team + " --------------");
        for (String athleteResult : fiveBestAthletes) {
            System.out.println(athleteResult);
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    private int getDisciplinIndex(String disciplin) {
        switch (disciplin.toUpperCase()) {
            case "BUTTERFLY":
                return 3;
            case "CRAWL":
                return 4;
            case "RYGCRAWL":
                return 5;
            case "BRYSTSVØMNING":
                return 6;
            default:
                return -1;
        }
    }

    private void updateTrainingResult() {
        System.out.println("Navn på atlet:");
        String name = sc.nextLine();
        name = findCorrectAthlete(name);
        String[] athleteName = name.split(",");
        System.out.println("Navn på disciplin {Crawl, Butterfly, Rygcrawl, Brystsvømning}:");
        String discipline = sc.nextLine();
        System.out.println("Ny trænings tid:");
        double newTime = sc.nextDouble();
        controller.setAthleteTrainingTime(name, discipline, newTime);
        name = athleteName[0] + " " + athleteName[1];
        System.out.println(discipline + " for " + name + " er blevet ændret til: " + newTime);
        sc.nextLine();
    }

    private String findCorrectAthlete(String name) {
        ArrayList<String> oneOrMoreAthletes = controller.findCorrectAthlete(name.toLowerCase());
        int index = 1;
        if (oneOrMoreAthletes.size() == 1) {
            return oneOrMoreAthletes.getFirst();
        } else {
            System.out.println("More than one Trainer.Athlete of that name exists, please pick correct Trainer.Athlete: ");
            for (int i = 0; i < oneOrMoreAthletes.size(); i++) {
                if (oneOrMoreAthletes.get(i) != null) {
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

    //--------------------Method to add a competition type to an athlete------------------------------------------------
    private void addCompetitionToAthlete() {
        System.out.println("Navn på atlet:");
        String name = sc.nextLine();
        name = findCorrectAthlete(name);
        System.out.println("Navn på stævne:");
        String competitionName = sc.nextLine();
        System.out.println("Hvilken disciplin deltog han i? {Crawl, Rygcrawl, Butterfly, Brystsvømning");
        String discipline = sc.nextLine();
        System.out.println("Hvad var deres svømmetid?");
        double swimmingResult = sc.nextDouble();
        System.out.println("Hvad blev deres placering?");
        int placement = sc.nextInt();
        controller.addCompetitionToAthlete(name, competitionName, discipline, swimmingResult, placement);
        sc.nextLine();
    }

    //--------------------Accounting.Accountant menu-------------------------------------------------------------------------------
    private void accountantMenu() {
        System.out.println("Velkommen Kassér!");

        while (true) {
            System.out.println("===================================");
            System.out.println("            Kassérmenu             ");
            System.out.println("===================================");
            System.out.println("| [1] [ Vis betalingsstatus     ] |");
            System.out.println("| [2] [ Opdater betalingsstatus ] |");
            System.out.println("| [3] [ Vis statistik           ] | ");
            System.out.println("| [9] [ Hovedmenu               ] | ");
            System.out.println("===================================");
            System.out.print("Vælg en funktion (1-3 eller 9): ");

            String input = sc.nextLine();
            switch (input) {
                case "1" -> showPaymentStatusSubMenu();
                case "2" -> updatePaymentStatus();
                case "3" -> showStatisticsSubMenu();
                case "9" -> {
                    System.out.println("Tilbage til hovedmenuen.");
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
                System.out.println((i + 1) + ". Navn: " + member.getFirstName() + " " + member.getLastName() + ", ID: "
                        + member.getMemberNumber() + ", Telefonnummer: " + member.getPhoneNumber());
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
                    System.out.println(controller.getFormattedMemberPaymentStatus(controller.getAllMembers()));
                }
                case "2" -> {
                    System.out.println("Medlemmer, der har betalt:");
                    System.out.println(controller.getFormattedMemberPaymentStatus(controller.getFilteredMembers(true)));
                }
                case "3" -> {
                    System.out.println("Medlemmer, der mangler at betale:");
                    System.out.println(controller.getFormattedMemberPaymentStatus(controller.getFilteredMembers(false)));
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
                    System.out.println("Finansielt overblik:\n" + "Samlede kontingent indtægter: " + controller.getCalculateTotalMembershipFees() + " DKK\n" + "Modtagene betalinger: " + controller.getCalculateReceivedPayments() + " DKK\n" + "Udestående betalinger: " + controller.getCalculateOutstandingPayments() + " DKK\n");
                }
                case "2" -> {
                    System.out.println("Antal medlemmer: " + controller.getAllMembers().size() + "\n");
                }
                case "3" -> {
                    System.out.println(String.format("Procentdel af betalte kontingentgebyrer: %.2f%%", controller.getCalculatePaidPercentage()) + "\n");
                }
                case "4" -> {
                    System.out.println(String.format("Procentdel af ubetalte kontingentgebyrer: %.2f%%", controller.getCalculateOutstandingPercentage()) + "\n");
                }
                case "9" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }
}