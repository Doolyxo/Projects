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
