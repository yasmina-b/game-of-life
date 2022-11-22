package food;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Food {
    private int resourceUnits;
    public String name;
    public final Lock lock = new ReentrantLock();

    public Food(int resourceUnits, String name) {
        this.resourceUnits = resourceUnits;
        this.name = name;
    }

    public int getResourceUnits() {
        return resourceUnits;
    }

    public void decrementResourceUnits() {
        this.resourceUnits --;
    }

    public void incrementResourceUnits(int units) {
        this.resourceUnits += units;
    }

    @Override
    public String toString() {
        return "Food{" +
                "resourceUnits=" + resourceUnits +
                ", name='" + name + '\'' +
                '}';
    }

}