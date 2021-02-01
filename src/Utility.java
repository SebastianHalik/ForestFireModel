import java.awt.*;
import java.util.Random;


public class Utility  {
    DataManager dm;
    Utility(DataManager dm) {
        this.dm=dm;
    }

    /*
    E F D
    G X C   W taki sposob oznaczalem punkty w macierzy, X to srodkowy, na dole przypisanie zeby sie nie pomylic
    H A B
     */

    public void rain(DataManager dm, Cell[][] cells){
        for (int y = 0; y < dm.getNumCellsHeight(); y++) {
            for (int x = 0; x < dm.getNumCellsWidth(); x++) {
                    if (cells[x][y].getState().equals("Burned Tree")) {
                        for (int i = 0; i < 45; i++) { //45- wartosc metoda prob i bledów
                            if (cells[x][y].getLives() < 15) {
                                cells[x][y].setLives(cells[x][y].getLives() + 1);
                                cells[x][y].setNextState("Burned Tree");
                                break;
                            } else if (cells[x][y].getLives() == 15) {
                                cells[x][y].setNextState("Tree");
                                break;
                            } else {
                                cells[x][y].setNextState(cells[x][y].getState());
                            }
                        }
                    }
                    if (cells[x][y].getNextState() == null)
                        cells[x][y].setNextState(cells[x][y].getState());
            }
        }
        refreshStatus();
    }

    public void rebornForest(DataManager dm, Cell[][] cells){
        for (int y = 0; y < dm.getNumCellsHeight(); y++) {
            for (int x = 0; x < dm.getNumCellsWidth(); x++) {
                if (cells[x][y].getState().equals("Burned Tree") || cells[x][y].getState().equals("Dead")) {
                    for (int i = 0; i < 45; i++) { //45- wartosc metoda prob i bledów
                        if (cells[x][y].getLives() < 15) {
                            cells[x][y].setLives(cells[x][y].getLives() + 1);
                            cells[x][y].setNextState("Burned Tree");
                            break;
                        } else if (cells[x][y].getLives() == 15) {
                            cells[x][y].setNextState("Tree");
                            break;
                        } else {
                            cells[x][y].setNextState(cells[x][y].getState());
                        }
                    }
                }
                if (cells[x][y].getNextState() == null)
                    cells[x][y].setNextState(cells[x][y].getState());
            }
        }
        refreshStatus();
    }


    public void checkStatus(){
        int numberOfBurning=0;
        int numberOfDead=0;
        int waters = 0;
        for(Cell[] cells: dm.getStateCells())
            for(Cell cell: cells) {
                switch (cell.getState()) {
                    case "Burned Tree" -> numberOfBurning++;
                    case "Dead" -> numberOfDead++;
                    case "Water" -> waters++;
                }
            }
        System.out.println("Number of burning:  " +numberOfBurning);
        System.out.println("Number of dead:  " +numberOfDead);
        System.out.println("Number of water:  " +waters+ "\n");
    }

    public void checkLives(){
        for(Cell[] cells: dm.getStateCells())
            for(Cell cell: cells) {
                if(cell.getState().equals("Burned Tree"))
                    System.out.println("Lives:  "+cell.getLives());
            }
    }


    public void refreshStatus()
    {
        for(Cell[] cells: dm.getStateCells())
            for(Cell cell: cells) {
                cell.changeState();
            }
    }


    public void MouseChangeState(int x, int y,String chosenState){
        if(!chosenState.equals("Firefighter"))
        for(Cell[] cells: dm.getStateCells())
            for(Cell cell: cells) {
                if (cell.getX() == x && cell.getY() == y) {
                    cell.setState(chosenState);
                }
            }
        else
        {
            for (int i = x; i < x+10; i++) {
                for (int j = y; j < y+10; j++) {
                    if(!dm.getStateCells()[i][j].getState().equals("Dead") && !dm.getStateCells()[i][j].getState().equals("Water"))
                    {
                        dm.getStateCells()[i][j].setLives(15);
                        dm.getStateCells()[i][j].setState("Tree");
                    }
                }
            }
        }

    }
    public void reset(){
        for (int i = 0; i < dm.getNumCellsWidth(); i++) {
            for (int j = 0; j < dm.getNumCellsHeight(); j++) {
                if(!dm.getStateCells()[i][j].getState().equals("Water")) {
                    dm.getStateCells()[i][j].setNextState("Tree");
                    dm.getStateCells()[i][j].setLives(15);
                }
            }
        }
        refreshStatus();
    }


