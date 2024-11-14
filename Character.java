public class Character {
    private String name;
    private int health;
    private String description;

    public Character(String name, int health, String description) {
        this.name = name;
        this.health = health;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public String getDescription() {
        return description;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            System.out.println(name + " has been defeated.");
        } else {
            System.out.println(name + " takes " + amount + " damage. Remaining health: " + health);
        }
    }

    public void speak(String dialogue) {
        System.out.println(name + ": \"" + dialogue + "\"");
    }
    
    public boolean isAlive() {
        return health > 0;
    }
}
