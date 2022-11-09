package cells;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import enums.CellStates;

import javax.lang.model.type.ArrayType;

public class SexuateCell extends Cell {
    public final Lock lock = new ReentrantLock();
    private boolean divisible = false;
    public boolean hasDivided = false;

    public boolean getDivisibleStatus() {
        return divisible;
    }

    public void setDivisibleStatus(boolean status) {
        this.divisible = status;
    }

    public SexuateCell(String cellName, int timeUntilHungry, int timeUntilStarve) {
        super(cellName, timeUntilHungry, timeUntilStarve);
    }

    @Override
    public void divide() {
        System.out.println("~~~~~~" + this.cellName + " wants to divide!");
        //search for cells that want to divide too
        ArrayList<Cell> cellsQ = gameSpace.getCellsQueue();
        Iterator<Cell> it = cellsQ.iterator();
        try {
            while (it.hasNext()) {
                Cell currentCell = it.next();
                if(!(currentCell instanceof SexuateCell))
                    continue;
                SexuateCell sexuateCell = (SexuateCell)currentCell;
                if (!sexuateCell.equals(this)) {
                    if (sexuateCell.getDivisibleStatus() && sexuateCell.hasDivided == false) {
                        boolean lockCell = sexuateCell.lock.tryLock();
                        if (lockCell) {
                            try {
                                boolean lockThis = sexuateCell.lockCell(this);
                                if(lockThis) {
                                    try {
                                        System.out.println("********************************"+this.cellName+ " was locked by "+currentCell.cellName);
                                        //make baby
                                        this.divisible = false;
                                        this.hasDivided = true;
                                        sexuateCell.setDivisibleStatus(false);
                                        sexuateCell.hasDivided = true;
                                        System.out.println("#############################################> Sexuate division this-> " + this.cellName + " and other-> " + currentCell.cellName);
                                        System.out.println("#############################################");
                                        Cell c = new SexuateCell(this.cellName + "-Schild", this.timeUntilHungry, this.timeUntilStarve);
                                        c.cellState = CellStates.STARVING;
                                        gameSpace.addCell(c);
                                        Thread t = new Thread(c);
                                        t.start();
                                    }
                                    finally{
                                        this.cellState = CellStates.FULL;
                                        currentCell.cellState= CellStates.DEAD;

                                        gameSpace.removeCell(sexuateCell);
                                        System.out.println("Removed current cell -> " + this.cellName);
                                        gameSpace.removeCell(this);
                                        System.out.println("Removed other cell -> " + sexuateCell.cellName);
                                        sexuateCell.unlockCell(this);
                                    }
                                }
                            } finally {
                                sexuateCell.lock.unlock();
                                //MUST ALSO STOP THREADS??!!!

                            }
                        }

                    }
                }
                if(this.hasDivided == true)
                    break;
                it.next();
            }
        } catch (Exception e) {
            System.out.println("No cells left in queue!");
        }
    }


    public boolean lockCell(SexuateCell c) {
        if (c.lock.tryLock()) {
            return true;
        } else {
            return false;
        }
    }

    public void unlockCell(SexuateCell c) {
        c.lock.unlock();
    }

    public boolean canDivide() {
        if ((this.nrOfTimesCellHasEaten >= 10) && (hasDivided == false)) {
            divisible = true;
            return true;
        }
        return false;
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }
}