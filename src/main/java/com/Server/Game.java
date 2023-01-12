package com.Server;

import com.Data.*;
import com.Data.English.ValidatorEnglish;
import com.Data.Standart.ValidatorStandart;
import com.Data.Standart.FactoryBoardStandart;
import javafx.util.Pair;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    Board board;
    AbstractFactoryBoard factoryBoard;
    AbstractValidator validator;
    Player currentPlayer;
    GameType currentType;
    public Game(){}

    /**
     * Constructor to set a game type and size of a field
     * @param type
     * @param size
     */
    public Game(GameType type, int size){
        currentType = type;
        if(type == GameType.Russian){
            factoryBoard = new FactoryBoardStandart();
            board = factoryBoard.createBoard(size);
            validator = new ValidatorStandart();
        }
        if(type == GameType.English) {
            factoryBoard = new FactoryBoardStandart();
            board = factoryBoard.createBoard(size);
            validator = new ValidatorEnglish();
        }
        if(type == GameType.Polish) {
            factoryBoard = new FactoryBoardStandart();
            board = factoryBoard.createBoard(size);
            validator = new ValidatorStandart();
        }
    }
    /**
     * Inner class to check the player's state,
     * to handle player's moves and to print different data
     */
    class Player implements Runnable{
        private Color mark;
        Player opponent;
        Socket socket;
        Scanner input;
        PrintWriter output;
        Move lastMyMove;
        /**
         * Constructor to create new players
         * @param socket
         * @param mark
         */
        public Player(Socket socket, Color mark){
            this.socket = socket;
            this.mark = mark;
        }
        /**
         * Method that calls validator in order to perform
         * different behavior of a piece.
         * @param move
         * @return false if we need to pass turn to another player, return true if current player must make one or more moves
         */
        public boolean moveValidation(Move move) {
            Pair<Integer, String> typeWithMessage = validator.isValidMove(board, move, lastMyMove, mark);
            // 0 - impossible move, 1 - simple move, 2 - move with capture
            int typeOfMove = typeWithMessage.getKey();
            // Move with capture
            if (typeOfMove == 2) {
                validator.makeMove(board, move);
                lastMyMove = move;
                // Check if there are any more moves with capture
                if(validator.checkForNextMove(board, lastMyMove.getEndX(), lastMyMove.getEndY())) {
                    output.println("VALID_MOVE YOUR_TURN " + board.boardToString());
                    return false;
                }
                output.println("VALID_MOVE WAIT " + board.boardToString());
                return true;
            }
            // Move without capture
            if(typeOfMove == 1){
                validator.makeMove(board, move);
                lastMyMove = move;
                output.println("VALID_MOVE WAIT " + board.boardToString());
                return true;
            }
            // invalid move
            board.printBoard();
            output.println("NOT_VALID_MOVE " + board.boardToString());
            output.println("MESSAGE " + typeWithMessage.getValue());
            return false;
        }
        /**
         * Method run is responsible for linking a player with
         * thread and connects to server
         */
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
        /**
         * Method sets input and output channels for players
         * @throws IOException
         */
        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            if(mark == Color.White)
                output.println("MESSAGE WELCOME WHITE " + board.getSize());
            else
                output.println("MESSAGE WELCOME BLACK " + board.getSize());
            if (mark == Color.White) {
                currentPlayer = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                // First move to Black
                if(currentType == GameType.English) {
                    currentPlayer.output.println("MESSAGE YOUR MOVE");
                }
                // First move to White
                else {
                    opponent.output.println("MESSAGE YOUR MOVE");
                }
            }
        }
        /**
         * Method to handle commands from player
         */
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
        /**
         * Handles player's move
         * @param move
         */
        private void processMoveCommand(String move) {
            try {
                System.out.println(move);
                if(!moveValidation(Move.StringToMove(move))) {return;}
                lastMyMove = null;
                checkStateOfGame();
                currentPlayer = currentPlayer.opponent;
                opponent.output.println("OPPONENT_MOVED " + board.boardToString());
                checkStateOfGame();
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }

        /**
         * Method to check the state of the game
         */
        private void checkStateOfGame() {
            if(!validator.isPlayerHasAtLeastOneMove(board, mark)) {
                output.println("DEFEAT, you don't have possible moves");
                opponent.output.println("VICTORY, opponent doesn't have possible moves");
            }
            if (validator.isTie()) {
                output.println("TIE");
                opponent.output.println("TIE");
            }
            if (validator.isWinner(board, mark)) {
                output.println("VICTORY, you kill all enemy pieces");
                opponent.output.println("DEFEAT, you don't have any pieces");
            }
        }
    }
}
