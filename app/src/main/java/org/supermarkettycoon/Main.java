package app.src.main.java.org.supermarkettycoon;
import javax.swing.*;
import java.awt.*;

class Main extends JFrame {

  public Main() {
    // Creates the layout for the window
    GridBagLayout layout = new GridBagLayout();
    layout.columnWeights = new double[] { 0.0, 1.0 };
    layout.rowWeights = new double[] { 1.0 };
    layout.columnWidths = new int[] { 400, 0 };

    // Creates the panel that will hold the current page
    JPanel currentPagePanel = new JPanel();
    CardLayout cardLayout = new CardLayout();
    currentPagePanel.setLayout(cardLayout);

    // Creates the sidebar
    Sidebar sidebar = new Sidebar();
    Stocks stocks = new Stocks();

    currentPagePanel.add("stocks", stocks);

    // Sets the layout of the main window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(layout);
    setSize(1920, 1200);

    // Adds the sidebar to the main window
    add(sidebar.initialize_page());
    add(currentPagePanel);

    // Make the window visible
    setLocationRelativeTo(null);
    setVisible(true);

  }

  public static void main(String[] args) {
    new Main();
  }

}