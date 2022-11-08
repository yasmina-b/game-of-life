package space;

import java.util.ArrayList;
import cells.Cell;
import food.Food;

public class Space {

    private ArrayList<Food> food = new ArrayList<>();
    private ArrayList<Cell> cells = new ArrayList<>();
    
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

}
