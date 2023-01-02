package com.Another;

public class Move {
    private Position start;
    private Position destination;
    private boolean jump;
    private boolean captureFalse;
    private boolean mustCapture;

    public Move(Position start, Position destination){
        this.start = start;
        this.destination = destination;
        this.jump = false;
        this.captureFalse = false;
        this.mustCapture = false;
    }
    public Move(Position start, Position destination, boolean jump){
        this.start = start;
        this.destination = destination;
        this.jump = jump;
        this.captureFalse = false;
        this.mustCapture = false;
    }
    public Move(Position start, Position destination, boolean jump, boolean captureFalse, boolean mustCapture){
        this.start = start;
        this.destination = destination;
        this.jump = jump;
        this.captureFalse = captureFalse;
        this.mustCapture = mustCapture;
    }

    public Position getStart(){
        return start;
    }
    public Position getDestination(){
        return destination;
    }
    public boolean isJump(){
        return jump;
    }

    public boolean isCaptureFalse() {
        return captureFalse;
    }

    public boolean isMustCapture() {
        return mustCapture;
    }

}
