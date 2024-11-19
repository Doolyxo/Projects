import java.util.Scanner;
// Since game manager already tracks and manages the loop with isGameRunning
// The app class can just delegate the game loop entirely to game manager
public class App {
public static void main(String[] args) {
        GameManager gameManager = new GameManager(); // Initialize the game
        gameManager.startGame(); // Delegate the entire game loop to GameManager
    }
}
