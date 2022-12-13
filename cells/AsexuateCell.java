package cells;

public class AsexuateCell extends Cell {

    public AsexuateCell(int timeUntilHungry, int timeUntilStarve, String name) {
        super(timeUntilHungry, timeUntilStarve, name);
    }

    @Override
    public void divide() {

        Cell c1 = new AsexuateCell(this.timeUntilHungry,this.timeUntilStarve,this.cellName+"-Asexuate child1");
        Cell c2 = new AsexuateCell(this.timeUntilHungry,this.timeUntilStarve,this.cellName+"-Asexuate child2");
        this.stop(); // this cell that divided is no longer alive
        System.out.println("~~~~~~~~~~~~CELL " + this.cellName + " HAS DIVIDED!~~~~~~~~~~~");
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);

        //add the cells to the space
        addCellToSpace(c1);
        addCellToSpace(c2);

        //start two new threads
        t1.start();
        t2.start();
    }

}

