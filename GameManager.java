// I added displayEnding()
// I added Three complex commands in (verb-noun-noun format). 
// It is handled in the processCommand() method using (executeVerbNounNoun() method
// Updated processCommand() to call executeVerbNounNoun() for three-word commands


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
        helio = new Character("Helio Umetris", 100, "A wise adviser skilled in elemental magic.");
         new String[]{
                        "Stay vigilant; the ruins are full of hidden dangers.",
                        "Use your strength wisely, Elara. It will guide us to victory.",
                        "The entity is restless; we must act soon."
                });
        // Initialize Mylo with interaction phrases
        mylo = new Character("Mylo Yasujiro", 100, "A visiting dignitary with vast knowledge of the world.");
       new String[]{
                        "These ruins are treacherous, but their mysteries call to us. Stay vigilant—one wrong step and we'll be just another story lost to the sands.",
                        "I've seen places like this before. Stay alert.",
                        "If we make it through this, stories of this day will be legendary."
                });
    }
    
     private void initializeLocations() {
        locations = new HashMap<>();

        // Define locations
        Location royalPalace = new Location("Royal Palace", "The center of the kingdom, where political decisions are made.");
        Location oasisOfIsolde = new Location("Oasis of Isolde", "A sanctuary of life amidst the arid desert.");
        Location eternalDunes = new Location("Eternal Dunes", "The vast deserts of Aridia.");
        Location desertRuins = new Location("Undiscovered Desert Ruins", "Hidden throughout the desert, these ruins contain artifacts.");

        // Add locations to the map
        locations.put("royal palace", royalPalace);
        locations.put("oasis of isolde", oasisOfIsolde);
        locations.put("eternal dunes", eternalDunes);
        locations.put("desert ruins", desertRuins);

        // Set initial location
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
        System.out.println("- use crown foresight: Use the Crown of Foresight if available.");
        System.out.println("- menu or help: Show this list of commands.");
        System.out.println("- quit: Exit the game.\n");
    }

    private void processCommand(String command, Scanner scanner) {
        String[] words = command.split(" ");
        if (words.length == 3) {
            executeVerbNounNoun(words[0], words[1], words[2]);
        } else {
            switch (command.toLowerCase()) {
                case "travel":
                    if (currentLocation == null) {
                        System.out.println("Error: Current location is not set.");
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
                        player.heal(20);
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

                    

                case "quit":
                    isGameRunning = false;
                    System.out.println("Thank you for playing!");
                    break;

                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }

    private void executeVerbNounNoun(String verb, String noun1, String noun2) {
        if (verb.equalsIgnoreCase("use") && noun1.equalsIgnoreCase("crown") && noun2.equalsIgnoreCase("foresight")) {
            if (player.hasItem("Crown of Foresight")) {
                player.useItem("Crown of Foresight");
                System.out.println("The crown reveals a vision of possible futures.");
            } else {
                System.out.println("You don't have the Crown of Foresight.");
            }
        } else if (verb.equalsIgnoreCase("attack") && noun1.equalsIgnoreCase("ancient") && noun2.equalsIgnoreCase("entity")) {
            if (ritualPerformed && ruinsInvestigated) {
                System.out.println("You attack the ancient entity!");
                player.attackEntity();
                entityDefeated = true;
                displayEnding("kingdomSaved");
            } else {
                System.out.println("You are not prepared to face the entity!");
                displayEnding("fallOfAridia");
            }
        } else if (verb.equalsIgnoreCase("consult") && noun1.equalsIgnoreCase("helio") && noun2.equalsIgnoreCase("wisdom")) {
            helio.speak("Elara, trust your instincts. The fate of the kingdom depends on you.");
        } else {
            System.out.println("Invalid complex command.");
        }
    }

    private void displayEnding(String endingType) {
        switch (endingType) {
            case "kingdomSaved":
                System.out.println("\nEnding: The Kingdom Saved");
                System.out.println("Elara successfully defeats the ancient entity. The kingdom thrives, and her people honor her as a hero.");
                break;
            case "fallOfAridia":
                System.out.println("\nEnding: The Fall of Aridia");
                System.out.println("Elara fails to realize her potential. The entity takes over, and the kingdom falls into ruin, forgotten by the world.");
                break;
            case "destructionAndRebirth":
                System.out.println("\nEnding: Destruction and Rebirth");
                System.out.println("Despite Elara's efforts, the entity's power is overwhelming. The kingdom suffers losses, but a new era begins.");
                break;
            default:
                System.out.println("\nEnding: Unknown");
                System.out.println("An unexpected fate has occurred.");
                break;
        }
        isGameRunning = false; // End the game
    }

    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.startGame();
    }
}
