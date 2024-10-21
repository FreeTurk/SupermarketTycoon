package app.src.main.java.org.supermarkettycoon;
import javax.swing.*;
import java.awt.*;

/**
 * Sidebar
 */
public class Sidebar {

  public JPanel initialize_page() {

    JButton stocksPageButton = new JButton("Stocks");

    JPanel sidebar_panel = new JPanel();
    sidebar_panel.setLayout(new BoxLayout(sidebar_panel, BoxLayout.Y_AXIS));

    sidebar_panel.add(stocksPageButton);

    return sidebar_panel;
  }

}