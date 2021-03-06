package com.slimtrade.gui.dialogs;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.MatchType;
import com.slimtrade.gui.options.ignore.IgnoreData;
import com.slimtrade.gui.options.ignore.IgnoreRow;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class IgnoreItemWindow extends AbstractResizableWindow implements IColorable {

    private static final long serialVersionUID = 1L;

    private JLabel sureLabel = new CustomLabel("The following item will be ignored for 1 hour :");
    private JLabel itemLabel = new CustomLabel("TEST");

    private JButton cancelButton = new DenyButton("Cancel");
    private JButton confirmButton = new ConfirmButton("Ignore Item");

    public IgnoreItemWindow() {
        super("Ignore Item", false);

        container.setLayout(new GridBagLayout());

        ContainerPanel innerPanel = new ContainerPanel();
        JPanel innerContainer = innerPanel.container;

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        innerContainer.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.bottom = 20;

        //Button Panel
        gc.insets.right = 40;
        buttonPanel.add(cancelButton, gc);
        gc.gridx++;
        gc.insets.right = 0;
        buttonPanel.add(confirmButton, gc);

        //Reset GC
        gc.gridx = 0;
        gc.gridy = 0;

        //Container
        innerContainer.add(sureLabel, gc);
        gc.gridy++;
        innerContainer.add(itemLabel, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        innerContainer.add(buttonPanel, gc);

        container.add(innerPanel);

        // Cancel Button
        cancelButton.addActionListener(e -> setVisible(false));

        // Confirm Button
        confirmButton.addActionListener(e -> {
            IgnoreData data = new IgnoreData(itemLabel.getText(), MatchType.EXACT, 60);
            FrameManager.ignoreItemAddRemovePanel.add(new IgnoreRow(data, FrameManager.ignoreItemAddRemovePanel));

            FrameManager.ignoreItemAddRemovePanel.clearHiddenPanels();
            FrameManager.itemIgnorePanel.save();
            FrameManager.messageManager.closeTradesByItem(itemLabel.getText());
            App.saveManager.saveSettingsToDisk();
            FrameManager.itemIgnorePanel.load();
            ColorManager.recursiveColor(FrameManager.itemIgnorePanel);
            setVisible(false);
        });

        this.pack();
        this.setMinimumSize(this.getPreferredSize());
        ColorManager.recursiveColor(this);

    }

    public void setItem(String itemName) {
        itemLabel.setText(itemName);
    }

    @Override
    public void updateColor() {
        super.updateColor();
    }


}
