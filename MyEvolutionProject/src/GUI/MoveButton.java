package GUI;

import Evolution.RectangularMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveButton extends JPanel implements ActionListener{

    public static final int HEIGHT = 100;
    public static final int WIDTH = 300;
    private static JPanel panel;
    private static RectangularMap map;
    private JButton startButton;
    private Object MapThread;

    public MoveButton(JPanel panel, RectangularMap map) {
        this.panel = panel;
        this.map = map;

        startButton = new JButton("Start");
        startButton.addActionListener((ActionListener) this);

        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(startButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == startButton)
            MapThread = new MapThread(this.panel);
    }

}