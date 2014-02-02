#
# SCCS @(#)Makefile	1.6 - 03/08/99 - 17:18:29
#
#get list of java sources
# 98/8/24 aa - added extra level that occures in nsls/struct/
# 98/10/6 aa - added extra levels that occures in nsls/jacl/cornell/Tcl
# 99/2/11 aa - added extra levels for that occures in scriptics nsls/jacl/
# 99/2/11 aa - make clean - now removes inner classes
#
# Note that we do not use the "find" command below because it follows symbolic
# links - always !
#
# had to do the following to avoid compiling in SCCS
# skip level 3 because no java files there.  Goes 5 levels deep

javafiles:sh = ls [a-z]*/*.java; ls [a-z]*/[a-z]*/*.java; ls [a-z]*/[a-z]*/[a-z]*/[a-z]*/*.java; ls [a-z]*/[a-z]*/[a-z]*/[a-z]*/[a-z]*/*.java; ls [a-z]*/[a-z]*/[a-z]*/[a-z]*/[a-z]*/*.java

# Had to have a special list of files to remove for the inner classes
# needed quotes around the names.

dirlist:sh = du -k |awk '{print $2}'

here:sh = pwd

#replace java extensions with class to get classfiles to be built

mkclassfiles=$(javafiles:.java=.class);

all: ${mkclassfiles}
#	echo ${mkclassfiles}

%.class:%.java
#	@echo ${javafiles}
#	@echo "$@(base:$*) depends on $<"
#	@echo "[$*]"
	javac $<

clean:
	for i in ${dirlist}; do \
	if \
		diff ./nsls/jacl/com/oroinc/text/regex $$i> _junk1; \
	then \
		echo "will not delete class files in following dir";\
		echo $$i;\
	elif \
		diff ./nsls/jacl/tcl/lang $$i> _junk1; \
	then \
		echo "will not delete class files in following dir";\
		echo $$i;\
	elif \
		diff ./nsls/jacl/tcl/lang/library $$i> _junk1; \
	then \
		echo "will not delete class files in following dir";\
		echo $$i;\
	elif \
		diff ./nsls/jacl/tcl/regex $$i> _junk1; \
	then \
		echo "will not delete class files in following dir";\
		echo $$i;\
	elif \
		diff ./nsls/tcljava/tcl/lang $$i> _junk1; \
	then \
		echo "will not delete class files in following dir";\
		echo $$i;\
	elif \
		diff ./nsls/tcljava/tcl/lang/reflect $$i> _junk1; \
	then \
		echo "will not delete class files in following dir";\
		echo $$i;\
	else \
		cd $$i; \
		echo $$i; \
		rm -f *.class; \
		cd ${here}; \
	fi; \
	rm _junk1; \
	done		 

#zip: all
#       see ~nsl/scripts/sccs/sccszip instead





