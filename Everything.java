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


