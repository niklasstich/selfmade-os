package kernel;

import graphics.Console;

public class Kernel {
  public static void main() {
    Console.ClearConsole();
    Console.Print("Doin\n your mom", (byte)0x07);
    while(true);
  }
}
