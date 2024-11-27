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
        helio = new Character("Helio Umetris", 100, "A wise adviser skilled in elemental magic.");
        helio.setInteractionPhrases(new String[]{
                "Stay vigilant; the ruins are full of hidden dangers.",
                "Use your strength wisely, Elara. It will guide us to victory.",
                "The entity is restless; we must act soon."
        });

        // Initialize Mylo with interaction phrases
        mylo = new Character("Mylo Yasujiro", 100, "A visiting dignitary with vast knowledge of the world.");
        mylo.setInteractionPhrases(new String[]{
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

                case "consult with helio":
                    helio.speakRandomPhrase();
                    break;

                case "consult with mylo":
                    mylo.speakRandomPhrase();
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

//game manager finish

// item section start
public class Item {
    private String name;
    private String description;
    private boolean isUsable;

    public Item(String name, String description, boolean isUsable) {
        this.name = name;
        this.description = description;
        this.isUsable = isUsable;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void inspect() {
        System.out.println("Item: " + name);
        System.out.println("Description: " + description);
    }

    public void use() {
        if (isUsable) {
            switch (name.toLowerCase()) {
                case "crown of foresight":
                    System.out.println("A wave of clarity washes over you as visions of possible outcomes reveal themselves.");
                    // Additional logic for Crown of Foresight
                    break;
                case "ancient key":
                    System.out.println("The ancient key glows faintly as it fits into a mysterious lock.");
                    // Additional logic for Ancient Key
                    break;
                default:
                    System.out.println("You used the " + name + ".");
            }
        } else {
            System.out.println("The " + name + " cannot be used right now.");
        }
    }
}

// item section finish

// location section start

import java.util.List;
import java.util.ArrayList;

public class Location {
    private String name; // Name of the location
    private String description; // Description of the location
    private List<Item> itemsHere; // List of items in this location

    // Constructor with null-check for itemsHere
    public Location(String name, String description, List<Item> itemsHere) {
        this.name = name;
        this.description = description;
        this.itemsHere = (itemsHere != null) ? itemsHere : new ArrayList<>();
    }

    // Alternate Constructor: Initializes with an empty itemsHere list
    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.itemsHere = new ArrayList<>();
    }

    // Getter for the name of the location
    public String getName() {
        return name;
    }

    // Getter for the description of the location
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves and removes an item from the location by name.
     * @param itemName The name of the item to retrieve.
     * @return The item if found, or null if not found.
     */
    public Item getItem(String itemName) {
        for (Item item : itemsHere) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemsHere.remove(item); // Removes the item from the list
                return item; // Returns the found item
            }
        }
        System.out.println("Item not found.");
        return null; // Returns null if the item is not found
    }

    /**
     * Adds an item to the location.
     * @param item The item to add.
     */
    public void addItem(Item item) {
        itemsHere.add(item);
    }

    /**
     * Displays the location details.
     */
    public void enterLocation() {
        if (name == null || description == null) { // Check if the location details are valid
            System.out.println("Error: Current location is not valid.");
            return;
        }

        System.out.println("You have entered " + name + ".");
        System.out.println(description);
        if (itemsHere.isEmpty()) {
            System.out.println("There are no items here.");
        } else {
            System.out.println("You see the following items:");
            for (Item item : itemsHere) {
                System.out.println("- " + item.getName());
            }
        }
    }
}

// location section finish

// player section start
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private List<Item> inventory;

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.inventory = new ArrayList<>();
    }

    public void addToInventory(Item item) {
        inventory.add(item);
        System.out.println(item.getName() + " has been added to your inventory.");
    }

    public boolean hasItem(String itemName) {
        return inventory.stream().anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public void useItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.use();
                inventory.remove(item);
                System.out.println(itemName + " has been used.");
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public void investigateRuins() {
        System.out.println(name + " investigates the ruins and finds some ancient artifacts.");
    }

    public void performRitual() {
        System.out.println(name + " performs a powerful ritual.");
    }

    public void attackEntity() {
        System.out.println(name + " attacks the ancient entity with bravery.");
    }

    public void heal(int amount) {
        health += amount;
        if (health > 100) {
            health = 100;
        }
        System.out.println(name + "'s health is now " + health);
    }
}

// player section finish
