#!/bin/bash

count=0
for i in `find . -iname position.txt -exec dirname {} \;`; do
	if [ $count -eq 8 ]; then
		wait
		count=0
	fi
	Rscript plotting.r $i &
	count=`expr $count + 1`
done

wait

# copy everything to one folder
mkdir pathPlots
for i in `find . -iname path.png`; do
	# Erase / and . to get a single filename
	cp $i pathPlots/`echo $i | sed s/[\.\/]//g`.png;
done

mkdir policyPlots
for i in `find . -iname policy.png`; do
	# Erase / and . to get a single filename
	cp $i policyPlots/`echo $i | sed s/[\.\/]//g`.png;
done
