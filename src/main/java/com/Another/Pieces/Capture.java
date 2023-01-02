package com.Another.Pieces;

import com.Another.Move;
import com.Another.Position;

public class Capture extends Move {
    private static boolean isCapture;
    private Position positionCapture;

    public Capture(Position start, Position destination, boolean jump, boolean captureFalse, boolean mustCapture) {
        super(start, destination, jump, captureFalse, mustCapture);
    }

    public boolean isCapture(){
        return isCapture;
    }
    public void setCapture(boolean capture){
        this.isCapture = isCapture;
    }
    public Position getPositionCapture(){
        return positionCapture;
    }
    public void setPositionCapture(Position positionCapture){
        this.positionCapture = positionCapture;
    }
}
