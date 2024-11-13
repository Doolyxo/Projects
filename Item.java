public class Item { // new
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
    

    public void use() {
        if (isUsable){
            System.out.println("You used the " + name + ".");
            // You can add item specific logic here if needed

        } else {
            System.out.println("This item cannot be used.");

        }
    
       //public void use() { // new might need to improvements
       //    if (isUsable) {
       //         switch (name.toLowerCase()) {
       //             case "crown of foresight":
        //                System.out.println("You feel a wave of clarity wash over you as visions of possible futures unfold.");
                        // Add logic specific to this item effect
        //                break;
         //           default:
        //                System.out.println("You used the " + name + ".");
        //        }
         //   } else {
        //        System.out.println("This item cannot be used.");
       //     } (new might need to improvements)
            
    }
}


