package com.Client;

class Square extends javafx.scene.control.Control {

    private Pawn pawn;
    private int row;
    private int col;
    private static Pawn selectedPawn;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;

        // Set the size of the square
        // ToDo Rework to resizable
        setMinSize(50, 50);
        setMaxSize(50, 50);

        // Set the color of the square
        if ((row + col) % 2 == 0) {
            setStyle("-fx-background-color: #eeeed2;" +
                    "-fx-border-color:#424242;");
        } else {
            setStyle("-fx-background-color: #769656;" +
                    "-fx-border-color:#424242;");
        }
        // Add a mouse listener to the square to handle clicks
        setOnMouseClicked(event -> {
            // Check is our move now
            if(!ClientApplication.myNetwork.IsYourMove()) {
                System.out.println("Not your move");
                return;
            }
            // If the square is empty, do nothing
            if (pawn == null && selectedPawn == null) {
                System.out.println("No pawn");
                return;
            }

            // If a pawn is already selected, try to move it to this square
            if (selectedPawn != null) {
                //System.out.println("Try to move pawn");
                // If the pawn can be moved to this square, move it and unselect it
                if (selectedPawn.canMoveTo(row, col)) {
                    String myMove = "MOVE " +
                            selectedPawn.getRow() + "," +
                            selectedPawn.getCol() + "," +
                            row + "," + col;
                    makeMove();
                    try {
                        ClientApplication.myNetwork.sendMove(myMove);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    selectedPawn.setSelected(false);
                    selectedPawn = null;
                }
            } else if(pawn != null) {
                //System.out.println("Pawn was selected");
                // If no pawn is selected, select this pawn
                pawn.setSelected(true);
                selectedPawn = pawn;
            }
        });
    }
    private void makeMove(){
        //System.out.println("Move is realised");
        ClientApplication.getSquare(selectedPawn.getRow(), selectedPawn.getCol()).setPawn(null);
        selectedPawn.setRow(row);
        selectedPawn.setCol(col);
        ClientApplication.getSquare(selectedPawn.getRow(), selectedPawn.getCol()).setPawn(null);
        setPawn(selectedPawn);
        selectedPawn.setSelected(false);
        selectedPawn = null;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
        getChildren().clear();
        if (pawn != null) {
            getChildren().add(pawn);
        }
    }

    public Pawn getPawn() {
        return pawn;
    }
}