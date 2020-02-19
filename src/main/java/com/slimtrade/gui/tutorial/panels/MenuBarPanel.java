package com.slimtrade.gui.tutorial.panels;

import com.slimtrade.gui.basic.SectionHeader;

import javax.swing.*;

public class MenuBarPanel extends AbstractTutorialPanel {

    JLabel info1 = new JLabel("SlimTrade only appears when POE is focused, but can always be access from the system tray.");
    JLabel info2 = new JLabel("Leave the defaults as is, or hover over the button in the upper left to start customizing!");

    public MenuBarPanel() {
        gc.insets.bottom = 10;
        container.add(new SectionHeader("Get Started"), gc);
        gc.gridy++;
        gc.insets.bottom = 10;
        container.add(new ImageLabel("images/menubar-toggle.png"), gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        container.add(info1, gc);
        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
    }

}
