import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private List<Item> inventory;


    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.inventory = new ArrayList<>();

    }
    public void addToInventory(Item item) {
        inventory.add(item);
        System.out.println(item.getName() + " has been added to your inventory.");
    }

    public boolean hasItem(String itemName) {
        return inventory.stream().anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public void useItem(String itemName) { // new
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.use(); // Logic for using the item
                inventory.remove(item);
                System.out.println(itemName + " has been used.");
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }
    
    public void investigateRuins() {
        System.out.println(name + " investigates the ruins and finds some ancient artifacts.");
    }

    public void performRitual() {
        System.out.println(name + " performs a powerful ritual.");
    }

    public void attackEntity() {
        System.out.println(name + " attacks the ancient entity with bravery.");
    }
    Public void heal(int amount) {
        health += amount;
        if (health > 100) { // assuming 100 is the max health
            health = 100;
        }
        System.out.println(name + " 's health is now " + health);
}

