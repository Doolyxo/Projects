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
