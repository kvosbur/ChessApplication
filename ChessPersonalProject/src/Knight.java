import java.awt.*;

/**
 * Created by kevin on 11/4/2016.
 */
public class Knight extends Piece{

    public Knight(Color color, int rowLocation, int columnLocation){
        super(color,rowLocation,columnLocation);
        this.name = "Knight";
    }


    //make method static so can use it with movePiece with the king class
    public static int[][] checkPositionMove(Piece[][] pieceArray, int[][] positions, int currentRow, int currentColumn, Piece piece){
        if(currentRow >= 0 && currentColumn >= 0 && currentRow <= 7 && currentColumn <= 7){
            if(pieceArray[currentRow][currentColumn] != null) {
                if(pieceArray[currentRow][currentColumn].getColor() != piece.getColor()) {
                    int[] coordinate = {currentRow, currentColumn};
                    addCoordinate(positions, coordinate);
                }
            }else{
                int[] coordinate = {currentRow, currentColumn};
                addCoordinate(positions, coordinate);
            }

        }
        return positions;
    }

    public int[][] movePiece(Piece[][] pieceArray){
        int currentRow,currentColumn;

        int[][] positions = new int[10][];
        //check each individual position by itself using common if statements to check for inbounds of board
        int pieceRow = getRowLocation();
        int pieceColumn = getColumnLocation();

        //check position 1 up 2 left
        currentRow = pieceRow - 1;
        currentColumn = pieceColumn - 2;
        positions = checkPositionMove(pieceArray,positions,currentRow,currentColumn, this);

        //check position 2 up 1 left
        currentRow = pieceRow -2;
        currentColumn = pieceColumn - 1;
        positions = checkPositionMove(pieceArray,positions,currentRow,currentColumn, this);

        //check position 1 up 2 right
        currentRow = pieceRow - 1;
        currentColumn = pieceColumn + 2;
        positions = checkPositionMove(pieceArray,positions,currentRow,currentColumn, this);

        //check position 2 up 1 right
        currentRow = pieceRow - 2;
        currentColumn = pieceColumn + 1;
        positions = checkPositionMove(pieceArray,positions,currentRow,currentColumn, this);

        //check position 1 down 2 left
        currentRow = pieceRow + 1;
        currentColumn = pieceColumn -2;
        positions = checkPositionMove(pieceArray,positions,currentRow,currentColumn, this);

        //check position 2 down 1 left
        currentRow = pieceRow + 2;
        currentColumn = pieceColumn -1;
        positions = checkPositionMove(pieceArray,positions,currentRow,currentColumn, this);

        //check position 1 down 2 right
        currentRow = pieceRow + 1;
        currentColumn = pieceColumn + 2;
        positions = checkPositionMove(pieceArray,positions,currentRow,currentColumn, this);

        //check position 2 down 1 right
        currentRow = pieceRow + 2;
        currentColumn = pieceColumn + 1;
        positions = checkPositionMove(pieceArray,positions,currentRow,currentColumn, this);

        return positions;
    }
}
