package com.View;


import com.Another.Move;
import com.Another.Position;
import com.Controller.Game8x8Controller;
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
    private Game8x8Controller controller;

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

        paneNode.getChildren().addAll(new Rectangle(80, 80, Color.TRANSPARENT));
        return paneNode;
    }

    public void setSymbol(String symbol) {
        paneNode.getChildren().clear();
        this.symbol = symbol;
        Label label = new Label(symbol);
        Rectangle rectangle = new Rectangle(80, 80 , Color.TRANSPARENT);
        rectangle.setFill(Color.GRAY);

        final Rectangle temp = new Rectangle(80, 80);
        rectangle.setOnMouseClicked(e->{
            rectangle.setFill(Color.GREEN);
        });
        rectangle.setOnDragDetected(
                event -> {
                    System.out.println("11");
                    temp.setX(((int) event.getX() / 80) * 80 );
                    temp.setY(((int) event.getY() / 80) * 80 );
                }
        );
        rectangle.setOnMouseDragged(
                event -> {
                    System.out.println("22");
                    if (event.getX() < 0) {
                        rectangle.setX(0);
                    } else if (event.getX() > 720) {
                        rectangle.setX(720);
                    } else {
                        rectangle.setX(event.getX());
                    }

                    if (event.getY() < 0) {
                        rectangle.setY(0);
                    } else if (event.getY() > 670) {
                        rectangle.setY(670);
                    } else {
                        rectangle.setY(event.getY());
                    }
                }
        );
        /*rectangle.setOnMouseReleased(event ->{
            try{
                System.out.println("33");
                rectangle.setX((int)(event.getX()/80)*80);
                rectangle.setY((int)(event.getY()/80)*80);
                //controller.makeMove(new Move(new Position((int) temp.getX(),(int) temp.getY()), new Position(((int) rectangle.getX()/80)*80,( (int) rectangle.getY()/80)*80)));

            }catch (Exception e){
                System.out.println("aa");
            }
        */
        //});
        label.setFont(new Font(60));
        paneNode.getChildren().addAll(rectangle, label);
    }


    public String getSymbol() {
        return symbol;
    }
    public void clear(){
        Background back = new Background(new BackgroundFill(color, new CornerRadii(0), new Insets(0)));
        paneNode.setBackground(back);
    }
    public void highlight(Color color) {
        // TODO
        Background highlight = new Background(new BackgroundFill(color,
                new CornerRadii(0), new Insets(0)));
        paneNode.setBackground(highlight);
    }

}
