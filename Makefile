outputdir = out
compilerdir = sjc
compiler_executable = compile
sourcedir = src
qemu_executable = qemu
toolchain_version = 0190

compile:
	mkdir -p $(outputdir)
	cd $(compilerdir) && ./$(compiler_executable) ../$(sourcedir) rte.java -o boot && mv BOOT_FLP.IMG syminfo.txt ../$(outputdir)

run: compile
	$(qemu_executable) -m 32 -boot a -fda out/BOOT_FLP.IMG

install-compiler:
	curl https://www.fam-frenz.de/stefan/exec$(toolchain_version).zip -o tools.zip
	mkdir -p $(compilerdir)
	unzip -d $(compilerdir) -j tools.zip  
	rm tools.zip