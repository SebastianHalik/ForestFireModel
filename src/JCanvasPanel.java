import javax.swing.*;
import java.awt.*;

public class JCanvasPanel extends JPanel {

    DataManager dm;

    public JCanvasPanel(DataManager dm){
    this.dm=dm;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(dm.getBgImg(), 0, 0, this);
        for(int i=0;i<dm.getNumCellsWidth();i++){
            for(int j=0;j<dm.getNumCellsHeight();j++){
                if(dm.getStateCells()[i][j].getState().equals("Burned Tree")){
                    g.setColor(Color.RED);
                    g.fillRect(i,j,1,1);
                }
                else if(dm.getStateCells()[i][j].getState().equals("Dead")){
                    g.setColor(Color.BLACK);
                    g.fillRect(i,j,1,1);
                }
                else if(dm.getStateCells()[i][j].getState().equals("Water")){
                    g.setColor(Color.blue);
                    g.fillRect(i,j,1,1);
                }
                else if(dm.getStateCells()[i][j].getState().equals("Tree")){
                    if(Main.seasonCombo.getSelectedItem().toString().equals("Spring")) {
                        Color tree = new Color(0, 100, 0);
                        g.setColor(tree);
                        g.fillRect(i, j, 1, 1);
                    }
                    else if (Main.seasonCombo.getSelectedItem().toString().equals("Winter")){
                        Color tree = new Color(229, 228, 226);
                        g.setColor(tree);
                        g.fillRect(i, j, 1, 1);
                    }
                    else if (Main.seasonCombo.getSelectedItem().toString().equals("Autumn")){
                        Color tree = new Color(255, 204, 153);
                        g.setColor(tree);
                        g.fillRect(i, j, 1, 1);
                    }
                    else if (Main.seasonCombo.getSelectedItem().toString().equals("Summer")){
                        Color tree = new Color(0, 200, 0);
                        g.setColor(tree);
                        g.fillRect(i, j, 1, 1);
                    }
                }
                super.repaint();
            }
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
