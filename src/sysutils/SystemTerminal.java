package sysutils;

import graphics.Console;
import graphics.ConsoleColors;
import hardware.Key;
import hardware.Keyboard;
import hardware.KeyboardEvent;

public class SystemTerminal {
	private static final int INPUT_BUFFER_SIZE = 320;
	private Console console;
	private char[] currentInputBuffer = new char[INPUT_BUFFER_SIZE];
	public SystemTerminal(Console console) {
		this.console = console;
	}
	
	//display a nice splash and a prompt
	public void init() {
		console.setColor(ConsoleColors.FG_LIGHTCYAN, ConsoleColors.BG_BLACK, false);
		console.clearConsole();
		//top line
		printBlock(80);
		//2nd line
		printBlock(29);
		console.print((char)0xC9);
		for (int i = 0; i < 20; i++) {
			console.print((char)0xCD);
		}
		console.print((char)0xBB);
		printBlock(29);
		//middle line
		printBlock(29);
		console.print((char)0xBA);
		console.print(" Welcome to ClubOS! ");
		console.print((char)0xBA);
		printBlock(29);
		//4th line
		printBlock(29);
		console.print((char)0xC8);
		for (int i = 0; i < 20; i++) {
			console.print((char)0xCD);
		}
		console.print((char)0xBC);
		printBlock(29);
		//bottom line
		printBlock(80);
		console.setDefaultColor();
		
		printPrompt();
	}
	
	private void printBlock(int blocks) {
		for (int i = 0; i < blocks; i++) {
			console.print((char) 0xDB);
		}
	}
	
	private void printPrompt() {
		console.print(">");
	}
	
	//takes over control and starts handling keyboard inputs
	public void focus() {
		while (true) {
			Keyboard.processInputBuffer();
			if (Keyboard.eventAvailable()) {
				KeyboardEvent kev = Keyboard.getNextKeyboardEvent();
				switch (kev.KEYCODE) {
					//TODO: handle all the different inputs that could be interesting to us
				}
			}
		}
	}
	
}
