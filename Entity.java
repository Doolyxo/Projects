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
        System.out.println(name + " attacks with power " + attackPower + "!" );
        return attackPower;
    }
}
