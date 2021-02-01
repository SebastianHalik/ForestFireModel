import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DataManager {
    private int boardWidth, boardHeight;
    private int numCellsWidth;
    private int numCellsHeight;
    private Cell [][] stateCells;
    private BufferedImage bgImg= ImageIO.read(new File("map.bmp")); //img 600x480


    public DataManager() throws IOException {
        initNumCells();
        initStateCells();
    }

    public void initNumCells(){
        this.numCellsWidth= this.bgImg.getWidth();
        this.numCellsHeight=this.bgImg.getHeight();
    }

    public void initStateCells(){
        this.stateCells=new Cell[this.numCellsWidth][this.numCellsHeight];
        for(int i=0;i<this.numCellsWidth;i++)
            for(int j=0;j<this.numCellsHeight;j++) {
                this.stateCells[i][j] = new Cell(i , j , "Tree");
                Color color=new Color(bgImg.getRGB(i, j));
                int blue=color.getBlue();
                if(blue==139)
                    stateCells[i][j].setState("Water");
            }
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setWidth(int width) {
        this.boardWidth = width;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getNumCellsWidth() {
        return numCellsWidth;
    }

    public void setNumCellsWidth(int numCellsWidth) {
        this.numCellsWidth = numCellsWidth;
    }

    public int getNumCellsHeight() {
        return numCellsHeight;
    }

    public void setNumCellsHeight(int numCellsHeight) {
        this.numCellsHeight = numCellsHeight;
    }

    public Cell[][] getStateCells() {
        return stateCells;
    }

    public void setStateCells(Cell[][] stateCells) {
        this.stateCells = stateCells;
    }

    public BufferedImage getBgImg() {
        return bgImg;
    }

    public void setBgImg(BufferedImage bgImg) {
        this.bgImg = bgImg;
    }
}
