package GUI;

import java.awt.Component;

public class MapThread implements Runnable{
    private final Component c;

    public MapThread(Component c)
    {
        this.c = c;
        (new Thread(this, "F")).start();
    }

    public void run()
    {
        while (true) {
            c.repaint();
            try {
                Thread.sleep(150);
            }
            catch (Exception e) {
                System.out.println("Wyjątek!");
            }
        }
    }

}