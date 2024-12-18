// I added displayEnding()
// I added Three complex commands in (verb-noun-noun format). 
// It is handled in the processCommand() method using (executeVerbNounNoun() method
// Updated processCommand() to call executeVerbNounNoun() for three-word commands

//Commandsystem start
public class CommandSystem {

    private GameState state;
    private GameManager game;

    public CommandSystem(GameState state, GameManager game) {
        this.state = state;
        this.game = game;

        // Add verbs to the game manager
        game.addVerb("?", "Show this help screen.");
        game.addVerb("look", "Use the look command by itself to look in your current area. " +
                "You can also look at a person or object by typing look and the name of what you want to look at. " +
                "Example: look book");
        game.addVerb("l", "Same as the look command.");
        game.addVerb("quit", "Quit the game.");
        game.addVerb("go", "Move to a different location. Example: go Kitchen");
        game.addVerb("take", "Take an item from the current location. Example: take Key");
        game.addVerb("inventory", "Display the items in your inventory.");
        game.addVerb("talk", "Talk to a character. Example: talk Old Man");
        game.addVerb("use", "Use an item. Example: use Key"); 
    }

    public void executeVerb(String verb) {
        switch (verb) {
            case "l":
            case "look":
                game.print("You look around.");
                game.print(state.getCurrentLocation().getDescription());
                break;
            case "?":
                game.printHelp();
                break;
            case "inventory":
                game.print("Your Inventory:");
                for (Item item : state.getPlayer().getInventory().getItems()) {
                    game.print("- " + item.getName());
                }
                break;
        }
    }

    public void executeVerbNoun(String verb, String noun) {
        switch (verb) {
            case "l":
            case "look":
                game.print(lookAt(noun));
                break;
            case "go":
                state.travelToLocation(noun);
                break;
            case "take":
                Item itemToTake = state.getCurrentLocation().getItem(noun);
                if (itemToTake != null) {
                    state.getPlayer().getInventory().addItem(itemToTake);
                    game.print("You picked up the " + noun + ".");
                } else {
                    game.print("You don't see a " + noun + " here.");
                }
                break;
            case "talk":
                Character character = state.getCharacters().get(noun);
                if (character != null) {
                    // Handle character interaction (e.g., display dialogue)
                    character.speak(0); // Example: Display the first dialogue
                } else {
                    game.print("There is no " + noun + " here.");
                }
                break;
        }
    }

    public void executeVerbNounNoun(String verb, String noun1, String noun2) {
        switch (verb) {
            case "use":
                // Handle item usage (e.g., using a key on a door)
                if (noun1.equalsIgnoreCase("key") && noun2.equalsIgnoreCase("door")) { 
                    // Example: Using a key on a door
                    game.print("You try to use the key on the door...");
                    // Add game logic for using the key
                } else {
                    game.print("Invalid use of the 'use' command.");
                }
                break;
            default:
                game.print("Invalid complex command.");
        }
    }

    public String lookAt(String noun) {
        // Look at an item in the current location
        Item item = state.getCurrentLocation().getItems().stream()
                .filter(i -> i.getName().equalsIgnoreCase(noun))
                .findFirst()
                .orElse(null);
        if (item != null) {
            return item.getDescription();
        }

        // Look at a character in the current location
        Character character = state.getCharacters().get(noun);
        if (character != null) {
            return character.getDescription();
        }

        return "You don't see a " + noun + " here.";
    }

    public void processCommand(String[] command) {
        switch (command.length) {
            case 1:
                executeVerb(command[0]);
                break;
            case 2:
                executeVerbNoun(command[0], command[1]);
                break;
            case 3:
                executeVerbNounNoun(command[0], command[1], command[2]);
                break;
        }
    }
}
//Commandsystem finish

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

//Game state start
public class GameState {

    private GameManager gameManager; 
    private Location currentLocation;
    private Player player; 
    private Map<String, Location> locations; 
    private Map<String, Character> characters; 
    private Map<String, Item> items; 

    public GameState(GameManager game) {
        this.gameManager = game; 
        this.player = new Player("Queen Elara", 100); // Initialize player
        this.locations = new HashMap<>();
        this.characters = new HashMap<>();
        this.items = new HashMap<>();

        // Initialize locations
        this.locations.put("Royal Palace", new Location("Royal Palace", "The center of the kingdom, where political decisions are made."));
        this.locations.put("Oasis of Isolde", new Location("Oasis of Isolde", "A sanctuary of life amidst the arid desert."));
        this.locations.put("Eternal Dunes", new Location("Eternal Dunes", "The vast deserts of Aridia."));
        this.locations.put("Desert Ruins", new Location("Desert Ruins", "Hidden throughout the desert, these ruins contain artifacts."));

        // Initialize characters
        this.characters.put("Helio Umetris", new Character("Helio Umetris", 100, "A wise adviser skilled in elemental magic.", 
                new String[]{"Stay vigilant; the ruins are full of hidden dangers.", 
                            "Use your strength wisely, Elara. It will guide us to victory.", 
                            "The entity is restless; we must act soon."}));
        this.characters.put("Mylo Yasujiro", new Character("Mylo Yasujiro", 100, "A visiting dignitary with vast knowledge of the world.", 
                new String[]{"These ruins are treacherous, but their mysteries call to us. Stay vigilant—one wrong step and we'll be just another story lost to the sands.", 
                            "I've seen places like this before. Stay alert.", 
                            "If we make it through this, stories of this day will be legendary."}));

        // Initialize items
        this.items.put("Ancient Key", new Item("Ancient Key", "A mysterious key needed to unlock an ancient power.", true));
        this.items.put("Crown of Foresight", new Item("Crown of Foresight", "A magical artifact that reveals possible futures.", true));

        // Add initial items to locations
        this.locations.get("Desert Ruins").addItem(this.items.get("Ancient Key"));
        this.locations.get("Oasis of Isolde").addItem(this.items.get("Crown of Foresight"));

        // Set starting location
        this.currentLocation = this.locations.get("Royal Palace"); 

        // Add nouns to GameManager
        this.gameManager.addNoun("Royal Palace");
        this.gameManager.addNoun("Oasis of Isolde");
        this.gameManager.addNoun("Eternal Dunes");
        this.gameManager.addNoun("Desert Ruins");
        this.gameManager.addNoun("Helio Umetris");
        this.gameManager.addNoun("Mylo Yasujiro");
        this.gameManager.addNoun("Ancient Key");
        this.gameManager.addNoun("Crown of Foresight");
    }