    public void fireSpread(DataManager dm, Cell[][] cells, int probability, int humidity, String wind, String season, String forestType){
        int numberOfBurn=0;
        Random generator = new Random();
        switch (season) {
            case "Summer" -> probability += 10;
            case "Winter" -> probability -= 20;
            case "Spring" -> probability -= 10;
            case "Autumn" -> probability += 20;
        }

        if(forestType.equals("Coniferous"))
            probability-=10;
        else if(forestType.equals("Deciduous"))
            probability+=10;

        probability-=humidity;

        for (int y = 0; y < dm.getNumCellsHeight(); y++) {
            for (int x = 0; x < dm.getNumCellsWidth(); x++) {
                //Boundary Conditions
                //Side points
                if(x==0) { //0,0
                    if (y == 0) {
                        if (cells[dm.getNumCellsWidth() - 1][dm.getNumCellsHeight() - 1].getState().equals("Burned Tree")){ //F point
                            numberOfBurn++;
                        }
                        if (cells[dm.getNumCellsWidth() - 1][0].getState().equals("Burned Tree")){ //G
                            numberOfBurn++;
                        }
                        if (cells[dm.getNumCellsWidth() - 1][y + 1].getState().equals("Burned Tree")){//H
                            numberOfBurn++;
                        }
                        if (cells[0][dm.getNumCellsHeight() - 1].getState().equals("Burned Tree")){ //E
                            numberOfBurn++;
                        }
                        if (cells[x + 1][dm.getNumCellsHeight() - 1].getState().equals("Burned Tree")){ //D
                            numberOfBurn++;
                        }
                        if (cells[x + 1][y + 1].getState().equals("Burned Tree")){ //B
                            numberOfBurn++;
                        }
                        if (cells[x][y + 1].getState().equals("Burned Tree")){ //A
                            numberOfBurn++;
                        }
                        if (cells[x + 1][y].getState().equals("Burned Tree")){ //C
                            numberOfBurn++;
                        }
                    } else if (y == dm.getNumCellsHeight() - 1) { //X=0, Y=max
                        if (cells[x][0].getState().equals("Burned Tree")){ //A
                            numberOfBurn++;
                        }
                        if (cells[x + 1][0].getState().equals("Burned Tree")){ //B
                            numberOfBurn++;
                        }
                        if (cells[x + 1][y].getState().equals("Burned Tree")){ //C
                            numberOfBurn++;
                        }
                        if (cells[x + 1][y - 1].getState().equals("Burned Tree")){ //D
                            numberOfBurn++;
                        }
                        if (cells[x][y - 1].getState().equals("Burned Tree")){ //E
                            numberOfBurn++;
                        }
                        if (cells[dm.getNumCellsWidth() - 1][y - 1].getState().equals("Burned Tree")){ //F
                            numberOfBurn++;
                        }
                        if (cells[dm.getNumCellsWidth() - 1][y].getState().equals("Burned Tree")){ //G
                            numberOfBurn++;
                        }
                        if (cells[dm.getNumCellsWidth() - 1][0].getState().equals("Burned Tree")){ //H
                            numberOfBurn++;
                        }
                    }
                }
                if(x==dm.getNumCellsWidth()-1) { //max,max
                    if (y == dm.getNumCellsHeight() - 1) {
                        if (cells[dm.getNumCellsWidth() - 1][0].getState().equals("Burned Tree")){ //A
                            numberOfBurn++;
                        }
                        if (cells[0][0].getState().equals("Burned Tree")){ //B
                            numberOfBurn++;
                        }
                        if (cells[0][dm.getNumCellsHeight() - 1].getState().equals("Burned Tree")){ //C
                            numberOfBurn++;
                        }
                        if (cells[0][y - 1].getState().equals("Burned Tree")){ //D
                            numberOfBurn++;
                        }
                        if (cells[x][y - 1].getState().equals("Burned Tree")){ //E
                            numberOfBurn++;
                        }
                        if (cells[x - 1][y - 1].getState().equals("Burned Tree")){ //F
                            numberOfBurn++;
                        }
                        if (cells[x - 1][y].getState().equals("Burned Tree")){ //G
                            numberOfBurn++;
                        }
                        if (cells[x - 1][0].getState().equals("Burned Tree")){ //H
                            numberOfBurn++;
                        }
                    } else if (y == 0) { //max,0
                        if (cells[x][y + 1].getState().equals("Burned Tree")){ //A
                            numberOfBurn++;
                        }
                        if (cells[0][y + 1].getState().equals("Burned Tree")){ //B
                            numberOfBurn++;
                        }
                        if (cells[x][0].getState().equals("Burned Tree")){ //C
                            numberOfBurn++;
                        }
                        if (cells[0][dm.getNumCellsHeight() - 1].getState().equals("Burned Tree")){ //D
                            numberOfBurn++;
                        }
                        if (cells[x][dm.getNumCellsHeight() - 1].getState().equals("Burned Tree")){ //E
                            numberOfBurn++;
                        }
                        if (cells[x - 1][dm.getNumCellsHeight() - 1].getState().equals("Burned Tree")){ //F
                            numberOfBurn++;
                        }
                        if (cells[x - 1][y].getState().equals("Burned Tree")){ //G
                            numberOfBurn++;
                        }
                        if (cells[x - 1][y + 1].getState().equals("Burned Tree")){ //H
                            numberOfBurn++;
                        }
                    }

                }
                //Sides
                if(x==0 && y!=0 & y!= dm.getNumCellsHeight()-1) { //left side
                    if(cells[x][y+1].getState().equals("Burned Tree")){ //A
                        numberOfBurn++;
                    }
                    if(cells[x+1][y+1].getState().equals("Burned Tree")){ //B
                        numberOfBurn++;
                    }
                    if(cells[x+1][y].getState().equals("Burned Tree")){ //C
                        numberOfBurn++;
                    }
                    if(cells[x+1][y-1].getState().equals("Burned Tree")){ //D
                        numberOfBurn++;
                    }
                    if(cells[x][y-1].getState().equals("Burned Tree")){ //E
                        numberOfBurn++;
                    }
                    if(cells[dm.getNumCellsWidth()-1][y-1].getState().equals("Burned Tree")){ //F
                        numberOfBurn++;
                    }
                    if(cells[dm.getNumCellsWidth()-1][y].getState().equals("Burned Tree")){ //G
                        numberOfBurn++;
                    }
                    if(cells[dm.getNumCellsWidth()-1][y+1].getState().equals("Burned Tree")){ //H
                        numberOfBurn++;
                    }
                }

                else if(y==dm.getNumCellsHeight()-1 && x!=0 && x!=dm.getNumCellsWidth()-1){ //bottom side
                    if(cells[x][0].getState().equals("Burned Tree")){ //A
                        numberOfBurn++;
                    }
                    if(cells[x+1][0].getState().equals("Burned Tree")){ //B
                        numberOfBurn++;
                    }
                    if(cells[x+1][y].getState().equals("Burned Tree")){ //C
                        numberOfBurn++;
                    }
                    if(cells[x+1][y-1].getState().equals("Burned Tree")){ //D
                        numberOfBurn++;
                    }
                    if(cells[x][y-1].getState().equals("Burned Tree")){ //E
                        numberOfBurn++;
                    }
                    if(cells[x-1][y-1].getState().equals("Burned Tree")){ //F
                        numberOfBurn++;
                    }
                    if(cells[x-1][y].getState().equals("Burned Tree")){ //G
                        numberOfBurn++;
                    }
                    if(cells[x-1][0].getState().equals("Burned Tree")){ //H
                        numberOfBurn++;
                    }
                }
                else if(x==dm.getNumCellsWidth()-1 && y!=0 && y!=dm.getNumCellsHeight()-1){ //right side0
                    if(cells[x][y+1].getState().equals("Burned Tree")){ //A
                        numberOfBurn++;
                    }
                    if(cells[0][y+1].getState().equals("Burned Tree")){ //B
                        numberOfBurn++;
                    }
                    if(cells[0][y].getState().equals("Burned Tree")){ //C
                        numberOfBurn++;
                    }
                    if(cells[0][y-1].getState().equals("Burned Tree")){ //D
                        numberOfBurn++;
                    }
                    if(cells[x][y-1].getState().equals("Burned Tree")){ //E
                        numberOfBurn++;
                    }
                    if(cells[x-1][y-1].getState().equals("Burned Tree")){ //F
                        numberOfBurn++;
                    }
                    if(cells[x-1][y].getState().equals("Burned Tree")){ //G
                        numberOfBurn++;
                    }
                    if(cells[x-1][y+1].getState().equals("Burned Tree")){ //H
                        numberOfBurn++;
                    }
                }
                else if(y==0 && x!=0 && x!=dm.getNumCellsWidth()-1){//upper side
                    if(cells[x][y+1].getState().equals("Burned Tree")){ //A
                        numberOfBurn++;
                    }
                    if(cells[x+1][y+1].getState().equals("Burned Tree")){ //B
                        numberOfBurn++;
                    }
                    if(cells[x+1][y].getState().equals("Burned Tree")){ //C
                        numberOfBurn++;
                    }
                    if(cells[x+1][dm.getNumCellsHeight()-1].getState().equals("Burned Tree")){ //D
                        numberOfBurn++;
                    }
                    if(cells[x][dm.getNumCellsHeight()-1].getState().equals("Burned Tree")){ //E
                        numberOfBurn++;
                    }
                    if(cells[x-1][dm.getNumCellsHeight()-1].getState().equals("Burned Tree")){ //F
                        numberOfBurn++;
                    }
                    if(cells[x-1][y].getState().equals("Burned Tree")){ //G
                        numberOfBurn++;
                    }
                    if(cells[x-1][y+1].getState().equals("Burned Tree")){ //H
                        numberOfBurn++;
                    }
                }

                //Other
                else if(x!=0 && y!=0 && y!=dm.getNumCellsHeight()-1 && x!= dm.getNumCellsWidth()-1){
                    if(cells[x][y+1].getState().equals("Burned Tree")){ //A
                        numberOfBurn++;
                    }
                    if(cells[x+1][y+1].getState().equals("Burned Tree")){ //B
                        numberOfBurn++;
                    }
                    if(cells[x+1][y].getState().equals("Burned Tree")){ //C
                        numberOfBurn++;
                    }
                    if(cells[x+1][y-1].getState().equals("Burned Tree")){ //D
                        numberOfBurn++;
                    }
                    if(cells[x][y-1].getState().equals("Burned Tree")){ //E
                        numberOfBurn++;
                    }
                    if(cells[x-1][y-1].getState().equals("Burned Tree")){ //F
                        numberOfBurn++;
                    }
                    if(cells[x-1][y].getState().equals("Burned Tree")){ //G
                        numberOfBurn++;
                    }
                    if(cells[x-1][y+1].getState().equals("Burned Tree")){ //H
                        numberOfBurn++;
                    }
                }
                //Change states
                if(cells[x][y].getState().equals("Tree") || cells[x][y].getState().equals("Burned Tree")) {
                    for (int i = 0; i < numberOfBurn; i++) {
                        int surviving = generator.nextInt(100);
                        if (probability - surviving > 0){
                            if (cells[x][y].getLives() > 0) {
                                cells[x][y].setLives(cells[x][y].getLives() - 1);
                                cells[x][y].setNextState("Burned Tree");
                                break;
                            } else if(cells[x][y].getLives()==0){
                                cells[x][y].setNextState("Dead");
                                break;
                            }
                            else {
                                cells[x][y].setNextState(cells[x][y].getState());
                            }
                        }
                    }
                }

                if(wind.equals("S")) {
                    int tmp=y;
                    if(y==0)
                        y= dm.getNumCellsHeight()-1;
                    if (cells[x][y -1].getState().equals("Tree") || cells[x][y -1].getState().equals("Burned Tree")) {
                        for (int i = 0; i < numberOfBurn; i++) {
                            int surviving = generator.nextInt(100);
                            if (probability - surviving > 0) {
                                if (cells[x][y-1].getLives() > 0) {
                                    cells[x][y-1].setLives(cells[x][y].getLives() - 1);
                                    cells[x][y-1].setNextState("Burned Tree");
                                    break;
                                } else if (cells[x][y-1].getLives() == 0) {
                                    cells[x][y-1].setNextState("Dead");
                                    break;
                                } else {
                                    cells[x][y-1].setNextState(cells[x][y-1].getState());
                                }
                            }
                        }
                    }
                    y=tmp;
                }

                else if(wind.equals("N")) {
                    int tmp=y;
                    if(y==dm.getNumCellsHeight()-1)
                        y=0;
                    if (cells[x][y +1].getState().equals("Tree") || cells[x][y +1].getState().equals("Burned Tree")) {
                        for (int i = 0; i < numberOfBurn; i++) {
                            int surviving = generator.nextInt(100);
                            if (probability - surviving > 0) {
                                if (cells[x][y+1].getLives() > 0) {
                                    cells[x][y+1].setLives(cells[x][y].getLives() - 1);
                                    cells[x][y+1].setNextState("Burned Tree");
                                    break;
                                } else if (cells[x][y+1].getLives() == 0) {
                                    cells[x][y+1].setNextState("Dead");
                                    break;
                                } else {
                                    cells[x][y+1].setNextState(cells[x][y+1].getState());
                                }
                            }
                        }
                    }
                    y=tmp;
                }

                else if(wind.equals("W")) {
                    int tmp=x;
                    if(x==dm.getNumCellsWidth()-1)
                        x=0;
                    if (cells[x+1][y].getState().equals("Tree") || cells[x+1][y].getState().equals("Burned Tree")) {
                        for (int i = 0; i < numberOfBurn; i++) {
                            int surviving = generator.nextInt(100);
                            if (probability - surviving > 0) {
                                if (cells[x+1][y].getLives() > 0) {
                                    cells[x+1][y].setLives(cells[x][y].getLives() - 1);
                                    cells[x+1][y].setNextState("Burned Tree");
                                    break;
                                } else if (cells[x+1][y].getLives() == 0) {
                                    cells[x+1][y].setNextState("Dead");
                                    break;
                                } else {
                                    cells[x+1][y].setNextState(cells[x+1][y].getState());
                                }
                            }
                        }
                    }
                    x=tmp;
                }

                else if(wind.equals("E")) {
                    int tmp=x;
                    if(x==0)
                        x=dm.getNumCellsWidth()-1;
                    if (cells[x-1][y].getState().equals("Tree") || cells[x-1][y].getState().equals("Burned Tree")) {
                        for (int i = 0; i < numberOfBurn; i++) {
                            int surviving = generator.nextInt(100);
                            if (probability - surviving > 0) {
                                if (cells[x-1][y].getLives() > 0) {
                                    cells[x-1][y].setLives(cells[x][y].getLives() - 1);
                                    cells[x-1][y].setNextState("Burned Tree");
                                    break;
                                } else if (cells[x-1][y].getLives() == 0) {
                                    cells[x-1][y].setNextState("Dead");
                                    break;
                                } else {
                                    cells[x-1][y].setNextState(cells[x-1][y].getState());
                                }
                            }
                        }
                    }
                    x=tmp;
                }

                if(cells[x][y].getNextState()==null)
                    cells[x][y].setNextState(cells[x][y].getState());


                numberOfBurn=0;
            }

        }
        refreshStatus();
    }

