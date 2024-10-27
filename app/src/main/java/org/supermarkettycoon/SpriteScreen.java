package org.supermarkettycoon;

import com.google.common.eventbus.Subscribe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class SpriteScreen extends JPanel implements ActionListener {
    private int centerx = (getWidth() / 2);
    private int centery = (getWidth() / 2);


    private double customerAngle = 0;
    private double upgradeAngle = 0;

    private Timer timer;

    private Globals _globals;

    private int customerAmount = 0;

    private boolean areCustomersInitialized = false;

    ArrayList<java.net.URL> customersURLs = new ArrayList<java.net.URL>();

    @Subscribe
    public void getNewCustomers(RedrawSpriteEvent rse) {
        customerAmount = rse.customerNumber;

        if (customerAmount == 0) {
            customersURLs.clear();
        }

        areCustomersInitialized = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        Image centralImage;

        try {
            centralImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("assets/money.png")));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        int sizex = 200;
        int sizey = 200;

        int relcenterx = centerx - sizex / 2;
        int relcentery = centery - sizey / 2;
        g.drawImage(Objects.requireNonNull(centralImage), relcenterx, relcentery, sizex, sizey, null);

        int radius = 260;
        if (!areCustomersInitialized)
            for (int i = 0; i < customerAmount; i++) {

                java.net.URL imageResource = Objects.requireNonNull(getClass().getResource("assets/character-1.png"));

                Random random = new Random();
                int option = random.nextInt(0, 2);
                if (option == 0) {
                    imageResource = getClass().getResource("assets/character-1.png");
                } else if (option == 1) {
                    imageResource = getClass().getResource("assets/character-2.png");
                }

                customersURLs.add(imageResource);

            }

        areCustomersInitialized = true;

        int finalRadius = radius;
        customersURLs.forEach(url -> {
            customerAngle += (2 * Math.PI / customerAmount);


            int x = centerx + (int) (finalRadius * Math.cos(customerAngle)) - 40;
            int y = centery + (int) (finalRadius * Math.sin(customerAngle)) - 40;

            try {
                Image licenseImage = ImageIO.read(Objects.requireNonNull(url));
                g.drawImage(licenseImage, x, y, 80, 80, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });


        double oldUpgradeAngel = upgradeAngle;

        radius = 130;
        if (!_globals.licenses.isEmpty() || !_globals.upgrades.isEmpty()) {
            for (int i = 0; i < _globals.licenses.size() + _globals.upgrades.size(); i++) {
                upgradeAngle += (2 * Math.PI / (_globals.licenses.size() + _globals.upgrades.size()));

                int x = centerx + (int) (radius * Math.cos(upgradeAngle)) - 30;
                int y = centery + (int) (radius * Math.sin(upgradeAngle)) - 30;

                String name = "";


                if (i < _globals.licenses.size()) {
                    TLicense license = _globals.licenses.get(i);
                    name = license.name;
                } else {
                    TUpgrade upgrade = _globals.upgrades.get(i - _globals.licenses.size());
                    name = upgrade.name;
                }
                try {
                    Image licenseImage = ImageIO.read(Objects.requireNonNull(getClass().getResource(String.format("assets/%s.png", name))));
                    g.drawImage(licenseImage, x, y, 60, 60, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        upgradeAngle = oldUpgradeAngel;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        customerAngle += 0.01;
        upgradeAngle += 0.01;
        repaint();
    }

    public SpriteScreen(Globals globals) {
        _globals = globals;
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
