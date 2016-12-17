import java.awt.*;

/**
 * Created by kevin on 11/4/2016.
 */
public class Bishop extends Piece{

    public Bishop(Color color, int rowLocation, int columnLocation){
        super(color,rowLocation,columnLocation);
        this.name = "Bishop";
    }

    public int[][] movePiece(Piece[][] pieceArray){
        int[][] positions = new int[10][];

        //split bishop movement to four directions upperLeft, upperRight, lowerLeft, lowerRight
        //upperLeft
        positions = addCoordinateArray(positions, moveUpperLeftDiagonal(pieceArray,this));

        //upperRight
        positions = addCoordinateArray(positions, moveUpperRightDiagonal(pieceArray,this));

        //lowerLeft
        positions = addCoordinateArray(positions, moveLowerLeftDiagonal(pieceArray,this));

        //lowerRight
        positions = addCoordinateArray(positions, moveLowerRightDiagonal(pieceArray,this));

        return positions;
    }

    public static int[][] moveUpperLeftDiagonal(Piece[][] pieceArray, Piece piece){
        int[][] positions = new int[10][];

        if(piece.getColumnLocation() > 0 && piece.getRowLocation() > 0){
            for(int row = piece.getRowLocation() - 1, column = piece.getColumnLocation() - 1; row >= 0 && column >= 0; row--,column--){
                if(pieceArray[row][column] != null){
                    if(piece.getColor() != pieceArray[row][column].getColor()) {
                        int[] coordinate = {row, column};
                        addCoordinate(positions, coordinate);
                    }
                    row = -5;
                }else{
                    int[] coordinate = {row, column};
                    addCoordinate(positions,coordinate);
                }
            }
        }
        return positions;
    }

    public static int[][] moveUpperRightDiagonal(Piece[][] pieceArray, Piece piece){
        int[][] positions = new int[10][];

        if(piece.getColumnLocation() < 7 && piece.getRowLocation() > 0){
            for(int row = piece.getRowLocation() - 1, column = piece.getColumnLocation() + 1; row >= 0 && column <= 7; row--,column++){
                if(pieceArray[row][column] != null){
                    if(piece.getColor() != pieceArray[row][column].getColor()) {
                        int[] coordinate = {row, column};
                        addCoordinate(positions, coordinate);
                    }
                    row = -5;
                }else{
                    int[] coordinate = {row, column};
                    addCoordinate(positions,coordinate);
                }
            }
        }
        return positions;
    }

    public static int[][] moveLowerLeftDiagonal(Piece[][] pieceArray, Piece piece){
        int[][] positions = new int[10][];

        if(piece.getColumnLocation() > 0 && piece.getRowLocation() < 7){
            for(int row = piece.getRowLocation() + 1, column = piece.getColumnLocation() - 1; row <= 7 && column >= 0; row++,column--){
                if(pieceArray[row][column] != null){
                    if(piece.getColor() != pieceArray[row][column].getColor()) {
                        int[] coordinate = {row, column};
                        addCoordinate(positions, coordinate);
                    }
                    row = 10;
                }else{
                    int[] coordinate = {row, column};
                    addCoordinate(positions,coordinate);
                }
            }
        }
        return positions;
    }

    public static int[][] moveLowerRightDiagonal(Piece[][] pieceArray, Piece piece){
        int[][] positions = new int[10][];

        if(piece.getColumnLocation() < 7 && piece.getRowLocation() < 7){
            for(int row = piece.getRowLocation() + 1, column = piece.getColumnLocation() + 1; row <= 7 && column <= 7; row++,column++){
                if(pieceArray[row][column] != null){
                    if(piece.getColor() != pieceArray[row][column].getColor()) {
                        int[] coordinate = {row, column};
                        addCoordinate(positions, coordinate);
                    }
                    row = 10;
                }else{
                    int[] coordinate = {row, column};
                    addCoordinate(positions,coordinate);
                }
            }
        }
        return positions;
    }
}
