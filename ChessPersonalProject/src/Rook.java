import java.awt.*;

/**
 * Created by kevin on 11/4/2016.
 * move functionality finished
 */
public class Rook extends Piece{

    public Rook(Color color, int rowLocation, int columnLocation){
        super(color,rowLocation,columnLocation);
        this.name = "Rook";
    }

    public static int[][] moveNorth(Piece[][] pieceArray, Piece piece){
        int[][] positions = new int[10][];
        //north
        if(piece.getRowLocation() - 1 >= 0) {  //check to see if moving north is within board
            for (int row = piece.getRowLocation() - 1; row >= 0; row--){  //use getRowLocation to get position on board to start at
                if(pieceArray[row][piece.getColumnLocation()] != null){
                    if(piece.getColor() != pieceArray[row][piece.getColumnLocation()].getColor()){ //means that it is other player's piece
                        int[] coordinate = {row, piece.getColumnLocation()};
                        positions = addCoordinate(positions, coordinate);
                    }
                    row = -10;
                }else{  //empty position in which you can move to
                    int[] coordinate = {row, piece.getColumnLocation()};
                    positions = addCoordinate(positions, coordinate);
                }
            }
        }
        return positions;
    }

    public static int[][] moveSouth(Piece[][] pieceArray, Piece piece){
        int[][] positions = new int[10][];

        if(piece.getRowLocation() + 1 <= 7) {  //check to see if moving south is within board
            for (int row = piece.getRowLocation() + 1; row <= 7; row++){  //use getRowLocation to get position on board to start at
                if(pieceArray[row][piece.getColumnLocation()] != null){
                    if(piece.getColor() != pieceArray[row][piece.getColumnLocation()].getColor()){ //means that it is other player's piece
                        int[] coordinate = {row, piece.getColumnLocation()};
                        positions = addCoordinate(positions, coordinate);
                    }
                    row = 10;
                }else{  //empty position in which you can move to
                    int[] coordinate = {row, piece.getColumnLocation()};
                    positions = addCoordinate(positions, coordinate);
                }
            }
        }
        return positions;
    }

    public static int[][] moveWest(Piece[][] pieceArray, Piece piece){
        int[][] positions = new int[10][];

        if(piece.getColumnLocation() - 1 >= 0) {  //check to see if moving west is within board
            for (int column = piece.getColumnLocation() - 1; column >= 0; column--){  //use getRowLocation to get position on board to start at
                if(pieceArray[piece.getRowLocation()][column] != null){
                    if(piece.getColor() != pieceArray[piece.getRowLocation()][column].getColor()){ //means that it is other player's piece
                        int[] coordinate = {piece.getRowLocation(), column};
                        positions = addCoordinate(positions, coordinate);
                    }
                    column = -10;
                }else{  //empty position in which you can move to
                    int[] coordinate = {piece.getRowLocation(), column};
                    positions = addCoordinate(positions, coordinate);
                }
            }
        }
        return positions;
    }

    public static int[][] moveEast(Piece[][] pieceArray, Piece piece){
        int[][] positions = new int[10][];

        if(piece.getColumnLocation() + 1 <= 7) {  //check to see if moving east is within board
            for (int column = piece.getColumnLocation() + 1; column <= 7; column++){  //use getRowLocation to get position on board to start at
                if(pieceArray[piece.getRowLocation()][column] != null){
                    if(piece.getColor() != pieceArray[piece.getRowLocation()][column].getColor()){ //means that it is other player's piece
                        int[] coordinate = {piece.getRowLocation(), column};
                        positions = addCoordinate(positions, coordinate);
                    }
                    column = 10; //end loop when piece found
                }else{  //empty position in which you can move to
                    int[] coordinate = {piece.getRowLocation(), column};
                    positions = addCoordinate(positions, coordinate);
                }
            }
        }
        return positions;
    }

    public int[][] movePiece(Piece[][] pieceArray){
        int[][] positions = new int[10][];

        //split rook motion into 4 portions north south west east, treat each separately and add their possible moves to overall move set
        //north
        positions = addCoordinateArray(positions, moveNorth(pieceArray, this));

        //south
        positions = addCoordinateArray(positions, moveSouth(pieceArray,this));

        //west
        positions = addCoordinateArray(positions, moveWest(pieceArray,this));

        //east
        positions = addCoordinateArray(positions, moveEast(pieceArray,this));

        return positions;
    }
}
