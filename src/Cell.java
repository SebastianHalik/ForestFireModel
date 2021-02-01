public class Cell {

   private int x,y;
   private String state;
   private String nextState;
   private int lives;


    Cell(int x, int y, String state){
       this.x=x;
       this.y=y;
       this.state=state;
       lives=15;
   }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setNextState(String nextState){
        this.nextState=nextState;
    }

    public String getNextState() { return nextState; }

    public void changeState(){state=nextState;}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void dead(){ this.state="Dead"; }
    public void alive(){this.state="Tree"; }
}

