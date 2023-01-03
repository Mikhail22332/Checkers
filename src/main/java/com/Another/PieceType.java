package com.Another;

public enum PieceType implements SymbolPiece{
    Pawn("○", "●"),
    Quene("⚆", "⚈");
    private String white, black;

    PieceType(String white, String black) {
        this.black = black;
        this.white = white;
    }

    @Override
    public String getSymbol(Color color) {
        return color == Color.White ? white : black;
    }
}
