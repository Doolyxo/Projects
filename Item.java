public class Item {
    private String name;
    private String description;
    private boolean isUsable;

    public Item(String name, String description, boolean isUsable) {
        this.name = name;
        this.description = description;
        this.isUsable = isUsable;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void inspect() {
        System.out.println("Item: " + name);
        System.out.println("Description: " + description);
    }

    public void use() {
        if (isUsable) {
            switch (name.toLowerCase()) {
                case "crown of foresight":
                    System.out.println("A wave of clarity washes over you as visions of possible outcomes reveal themselves.");
                    // Additional logic for Crown of Foresight
                    break;
                case "ancient key":
                    System.out.println("The ancient key glows faintly as it fits into a mysterious lock.");
                    // Additional logic for Ancient Key
                    break;
                default:
                    System.out.println("You used the " + name + ".");
            }
        } else {
            System.out.println("The " + name + " cannot be used right now.");
        }
    }
}
