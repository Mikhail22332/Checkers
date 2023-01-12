package com.Data;

/**
 * Class Move is used to create and modify and reposition
 */
public class Move {
    private int startX = 0;
    private int startY = 0;
    private int endX = 0;
    private int endY = 0;
    private int stepCounter = 0;
    public Move(){}

    /**
     * Constructor
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     */
    public Move(int startX, int startY, int endX, int endY){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * Constructor
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param step
     */
    public Move(int startX, int startY, int endX, int endY, int step) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.stepCounter = step;
    }

    /**
     * Sets value to step counter
     * @param stepCounter
     */
    public void setStepCounter(int stepCounter) { this.stepCounter = stepCounter; }
    /**
     *
     * @return number of steps made in recursion
     */
    public int getStepCounter() { return stepCounter; }
    /**
     * sets new array of start and end coordinates
     * @param s
     */
    public void SetMove(String s[]){
        this.startX = Integer.parseInt(s[0]);
        this.startY = Integer.parseInt(s[1]);
        this.endX = Integer.parseInt(s[2]);
        this.endY = Integer.parseInt(s[3]);
    }
    /**
     *
     * @param s
     * @return
     */
    public static Move StringToMove(String s){
        String[] a = s.split(",");
        Move move = new Move();
        move.SetMove(a);
        return move;
    }
    /**
     * @return start x coordinate
     */
    public int getStartX(){return startX;}
    /**
     * @return start y coordinate
     */
    public int getStartY(){return startY;}
    /**
     * @return end x coordinate
     */
    public int getEndX(){return endX;}
    /**
     * @return end y coordinate
     */
    public int getEndY(){return endY;}
    /**
     * Method converts piece coordinates into string
     * and sends that information to server
     * @param move
     * @return string
     */

    /*public static String MoveToString(Move move){
        String s = "";
        s += move.getStartX() + ",";
        s += move.getStartY() + ",";
        s += move.getEndX() + ",";
        s += move.getEndY();
        return s;
    }*/
    /**
     * @param move
     * @return true if start coordinates equal to end coordinates, false otherwise
     */
    public boolean isEqual(Move move) {
        return this.getStartX() == move.getStartX() &&
                this.getStartY() == move.getStartY() &&
                this.getEndX() == move.getEndX() &&
                this.getEndY() == move.getEndY();
    }
}