    // Getters and Setters (if needed)
    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Player getPlayer() {
        return player;
    }

    // ... other getters and setters as needed

    // Methods for interacting with game objects (example)
    public void travelToLocation(String locationName) {
        if (locations.containsKey(locationName)) {
            currentLocation = locations.get(locationName); 
            // Update game state (e.g., check for item acquisition)
            if (locationName.equals("Desert Ruins") && !player.getInventory().contains(items.get("Ancient Key"))) {
                player.getInventory().addItem(items.get("Ancient Key"));
                gameManager.setKeyFound(true);
            } 
            // ... other location-specific actions
        } else {
            System.out.println("Unknown location.");
        }
    }

}

//game state finish


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
    private boolean keyFound; // Track if the key has been found
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
        keyFound = false; // Initialize as false

        initializeLocations();

        helio = new Character("Helio Umetris", 100, "A wise adviser skilled in elemental magic.",
                new String[]{
                        "Stay vigilant; the ruins are full of hidden dangers.",
                        "Use your strength wisely, Elara. It will guide us to victory.",
                        "The entity is restless; we must act soon."
                });

        mylo = new Character("Mylo Yasujiro", 100, "A visiting dignitary with vast knowledge of the world.",
                new String[]{
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
        Location desertRuins = new Location("Desert Ruins", "Hidden throughout the desert, these ruins contain artifacts.");

        // Add items to the Desert Ruins and Oasis of Isolde
        desertRuins.addItem(new Item("Ancient Key", "A mysterious key needed to unlock an ancient power.", true));
        oasisOfIsolde.addItem(new Item("Crown of Foresight", "A magical artifact that reveals possible futures.", true));

        locations.put("royal palace", royalPalace);
        locations.put("oasis of isolde", oasisOfIsolde);
        locations.put("eternal dunes", eternalDunes);
        locations.put("desert ruins", desertRuins);

        currentLocation = royalPalace; // Start in the Royal Palace
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
        if (ruinsInvestigated && ritualPerformed && keyFound) {
            System.out.println("- attack ancient entity: Attempt to defeat the ancient entity.");
        }
        System.out.println("- attack ancient entity");
        System.out.println("- consult with helio: Seek advice from Helio.");
        System.out.println("- consult with mylo: Seek Mylo's perspective.");
        System.out.println("- use crown foresight: Use the Crown of Foresight if available.");
        System.out.println("- menu or help: Show this list of commands.");
        System.out.println("- quit: Exit the game.\n");
    }

    private void processCommand(String command, Scanner scanner) {
        command = command.toLowerCase().trim();
        String[] words = command.split(" ");
        if (words.length == 3) {
            executeVerbNounNoun(words[0], words[1], words[2]);
        } else {
            switch (command) {
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
                        if (destination.equals("desert ruins") && !keyFound) {
                            System.out.println("You have found the Ancient Key in the ruins!");
                            Item key = currentLocation.getItem("Ancient Key");
                            if (key != null) {
                                player.addToInventory(key);
                                keyFound = true; // Automatically mark the key as found
                            }
                        } else if (destination.equals("oasis of isolde") && !player.hasItem("Crown of Foresight")) {
                            System.out.println("You have found the Crown of Foresight at the oasis!");
                            Item crown = currentLocation.getItem("Crown of Foresight");
                            if (crown != null) {
                                player.addToInventory(crown);
                            }
                        }
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
            if (!keyFound) {
                System.out.println("You need the Ancient Key to fight the entity!");
            } else if (ritualPerformed && ruinsInvestigated) {
                System.out.println("You attack the ancient entity!");
                player.attackEntity();
                entityDefeated = true;
                displayEnding("kingdomSaved");
            } else {
                System.out.println("You are not prepared to face the entity!");
                displayEnding("fallOfAridia");
            }
        } else if (verb.equalsIgnoreCase("consult") && noun1.equalsIgnoreCase("with") && noun2.equalsIgnoreCase("helio")) {
            helio.speak(0); // Display the first dialogue for Helio
        } else if (verb.equalsIgnoreCase("consult") && noun1.equalsIgnoreCase("with") && noun2.equalsIgnoreCase("mylo")) {
            mylo.speak(1); // Display the second dialogue for Mylo
        } else {
            System.out.println("Invalid complex command.");
        }
    }


    private void determineEnding() {
        boolean hasCrown = player.hasItem("Crown of Foresight");

        if (hasCrown && ritualPerformed) {
            displayEnding("kingdomSaved");
        } else if (hasCrown || ritualPerformed) {
            displayEnding("destructionAndRebirth");
        } else {
            displayEnding("fallOfAridia");
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
        isGameRunning = false;
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
