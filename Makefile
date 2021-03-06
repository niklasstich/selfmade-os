outputdir = out
compilerdir = sjc
compiler_executable = compile
sourcedir = src
compatdir = compat
qemu_executable = qemu-system-x86_64
toolchain_version = 0190

compile:
	mkdir -p $(outputdir)
	cd $(compilerdir) && ./$(compiler_executable) ../$(sourcedir) ../$(compatdir)/rte ../$(compatdir)/java/lang -o boot -u rte -g -G -t ia32 -T nsop -s 1M && mv BOOT_FLP.IMG syminfo.txt ../$(outputdir)

run: compile
	$(qemu_executable) -m 32 -boot a -serial stdio -fda out/BOOT_FLP.IMG

install-compiler:
	curl https://www.fam-frenz.de/stefan/compsnpe.zip -o tools.zip
	mkdir -p $(compilerdir)
	unzip -d $(compilerdir) -j tools.zip  
	rm tools.zip