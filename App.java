import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager(); // Initialize the game
        Scanner scanner = new Scanner(System.in);
        boolean isGameRunning = true;

        System.out.println("Welcome to the game!");

        while (isGameRunning) {
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();
            
            if (command.equalsIgnoreCase("quit")) {
                isGameRunning = false;
                System.out.println("Thank you for playing!");
            } else {
                gameManager.processCommand(command); // Delegate commands to GameManager
            }
        }

        scanner.close();
    }
}