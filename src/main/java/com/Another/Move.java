package com.Another;

public class Move {
    private Position start;
    private Position destination;
    private boolean mustCapture;
    private PieceType type;

    public Move(Position start, Position destination){
        this.start = start;
        this.destination = destination;
    }

    public Move(Position start, Position destination, boolean must){
        this.start = start;
        this.destination = destination;
        this.mustCapture = must;
    }
    public Move(Position start, Position destination, boolean must, PieceType type){
        this.start = start;
        this.destination = destination;
        this.mustCapture = must;
        this.type = type;
    }


    public Position getStart(){
        return start;
    }
    public Position getDestination(){
        return destination;
    }

    public boolean isMustCapture() {
        return mustCapture;
    }
    public void setPieceType(PieceType type){
        this.type = type;
    }
}
