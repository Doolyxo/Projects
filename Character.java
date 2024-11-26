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
