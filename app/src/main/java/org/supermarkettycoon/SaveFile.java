package org.supermarkettycoon;

import com.google.gson.Gson;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Scanner;

public class SaveFile extends TSaveFile {

    // Creates a new game and saves it with the specified username
    public void createGame(String username, Globals globals) throws java.io.IOException {
        // Defines the root folder to store save files
        File rootFolder = new File(System.getProperty("user.home") + "/.smtycoon");

        // Checks if the folder exists; if not, attempts to create it
        if (!rootFolder.exists()) {
            boolean isFolderCreated = rootFolder.mkdirs();
        }

        // Defines the save file path based on the username
        File savefile = new File(System.getProperty("user.home") + "/.smtycoon/" + username + ".json");

        // Checks if a save file with the same username already exists
        if (savefile.exists()) {
            // Displays an error dialog if the username already exists
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

        // Creates a new TSaveFile instance to hold save data
        TSaveFile sf = new TSaveFile();
        sf.username = username; 

        // Converts the save file object to JSON format
        Gson gson = new Gson();
        String json = gson.toJson(sf);

        // Writes the JSON data to the save file
        FileWriter writer = new FileWriter(savefile.getPath());
        writer.write(json);
        writer.close();

        loadGame(globals, savefile);
    }

    // Saves the current game data to the specified save file
    public void saveGame(Globals globals) throws java.io.IOException {
        // Defines the save file based on the current username
        File saveFile = new File(System.getProperty("user.home") + "/.smtycoon/" + username + ".json");
        FileWriter writer = new FileWriter(saveFile, false);

        // Creates a TSaveFile object with current game data
        Gson gson = new Gson();
        TSaveFile currentData = new TSaveFile();
        currentData.username = globals.username;
        currentData.day = globals.day;
        currentData.licenses = globals.licenses;
        currentData.money = globals.money;
        currentData.power = globals.power;
        currentData.products = globals.products;
        currentData.upgrades = globals.upgrades;

        // Converts the current game data to JSON and writes it to the save file
        String newSaveData = gson.toJson(currentData, TSaveFile.class);
        writer.write(newSaveData);
        writer.close(); 
    }

    // Loads the game data from the specified save file into the Globals object
    public void loadGame(Globals globals, File saveFile) throws java.io.IOException {
        String saveFileJSON = ""; 

        // Reads the save file line by line and concatenates the data into a JSON string
        Scanner scanner = new Scanner(saveFile);
        while (scanner.hasNextLine()) {
            saveFileJSON += scanner.nextLine();
        }

        // Converts the JSON data to a TSaveFile object
        Gson gson = new Gson();
        TSaveFile sf = gson.fromJson(saveFileJSON, TSaveFile.class);

        // Loads the data from the save file into the Globals object
        globals.username = sf.username;
        globals.day = sf.day;
        globals.licenses = sf.licenses;
        globals.money = sf.money;
        globals.power = sf.power;
        globals.products = sf.products;
        globals.upgrades = sf.upgrades;
    }
}
