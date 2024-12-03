import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

//TODO
// Implement SRP for chairman

public class UserInterface {
    private Controller controller;
    private Chairman chairman;
    private Scanner sc;
    private Accountant accountant;
    private Member member;
    private Controller cont;

    //--------------------Constructor-----------------------------------------------------------------------------------
    public UserInterface() {
        this.chairman = new Chairman();
        this.fh = new FileHandler();
        this.sc = new Scanner(System.in);
        this.accountant = new Accountant();
        this.cont = new Controller();
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
                    9. Tilbage til hovedmenu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> addMember();
                case "2" -> editMember();
                case "3" -> {
                case "2" -> System.out.println("Opdater medlemsoplysninger (funktionalitet ikke implementeret endnu)");
                case "3" -> displayMemberList();
                case "9" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }

    private void trainerMenu() {
        System.out.println("Velkommen Træner!");
        controller.addAthletesToList();
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


    private boolean nameIsValid(String stringToTest) {
        return stringToTest.matches("^[^0-9]+$");
    }

    //Create a new firstname for a member. If the firstname is not a string it will be invalid
    private String createFirstName() {
        System.out.println("Indtast fornavn");
        String firstName = sc.nextLine();
        while (!nameIsValid(firstName)) {
            System.out.println("Ugyldigt fornavn. Prøv igen");
            firstName = sc.nextLine();
        }
        return firstName;
    }
    //Creates a new lastname for a member. If the lastname is not a string it will be invalid
    private String createLastName() {
        System.out.println("indtast efternavn");
        String lastName = sc.nextLine();
        while (!nameIsValid(lastName)) {
            System.out.println("Ugyldigt efternavn. Prøv igen");
            lastName = sc.nextLine();
        }
        return lastName;
    }

    //Makes it possible to change the gender of a member in case they change their gender
    private String createGender() {
        System.out.println("""
                Vælg køn:
                1) Kvinde
                2) Mand""");
        if (chairman.takeIntUserInput(1, 2) == 1) {
            return "Kvinde";
        } else {
            return "Mand";
        }
    }

    //Makes it possible to change address
    private String createAdress() {
        System.out.println("Indtast den nye adresse (Vej Nr PostNr By");
        return sc.nextLine();
    }

    //Makes it possible to change a phonenumber
    private int createPhoneNumber(){
        System.out.println("Indtast telefonnummer");
        return chairman.takeIntUserInput(10000000,99999999);
    }

    //Makes it possible to change the agegroup for a member
    private void changeAgeGroupForMember (Member selectedMember){
        System.out.println("Skriv ny aldersgruppe {JUNIOR, SENIOR, PENSIONIST}");
        String ageGroup = sc.nextLine();
                    cont.setAgeGroupForMember(MembershipType.membershipAgeGroup(ageGroup.toUpperCase()), selectedMember);
            System.out.println("Medlems aldergruppe er blevet ændret til: " + MembershipType.membershipAgeGroup(ageGroup.toUpperCase()));
    }

    //Makes it possible to change if the member is active or passive
    private void changeActivePassiveForMember(Member selectedMember) {
        System.out.println("Er medlemmet AKTIV eller PASSIV");
        String aktivPassiv = sc.nextLine();
        cont.setActivePassiveForMember(MembershipType.activePassiveMember(aktivPassiv.toUpperCase()), selectedMember);
        System.out.println("Medlemmets status er ændret til: " + MembershipType.activePassiveMember(aktivPassiv.toUpperCase()));
    }

    //Makes it possible to change if the member is swimming as an athlete or hobby
    private void changeHobbyAthleteForMember (Member selectedMember) {
        System.out.println("Skal medlemmet være HOBBY eller ATLET");
        String hobbyAtlet = sc.nextLine();
        cont.setHobbyAthleteForMember(MembershipType.memberHobbyAthlete(hobbyAtlet.toUpperCase()), selectedMember);
        System.out.println("Medlemstypen for medlemmet er ændret til: " + MembershipType.memberHobbyAthlete(hobbyAtlet.toUpperCase()));
    }

    //Method to edit the data of a member
    public void editMember() {
    Member selectedMember = findMember();

    if (selectedMember != null) {
        System.out.println("""
                Hvad vil du gerne ændre?
                1) Fornavn
                2) Efternavn
                3) Køn
                4) Adresse
                5) Telefonnummer
                6) Aktiv/Passiv
                7) Medlemstype
                8) Atlet/Hobby
                """);
        switch (takeIntUserInput()) {
            case 1 -> {
                selectedMember.setFirstName(createFirstName());
                System.out.println("Fornavn er ændret til " + selectedMember.getFirstName());
            }
            case 2 -> {
                selectedMember.setLastName(createLastName());
                System.out.println("Efternavn er ændret til " + selectedMember.getLastName());
            }
            case 3 -> {
                selectedMember.setGender(createGender());
                System.out.println("Køn ere ændret til " + selectedMember.getGender());
            }
            case 4 -> {
                selectedMember.setAddress(createAdress());
                System.out.println("Adresse er ændret til " + selectedMember.getAddress());
            }
            case 5 -> {
                selectedMember.setPhoneNumber(createPhoneNumber());
                System.out.println("Telefonnummer er ændret til " + selectedMember.getPhoneNumber());
            }
            case 6 -> {
                changeActivePassiveForMember(selectedMember);
            }
            case 7 -> {
                changeAgeGroupForMember(selectedMember);
            }
            case 8 -> {
            changeHobbyAthleteForMember(selectedMember);
            }
            default -> System.out.println("Ugyldigt valg");
        }

    }
    }

    //Finds a member by taking a string input from the chairman and checks if the input matches a name in the arrayList
    public Member findMember() {
        System.out.println("Vælg navnet på det medlem du gerne vil ændre");
        Member selectedMember;
        ArrayList<Member> foundMembers = new ArrayList<>();

        while (foundMembers.isEmpty()) {
            String search = sc.nextLine();
            foundMembers = chairman.findMembers(search);
            if (!foundMembers.isEmpty()) {
                int index = 1;
                for (Member member : foundMembers) {
                    if (member != null) {
                        System.out.println(index + ") " + cont.getName(member));
                    } else {
                        System.out.println("Fejl. Ukendt medlem"  );
                    }
                    index ++;
                }
            } else {
                System.out.println("Der blev ikke fundet nogle medlemmer med dette navn. Prøv igen");
            }
        }
        System.out.println("Vælg venligst et medlem");
        int choice = takeIntUserInput();

        selectedMember = cont.getMemberFromIndex(choice, foundMembers);
        System.out.println("Du har valgt " + selectedMember);
        return selectedMember;

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
                    3. Vis samlede kontigent indtægter
                    4. Søg efter medlem
                    9. Tilbage til hovedmenu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> showPaymentStatusSubMenu();
                case "2" -> updatePaymentStatus();
                case "3" -> System.out.println(controller.getFormattedTotalMembershipFees());
                case "4" -> searchForMember();
                case "9" -> {
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
                case "9" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
            }
        }
    }
}