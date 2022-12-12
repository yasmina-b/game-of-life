package cells;

import space.Space;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Cell implements Runnable {
    public static Space spaceObj; //the actual game

    public abstract void divide();

    protected int nrOfTimesCellHasEaten;

    protected int timeUntilHungry;
    protected int timeUntilStarve;

    private int currentTimeUntilHungry;
    private int currentTimeUntilStarve;

    protected boolean alive = true;

    protected String cellName;

    public Cell(int timeUntilHungry, int timeUntilStarve, String name) {
        this.nrOfTimesCellHasEaten = 0;
        this.timeUntilHungry = timeUntilHungry;
        this.timeUntilStarve = timeUntilStarve;
        this.cellName = name;
        setTime();
    }

    public void setTime(){
        this.currentTimeUntilHungry = this.timeUntilHungry;
        this.currentTimeUntilStarve = this.timeUntilStarve;
    }

    public void addCellToSpace(Cell c){
        spaceObj.addCell(c);
    }

    public void live() throws InterruptedException { // this is thrown when a thread is interrupted
        while(alive) {
            eat(spaceObj);
            if(canDivide()) divide();
        }
    }

    public void eat(Space space) throws InterruptedException {
        if (space.checkSpaceForFood(cellName)) {
            System.out.println(" - Cell: " + this.cellName + " ate. ");
            nrOfTimesCellHasEaten++;
            setTime(); //time for hungry&starve are reset
            //Thread.sleep(1000 * timeUntilHungry); // it's full does not have to eat

        } else { // if the cell hasn't found available food resources
            currentTimeUntilHungry--;
            if (currentTimeUntilHungry < 0) {
                currentTimeUntilStarve--;
                if (currentTimeUntilStarve == 0) {
                    System.out.println("----------For Cell " + this.cellName + " it's game over!----------");
                    this.alive = false;
                    //randomly generated resources after cell death by starvation
                    int randomResources = ThreadLocalRandom.current().nextInt(1, 5);
                    System.out.println("----------Cell " + this.cellName + " has generated " + randomResources + " resources!----------");
                    spaceObj.addFood(randomResources, cellName);
                }
            }
        }
    }

    public boolean canDivide() {
        if(this.nrOfTimesCellHasEaten >= 10){
            return true;
        }
        return false;
    }

    public String toString() {
        return this.cellName;
    }

    @Override
    public void run() {
        try {
            live();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}