import java.awt.*;

/**
 * Created by kevin on 11/4/2016.
 */


public class Queen extends Piece{

    public Queen(Color color, int rowLocation, int columnLocation){
        super(color,rowLocation,columnLocation);
        this.name = "Queen";
    }

    public int[][] movePiece(Piece[][] pieceArray){
        int[][] positions = new int[10][];
        //queen has the moveset of both the rook and bishop so use static methods to give those moves to queen

        //rook moveset
        //north
        positions = addCoordinateArray(positions,Rook.moveNorth(pieceArray,this));

        //south
        positions = addCoordinateArray(positions,Rook.moveSouth(pieceArray,this));

        //west
        positions = addCoordinateArray(positions,Rook.moveWest(pieceArray,this));

        //east
        positions = addCoordinateArray(positions,Rook.moveEast(pieceArray,this));

        //bishop moveset
        positions = addCoordinateArray(positions, Bishop.moveUpperLeftDiagonal(pieceArray,this));

        //upperRight
        positions = addCoordinateArray(positions, Bishop.moveUpperRightDiagonal(pieceArray,this));

        //lowerLeft
        positions = addCoordinateArray(positions, Bishop.moveLowerLeftDiagonal(pieceArray,this));

        //lowerRight
        positions = addCoordinateArray(positions, Bishop.moveLowerRightDiagonal(pieceArray,this));

        return positions;
    }
}
