import javax.swing.*;

public class Stocks {
  public void initialize_page() {
    // Create a new JFrame object
    JFrame frame = new JFrame();

    // Set the size of the JFrame
    frame.setSize(1920, 1200);

    // Set the layout of the JFrame
    frame.setLayout(null);

    // Set the visibility of the JFrame
    frame.setVisible(true);

    // Set the resizability of the JFrame
    frame.setResizable(true);

    String data[][] = { { "101", "Amit", "670000" },
        { "102", "Jai", "780000" },
        { "101", "Sachin", "700000" } };

    String column[] = { "ID", "NAME", "SALARY" };

    // Create a new JTable object
    JTable table = new JTable(data, column);
    table.setBounds(0, 0, 1920, 1200);

    frame.add(table);
  }
}
