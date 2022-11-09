package space;

import java.util.ArrayList;

import cells.AsexuateCell;
import cells.Cell;
import food.Food;

public class Space {

    private final ArrayList<Food> food = new ArrayList<>();
    private final ArrayList<Cell> cells = new ArrayList<>();
    
    public boolean checkSpaceForFood() {
        // to be implemented
        return true;
    }

    public void addCell(Cell c) {
    	cells.add(c);
    }

    public void addFood(Food f) {
        food.add(f);

    }

    public void removeCell(Cell c){
        cells.remove(c);
    }


    public ArrayList<Cell> getCellsQueue() {
        return cells;
    }

    public static void main(String[] args) {
        Space gameOfLife = new Space();

        gameOfLife.addFood(new Food("resourceA"));
        gameOfLife.addFood(new Food("resourceB"));
        //gameOfLife.addFood(new Food(4));
        //gameOfLife.addFood(new Food(1));

        Cell a = new AsexuateCell("A", 10, 5);
        Cell b = new AsexuateCell("B",3,1);
        Cell c = new AsexuateCell("C",1,1);
        Cell d = new AsexuateCell("D",1,3);

        Cell.gameSpace = gameOfLife;

        //gameOfLife.addCell(a);
        gameOfLife.addCell(b);
        //gameOfLife.addCell(c);
        //gameOfLife.addCell(d);
        //gameOfLife.printEverything();

    }

}
