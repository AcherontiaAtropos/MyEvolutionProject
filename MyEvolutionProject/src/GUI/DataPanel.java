package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataPanel extends JPanel implements ActionListener {
    JButton widthButton = new JButton("Submit");
    JLabel widthLabel = new JLabel("Type width");
    JTextField widthField = new JTextField(16);
    int widthData = 0;

    public void DataPanel(){
        this.add(widthButton);
        this.add(widthLabel);
        this.add(widthField);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == widthButton) widthData = Integer.parseInt(widthField.getText());
        this.setVisible(false);
    }
}
