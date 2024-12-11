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

        // Add items to the Desert Ruins
        // Add items to the Desert Ruins and Oasis of Isolde
        desertRuins.addItem(new Item("Ancient Key", "A mysterious key needed to unlock an ancient power.", true));
        oasisOfIsolde.addItem(new Item("Crown of Foresight", "A magical artifact that reveals possible futures.", true));

        locations.put("royal palace", royalPalace);
        locations.put("oasis of isolde", oasisOfIsolde);
@@ -81,6 +82,7 @@
        if (ruinsInvestigated && ritualPerformed && keyFound) {
            System.out.println("- attack ancient entity: Attempt to defeat the ancient entity.");
        }
        System.out.println("- attack ancient entity");
        System.out.println("- consult with helio: Seek advice from Helio.");
        System.out.println("- consult with mylo: Seek Mylo's perspective.");
        System.out.println("- use crown foresight: Use the Crown of Foresight if available.");
@@ -113,6 +115,12 @@
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
@@ -173,30 +181,34 @@
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
