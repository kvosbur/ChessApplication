import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * picture boxes
 */
public class ChessView extends JFrame implements ActionListener{
    private ChessController controller;
    private JButton[][] pieces;
    private Color[] colors = {Color.black, Color.white};
    private JPanel graveWest, graveEast;
    private JLabel status, lastMove;
    public static ArrayList<String> rowHeaders = new ArrayList<String>();

    //constructors (null controller for testing purposes)
    public ChessView(){
        this(null);
    }

    public ChessView(ChessController controller){
        this.controller = controller;

        this.setTitle("Chess");

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        populateRowHeaders();

        //create general JPanels to section off areas
        JPanel graveyard = new JPanel(new FlowLayout());
        JPanel board = new JPanel(new GridLayout(9,9));
        JPanel footer = new JPanel(new FlowLayout());
        JPanel header = new JPanel(new FlowLayout());

        //populate graveyard panel
        graveWest = new JPanel(new BorderLayout());
        JLabel headerGraveWest = new JLabel("Graveyard Gray");
        graveWest.add(headerGraveWest, BorderLayout.NORTH);
        graveEast = new JPanel(new BorderLayout());
        JLabel headerGraveEast = new JLabel("Graveyard Red");
        graveEast.add(headerGraveEast, BorderLayout.NORTH);
        contentPane.add(graveWest, BorderLayout.WEST);
        contentPane.add(graveEast, BorderLayout.EAST);

        //populate footer
        JPanel flowBufferStatus = new JPanel(new FlowLayout(1,0,10));
        flowBufferStatus.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        status = new JLabel("status");
        flowBufferStatus.add(status);
        contentPane.add(flowBufferStatus, BorderLayout.SOUTH);

        //populate header
        JPanel flowBufferLastMove = new JPanel(new FlowLayout(1,0,10));
        flowBufferLastMove.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        lastMove = new JLabel("previous move");
        flowBufferLastMove.add(lastMove);
        contentPane.add(flowBufferLastMove, BorderLayout.NORTH);

        //populate board
        contentPane.add(populateBoard(board), BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //method to create board with JButtons
    public JPanel populateBoard(JPanel board){
        //add column headers
        String text;
        for(int i = 0; i < 9; i++){
            text = "" + i;
            if(i == 0){
                text = " ";
            }
            JPanel border = new JPanel(new BorderLayout());
            JPanel flowBuffer = new JPanel(new FlowLayout(1,10,10));
            JLabel label = new JLabel(text);
            flowBuffer.add(label);
            border.add(flowBuffer, BorderLayout.SOUTH);
            board.add(border);
        }

        this.pieces = new JButton[8][8];
        for(int row = 0; row < pieces.length; row++){
            //add row header
            JPanel buff = new JPanel(new FlowLayout(2, 15, 30));
            JLabel label = new JLabel(rowHeaders.get(row));
            buff.add(label);
            board.add(buff);
            int total = row + 1;
            for(int column = 0; column < pieces[0].length; column++){
                JPanel flowBuffer = new JPanel(new FlowLayout());
                flowBuffer.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
                pieces[row][column] = new JButton("      ");
                pieces[row][column].setEnabled(false);
                pieces[row][column].setBackground(null);
                flowBuffer.setBackground(colors[total % 2]);
                pieces[row][column].addActionListener(this);

                flowBuffer.add(pieces[row][column]);
                board.add(flowBuffer);
                total++;
            }
        }
        board.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
        return board;


    }

    //change Enabled boolean for a JButton at a specific position
    public void setBoardPositionEnabled(boolean status, int rowLocation, int columnLocation){
        pieces[rowLocation][columnLocation].setEnabled(status);
    }

    //change text for a JButton at a specific position(gives idea of pieces moving)
    public void setBoardPosition(String name, int rowLocation, int columnLocation, boolean status){
        pieces[rowLocation][columnLocation].setText(name);
        setBoardPositionEnabled(status, rowLocation, columnLocation);

    }

    //change color for a JButton at location (pieces move colors must go with them)
    //no piece has absence of color
    public void setBoardPositionColor(int rowLocation, int columnLocation, Color color){
        pieces[rowLocation][columnLocation].setBackground(color);
    }

    //catch when JButton clicked (only clickable buttons can be clicked which will be those that have pieces at them)
    public void actionPerformed(ActionEvent e){
        JButton source = (JButton) e.getSource();
        //check to see which button it was that was clicked
        for(int row = 0; row < pieces.length; row++){
            for(int column = 0; column < pieces[0].length; column++){
                if(source == pieces[row][column]){
                    Color color = source.getBackground();
                    if(color == Color.GREEN){  //check if it is a position that the player wants to move to
                        controller.movePieceToLocation(row,column); //moves piece that was last clicked to this position
                    }else {
                        controller.PossiblemovePieceLocations(row, column);  //changes buttons to green background if they are a possible move for this piece
                    }
                }
            }
        }
    }

    public JPanel updateGraveyardGeneral(Piece[] pieceArray){
        JPanel pieceGrid = new JPanel(new GridLayout(8,2));
        for(int pieceIndex = 0; pieceIndex < pieceArray.length; pieceIndex++){
            JLabel pieceName;
            if(pieceArray[pieceIndex] != null){
                pieceName = new JLabel(pieceArray[pieceIndex].getName());
            }else{
                pieceName = new JLabel("    ");
            }
            pieceGrid.add(pieceName);
        }
        return pieceGrid;
    }

    public void updateGraveyardWestView(Piece[] pieceArray){
        graveWest.add(updateGraveyardGeneral(pieceArray), BorderLayout.CENTER);
    }

    public void updateGraveyardEastView(Piece[] pieceArray){
        graveEast.add(updateGraveyardGeneral(pieceArray), BorderLayout.CENTER);
    }

    public void setStatusMessage(String message){
        status.setText(message);
    }

    public void setLastMove(String move){
        lastMove.setText(move);
    }

    public void populateRowHeaders(){
        int unicode = (int) 'A';
        for(int i = 0; i < 8; i++){
            rowHeaders.add("" + ((char) unicode));
            unicode++;
        }
    }

    public static void main(String[] args) {
        ChessView view = new ChessView();
    }
}
