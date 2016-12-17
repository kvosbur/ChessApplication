import java.awt.*;

/**
 * Created by kevin on 11/4/2016.
 */
public class King extends Piece{

    public King(Color color, int rowLocation, int columnLocation){
        super(color,rowLocation,columnLocation);
        this.name = "King";
    }

    //key difference with king is that he can not move into check
    public int[][] movePiece(Piece[][] pieceArray){

        int[][] positions = new int[10][];
        // check every possible position around the king for possible moves
        int currentRow = getRowLocation();
        int currentColumn = getColumnLocation();

        //top left
        positions = Knight.checkPositionMove(pieceArray, positions,currentRow - 1, currentColumn - 1, this);

        //top
        positions = Knight.checkPositionMove(pieceArray, positions,currentRow - 1, currentColumn, this);

        //top right
        positions = Knight.checkPositionMove(pieceArray, positions,currentRow - 1, currentColumn + 1, this);

        //right
        positions = Knight.checkPositionMove(pieceArray, positions,currentRow, currentColumn + 1, this);

        //bottom right
        positions = Knight.checkPositionMove(pieceArray, positions,currentRow + 1, currentColumn + 1, this);

        //bottom
        positions = Knight.checkPositionMove(pieceArray, positions,currentRow + 1, currentColumn, this);

        //bottom left
        positions = Knight.checkPositionMove(pieceArray, positions,currentRow + 1, currentColumn - 1, this);

        //left
        positions = Knight.checkPositionMove(pieceArray, positions,currentRow, currentColumn - 1, this);

        //System.out.println("king move piece : ");
        //System.out.println(Piece.listToString(positions));

        return positions;
    }

    public int[][] restrictMovementCheck(int[][] positions, Piece[][] pieceArray){
        //figure out other team's color

        Color enemyColor;
        if(ChessModel.players[0] == this.getColor()){
            enemyColor = ChessModel.players[1];
        } else{
            enemyColor = ChessModel.players[0];
        }

        //get other team's move set
        int[][] moves = ChessModel.accumulatePlayerPossibleMoves(enemyColor,pieceArray);

        //limit king's moves so that he can not move into check
        for(int coordinates = 0; coordinates < moves.length; coordinates++){
            if(moves[coordinates] != null) {
                for (int position = 0; position < positions.length; position++) {
                    if (positions[position] != null) {
                        boolean coordinatesMatch = equateCoordinates(moves[coordinates], positions[position]);
                        if (coordinatesMatch) {
                            System.out.println("mathched pair " + moves[coordinates][0] + moves[coordinates][1] + "  " + positions[position][0] + positions[position][1]);
                            positions = removeElement(positions, positions[position]);
                        }
                    }
                }
            }
        }

        return positions;
    }

    public static boolean equateCoordinates(int[] coord1, int[] coord2){
        if(coord1[0] == coord2[0]){
            if(coord1[1] == coord2[1]){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public static int[][] removeElement(int[][] positions, int[] coordinate){
        for(int index = 0; index < positions.length; index++) {
            if (positions[index] == coordinate) {
                positions[index] = null;
                for (int shiftIndex = index + 1; shiftIndex < positions.length; shiftIndex++) {
                    positions[shiftIndex - 1] = positions[shiftIndex];
                }
                positions[positions.length - 1] = null;
            }
        }
        return positions;
    }

    public boolean isInCheck(Piece[][] pieceArray){
        //System.out.println("Checking for Check!");
        //figure out other team's color
        Color enemyColor = Piece.getEnemyColor(this);

        int[][] moves = ChessModel.accumulatePlayerPossibleMoves(enemyColor,pieceArray);
        int[] currentPosition = {this.getRowLocation(), this.getColumnLocation()};

        for(int[] coords: moves){
            if(coords != null) {
                if (King.equateCoordinates(coords, currentPosition)) {
                    //System.out.println("Returning True");
                    return true;
                }
            }
        }
        //System.out.println("Returning False");
        return false;
    }

    public boolean isInCheckMate(ChessModel model){
        //check for possible moves of king first(no need to use more processing power doing entire team when the king is able to move himself
        Piece[][] pieceArray = model.getPieces();
        int[][] positions = this.movePiece(pieceArray);
        positions = this.restrictMovementCheck(positions,pieceArray);

        if(positions[0] == null){ // has no operable moves
            boolean checkMate;
            //must check if any moves of other moves can get king out of check
            //try all moves of same team and check for hypothetical check (minus the king)
            checkMate = model.hypotheticalCheckMate(this);
            if(checkMate){
                return true;
            }
            return false;

        }else{
            return false;
        }
    }
}
