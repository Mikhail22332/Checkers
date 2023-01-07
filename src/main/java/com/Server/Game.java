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
    AbstractValidator checker;
    Player currentPlayer;
    public Game(GameType type, int size){
        if(type == GameType.Standart){
            factoryBoard = new FactoryBoardStandart();
            board = factoryBoard.CreateBoard(size);
            checker = new ValidatorStandart();
        }
    }

    public synchronized void move(Move move){
        if(!checker.isValidMove(board, move))
            throw new IllegalStateException("NOT_VALID_MOVE");
        checker.MakeMove(board, move);
        currentPlayer = currentPlayer.opponent;
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
                output.println("WELCOME WHITE");
            else
                output.println("WELCOME BLACK");
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
        public String BoardToString(){
            String s = "";
            for(int i = 0; i < board.GetSize(); i++){
                for(int j = 0; j < board.GetSize(); j++){
                    if(board.GetField(i,j).getPieceType() == PieceType.Blank){
                        s = s + "0";
                    }
                    else if(board.GetField(i,j).getPieceType() == PieceType.Pawn){
                        s = s + "1";
                    }
                    else {
                        s = s + "2";
                    }
                    s = s + ",";
                }
                s = s + "ss";
            }
            return s;
        }
        private void processMoveCommand(String move) {
            try {
                System.out.println(move);
                move(Move.StringToMove(move));
                //ToDo Check move
                output.println("VALID_MOVE");
                opponent.output.println("OPPONENT_MOVED " + "ss" + BoardToString());
                if (checker.isWinner(board, mark)) {
                    output.println("VICTORY");
                    opponent.output.println("DEFEAT");
                }
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }
    }
}
