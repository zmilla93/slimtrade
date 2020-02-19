package com.slimtrade.core.managers;

import com.google.gson.*;
import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.OverlaySaveFile;
import com.slimtrade.core.SaveSystem.SaveFile;
import com.slimtrade.core.SaveSystem.ScannerSaveFile;
import com.slimtrade.core.SaveSystem.StashSaveFile;
import com.slimtrade.gui.scanner.ScannerMessage;

import java.io.*;
import java.lang.reflect.Type;
import java.time.*;

public class SaveManager {

    // Public Info
    public final String savePath;
    public final String stashSavePath;
    public final String overlaySavePath;
    public final String scannerSavePath;
    public final String saveDirectory;
    public SaveFile saveFile = new SaveFile();
    public StashSaveFile stashSaveFile = new StashSaveFile();
    public OverlaySaveFile overlaySaveFile = new OverlaySaveFile();
    public ScannerSaveFile scannerSaveFile = new ScannerSaveFile();

    //Internal
    private final String folderWin = "SlimTrade";
    private final String folderOther = ".slimtrade";
    private final String fileName = "settings.json";
    private final String stashFileName = "stash.json";
    private final String overlayFileName = "overlay.json";
    private final String scannerFileName = "scanner.json";

    private boolean validSavePath = false;

    // File Stuff
    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private Gson gson;

    public SaveManager() {

        // Set save directory
        String os = (System.getProperty("os.name")).toUpperCase();
        if (os.contains("WIN")) {
            saveDirectory = System.getenv("LocalAppData") + File.separator + folderWin;
        } else {
            saveDirectory = System.getProperty("user.home") + File.separator + folderOther;
        }
        savePath = saveDirectory + File.separator + fileName;
        stashSavePath = saveDirectory + File.separator + stashFileName;
        overlaySavePath = saveDirectory + File.separator + overlayFileName;
        scannerSavePath = saveDirectory + File.separator + scannerFileName;
        File saveDir = new File(saveDirectory);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        if (saveDir.exists()) {
            validSavePath = true;
        }

        // Gson instance with added support for LocalDateTime
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
//                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
            }
        }).create();
//        System.out.println("Save Directory : " + saveDirectory);
//        System.out.println("Save path : " + savePath);
//        loadFromDisk();
//        saveToDisk();
    }

    public void loadFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(savePath));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            saveFile = gson.fromJson(builder.toString(), SaveFile.class);
            if (saveFile == null) {
                saveFile = new SaveFile();
            }
        } catch (JsonSyntaxException e1) {
            saveFile = new SaveFile();
            validateClientPath();
            System.out.println("Corrupted save file!");
            return;
        } catch (IOException e2) {
            saveFile = new SaveFile();
            validateClientPath();
            System.out.println("IO Error with save file!");
            return;
        }
        validateClientPath();
    }

    public void saveToDisk() {
        try {
            fw = new FileWriter(savePath);
            fw.write(gson.toJson(saveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void loadStashFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(stashSavePath));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            stashSaveFile = gson.fromJson(builder.toString(), StashSaveFile.class);
            if (stashSaveFile == null) {
                stashSaveFile = new StashSaveFile();
            }
        } catch (JsonSyntaxException e1) {
            stashSaveFile = new StashSaveFile();
            System.out.println("Corrupted save file!");
            return;
        } catch (IOException e2) {
            stashSaveFile = new StashSaveFile();
            System.out.println("IO Error with save file!");
            return;
        }
    }

    public void saveStashToDisk() {
        try {
            fw = new FileWriter(stashSavePath);
            fw.write(gson.toJson(stashSaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void loadOverlayFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(overlaySavePath));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            overlaySaveFile = gson.fromJson(builder.toString(), OverlaySaveFile.class);
            if (overlaySaveFile == null) {
                overlaySaveFile = new OverlaySaveFile();
            }
        } catch (JsonSyntaxException e1) {
            overlaySaveFile = new OverlaySaveFile();
            System.out.println("Corrupted save file!");
            return;
        } catch (IOException e2) {
            overlaySaveFile = new OverlaySaveFile();
            System.out.println("IO Error with save file!");
            return;
        }
    }

    public void saveOverlayToDisk() {
        try {
            fw = new FileWriter(overlaySavePath);
            fw.write(gson.toJson(overlaySaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void loadScannerFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(scannerSavePath));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            scannerSaveFile = gson.fromJson(builder.toString(), ScannerSaveFile.class);
            if (scannerSaveFile == null) {
                scannerSaveFile = new ScannerSaveFile();
            }
        } catch (JsonSyntaxException e1) {
            scannerSaveFile = new ScannerSaveFile();
            System.out.println("Corrupted save file!");
            return;
        } catch (IOException e2) {
            scannerSaveFile = new ScannerSaveFile();
            saveScannerToDisk();
//            System.out.println("IO Error with save file!");
            return;
        }
    }

    public void saveScannerToDisk() {
        try {
            fw = new FileWriter(scannerSavePath);
            fw.write(gson.toJson(scannerSaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public int validateClientPath() {
        int clientCount = 0;
        String clientPath = saveFile.clientPath;
        if (clientPath != null) {
            File file = new File(clientPath);
            if (file.exists() && file.isFile()) {
                return 1;
            }
        }
        String[] commonDrives = {"C", "D", "E", "F"};
        String clientSteamStub = ":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt";
        String clientStandAloneStub = ":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt";
        for (String drive : commonDrives) {
            File clientFile = new File(drive + clientSteamStub);
            if (clientFile.exists() && clientFile.isFile()) {
                saveFile.clientPath = drive + clientSteamStub;
                clientCount++;
                App.debugger.log("Valid client path found on " + drive + " drive. (Steam)");
            }
        }
        for (String drive : commonDrives) {
            File clientFile = new File(drive + clientStandAloneStub);
            if (clientFile.exists() && clientFile.isFile()) {
                saveFile.clientPath = drive + clientStandAloneStub;
                clientCount++;
                App.debugger.log("Valid client path found on " + drive + " drive. (Stand Alone)");
            }
        }
        return clientCount;
    }


}
