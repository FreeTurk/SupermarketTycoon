package org.supermarkettycoon;

import javax.swing.*;
import java.awt.*;

public class SpriteScreen extends JPanel {
  public SpriteScreen() {
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    setLayout(layout);

    setBackground(Color.BLUE);
  }
}
