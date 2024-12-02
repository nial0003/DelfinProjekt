import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class UserInterface {
    Controller controller = new Controller();

    private Chairman chairman;
    private FileHandler fh;
    private Scanner sc;

    public UserInterface() {
        this.chairman = new Chairman();
        this.fh = new FileHandler();
        this.sc = new Scanner(System.in);
    }

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
                case "2" -> System.out.println("(funktionalitet ikke implementeret endnu)");
                case "3" -> System.out.println(controller.getFormattedTotalMembershipFees());
                case "4" -> System.out.println("(funktionalitet ikke implementeret endnu)");
                case "5" -> {
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

        chairman.addMember(firstName, lastName, year, month, day, gender, address, phoneNumber, membershipStatus, membershipType, hasPaid);
        System.out.println("Medlem tilføjet!");
    }

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
}