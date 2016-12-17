import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by kevin on 12/17/2016.
 */
public class Menu extends JFrame implements ActionListener{

    public Menu(){
        Container contentPane = this.getContentPane();

        contentPane.setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(1,2,20,0));
        JPanel timedBuffer = new JPanel(new FlowLayout());
        JButton startTimedGame = new JButton("Start Timed Game");
        startTimedGame.addActionListener(this);
        timedBuffer.add(startTimedGame);

        JPanel untimedBuffer = new JPanel(new FlowLayout());
        JButton startUntimedGame = new JButton("Start Untimed Game");
        startUntimedGame.addActionListener(this);
        untimedBuffer.add(startUntimedGame);

        JPanel northBuffer = new JPanel(new FlowLayout(1,40,90));
        JPanel southBuffer = new JPanel(new FlowLayout(1,50,40));

        grid.add(timedBuffer);
        grid.add(untimedBuffer);

        contentPane.add(grid, BorderLayout.CENTER);
        contentPane.add(northBuffer, BorderLayout.NORTH);
        contentPane.add(southBuffer, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
        this.setSize(350, 500);

    }

    public void actionPerformed(ActionEvent e){

    }

    public static void main(String[] args) {
        Menu menu = new Menu();
    }
}
