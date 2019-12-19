package GUI;

import Evolution.RectangularMap;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    final RectangularMap map;
    final int mapWidth;
    final int mapHeight;
    final int scale;
    public MyFrame (RectangularMap map){
        super("Game of evolution");
        this.map = map;
        this.mapWidth = this.map.width;
        this.mapHeight = this.map.height;
        this.scale = 800/Math.max(mapWidth, mapHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);
        setSize(mapWidth*scale + scale,mapHeight*scale + 100);

        MapPanel panel = new MapPanel(map,scale);
        add(panel);

        MoveButton moveButton = new MoveButton(panel, map);
        add(moveButton);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setLocation(screenWidth / 4, screenHeight / 4 - 200);
        setLayout(new FlowLayout());

    }
}
