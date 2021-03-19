package kernel;

import graphics.Console;
import graphics.ConsoleColors;

public class Kernel {
  public static void main() {
    Console.ClearConsole();
    Console.Print("Hello World", ConsoleColors.BG_BLUE | ConsoleColors.FG_LIGHTGREEN);
    while(true);
  }
}
