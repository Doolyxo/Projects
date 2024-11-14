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

    public void use() {
        if (isUsable) {
            switch (name.toLowerCase()) {
                case "crown of foresight":
                    System.out.println("You feel a wave of clarity wash over you as visions of possible futures unfold.");
                    // Additional logic specific to the Crown of Foresight can go here
                    break;
                default:
                    System.out.println("You used the " + name + ".");
                    // Add any default item usage logic here if needed
            }
        } else {
            System.out.println("This item cannot be used.");
        }
    }
}
