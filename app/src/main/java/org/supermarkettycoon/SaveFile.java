package org.supermarkettycoon;

import com.google.gson.Gson;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Scanner;

public class SaveFile extends TSaveFile {

    public void createGame(String username, Globals globals) throws java.io.IOException {
        File rootFolder = new File(System.getProperty("user.home") + "/.smtycoon");

        if (!rootFolder.exists()) {
            boolean isFolderCreated = rootFolder.mkdirs();
        }

        File savefile = new File(System.getProperty("user.home") + "/.smtycoon/" + username + ".json");

        if (savefile.exists()) {
            JDialog dialog = new JDialog();
            dialog.setTitle("Error");
            dialog.setModal(true);
            dialog.setLocationRelativeTo(null);
            dialog.setSize(300, 200);

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


        loadGame(globals, savefile);

    }

    public void saveGame(Globals globals) throws java.io.IOException {
        File saveFile = new File(System.getProperty("user.home") + "/.smtycoon/" + username + ".json");
        FileWriter writer = new FileWriter(saveFile, false);

        Gson gson = new Gson();
        TSaveFile currentData = new TSaveFile();
        currentData.username = globals.username;
        currentData.day = globals.day;
        currentData.licenses = globals.licenses;
        currentData.money = globals.money;
        currentData.power = globals.power;
        currentData.products = globals.products;
        currentData.upgrades = globals.upgrades;


        String newSaveData = gson.toJson(currentData, TSaveFile.class);
        writer.write(newSaveData);
        writer.close();
    }

    public void loadGame(Globals globals, File saveFile) throws java.io.IOException {
        String saveFileJSON = "";

        Scanner scanner = new Scanner(saveFile);

        while (scanner.hasNextLine()) {
            saveFileJSON += scanner.nextLine();
        }

        Gson gson = new Gson();

        TSaveFile sf = gson.fromJson(saveFileJSON, TSaveFile.class);

        globals.username = sf.username;
        globals.day = sf.day;
        globals.licenses = sf.licenses;
        globals.money = sf.money;
        globals.power = sf.power;
        globals.products = sf.products;
        globals.upgrades = sf.upgrades;


    }
}
