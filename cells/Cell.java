package cells;

import events.CellEvents;
import events.EventType;
import org.apache.kafka.clients.producer.ProducerRecord;
import producer.KProducer;
import space.Space;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Cell implements Runnable {
    public static Space spaceObj; //the actual game

    public abstract void divide();

    protected int nrOfTimesCellHasEaten;

    protected int timeUntilHungry;
    protected int timeUntilStarve;

    private int currentTimeUntilHungry;
    private int currentTimeUntilStarve;

    public AtomicBoolean alive = new AtomicBoolean(true);

    public Thread thread;

    public String cellName;

    String lifecycleTopic="lifecycleTopic";

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

    public void stop() {
        alive.set(false);
    }

    public void live() throws InterruptedException { // this is thrown when a thread is interrupted
        while (alive.get()) {
            try {
                //Thread.sleep(1000);
                eat(spaceObj);
                if(alive.get() && canDivide()) divide();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, Failed to complete operation");
            }
        }
    }

    public void eat(Space space) throws InterruptedException {
        if (space.checkSpaceForFood(cellName)) {
            System.out.println(" - Cell: " + this.cellName + " ate. ");
            nrOfTimesCellHasEaten++;
            CellEvents cellEvents = new CellEvents(this.cellName, EventType.CELL_ATE);
            KProducer.send(new ProducerRecord(lifecycleTopic, UUID.randomUUID().toString(), cellEvents));
            setTime(); //time for hungry&starve are reset

        } else { // if the cell hasn't found available food resources
            currentTimeUntilHungry--; // decrement the time until it gets hungry
            if (currentTimeUntilHungry < 0) {
                currentTimeUntilStarve--;
                if (currentTimeUntilStarve == 0) {
                    System.out.println("----------For Cell " + this.cellName + " it's game over!----------");
                    stop();
                    //randomly generated resources after cell death by starvation
                    int randomResources = ThreadLocalRandom.current().nextInt(1, 5);
                    System.out.println("----------Cell " + this.cellName + " has generated " + randomResources + " resources!----------");
                    spaceObj.addFood(randomResources, cellName);
                    spaceObj.removeCell(this);
                    Thread.currentThread().interrupt();
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