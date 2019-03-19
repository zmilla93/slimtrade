package main.java.com.slimtrade.gui.history;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.debug.Debugger;
import main.java.com.slimtrade.enums.DateStyle;
import main.java.com.slimtrade.enums.TimeStyle;
import main.java.com.slimtrade.gui.options.OrderType;

public class HistoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// private TradeOffer savedTrade;

	private ArrayList<TradeOffer> trades = new ArrayList<TradeOffer>();
	private ArrayList<HistoryRow> tradePanels = new ArrayList<HistoryRow>();


	private JPanel contentPanel;
	
	private static int maxTrades = 10;
	
	private boolean close = false;
	
	HistoryPanel() {
		this.setLayout(new BorderLayout());
		this.setBackground(Color.RED);
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		this.add(contentPanel, BorderLayout.CENTER);
		this.setMaxTrades(Main.saveManager.getDefaultInt(0, 100, 50, "history", "messageCount"));
	}

	public void addTrade(TradeOffer trade, boolean updateUI) {
		int i = 0;
		// Delete old duplicate
		for (TradeOffer savedTrade : trades) {
			if (TradeUtility.isDuplicateTrade(trade, savedTrade)) {
				trades.remove(i);
				if (updateUI) {
					contentPanel.remove(tradePanels.get(i));
					tradePanels.remove(i);
				}
				break;
			}
			i++;
		}
		// Delete oldest trade if at max trades
		if (trades.size() == maxTrades) {
			trades.remove(0);
			if (updateUI) {
				contentPanel.remove(tradePanels.get(0));
				tradePanels.remove(0);
			}
		}
		// Add new trade
		trades.add(trade);
		if (updateUI) {
			tradePanels.add(new HistoryRow(trade, close));
			if(HistoryWindow.orderType == OrderType.NEW_FIRST){
				contentPanel.add(tradePanels.get(tradePanels.size() - 1), 0);
			}else{
				contentPanel.add(tradePanels.get(tradePanels.size() - 1));
			}
			this.revalidate();
			this.repaint();
		}
	}

	public void initUI() {
//		Debugger.benchmarkStart();
//		contentPanel.removeAll();
//		tradePanels.clear();
		for (TradeOffer trade : trades) {
			HistoryRow row = new HistoryRow(trade, close);
			if(HistoryWindow.orderType == OrderType.NEW_FIRST){
				contentPanel.add(row, 0);
			}else{
				contentPanel.add(row);
			}
			tradePanels.add(row);
		}
//		Main.logger.log(Level.INFO, "HISTORY BUILD TIME : " + Debugger.benchmark());
		this.revalidate();
		this.repaint();
	}
	
	public void refreshOrder(){
		if(HistoryWindow.orderType == OrderType.NEW_FIRST){
			for(HistoryRow row : tradePanels){
				contentPanel.add(row, 0);
			}
		}else{
			for(HistoryRow row : tradePanels){
				contentPanel.add(row);
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	public void updateDate(){
		for(HistoryRow row : tradePanels){
			row.updateDate();
		}
	}
	
	public void updateTime(){
		for(HistoryRow row : tradePanels){
			row.updateTime();
		}
	}
	
	public void setMaxTrades(int maxTrades){
		HistoryPanel.maxTrades = maxTrades;
	}
	
	public void setClose(boolean close){
		this.close = close;
	}
	
}