package com.Server;

import com.Data.*;
import com.Data.Standart.ValidatorStandart;
import com.Data.Standart.FactoryBoardStandart;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    Board board;
    AbstractFactory factoryBoard;
    AbstractValidator validator;
    Player currentPlayer;
    public Game(GameType type, int size){
        if(type == GameType.Standart){
            factoryBoard = new FactoryBoardStandart();
            board = factoryBoard.CreateBoard(size);
            validator = new ValidatorStandart();
        }
    }

    class Player implements Runnable{
        private Color mark;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;

        public Player(Socket socket, Color mark){
            this.socket = socket;
            this.mark = mark;
        }
        //0 - impossible move, 1 - simple move, 2 - capture
        public boolean moveValidation(Move move){
            int typeOfMove = validator.isValidMove(board, move);
            System.out.println(typeOfMove);
            if (typeOfMove == 2) {
                validator.makeCaptureMove(board, move);
                output.println("VALID_MOVE YOUR_TURN " + board.boardToString());
                return false;
            }
            currentPlayer = currentPlayer.opponent;
            if(typeOfMove == 1){
                validator.makeMove(board, move);
                output.println("VALID_MOVE WAIT " + board.boardToString());
                return true;
            }
            output.println("NOT_VALID_MOVE");
            return false;
        }
        @Override
        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            if(mark == Color.White)
                output.println("MESSAGE WELCOME WHITE");
            else
                output.println("MESSAGE WELCOME BLACK");
            if (mark == Color.White) {
                currentPlayer = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                opponent.output.println("MESSAGE YOUR MOVE");
            }
        }
        private void processCommands(){
            while (input.hasNextLine()) {
                var command = input.nextLine();
                if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("MOVE")) {
                    processMoveCommand(command.substring(5));
                }
            }
        }
        private void processMoveCommand(String move) {
            try {
                System.out.println(move);
                if(!moveValidation(Move.StringToMove(move))) {return;}
                opponent.output.println("OPPONENT_MOVED " + board.boardToString());
                if (validator.isWinner(board, mark)) {
                    output.println("VICTORY");
                    opponent.output.println("DEFEAT");
                }
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }
    }
}
