#!/bin/bash

export CLASSPATH_VAR=".:bin"

for jar in $(find lib); do
	export CLASSPATH_VAR=$CLASSPATH_VAR:$jar
done	

java -cp $CLASSPATH_VAR $*