    public void initialConditions(String value){
        if(value.equals("My choice")) {
            for (int i = 350; i < 400; i++) {
                for (int j = 100; j < 150; j++) {
                    if (!dm.getStateCells()[i][j].getState().equals("Dead")) {
                        dm.getStateCells()[i][j].setState("Burned Tree");
                        dm.getStateCells()[i][j].setLives(dm.getStateCells()[i][j].getLives() - 1);
                    }
                }
            }
        }
        else if(value.equals("Random")) {
            for (Cell[] cells : dm.getStateCells())
                for (Cell cell : cells)
                    if (!cell.getState().equals("Water")) {
                        Random generator = new Random();
                        int probabilityOfInitialFire = generator.nextInt(100);
                        if (probabilityOfInitialFire == 99)
                        {
                            cell.setState("Burned Tree");
                        }
                    }
        }
    }

    public void Binarization(DataManager dm, int seedPoint,Cell [][] stateCells) {
        int height=dm.getBgImg().getHeight();
        int width=dm.getBgImg().getWidth();
        if (seedPoint > 255 || seedPoint < 0)
            System.out.println("Wrong number! Please 0-255");
        for (int wi = 0; wi < width; wi++) {
            for (int hi = 0; hi < height; hi++) {
                Color color = new Color(dm.getBgImg().getRGB(wi, hi));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                if (red < 255 - seedPoint && green < 255 - seedPoint && blue < 255 - seedPoint) {
                    red = 0;
                    green = 0;
                    blue = 139;
                } else {
                    red = 0;
                    green = 100;
                    blue = 0;
                }
                color = new Color(red, green, blue);
                dm.getBgImg().setRGB(wi, hi, color.getRGB());
                if(blue==139)
                    stateCells[wi][hi].setState("Water");
            }
        }
    }
}