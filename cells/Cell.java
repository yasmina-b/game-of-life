package cells;

import java.util.concurrent.ThreadLocalRandom;
import enums.CellStates;
import food.Food;
import space.Space;

public abstract class Cell implements Runnable {

    protected String cellName;
    protected int nrOfTimesCellHasEaten;

    protected CellStates cellState = CellStates.FULL;

    protected int timeUntilHungry;
    protected int timeUntilStarve;
    
    private int timeHungry;
    //private int timeStarve;

    public static Space gameSpace;

    public abstract void divide();

    public Cell(String cellName, int timeUntilHungry, int timeUntilStarve) {
        this.cellName = cellName;
        this.nrOfTimesCellHasEaten = 0;
        this.timeUntilHungry = timeUntilHungry;
		this.timeUntilStarve = timeUntilStarve;
        setTime(); 
    }

    public void setTime() {
    	this.timeHungry = this.timeUntilHungry;
    	//this.timeStarve = this.timeUntilStarve;
    }

    public void addCellToGameSpace(Cell c) {
        gameSpace.addCell(c);
    }

    public void eat() throws InterruptedException {
        if(gameSpace.checkSpaceForFood()) { //check here if there exists food in gameSpace
            nrOfTimesCellHasEaten ++;
            System.out.println("Cell: " + this.cellName + " ate. ");
            this.timeUntilHungry = timeHungry;
            setTime(); //time for hungry&starve are reset
            Thread.sleep(this.timeHungry * 1000);
            
        } else {
            timeUntilHungry--;
            if (timeUntilHungry < 0) {
                timeUntilStarve--;
                if (timeUntilStarve == 0) {
                    System.out.println("For Cell " + this.cellName + " it's game over!");

                    //randomly generated resources after cell death by starvation
                    int randomResources = ThreadLocalRandom.current().nextInt(1, 5);
                    
                    for(int i=1;i<=randomResources;i++){
                        gameSpace.addFood(new Food("cellFood" + this.cellName));
                    }
                    System.out.println("Cell " + this.cellName + "has generated " + randomResources + " resources!");
                    this.cellState = CellStates.DEAD;
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
    
    // @Override
    // public void run() {
    //     try {
	// 		live();
	// 	} catch (InterruptedException e) {
	// 		// TODO Auto-generated catch block
	// 		e.printStackTrace();
	// 	}
    // }

}