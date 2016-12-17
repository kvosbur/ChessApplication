import java.awt.*;

/**
 * Created by kevin on 11/4/2016.
 * Finished all functionality for the pawn besides replacing it when it reaches the other side
 */
public class Pawn extends Piece{

    private int initialRow, initialColumn, promotionRow;
    private int direction; //keeps track of whether piece can go down or up the board

    public Pawn(Color color, int rowLocation, int columnLocation, int direction){
        super(color,rowLocation,columnLocation);
        this.name = "Pawn";
        this.initialRow = rowLocation;
        this.initialColumn = columnLocation;
        this.direction = direction;
        if(initialRow == 1){
            promotionRow = 7;
        }else{
            promotionRow = 0;
        }
    }

    public int[][] movePiece(Piece[][] pieces) {
        int[][] positions = new int[5][];
        //check for intial move of 2 squares forward
        if (rowLocation == initialRow) {
            if (pieces[rowLocation + (2 * direction)][columnLocation] == null && pieces[rowLocation + direction][columnLocation] == null) {
                int[] coordinates = {rowLocation + (2 * direction), columnLocation};
                positions = Piece.addCoordinate(positions, coordinates);
            }
            //check for 1 move forward
        }
        if (rowLocation + direction <= 7 && rowLocation + direction >= 0) {
            if (pieces[rowLocation + direction][columnLocation] == null) {
                int[] coordinates = {rowLocation + direction, columnLocation};
                if (positions[0] != null) {
                    positions = Piece.addCoordinate(positions, coordinates);
                } else {
                    positions = Piece.addCoordinate(positions, coordinates);
                }
            }
        }

        //check for diagonal attacks (check each diagnol seperately
        if (columnLocation > 0 && (rowLocation + direction) >= 0 && (rowLocation + direction) <= 7) {
            if (pieces[rowLocation + direction][columnLocation - 1] != null) {
                if (pieces[rowLocation + direction][columnLocation - 1].getColor() != getColor()) {
                    int[] coordinates = {rowLocation + direction, columnLocation - 1};
                    positions = Piece.addCoordinate(positions, coordinates);
                }
            }
        }
        if (columnLocation < 7 && (rowLocation + direction) >= 0 && (rowLocation + direction) <= 7) {
            if (pieces[rowLocation + direction][columnLocation + 1] != null) {
                if (pieces[rowLocation + direction][columnLocation + 1].getColor() != getColor()) {
                    int[] coordinates = {rowLocation + direction, columnLocation + 1};
                    positions = Piece.addCoordinate(positions, coordinates);
                }
            }
        }
        return positions;
    }

    public int[][] hypotheticalPawnAttack(Piece[][] pieces){
        int[][] positions = new int[5][];

        //check for diagonal attacks (check each diagnol seperately
        if (columnLocation > 0 && (rowLocation + direction) >= 0 && (rowLocation + direction) <= 7) {
            if (pieces[rowLocation + direction][columnLocation - 1] == null) {
                int[] coordinates = {rowLocation + direction, columnLocation - 1};
                positions = Piece.addCoordinate(positions, coordinates);
            }
        }
        if (columnLocation < 7 && (rowLocation + direction) >= 0 && (rowLocation + direction) <= 7) {
            if (pieces[rowLocation + direction][columnLocation + 1] == null) {
                int[] coordinates = {rowLocation + direction, columnLocation + 1};
                positions = Piece.addCoordinate(positions, coordinates);
            }
        }
        return positions;
    }

    public boolean goodForPromotion(){
        if(promotionRow == this.rowLocation){
            return true;
        }
        return false;
    }
}
