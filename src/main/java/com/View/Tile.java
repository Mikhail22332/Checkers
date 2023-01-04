package com.View;


import com.Another.Position;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Tile extends Rectangle {
    private Position position;
    private StackPane paneNode = new StackPane();
    private Color color;
    private String symbol;

    public Tile(Position pos){
        this.position = pos;

        int positionSum = position.getX() + position.getY();
        if(positionSum % 2 ==0 )
            color = Color.ANTIQUEWHITE;
        else{
            color = Color.BROWN;
        }
        Background back = new Background(new BackgroundFill(color, new CornerRadii(0), new Insets(0)));
        paneNode.setBackground(back);
    }

    public Position getPosition(){
        return position;
    }

    public Node getPaneNode(){
        paneNode.getChildren().clear();

        paneNode.getChildren().addAll(new Rectangle(50, 50, Color.TRANSPARENT));
        return paneNode;
    }

    public void setSymbol(String symbol) {
        paneNode.getChildren().clear();
        this.symbol = symbol;
        Label label = new Label(symbol);
        label.setFont(new Font(50));
        paneNode.getChildren().addAll(new Rectangle(50, 50 , Color.TRANSPARENT), label);
    }

    public String getSymbol() {
        return symbol;
    }
}
