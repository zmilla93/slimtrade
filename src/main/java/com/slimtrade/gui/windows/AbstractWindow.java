package main.java.com.slimtrade.gui.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.basic.BasicMovableDialog;
import main.java.com.slimtrade.gui.buttons.IconButton;

public class AbstractWindow extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;
	public final int TITLEBAR_HEIGHT = 20;
	public final int BORDER_THICKNESS = 1;
	private int titlebarWidth;

	private JPanel titlebarPanel = new JPanel();
	protected JPanel center = new JPanel();

	private IconButton closeButton;
	protected Container contentPane = this.getContentPane();

	private Color borderColor = Color.orange;
	
	GridBagConstraints gc = new GridBagConstraints();

	public AbstractWindow(String title, boolean makeCloseButton) {
		this.setTitle(title);
		

		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(borderColor);
		titlebarPanel.setBackground(borderColor);
		center.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, borderColor));
		center.setBackground(Color.blue);

		titlebarPanel.setLayout(new BorderLayout());
		JPanel titleLabelPanel = new JPanel();
		JPanel titleButtonPanel = new JPanel();
//		titlebarPanel.setLayout(new Grid);
		center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		//TODO : Container color
		center.setBackground(borderColor);
		
		gc.weightx = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(0, 5, 0, 0);
		JLabel titleLabel = new JLabel("TITLE");
		gc.anchor = GridBagConstraints.LINE_START;
		titlebarPanel.add(titleLabel, BorderLayout.CENTER);
		JPanel p = new JPanel();
		

		gc.insets = new Insets(0, 0, 0, 0);

		gc.gridx++;
		JDialog local = this;
		if (makeCloseButton) {
			gc.anchor = GridBagConstraints.LINE_END;
			closeButton = new IconButton(ImagePreloader.close, TITLEBAR_HEIGHT);
//			titlebarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			titlebarPanel.add(closeButton, BorderLayout.EAST);
			closeButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					local.setVisible(false);
				}
			});

		}
		
		contentPane.add(titlebarPanel, BorderLayout.NORTH);
		contentPane.add(center, BorderLayout.CENTER);
//		contentPane.add(new BufferPanel(BORDER_THICKNESS, 0), BorderLayout.LINE_START);
//		contentPane.add(new BufferPanel(0, BORDER_THICKNESS), BorderLayout.PAGE_END);
//		contentPane.add(new BufferPanel(BORDER_THICKNESS, 0), BorderLayout.LINE_END);
		
//		container.add(new BasicPanel(200, 500, Color.red));

		this.setLocation(0, 0);
		titlebarPanel.setPreferredSize(new Dimension(50, TITLEBAR_HEIGHT));
		this.pack();
		if(closeButton != null && closeButton.getLocation().x%2 != 0){
			gc.insets = new Insets(0, 1, 0, 0);
			titlebarPanel.add(closeButton, gc);
		}
		this.createListeners(titlebarPanel);
	}
	
//	public void refresh(){
//		
//		this.pack();
//		if(closeButton != null && closeButton.getLocation().x%2 != 0){
//			gc.insets = new Insets(0, 1, 0, 0);
//			titlebarPanel.add(closeButton, gc);
//		}
//		this.pack();
//	}

}
