// App section start

import java.util.*;
// Since game manager already tracks and manages the loop with isGameRunning
// The app class can just delegate the game loop entirely to game manager
public class App {
public static void main(String[] args) {
        GameManager gameManager = new GameManager(); // Initialize the game
        gameManager.startGame(); // Delegate the entire game loop to GameManager
    }
}
// App section finish

// Character section start
public class Character {
    private String name;
    private int health;
    private String description;
    private String[] dialogues; // New field to store interaction phrases

    // Constructor with interaction phrases
    public Character(String name, int health, String description, String[] dialogues) {
        this.name = name;
        this.health = health;
        this.description = description;
        this.dialogues = dialogues;
    }

    // Getter for the name
    public String getName() {
        return name;
    }

    // Getter for the health
    public int getHealth() {
        return health;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }

    // Getter for dialogues
    public String[] getDialogues() {
        return dialogues;
    }

    // Method to simulate taking damage
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            System.out.println(name + " has been defeated.");
        } else {
            System.out.println(name + " takes " + amount + " damage. Remaining health: " + health);
        }
    }

    // Method for speaking a specific dialogue
    public void speak(int dialogueIndex) {
        if (dialogues != null && dialogueIndex >= 0 && dialogueIndex < dialogues.length) {
            System.out.println(name + ": \"" + dialogues[dialogueIndex] + "\"");
        } else {
            System.out.println(name + " has nothing to say.");
        }
    }

    // Method to check if the character is alive
    public boolean isAlive() {
        return health > 0;
    }
}
// Character section finish

// entity section start

public class Entity {
    private String name;
    private int health;
    private int attackPower;

    public Entity(String name, int health, int attackPower){
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    public String getName(){
        return name;
    }

    public int getHealth(){
        return health;
    }
    
    public void takeDamage(int damage){
        health -= damage;
        if (health <= 0){
            System.out.println(name + " has been defeated");

        }
    }

    public int attack()  {
        System.out.println(name + " attakcs with power " + attackPower + "!" );
        return attackPower;
    }
}

// entity section finish

// Game manager start
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
        helio = new Character("Helio Umetris", 100, "A wise adviser skilled in elemental magic.",
                new String[]{
                        "Stay vigilant; the ruins are full of hidden dangers.",
                        "Use your strength wisely, Elara. It will guide us to victory.",
                        "The entity is restless; we must act soon."
                });

        // Initialize Mylo with interaction phrases
        mylo = new Character("Mylo Yasujiro", 100, "A visiting dignitary with vast knowledge of the world.",
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
        System.out.println("- menu or help: Show this list of commands.");
        System.out.println("- quit: Exit the game.\n");
    }

    private void processCommand(String command, Scanner scanner) {
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

            case "attack ancient entity":
                if (!ruinsInvestigated || !ritualPerformed) {
                    System.out.println("You are not ready to face the entity. Investigate the ruins and perform the ritual first!");
                } else {
                    System.out.println("You attack the ancient entity...");
                    player.attackEntity();
                    entityDefeated = true;
                    System.out.println("The ancient entity has been defeated! You have saved the kingdom!");
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

//game manager finish


