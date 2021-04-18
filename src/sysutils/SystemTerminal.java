package sysutils;

import graphics.Console;
import graphics.ConsoleColors;
import hardware.keyboard.Key;
import hardware.keyboard.Keyboard;
import hardware.keyboard.KeyboardEvent;
import sysutils.exec.Executable;
import sysutils.exec.ExecutableStore;
import utils.ASCIIControlSequences;
import utils.ArrayUtils;

public class SystemTerminal {
	private static final int INPUT_BUFFER_SIZE = 320;
	private char[] inputBuffer = new char[INPUT_BUFFER_SIZE];
	private int inputBufferPointer = 0;
	
	//display a nice splash and a prompt
	public void init() {
		Console.setColor(ConsoleColors.FG_LIGHTCYAN, ConsoleColors.BG_BLACK, false);
		Console.clearConsole();
		//top line
		printBlock(80);
		//2nd line
		printBlock(29);
		Console.print((char)0xC9);
		for (int i = 0; i < 20; i++) {
			Console.print((char)0xCD);
		}
		Console.print((char)0xBB);
		printBlock(29);
		//middle line
		printBlock(29);
		Console.print((char)0xBA);
		Console.print(" Welcome to ClubOS! ");
		Console.print((char)0xBA);
		printBlock(29);
		//4th line
		printBlock(29);
		Console.print((char)0xC8);
		for (int i = 0; i < 20; i++) {
			Console.print((char)0xCD);
		}
		Console.print((char)0xBC);
		printBlock(29);
		//bottom line
		printBlock(80);
		Console.setDefaultColor();
		
		printPrompt();
	}
	
	private void printBlock(int blocks) {
		for (int i = 0; i < blocks; i++) {
			Console.print((char) 0xDB);
		}
	}
	
	private void printPrompt() {
		Console.print(">");
	}
	
	//takes over control and starts handling keyboard inputs
	public void focus() {
		while (true) {
			Keyboard.processInputBuffer();
			if (Keyboard.eventAvailable()) {
				KeyboardEvent kev = Keyboard.getNextKeyboardEvent();
				switch (kev.KEYCODE) { //special handling
					case Key.BACKSPACE: {
						if(inputBufferPointer>0) {
							inputBuffer[--inputBufferPointer] = '\u0000'; //reset to zerovalue
							Console.print((char) ASCIIControlSequences.BACKSPACE);
						}
						continue;
					}
					case Key.ENTER: {
						Console.print(ASCIIControlSequences.LINE_FEED);
						if (!isBufferWhitespace()) { //buffer may contain a command, we have to check
							//first, turn our buffer contents into a string and split it on spaces
							String buf = String.compactString(inputBuffer);
							String[] split = buf.split(' ');
							if (split.length > 0) {
								Executable ex = ExecutableStore.fetchExecutable(split[0]);
								if (ex!=null) {
									String[] args = ArrayUtils.subArray(split, 1, split.length);
									int ret = ex.execute(args);
									if(ret!=0) {
										printErrorCode(ret);
									}
								} else {
									printExNotFound(split[0]);
								}
							}
							
						}
						printPrompt();
						inputBuffer = new char[INPUT_BUFFER_SIZE];
						inputBufferPointer = 0;
					}
				}
				if (kev.KEYCODE >= Key.SPACE && kev.KEYCODE <= Key.TILDE) { //printable ascii, straight up cast and push
					inputBuffer[inputBufferPointer] = (char) (kev.KEYCODE&0xFF);
					inputBufferPointer++;
					Console.print((char)(kev.KEYCODE&0xFF));
					continue;
				}
			}
		}
	}
	
	//returns whether or not the buffer only consists of whitespace,
	public boolean isBufferWhitespace() {
		int i = 0;
		while(i < inputBufferPointer) {
			if (inputBuffer[i] != ' ' && inputBuffer[i] != '\u0000') return false;
			i++;
		}
		return true;
	}
	
	private void printErrorCode(int error) {
		//TODO: implement
	}
	
	private void printExNotFound(String name) {
		Console.print("no executable ".concat(name).concat(" found\n"));
	}
	
}
