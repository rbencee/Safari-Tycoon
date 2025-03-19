package lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.npc;


import lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.Moveable;
import lwjgl3.src.main.java.io.github.safari.lwjgl3.positionable.npc.animals.Animal;



public class Ranger implements Moveable {
    private boolean isAlive;
    private int x;
    private int y;
    private int shootRange;
    private static int range;
    private List<Poacher> knownPoachers;

    public Ranger() {
        this.isAlive = true;
    }

    public void KillAnimal(Animal target) {
    }

    public void KillPoacher(Poacher target) {
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    @Override
    public void move() {
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public void setSpeed(int speed) {
    }
}

