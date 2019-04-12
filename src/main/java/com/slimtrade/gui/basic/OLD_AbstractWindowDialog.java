package main.java.com.slimtrade.gui.basic;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.utility.PoeInterface;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.buttons.IconButton;

public abstract class OLD_AbstractWindowDialog extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;

	public static int defaultWidth = 400;
	public static int defaultHeight = 400;
	public static int titlebarHeight = 20;
	private static int titleOffset = 5;
	
	private int bufferWidth = 20;
	private int bufferHeight = 20;
	
	protected Container container = new JPanel();
	protected JPanel titlebarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	protected JPanel titlebarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, titleOffset, 0));
//	public IconButton_REMOVE closeButton = new IconButton_REMOVE("/resources/icons/close.png", titlebarHeight, titlebarHeight);
	protected IconButton closeButton = new IconButton("/resources/icons/close.png", titlebarHeight);
	
	
	
	public OLD_AbstractWindowDialog(){
		super(false);
		buildDialog("SlimTrade");
	}
	
	public OLD_AbstractWindowDialog(String title){
		super(false);
		buildDialog("SlimTrade - " + title);
	}
	
	//TODO : Add support for window borders
	//TODO : Center titlebar text
	private void buildDialog(String title){
//		this.setFocusableWindowState(true);
		this.getContentPane().setBackground(Color.GREEN);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setSize(defaultWidth, defaultHeight+titlebarHeight);
		
		titlebarPanel.setPreferredSize(new Dimension(defaultWidth-titlebarHeight, titlebarHeight));
		titlebarPanel.setBackground(Color.RED);
		
		titlebarContainer.setPreferredSize(new Dimension(defaultWidth, titlebarHeight));
		titlebarContainer.setBackground(Color.RED);
		
		JLabel titleLabel = new JLabel(title);
		titlebarPanel.add(titleLabel);
		
		titlebarContainer.add(titlebarPanel);
		titlebarContainer.add(closeButton);
		this.getContentPane().add(titlebarContainer);

		container.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
		this.getContentPane().add(container);
		createListeners(titlebarPanel);
		
		//TODO : Cleanup
		closeButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					hideWindow();
					PoeInterface.focus();
					FrameManager.forceAllToTop();
				}
			}
		});
	}
	
	public void resizeWindow(int width, int height){
		this.setSize(width, height+titlebarHeight);
		container.setPreferredSize(new Dimension(width, height));
		titlebarContainer.setPreferredSize(new Dimension(width, titlebarHeight));
		titlebarPanel.setPreferredSize(new Dimension(width-titlebarHeight, titlebarHeight));
		this.revalidate();
		this.repaint();
	}
	
	public void resizeWindow(Dimension size){
		this.setSize(size.width, size.height+titlebarHeight);
		container.setPreferredSize(new Dimension(size.width, size.height));
		titlebarContainer.setPreferredSize(new Dimension(size.width, titlebarHeight));
		titlebarPanel.setPreferredSize(new Dimension(size.width-titlebarHeight, titlebarHeight));
		this.revalidate();
		this.repaint();
	}
	
	public void autoResize(){
		container.setPreferredSize(null);
		Dimension size = container.getPreferredSize();
		size.width += bufferWidth;
		size.height += bufferHeight;
		this.resizeWindow(size);
	}
	
	private void hideWindow(){
		this.setVisible(false);
	}

	public void setBufferWidth(int bufferWidth) {
		this.bufferWidth = bufferWidth;
	}

	public void setBufferHeight(int bufferHeight) {
		this.bufferHeight = bufferHeight;
	}
	
	
	
}