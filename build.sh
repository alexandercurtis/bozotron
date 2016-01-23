#!/bin/bash

# Build script for Bozotron 
# by Alex Curtis

javac -sourcepath src -d build src/*.java
jar cvf Bozotron.jar -C build/ .

cp Bozotron.jar production


