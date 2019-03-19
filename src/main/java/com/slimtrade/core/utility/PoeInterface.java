package main.java.com.slimtrade.core.utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;

public class PoeInterface extends Robot {

	private static StringSelection pasteString;
	private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	private static Robot robot;

	public PoeInterface() throws AWTException {
		try {
			robot = new Robot();
//			robot.setAutoDelay(100);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void paste(String s, boolean... send) {
		pasteString = new StringSelection(s);
		clipboard.setContents(pasteString, null);
		PoeInterface.focus();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		if (send.length == 0 || send[0] == true) {
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}
	}

	// TODO : Figure out why this bugs out without delay.
	// Somehow related to stashHelperContainer -
	// this.setFocusableWindowState(false);
	public static void findInStash(String s) {
		new Thread() {
			public void run() {
				focus();
				pasteString = new StringSelection(s);
				clipboard.setContents(pasteString, null);

				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_F);
				robot.keyRelease(KeyEvent.VK_F);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
			}
		}.start();
	}

	// TODO: modify to remove lambda? or store it somewhere else if it will
	// actually be reused
	public static void focus() {
		System.out.println("Focusing POE...");
		User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
			char[] className = new char[512];
			User32.INSTANCE.GetClassName(hWnd, className, 512);
			String wText = Native.toString(className);
			if (wText.isEmpty()) {
				return true;
			}
			if (wText.equals("POEWindowClass")) {
				User32.INSTANCE.SetForegroundWindow(hWnd);
				return false;
			}
			return true;
		}, null);
	}
}