#!/bin/bash

shopt -s globstar

mkdir -p ./bin

export CLASSPATH_VAR="."

for jar in $(find lib); do
	export CLASSPATH_VAR=$CLASSPATH_VAR:$jar
done	

javac -cp $CLASSPATH_VAR ./src/**/*.java -d bin -verbose
