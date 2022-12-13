package space;

import cells.AsexuateCell;
import cells.Cell;
import cells.SexuateCell;
import food.Food;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

public class Space {

    private LinkedBlockingQueue<Cell> cellsQueue = new LinkedBlockingQueue<Cell>();
    private ArrayList<Food> food = new ArrayList<Food>();

    public boolean checkSpaceForFood(String threadInfo) throws InterruptedException {
        try {
            Boolean lockFood;
            Iterator<Food> foodIt = food.iterator();
            while(foodIt.hasNext()) {
                Food resource = foodIt.next();
                lockFood = resource.lock.tryLock();
                if (lockFood){
                    try{
                        System.out.println(threadInfo + " acquired lock for " + resource.name);
                        if(resource.getResourceUnits() > 0 ) {
                            resource.decrementResourceUnits();
                            System.out.println("Food:" + resource.name + " - " + "Resources left: " + resource.getResourceUnits());
                            return true;
                        }
                    }
                    finally
                    {
                        // Make sure to unlock so that we don't cause a deadlock
                        System.out.println(threadInfo + " unlocked " + resource.name);
                        resource.lock.unlock();
                    }
                }else
                    System.out.println(threadInfo + " tried to lock food " + resource.name);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public void addCell(Cell c) {
        try {
            cellsQueue.put(c);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addFood(int resources, String threadInfo) {
        try {
            Boolean lockFood;
            Iterator<Food> foodIt = food.iterator();
            while(foodIt.hasNext()) {
                Food availableResource = foodIt.next();
                lockFood = availableResource.lock.tryLock();
                if (lockFood)
                {
                    try{
                        System.out.println(threadInfo + " acquired lock for " + availableResource.name + "---- in order to add resource");
                        availableResource.incrementResourceUnits(resources);
                        break;
                    }
                    finally
                    {
                        // Make sure to unlock so that we don't cause a deadlock
                        System.out.println(threadInfo+" unlocked " + availableResource.name);
                        availableResource.lock.unlock();
                    }
                }else {
                    System.out.println(threadInfo + " tried to lock food " + availableResource.name);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public LinkedBlockingQueue<Cell> getCellsQueue() {
        return cellsQueue;
    }

    public void removeCell(Cell c){
        cellsQueue.remove(c);
    }

    public void startGameOfLife() {
        for(Cell cell: cellsQueue) {
            Thread t = new Thread(cell);
            cell.thread = t;
            t.start();
        }
    }

    public void addToInitialFoodStash(Food food){
        this.food.add(food);
    }

    public static void main(String[] args) {
        Space gameOfLife = new Space();

        gameOfLife.addToInitialFoodStash(new Food(30,"resourceA"));
        gameOfLife.addToInitialFoodStash(new Food(30,"resourceB"));
        gameOfLife.addToInitialFoodStash(new Food(30,"resourceC"));
        gameOfLife.addToInitialFoodStash(new Food(30,"resourceD"));

        Cell a = new AsexuateCell(10,5,"A");
        Cell b = new AsexuateCell(4,5,"B");
        Cell c = new AsexuateCell(3,1,"C");
        Cell d = new AsexuateCell(1,3,"D");

        Cell e = new SexuateCell(3,4,"sE");
        Cell f = new SexuateCell(3,4,"sF");
        Cell g = new SexuateCell(3,4,"sG");
        Cell h = new SexuateCell(3,4,"sH");

        Cell.spaceObj = gameOfLife;

        gameOfLife.addCell(a);

//        gameOfLife.addCell(c);
//        gameOfLife.addCell(b);
//        gameOfLife.addCell(d);
        gameOfLife.addCell(e);
        gameOfLife.addCell(f);
        gameOfLife.addCell(g);
        gameOfLife.addCell(h);

        gameOfLife.startGameOfLife();


    }

}