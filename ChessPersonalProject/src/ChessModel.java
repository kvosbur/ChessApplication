import java.awt.*;

/**
 * Created by kevin on 11/4/2016.
 */
public class ChessModel {
    /*
    Holds all information for the chess game, position of the Pieces
    checks after every move to see if there is a winner
     */

    private ChessController controller;
    private Piece[][] pieces;
    private Piece currentPiece;
    private int[][] currentPiecePositions;
    private Piece[] grayGraveyard, redGraveyard;
    public static Color[] players = {Color.LIGHT_GRAY, Color.RED};
    private int playerTurn;
    private King[] kings;

    public ChessModel(ChessController controller){
        this.controller = controller;
        this.grayGraveyard = new Piece[16];
        this.redGraveyard = new Piece[16];
        this.playerTurn = 0;

    }

    public void setKings(King[] kings){
        this.kings = kings;
    }

    public King[] getKings(){
        return this.kings;
    }

    public Color getPlayerTurn(){
        return players[playerTurn];
    }

    public void changePlayerTurn(){
        this.playerTurn = (playerTurn + 1) % 2;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setCurrentPiece(Piece piece){
        currentPiece = piece;
    }

    public Piece getCurrentPiece(){
        return this.currentPiece;
    }

    public void setCurrentPiecePositions(int[][] currentPiecePositions){
        this.currentPiecePositions = currentPiecePositions;
    }

    public int[][] getCurrentPiecePositions(){
        return this.currentPiecePositions;
    }

    public Piece[] addtoGraveyardArray(Piece piece, Piece[] grave){
        for(int item = 0; item < grave.length; item++){
            if(grave[item] == null){
                grave[item] = piece;
                System.out.println("added to graveyard " + piece.getName());
                controller.updateGraveyardView(piece.getColor());
                return grave;
            }
        }
        return grave;
    }

    public void addToGraveyard(Piece piece){
        //decide which graveyard the piece needs to be added to
        Color color = piece.getColor();
        if(color == Color.LIGHT_GRAY){
            this.grayGraveyard = addtoGraveyardArray(piece, grayGraveyard);
        }else{  //when color == Color.RED
            this.redGraveyard = addtoGraveyardArray(piece, redGraveyard);
        }
    }

    public Piece[] getGrayGraveyard(){
        return this.grayGraveyard;
    }

    public Piece[] getRedGraveyard(){
        return this.redGraveyard;
    }

    public static int[][] accumulatePlayerPossibleMoves(Color playerColor, Piece[][] pieceArray){
        int[][] possibleMoves = new int[10][];

        for(int row = 0; row < 8; row++){
            for(int column = 0; column < 8; column++){
                if(pieceArray[row][column] != null){
                    if(pieceArray[row][column].getColor() == playerColor){
                        if(pieceArray[row][column] instanceof Pawn){
                            Pawn current = (Pawn) pieceArray[row][column];
                            possibleMoves = Piece.addCoordinateArray(possibleMoves, current.hypotheticalPawnAttack(pieceArray));
                            //testing
                            if(row == 3 && column == 4){
                                System.out.println("Where pawn can attack");
                                System.out.println(Piece.listToString(current.hypotheticalPawnAttack(pieceArray)));
                            }
                        }else {
                            possibleMoves = Piece.addCoordinateArray(possibleMoves, pieceArray[row][column].movePiece(pieceArray));
                        }
                    }
                }
            }
        }
        return possibleMoves;

    }

    //checks if given move happens whether king would be put in check
    public boolean hypotheticalCheck(Piece piece, int targetRow, int targetColumn){
        King k;
        //find same color king
        if(piece.getColor() == Color.LIGHT_GRAY){
            k = kings[0];
        }else{
            k = kings[1];
        }

        //make copy of piece[][] to run hypothetical model on
        Piece[][] p = this.independentCopy(pieces);

        //make hypothetical move
        p[targetRow][targetColumn] = piece;
        p[piece.getRowLocation()][piece.getColumnLocation()] = null;

        //check if king results in check and return matching boolean statement
        if(k.isInCheck(p)){
            return true;
        }else{
            return false;
        }

    }

    public boolean hypotheticalCheckMate(King k){
        int[][] positions;
        boolean status;

        //loop through board to find pieces same color as King parameter
        for(int rowIndex = 0; rowIndex < pieces.length; rowIndex++){
            for(int colIndex = 0; colIndex < pieces[0].length; colIndex++){
                Piece current = pieces[rowIndex][colIndex];
                if(current != null && current != k){
                    if(current.getColor() == k.getColor()){
                        positions = current.movePiece(pieces);
                        //loop through possible moves and run hypothetical check on all moves(looking for one that is false)
                        for(int[] move: positions){
                            if(move != null) {
                                status = this.hypotheticalCheck(current,move[0],move[1]);
                                if(!status){
                                    return false;
                                }
                            }
                        }

                    }
                }
            }
        }
        return true;

    }

    public String boardToString(){
        String fullString = "";

        for(int row = 0; row < pieces.length; row++){
            for(int column = 0; column < pieces[0].length; column++){
                if(pieces[row][column] == null){
                    fullString += " null ";
                }else{
                    fullString += " " + pieces[row][column].getName() + " ";
                }
            }
            fullString += "\n";
        }
        return fullString;
    }

    public Piece[][] independentCopy(Piece[][] pieceArray){
        Piece[][] p = new Piece[8][8];

        for(int row = 0; row < pieceArray.length;row++){
            for(int column = 0; column < pieceArray[0].length; column++){
                p[row][column] = pieceArray[row][column];
            }
        }
        return p;
    }

}
