package org.supermarkettycoon;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;

public class SaveFile {

    public void newGame() {

    }

    public void createGame(String username) throws java.io.IOException {
        File rootFolder = new File(System.getProperty("user.home")+ "/.smtycoon");

        if (!rootFolder.exists()) {
            boolean isFolderCreated = rootFolder.mkdirs();
        }

        File savefile = new File(System.getProperty("user.home")+ "/.smtycoon/" + username + ".json");

        if (savefile.exists()) {
            JDialog dialog = new JDialog();
            dialog.setTitle("Error");
            dialog.setModal(true);
            dialog.setLocationRelativeTo(null);
            dialog.setSize(300,200);

            JLabel label = new JLabel("This username already exists!");
            dialog.add(label);

            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            return;
        } else {
            boolean isSaveFileCreated = savefile.createNewFile();
        }

        TSaveFile sf = new TSaveFile();
        sf.username = username;

        Gson gson = new Gson();

        String json = gson.toJson(sf);

        FileWriter writer = new FileWriter(savefile.getPath());
        writer.write(json);
        writer.close();
    }
}
