package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.enums.QuickPasteSetting;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * Adds a flavor change listener to the system clipboard to monitor for trade messages being copied.
 */

public class ClipboardManager implements ClipboardOwner {

    private String lastMessage;
    private volatile boolean disabled = false;
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public ClipboardManager() {

        ClipboardOwner owner = this;
        clipboard.setContents(clipboard.getContents(this), this);
        clipboard.addFlavorListener(e -> {
//            System.out.println("Flavor Changed");
            new Thread(() -> {
                if (disabled) {
                    return;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                String contents = getClipboardContents();
                if (lastMessage == null) {
                    lastMessage = contents;
                    if(App.saveManager.saveFile.quickPasteSetting == QuickPasteSetting.AUTOMATIC) {
                        PoeInterface.attemptQuickPaste(contents);
                    }
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    refreshClipboard();
                } else {
                    lastMessage = null;
                    disabled = true;
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    disabled = false;
                }
            }).start();
        });
    }

    private String getClipboardContents() {
        String contents;
        try {
            contents = (String) clipboard.getData(DataFlavor.stringFlavor);
            return contents;
        } catch (UnsupportedFlavorException | IOException | IllegalStateException e) {
            System.out.println("Failed to get clipboard contents");
            return null;
        }
    }

    private void refreshClipboard() {
        Transferable transferable = null;
        try {
            transferable = clipboard.getContents(this);
        } catch (IllegalStateException e) {
            System.out.println("Failed to refresh clipboard (getContents)");
        }
        try {
            clipboard.setContents(transferable, this);
        } catch (IllegalStateException e) {
            System.out.println("Failed to refresh clipboard (setContents)");
        }
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable transferable) {
        // Do Nothing
    }

}
