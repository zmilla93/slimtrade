package com.slimtrade.core.managers;

import com.slimtrade.App;

import java.io.File;

public class SetupManager {

    public volatile boolean setupRunning = false;

    public static boolean clientSetupCheck = false;
    public static int clientCount = 0;
    public static boolean characterNameCheck = false;
    public static boolean stashOverlayCheck = false;

    public static boolean isSetupRequired() {
        boolean needsSetup = false;
        int count = App.saveManager.validateClientPath();
        File file = new File(App.saveManager.saveFile.clientPath);
        if(count != 1 || !file.exists() || !file.isFile()) {
            SetupManager.clientSetupCheck = true;
            needsSetup = true;
        }
        if(App.saveManager.saveFile.characterName == null || App.saveManager.saveFile.characterName.equals("")) {
            SetupManager.characterNameCheck = true;
            needsSetup = true;
        }
        if(!App.saveManager.stashSaveFile.initialized) {
            SetupManager.stashOverlayCheck = true;
            needsSetup = true;
        }
        return needsSetup;
    }

}
