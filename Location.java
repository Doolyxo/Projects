import java.util.List;
import java.util.ArrayList;

public class Location {
    private String name; // Name of the location
    private String description; // Description of the location
    private List<Item> itemsHere; // List of items in this location

    // Constructor with null-check for itemsHere
    public Location(String name, String description, List<Item> itemsHere) {
        this.name = name;
        this.description = description;
        this.itemsHere = (itemsHere != null) ? itemsHere : new ArrayList<>();
    }

    // Alternate Constructor: Initializes with an empty itemsHere list
    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        this.itemsHere = new ArrayList<>();
    }

    // Getter for the name of the location
    public String getName() {
        return name;
    }

    // Getter for the description of the location
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves and removes an item from the location by name.
     * @param itemName The name of the item to retrieve.
     * @return The item if found, or null if not found.
     */
    public Item getItem(String itemName) {
        for (Item item : itemsHere) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemsHere.remove(item); // Removes the item from the list
                return item; // Returns the found item
            }
        }
        System.out.println("Item not found.");
        return null; // Returns null if the item is not found
    }

    /**
     * Adds an item to the location.
     * @param item The item to add.
     */
    public void addItem(Item item) {
        itemsHere.add(item);
    }

    /**
     * Displays the location details.
     */
    public void enterLocation() {
        if (name == null || description == null) { // Check if the location details are valid
            System.out.println("Error: Current location is not valid.");
            return;
        }

        System.out.println("You have entered " + name + ".");
        System.out.println(description);
        if (itemsHere.isEmpty()) {
            System.out.println("There are no items here.");
        } else {
            System.out.println("You see the following items:");
            for (Item item : itemsHere) {
                System.out.println("- " + item.getName());
            }
        }
    }
}
