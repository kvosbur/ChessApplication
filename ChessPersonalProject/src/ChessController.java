import javax.swing.*;
import java.awt.*;

/**
 * Created by kevin on 11/4/2016.
 * Capabitlities finished:
 * removed pieces join the graveyard
 * shows graveyards on sides of screen
 * shows possible postions to move to by setting background to green
 * Allows you to move piece to green positions
 * If you click other piece of same color possible moves change to that pieces possible moves
 */


/*
    Still Need-
    castling
    tell user when game ends
        give user option to rematch or go to main menu

    Future features wanted
        main menu for game
        have player accounts which keep track of your stats
        have timed games
        play from multiple devices (client server setup)
            need to look into what a socketFactory is
            eventually to make internet safe use SSLSocket to create more secure connections (learning curve)

        App????
            allow for it to be turn based so that it notifies user when it is their turn



    KNOWN ERRORS -

 */

public class ChessController {

    /*
    Takes gui events and tells the model what to change
     */
    ChessView view;
    ChessModel model;
    String[] originalSetup = {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"};

    public ChessController(){
        //initiate other classes
        this.view = new ChessView(this);
        this.model = new ChessModel(this);
        model.setPieces(createPieceArray());

        //set board initially so that Light_Gray has first move
        for(int row = 6; row < 8; row++){
            for(int column = 0; column < 8; column++){
                view.setBoardPositionEnabled(false, row, column);
            }
        }
    }

    //creates initial Piece array stored within the model
    public Piece[][] createPieceArray(){
        Piece[][] pieces = new Piece[8][8];
        King[] kings = new King[2];  //helps to keep track of kings for purposes of checking for check or checkmate
        //create pieces for top area
        for(int row = 0; row < 2; row++){
            for(int column = 0; column < 8; column++){
                if(row == 0){
                    String currentPiece = originalSetup[column];
                    if(currentPiece.equals("Rook")){
                        pieces[row][column] = new Rook(Color.LIGHT_GRAY, row, column);
                    }else if(currentPiece.equals("Knight")){
                        pieces[row][column] = new Knight(Color.LIGHT_GRAY, row, column);
                    }else if(currentPiece.equals("Bishop")){
                        pieces[row][column] = new Bishop(Color.LIGHT_GRAY, row, column);
                    }else if(currentPiece.equals("Queen")){
                        pieces[row][column] = new Queen(Color.LIGHT_GRAY, row, column);
                    }else if(currentPiece.equals("King")){
                        pieces[row][column] = new King(Color.LIGHT_GRAY, row, column);
                        kings[0] = (King) pieces[row][column];

                    }
                    view.setBoardPosition(pieces[row][column].getName(), row, column, true);
                }else {
                    pieces[row][column] = new Pawn(Color.LIGHT_GRAY, row, column, 1);
                    view.setBoardPosition(pieces[row][column].getName(), row, column, true);
                }
                view.setBoardPositionColor(row, column, Color.LIGHT_GRAY);
            }
        }

        //create pieces for bottom area
        for(int row = 6; row < 8; row++){
            for(int column = 0; column < 8; column++){
                if(row == 7){
                    String currentPiece = originalSetup[7 - column];
                    if(currentPiece.equals("Rook")){
                        pieces[row][column] = new Rook(Color.RED, row, column);
                    }else if(currentPiece.equals("Knight")){
                        pieces[row][column] = new Knight(Color.RED, row, column);
                    }else if(currentPiece.equals("Bishop")){
                        pieces[row][column] = new Bishop(Color.RED, row, column);
                    }else if(currentPiece.equals("Queen")){
                        pieces[row][column] = new Queen(Color.RED, row, column);
                    }else if(currentPiece.equals("King")){
                        pieces[row][column] = new King(Color.RED, row, column);
                        kings[1] = (King) pieces[row][column];
                    }
                    view.setBoardPosition(pieces[row][column].getName(), row, column, true);
                }else {
                    pieces[row][column] = new Pawn(Color.RED, row, column, -1);
                    view.setBoardPosition(pieces[row][column].getName(), row, column, true);
                }
                view.setBoardPositionColor(row, column, Color.RED);
            }
        }

        model.setKings(kings);
        return pieces;

    }

    /*
    finds the different locations in which that specific piece can be and highlights them green to be easy to see
     */

    public void PossiblemovePieceLocations(int row, int column){
        int[][] possibleMoves;

        //remove green backgrounds if user clicks different piece
        if(model.getPieces()[row][column] != model.getCurrentPiece()){
            if(model.getCurrentPiecePositions() != null) {
                setDefaultButtonFormatting(model.getCurrentPiecePositions(), model.getPieces());
            }
        }

        //get current piece to evaluate
        Piece currentPiece = model.getPieces()[row][column];
        //find moveset
        possibleMoves = currentPiece.movePiece(model.getPieces());

        /*
        if(currentPiece instanceof Pawn){// consider diagnols seperately for pawns for other aspects of code to work
            possibleMoves = Piece.addCoordinateArray(possibleMoves,((Pawn) currentPiece).pawnAttack(model.getPieces()));
        }
        */
        //loop through possible moves and check for hypothetical check of same color king(exposes king to other team attack) and remove those that result in true
        for(int index = 0; index < possibleMoves.length; index++){
            if(possibleMoves[index] != null){  //make sure not an empty part of array
                if(model.hypotheticalCheck(currentPiece, possibleMoves[index][0], possibleMoves[index][1])){
                    possibleMoves = Piece.removeCoordinate(possibleMoves[index], possibleMoves);
                    index--;
                }
            }
        }
        if(possibleMoves[0] == null){
            view.setLastMove(currentPiece.getName() + " At " + ChessView.rowHeaders.get(currentPiece.getRowLocation()) + (currentPiece.getColumnLocation() + 1) + " has no valid moves");
        }

        //is it necessary to even call this considering what previous loop does
        if(currentPiece instanceof King){
            King k = (King) currentPiece;
            possibleMoves = k.restrictMovementCheck(possibleMoves,model.getPieces());
        }
        //set the current piece to a saved area so it will know which moves are out there for that piece
        model.setCurrentPiece(currentPiece);
        for(int i = 0; i < possibleMoves.length;i++){
            if(possibleMoves[i] != null) {
                int[] coordinate = possibleMoves[i];
                //System.out.println("" + coordinate[0] +"  " +  coordinate[1]);
            }
        }

        //change board's color at locations
        changeColorMovePositions(possibleMoves, Color.GREEN);
        //save which positions were changed so that they can be unchanged after player has made a move
        model.setCurrentPiecePositions(possibleMoves);

    }


    /*
    changes the color to specified color of all jbuttons at the given positions and enables them to be clicked
     */
    public void changeColorMovePositions(int[][] positions, Color color){
        for(int i = 0; i < positions.length; i++){
            if(positions[i] != null){
                view.setBoardPositionColor(positions[i][0], positions[i][1], color);
                view.setBoardPositionEnabled(true,positions[i][0], positions[i][1]);
            }
        }
    }

    //move current piece to given location and remove any green jbutton backgrounds
    public void movePieceToLocation(int row,int column){
        Piece piece = model.getCurrentPiece();
        //System.out.println("moving");
        //check to see if where moving to has a different piece there that will be sent to the graveyard
        Piece[][] pieceArray = model.getPieces();
        if(pieceArray[row][column] != null){
            model.addToGraveyard(pieceArray[row][column]);
        }
        //change pieces location
        changePieceLocation(piece.getRowLocation(), piece.getColumnLocation(), row, column);
        //clear board of green background and set any enabled blank areas to disabled
        cleanBoard(piece.getRowLocation(), piece.getColumnLocation());
        //change pieces row and column info used for movePiece
        piece.setRowLocation(row);
        piece.setColumnLocation(column);

        //check for possible pawn promotion
        if(piece instanceof Pawn){
            Pawn temp = (Pawn) piece;
            if(temp.goodForPromotion()){
                //promote pawn
                String[] piecesToChoose = {"Rook", "Knight", "Bishop", "Queen"};
                Object selectedPiece = JOptionPane.showInputDialog(null, "Choose which Piece to Subsitute for Pawn.",
                        "Pawn Promotion", JOptionPane.INFORMATION_MESSAGE,null,piecesToChoose,
                        piecesToChoose[0]);
                System.out.println("pawn promotions selected to be " + selectedPiece);
                //replace pawn object with new piece object of selected type

                if(selectedPiece.equals(piecesToChoose[0])){
                    pieceArray[piece.getRowLocation()][piece.getColumnLocation()] = new Rook(piece.getColor(),piece.getRowLocation(),piece.getColumnLocation());
                }else if(selectedPiece.equals(piecesToChoose[1])){
                    pieceArray[piece.getRowLocation()][piece.getColumnLocation()] = new Knight(piece.getColor(),piece.getRowLocation(),piece.getColumnLocation());
                }else if(selectedPiece.equals(piecesToChoose[2])){
                    pieceArray[piece.getRowLocation()][piece.getColumnLocation()] = new Bishop(piece.getColor(),piece.getRowLocation(),piece.getColumnLocation());
                }else if(selectedPiece.equals(piecesToChoose[3])){
                    pieceArray[piece.getRowLocation()][piece.getColumnLocation()] = new Queen(piece.getColor(),piece.getRowLocation(),piece.getColumnLocation());
                }
                view.setBoardPosition(pieceArray[piece.getRowLocation()][piece.getColumnLocation()].getName(),piece.getRowLocation(), piece.getColumnLocation(),false);
            }
        }



        //change whose player it is and who can move their pieces
        model.changePlayerTurn();
        //disable players pieces who just moved and enable those who now can move
        for(int rowCheck = 0; rowCheck < 8;rowCheck++){
            for(int columnCheck = 0; columnCheck < 8; columnCheck++){
                if(pieceArray[rowCheck][columnCheck] != null){
                    if(pieceArray[rowCheck][columnCheck].getColor() == piece.getColor()){
                        view.setBoardPositionEnabled(false, rowCheck, columnCheck);
                    }else if(pieceArray[rowCheck][columnCheck].getColor() == model.getPlayerTurn()){
                        view.setBoardPositionEnabled(true, rowCheck, columnCheck);
                    }
                }
            }
        }

        //check for check / checkmate (only check for checkmate if in check)
        //finding enemy king
        King k;
        King[] kings = model.getKings();
        if(piece.getColor() != Color.LIGHT_GRAY){
            k = kings[0];
        }else{
            k = kings[1];
        }

        //check for check
        if(k.isInCheck(pieceArray)){
            //check for checkmate
            System.out.println("KING IN CHECK");
            view.setStatusMessage("King at " + ChessView.rowHeaders.get(k.rowLocation) + (k.columnLocation + 1) + " in Check!");
            if(k.isInCheckMate(model)){
                //in checkmate
                System.out.println("KING IN CHECKMATE");
                //send finished game message
            }else {
                //in check but there are possible moves
                //make pop up (possibly using joptionpane that says that they are in check
            }
        }

    }

    public void changePieceLocation(int initialRow, int initialColumn, int toRow, int toColumn){
        System.out.println("InitialRow: " + initialRow + "  InitialColumn " + initialColumn + "  toRow:  " + toRow + "  toColumn:  " + toColumn);
        Piece[][] board = model.getPieces();
        board[toRow][toColumn] = board[initialRow][initialColumn];
        System.out.println("MovingPiece:  " + board[toRow][toColumn].getName());
        view.setLastMove(board[toRow][toColumn].getName() + " From " + ChessView.rowHeaders.get(initialRow) + (initialColumn + 1) + " To " + ChessView.rowHeaders.get(toRow) + (toColumn + 1));
        view.setBoardPosition(board[toRow][toColumn].getName(), toRow, toColumn, true);
        board[initialRow][initialColumn] = null;
        view.setBoardPosition("    ", initialRow, initialColumn, false);
    }

    public void setDefaultButtonFormatting(int[][] positions, Piece[][] pieceArray) {
        for (int square = 0; square < positions.length; square++) {
            if (positions[square] != null) {
                int[] coordinate = positions[square];
                if (pieceArray[coordinate[0]][coordinate[1]] != null) { //catches if a piece that needs to switch back to its color
                    view.setBoardPositionColor(coordinate[0], coordinate[1], pieceArray[coordinate[0]][coordinate[1]].getColor());
                } else {  //catches empty spots that must be disabled and have color removed
                    view.setBoardPositionEnabled(false, coordinate[0], coordinate[1]);
                    view.setBoardPositionColor(coordinate[0], coordinate[1], null);
                }
            }
        }
    }

    public void cleanBoard(int initialRow, int initialColumn){
        int[][] positions = model.getCurrentPiecePositions();
        Piece[][] pieceArray = model.getPieces();

        setDefaultButtonFormatting(positions, pieceArray);
        view.setBoardPositionColor(initialRow, initialColumn, null);
    }

    public void updateGraveyardView(Color color){
        if(color == Color.LIGHT_GRAY){
            view.updateGraveyardWestView(model.getGrayGraveyard());
        }else{  //color == Color.Red
            view.updateGraveyardEastView(model.getRedGraveyard());
        }
    }

    public static void main(String[] args) {
        ChessController chess = new ChessController();
    }

}
