#!/bin/sh
echo `pwd`
ja-modules.sh -d classes -instance-modules M1 *.java *.module 2>&1 > out
diff out out.default
