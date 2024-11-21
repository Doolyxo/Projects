import java.util.*;

public class GameManager {
    private Player player;
    private boolean isGameRunning;
    private boolean ritualPerformed;
    private boolean entityDefeated;
    private boolean ruinsInvestigated;
    private Character helio;
    private Character mylo;
    private Map<String, Location> locations;
    private Location currentLocation;

    public GameManager() {
        player = new Player("Queen Elara", 100);
        isGameRunning = true;
        ritualPerformed = false;
        entityDefeated = false;
        ruinsInvestigated = false;

        initializeLocations();

        // Initialize Helio with interaction phrases
        helio = new Character("Helio Umetris", "A wise adviser skilled in elemental magic.",
                new String[] {
                    "Stay vigilant; the ruins are full of hidden dangers.",
                    "Use your strength wisely, Elara. It will guide us to victory.",
                    "The entity is restless; we must act soon."
                });

        // Initialize Mylo with interaction phrases
        mylo = new Character("Mylo Yasujiro", "A visiting dignitary with vast knowledge of the world.",
                new String[] {
                    "These ruins are treacherous, but their mysteries call to us. Stay vigilant—one wrong step and we'll be just another story lost to the sands.",
                    "I've seen places like this before. Stay alert.",
                    "If we make it through this, stories of this day will be legendary."
                });
    }

    private void initializeLocations() {
        locations = new HashMap<>();

        Location royalPalace = new Location("Royal Palace", "The center of the kingdom, where political decisions are made.");
        Location oasisOfIsolde = new Location("Oasis of Isolde", "A sanctuary of life amidst the arid desert.");
        Location eternalDunes = new Location("Eternal Dunes", "The vast deserts of Aridia.");
        Location desertRuins = new Location("Undiscovered Desert Ruins", "Hidden throughout the desert, these ruins contain artifacts.");

        locations.put("royal palace", royalPalace);
        locations.put("oasis of isolde", oasisOfIsolde);
        locations.put("eternal dunes", eternalDunes);
        locations.put("desert ruins", desertRuins);

        currentLocation = royalPalace;
    }

    public void startGame() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to Desert Kings and Secrets!");
            currentLocation.enterLocation();
            showMenu();

            while (isGameRunning) {
                System.out.print("Enter command: ");
                String command = scanner.nextLine();
                processCommand(command, scanner);
            }
        }
    }

    private void showMenu() {
        System.out.println("\nAvailable Commands:");
        System.out.println("- travel: Move to a different location.");
        if (!ruinsInvestigated) {
            System.out.println("- investigate ruins: Search the ancient ruins for clues and items.");
        }
        if (!ritualPerformed) {
            System.out.println("- perform ritual: Begin a powerful ritual to prepare for the battle.");
        }
        if (ruinsInvestigated && ritualPerformed) {
            System.out.println("- attack ancient entity: Attempt to defeat the ancient entity.");
        }
        System.out.println("- consult with helio: Seek advice from Helio.");
        System.out.println("- consult with mylo: Seek Mylo's perspective.");
        System.out.println("- menu or help: Show this list of commands.");
        System.out.println("- quit: Exit the game.\n");
    }

    private void processCommand(String command, Scanner scanner) {
        switch (command.toLowerCase()) {
            case "travel":
                if (currentLocation == null) {
                    Sysem.out.printlm(" Error: Current location is not set.");
                     break;
                }
                System.out.println("Where would you like to travel?");
                System.out.println("Available locations: " + locations.keySet());
                String destination = scanner.nextLine().toLowerCase();
                if (locations.containsKey(destination)) {
                    currentLocation = locations.get(destination);
                    currentLocation.enterLocation();
                } else {
                    System.out.println("Unknown location.");
                }
                break;

            case "investigate ruins":
                if (!ruinsInvestigated) {
                    System.out.println("You search the ancient ruins...");
                    player.investigateRuins();
                    ruinsInvestigated = true;
                    System.out.println("The ruins are now investigated.");
                } else {
                    System.out.println("You have already investigated the ruins.");
                }
                break;

             case "rest":
            if (currentLocation != null && currentLocation.getName().equalsIgnoreCase("Oasis of Isolde")) {
                System.out.println("You rest by the crystal-clear waters and feel your strength returning.");
                player.heal(20); // This requires a heal method in Player
            } else {
                System.out.println("You can only rest at the Oasis of Isolde.");
            }
            break;

            case "perform ritual":
                if (!ritualPerformed) {
                    System.out.println("You begin a powerful ritual...");
                    player.performRitual();
                    ritualPerformed = true;
                    System.out.println("The ritual has been completed.");
                } else {
                    System.out.println("You have already performed the ritual.");
                }
                break;

            case "attack ancient entity":
                if (!ruinsInvestigated || !ritualPerformed) {
                    System.out.println("You are not ready to face the entity. Investigate the ruins and perform the ritual first!");
                } else {
                    System.out.println("You attack the ancient entity...");
                    player.attackEntity();
                    entityDefeated
