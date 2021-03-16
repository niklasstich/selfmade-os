# Betriebssystem im Eigenbau - Self-made operating system
Meine Kursteilnahme am Masterkurs "Betriebssystem im Eigenbau" der Hochschule Kempten

My course participation on the masters course "self-made operating system" at University of Applied Sciences Kempten

## About
This repository contains a basic operating system written in a dialect of Java as defined by [the SJC compiler](https://www.fam-frenz.de/stefan/compiler.html).
## Prerequisites
Requires a full and functioning QEMU installation.

## Compiling and running
Simply run `make install-toolchain` to install the SJC compiler and `make run` to compile and run the operating system in a QEMU virtual machine.

## License
The code in this repository is released under the GNU Public License Version 3, of which you can find a copy [here](https://github.com/niklasstich/selfmade-os/blob/master/LICENSE).

The SJC compiler is released under the GNU Public License Version 3. All copyright remains with Stefan Frenz and Patrick Schmidt.
