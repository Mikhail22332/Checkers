package com.Controller;

public enum CheckerState implements GameState{
    White_Win("White win"),
    Black_Win("Black win"),
    UnFinished("Not finished yet");
    private String message;
    CheckerState(String m){
        this.message = m;
    }

    public boolean isgameOver() {
        if (equals(White_Win) || equals(Black_Win)){
            return true;
        }
        else {
            return false;
        }
    }
}
