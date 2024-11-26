import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Chairman chairman;
    private FileHandler fh;
    private Scanner sc;
    private Accountant accountant;

    public UserInterface() {
        this.chairman = new Chairman();
        this.fh = new FileHandler();
        this.sc = new Scanner(System.in);
        this.accountant = new Accountant();
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
                        System.out.println("Velkommen Kassér! (Funktionalitet ikke implementeret endnu)");
                    } else {
                        System.out.println("Ugyldig adgangskode. Prøv igen.");
                    }
                }
                case "3" -> {
                    if (authenticateRole("Træner", "3333")) {
                        System.out.println("Velkommen Træner! (Funktionalitet ikke implementeret endnu)");
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
                    3. Tilbage til hovedmenu
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> addMember();
                case "2" -> System.out.println("Opdater medlemsoplysninger (funktionalitet ikke implementeret endnu)");
                case "3" -> {
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
                    """);
            String input = sc.nextLine();
            switch (input) {
                case "1" -> showPaymentStatusSubMenu();
                case "2" -> System.out.println("(funktionalitet ikke implementeret endnu)");
                case "3" -> {
                    return;
                }
                default -> System.out.println("Ugyldigt valg. Prøv igen.");
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
                case "1" -> System.out.println("(Funktion til at vise alle medlemmer ikke implementeret endnu)");
                case "2" -> System.out.println("(Funktion til at vise medlemmer, der har betalt, ikke implementeret endnu)");
                case "3" -> System.out.println("(Funktion til at vise medlemmer, der mangler at betale, ikke implementeret endnu)");
                case "4" -> {
                    System.out.println("Tilbage til kasser-menu...");
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
}