import java.awt.*;
import java.util.Arrays;

/**
 * Created by kevin on 11/4/2016.
 */
public class Piece {
    /*
    Parent class for all pieces that stores its color
     */
    protected String name;
    protected int rowLocation, columnLocation;
    protected Color color;

    public Piece(Color color, int rowLocation, int columnLocation){
        this.color = color;
        this.rowLocation = rowLocation;
        this.columnLocation = columnLocation;
    }

    public void setRowLocation(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public void setColumnLocation(int columnLocation) {
        this.columnLocation = columnLocation;
    }

    public String getName(){
        return this.name;
    }

    public int getRowLocation(){
        return this.rowLocation;
    }

    public int getColumnLocation(){
        return this.columnLocation;
    }

    public int[][] movePiece(Piece[][] pieces){
        return new int[0][0];
    }

    public String toString(){
        return this.name;
    }

    public Color getColor(){
        return this.color;
    }

    public static int[][] addCoordinate(int[][] coordinates, int[] point){
        //loop through items until you find a null position
        for(int item = 0; item < coordinates.length; item++){
            if(coordinates[item] == null){
                coordinates[item] = point;
                return coordinates;
            }
        }
        //if null item not found increase length of array and then add it
        coordinates = Arrays.copyOf(coordinates, coordinates.length + 10);
        coordinates[coordinates.length - 10] = point;
        return coordinates;
    }

    public static int[][] addCoordinateArray(int[][] coordinates, int[][] points){

        for(int item = 0; item < points.length; item++){
            if(points[item] != null){
                coordinates = addCoordinate(coordinates, points[item]);
            }
        }
        return coordinates;
    }

    public static Color getEnemyColor(Piece piece){
        Color enemyColor;
        if(ChessModel.players[0] == piece.getColor()){
            enemyColor = ChessModel.players[1];
        } else{
            enemyColor = ChessModel.players[0];
        }
        return enemyColor;
    }

    public static boolean checkIfEqual(int[] a, int[] b){
        if(a.length == b.length) {
            for (int index = 0; index < a.length; index++){
                if(a[index] != b[index]){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static int[][] removeCoordinate(int[] point, int[][]coordinates){

        for(int index = 0; index < coordinates.length;index++){
            if(Piece.checkIfEqual(point, coordinates[index])){
                //shift all in array to left starting at index
                for(int newIndex = index; newIndex < coordinates.length - 1;newIndex++){
                    coordinates[newIndex] = coordinates[newIndex + 1];
                }
                coordinates[coordinates.length - 1] = null;
                return coordinates;
            }
        }
        return coordinates;
    }

    public static String listToString(int[][] positions){
        String returnString = "";
        for(int[] coord: positions){
            if(coord != null) {
                for (int pos : coord) {
                    returnString += pos;
                }
                returnString += "\n";
            }else{
                return returnString;
            }
        }
        return returnString;
    }

}
