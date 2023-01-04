package com.Controller;

import com.Another.*;
import com.Another.Pieces.Capture;
import com.Another.Pieces.GameBoard8x8Classic;
import com.Another.Pieces.Promotion;
import com.Another.Pieces.Quene;

import java.util.*;

public class Game8x8Controller implements GameController{

    private GameBoard8x8Classic board;
    private Color currentPlayer;
    private Map<Piece, Set<Move>> currentMoves;
    private GameState state;
    private Piece selectedPiece;


    public Game8x8Controller(){
        setGameBoard8x8Classic(new GameBoard8x8Classic());
        setCurrentPlayer(Color.White);
        setCurrentMoves(new HashMap<>());
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void startGame() {
        state = CheckerState.UnFinished;
        startTurn();
    }

    @Override
    public void startTurn() {
        boolean sideIsWhite = getState().equals(Color.White);

        try{
            setCurrentMoves(getBoard().generateAllMovesForSide(getCurrentPlayer()));
        }catch (Exception e){
            System.out.println("Side has no moves");
        }
    }
    //server must be here
    @Override
    public void endTurn() {
        boolean player = getCurrentPlayer().equals(Color.White);
        if(player)
            setCurrentPlayer(Color.Black);
        else {
            setCurrentPlayer(Color.White);
        }

        //ADD добавить проверку для конца игры, когда все шашки у кого-то убраны

    }

    @Override
    public Set<Move> getMovesForPieceAt(Position position) {
        Piece piece = getBoard().getPieceAt(position);
        if( piece == null || piece.getColor() != getCurrentPlayer())
            return new HashSet<>();
        else{
            return getCurrentMoves().get(piece);
        }
    }

    @Override
    public Map<Piece, Position> getAllActivePositions() {
        return getBoard().getAllActivePiecesPositions();
    }

    @Override
    public void makeMove(Move testedMove) {
        Piece movedPiece = getBoard().getPieceAt(testedMove.getStart());
        selectedPiece = movedPiece;

        Move move = possibilitiesForMove(testedMove);

        if(move == null){
            ///Exception needed
        }

        if(getCurrentMoves() != null && getCurrentMoves().get(movedPiece) != null &&
                        getCurrentMoves().get(movedPiece).contains(move)) {

            getBoard().movePiece(movedPiece, move);

            //promotion
            if(move instanceof Promotion){
                Promotion promotionMove = (Promotion) move;
                if(promotionMove.getPromotionToQuene() == null){
                    promotionMove.setPromotionToQuene(PieceType.Quene);
                }
                board.replacePieceAt(move.getDestination(), new Quene(currentPlayer));
            }
            //capture
            if(move instanceof Capture){
                //
            }
        }


    }

    @Override
    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    private Move possibilitiesForMove(Move move){
        Move myMove = null;
        for (Move testedMove: currentMoves.get(selectedPiece)){
            if(move.equals(testedMove)){
                myMove = testedMove;
            }
        }
        return myMove;
    }

    private GameBoard8x8Classic getBoard(){
        return board;
    }

    private void setGameBoard8x8Classic(GameBoard8x8Classic board){
        this.board = board;
    }
    private Map<Piece, Set<Move>> getCurrentMoves(){
        return currentMoves;
    }


    private void setCurrentMoves(Map<Piece, Set<Move>> currentMoves){
        this.currentMoves = currentMoves;
    }


    private void setCurrentPlayer(Color currentPlayer){
        this.currentPlayer = currentPlayer;
    }
    private boolean moveResultInCapture(Move move){
        Piece moved = board.getPieceAt(move.getStart());
        Piece target = board.getPieceAt(move.getDestination());

        if ( move instanceof Capture && ((Capture) move).isCapture()){

            target = board.getPieceAt(((Capture) move).getPositionCapture());
        }

        return target != null && !moved.getColor().equals(target.getColor());
    }

}
