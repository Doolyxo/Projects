import java.util.Scanner;

public class GameManager {
    private Player player;
    private boolean isGameRunning;
    private boolean ritualPerformed;
    private boolean entityDefeated;
    private boolean ruinsInvestigated;

    public GameManager() {
        player = new Player("Queen Elara", 100);
        isGameRunning = true;
        ritualPerformed = false;
        entityDefeated = false;
        ruinsInvestigated = false;
    }

    public void startGame() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to Desert Kings and Secrets!");
            System.out.println("You are Queen Elara, ruler of Aridia, tasked with saving your kingdom.");
            showMenu(); // Display the command menu at the start of the game

            while (isGameRunning) {
                System.out.print("Enter command: ");
                String command = scanner.nextLine();
                processCommand(command);
            }
        }
    }

    private void showMenu() {
        System.out.println("\nAvailable Commands:");
        System.out.println("- investigate ruins: Search the ancient ruins for clues and items.");
        System.out.println("- perform ritual: Begin a powerful ritual to prepare for the battle.");
        System.out.println("- attack ancient entity: Attempt to defeat the ancient entity.");
        System.out.println("- consult with helio: Seek advice from Helio.");
        System.out.println("- use crown foresight: Use the Crown of Foresight if available.");
        System.out.println("- menu or help: Show this list of commands.");
        System.out.println("- quit: Exit the game.\n");
    }

    private void processCommand(String command) {
        switch (command.toLowerCase()) {
            case "investigate ruins":
                System.out.println("You search the ancient ruins...");
                player.investigateRuins();
                ruinsInvestigated = true;
                break;
            case "perform ritual":
                if (!ritualPerformed) {
                    System.out.println("You begin a powerful ritual...");
                    player.performRitual();
                    ritualPerformed = true;
                    System.out.println("The ritual has been completed successfully.");
                } else {
                    System.out.println("You have already performed the ritual.");
                }
                break;
            case "attack ancient entity":
                if (!ritualPerformed) {
                    displayEnding("fallOfAridia");
                } else {
                    System.out.println("You attack the ancient entity...");
                    player.attackEntity();
                    entityDefeated = true;
                    displayEnding("kingdomSaved");
                }
                break;
            case "consult with helio":
                System.out.println("Helio shares his wisdom about the upcoming challenges...");
                break;
            case "use crown foresight":
                if (player.hasItem("Crown of Foresight")) {
                    player.useItem("Crown of Foresight");
                    System.out.println("You glimpse possible future outcomes.");
                } else {
                    System.out.println("You don't have the Crown of Foresight.");
                }
                break;
            case "menu":
            case "help":
                showMenu();
                break;
            case "quit":
                isGameRunning = false;
                System.out.println("Thank you for playing!");
                break;
            default:
                System.out.println("Unknown command. Type 'menu' or 'help' to see available commands.");
        }
    }

    private void displayEnding(String endingType) {
        switch (endingType) {
            case "kingdomSaved":
                System.out.println("\nEnding: The Kingdom Saved");
                System.out.println("Elara successfully defeats the ancient entity. The kingdom thrives, and her people honor her as a hero.");
                break;
            case "destructionAndRebirth":
                System.out.println("\nEnding: Destruction and Rebirth");
                System.out.println("Despite Elara's efforts, the entity's power is overwhelming. The kingdom suffers losses, but a new era begins.");
                break;
            case "fallOfAridia":
                System.out.println("\nEnding: The Fall of Aridia");
                System.out.println("Elara fails to realize her potential. The entity takes over, and the kingdom falls into ruin, forgotten by the world.");
                break;
            default:
                System.out.println("Unknown ending.");
        }
        isGameRunning = false; // Ends the game
    }

    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.startGame();
    }
}
