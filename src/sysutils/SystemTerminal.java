package sysutils;

import graphics.*;
import hardware.keyboard.Key;
import hardware.keyboard.Keyboard;
import hardware.keyboard.KeyboardEvent;
import hardware.keyboard.KeyboardEventRingBuffer;
import sysutils.exec.Executable;
import sysutils.exec.ExecutableStore;
import utils.ASCIIControlSequences;
import utils.ArrayUtils;

public class SystemTerminal {
	private static final int INPUT_BUFFER_SIZE = 320;
	KeyboardEventRingBuffer buffer = new KeyboardEventRingBuffer();
	private char[] inputBuffer = new char[INPUT_BUFFER_SIZE];
	private int inputBufferPointer = 0;
	private SchedulerTask activeTask;
	private boolean firstCall = true;
	VideoCharCopy[] myVidMem;
	int myXPos, myYPos;
	
	//display a nice splash and a prompt
	public void init() {
		if(firstCall) {
			firstCall = false;
			Console.setColor(ConsoleColors.FG_LIGHTCYAN, ConsoleColors.BG_BLACK, false);
			Console.clearConsole();
			//top line
			printBlock(80);
			//2nd line
			printBlock(29);
			Console.print((char) 0xC9);
			for (int i = 0; i < 20; i++) {
				Console.print((char) 0xCD);
			}
			Console.print((char) 0xBB);
			printBlock(29);
			//middle line
			printBlock(29);
			Console.print((char) 0xBA);
			Console.print(" Welcome to ClubOS! ");
			Console.print((char) 0xBA);
			printBlock(29);
			//4th line
			printBlock(29);
			Console.print((char) 0xC8);
			for (int i = 0; i < 20; i++) {
				Console.print((char) 0xCD);
			}
			Console.print((char) 0xBC);
			printBlock(29);
			//bottom line
			printBlock(80);
			Console.setDefaultColor();
			
			printPrompt();
		}
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
		if(activeTask != null) {
			if(activeTask.isFinished()) {
				//remove the active task and continue, as it is finished
				activeTask = null;
				printPrompt();
			} else {
				//we have to wait for the task to finished, pass on inputs if necessary
				while (buffer.canRead()) {
					activeTask.exec.buffer.writeEvent(buffer.readEvent());
				}
			}
		}
		//TODO: rewrite to taking inputs out of our own buffer instead of keyboard buffer directly
		while (buffer.canRead()) {
			KeyboardEvent kev = buffer.readEvent();
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
								//TODO: add event to the scheduler and pass arguments
								ex.setArgs(args);
								activeTask = Scheduler.addTask(ex, this);
							} else {
								printExNotFound(split[0]);
							}
						}
						
					}
					clearInputBuffer();
					continue;
				}
			}
			if ((kev.KEYCODE == Key.D || kev.KEYCODE == Key.d)&& kev.CONTROL && kev.SHIFT) { //breakpoint
				MAGIC.inline(0xCC);
			}
			if ((kev.KEYCODE == Key.C || kev.KEYCODE == Key.c) && kev.CONTROL) { //clear line
				clearInputBuffer();
				Console.print("^C\n");
				printPrompt();
				continue;
			}
			if(kev.CONTROL && kev.SHIFT) {
				//set correct terminal
				int terminalNum = kev.KEYCODE - 48;
				if(terminalNum >= 0 && terminalNum <= 9)
					Scheduler.setCurrentTerminal(terminalNum);
				return;
			}
			if (kev.KEYCODE >= Key.SPACE && kev.KEYCODE <= Key.TILDE) { //printable ascii, straight up cast and push
				inputBuffer[inputBufferPointer] = (char) (kev.KEYCODE&0xFF);
				inputBufferPointer++;
				Console.print((char)(kev.KEYCODE&0xFF));
				continue;
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
	
	private void clearInputBuffer() {
		for (int i = 0; i<INPUT_BUFFER_SIZE; i++) {
			inputBuffer[i] = 0;
		}
		inputBufferPointer = 0;
	}
	
	void storeMyMem() {
		myVidMem = Console.getCurrentVideoMemory();
		myXPos = Console.getXPos();
		myYPos = Console.getYPos();
	}
	
	void restoreMyMem() {
		if(myVidMem!=null) {
			Console.writeVideoMemory(myVidMem);
			myVidMem = null;
		}
		Console.setPos(myXPos, myYPos);
		Console.setCursor(myXPos, myYPos);
	}
}
