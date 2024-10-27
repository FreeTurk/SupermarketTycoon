package org.supermarkettycoon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Objects;

public class SpriteScreen extends JPanel implements ActionListener {
    private int centerx = (getWidth() / 2);
    private int centery = (getWidth() / 2);

    private int radius = 200;

    private double angle = 0;

    private Timer timer;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        Image centralImage;

        try {
            centralImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("assets/Money.png")));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        int sizex = 200;
        int sizey = 200;

        int relcenterx = centerx - sizex / 2;
        int relcentery = centery - sizey / 2;
        g.drawImage(Objects.requireNonNull(centralImage), relcenterx, relcentery, sizex, sizey, null);


        int x = centerx + (int) (radius * Math.cos(angle)) - 40;
        int y = centery + (int) (radius * Math.sin(angle)) - 40;

        g.drawImage(Objects.requireNonNull(centralImage), x, y, 80, 80, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(getHeight());


        angle += 0.02;
        angle = angle % (2 * Math.PI);
        repaint();
    }

    public SpriteScreen(Globals globals) {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(layout);

        setOpaque(true);
        setBackground(Color.WHITE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                centerx = (getWidth() / 2);
                centery = (getHeight() / 2);
            }
        });

        timer = new Timer(16, this);
        timer.start();
    }
}
