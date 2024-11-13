import java.util.*;

public class GameManager {
    private Player player;
    private boolean isGameRunning;
    private boolean ritualPerformed; // new
    private boolean entityDefeated;  // new

    public GameManager() {
        player = new Player("Elara", 100); // Starting health for the main character, Queen Elara
        isGameRunning = true;
        ritualPerformed = false;
        entityDefeated = false;
        
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Desert Kings and Secrets!");
        System.out.println("You are Queen Elara, ruler of Aridia, tasked with saving your kingdom.");
        
        while (isGameRunning) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            processCommand(command);
        }

        scanner.close();
    }

    private void processCommand(String command) {
        switch (command.toLowerCase()) {
            case "investigate ruins":
                System.out.println("You search the ancient ruins...");
                player.investigateRuins();
                break;
            case "perform ritual":
            if (!ritualPerformed) { // new 
                System.out.println("You begin a powerful ritual...");
                player.performRitual();
                ritualPerformed = true;
                System.out.println("The ritual has been completed successfully ");// just added
        } else {
            System.out.println("You have already performed the ritual");
        }
                break;
            case "attack ancient entity":
              if (ritualPerformed) {// new 
                System.out.println("You attack the ancient entity...");
                player.attackEntity();
                entityDefeated = true;
                System.out.println("The entity disolves into dust");
                displayEnding();
                isGameRunning = false;
    } else {
         System.out.println(" The ancient entity is too powerful. Perorm the ritual first!");
    }
    break;

    case "use crown foresight": // new
    if (player.hasItem("Crown of Foresight")){
        player.useItem("Crown of Foresight");
        System.out.println("You glimpse possible future outcomes.");
    } else {
        System.out.println("You don't have the Crown of Foresight.");
    }
    break;

    case "consult with helio":  // new
            System.out.println("Helio shares his wisdom about the upcoming challenges...");
            // Optionally, call a method from an Entity class or add more interaction logic here
            break;

               case "quit":
                isGameRunning = false;
                System.out.println("Thank you for playing!");
                break;
            default:
                System.out.println("Unknown command.");
        }
    }

    private void displayEnding() { // new
        if (entityDefeated) {
            System.out.println("The Kingdom is saved! Queen Elara and Helio return to palace as heros. Mylo bids farewell and departs.");
        } else {
            System.out.println("The Kingdom falls into despair as the ancient entity remains undefeated.");
        }
        }
    }

    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.startGame();
    }

