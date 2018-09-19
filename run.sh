#!/bin/bash

PACKAGE_NAME_TO_MAIN="main.ReversiSmart"
LIBS_FOLDER="lib"

CODE_LOC="src/main/java/*"

function remove {
  echo "Removing ./bin folder"
  rm -rf ./bin
}

function compile {
  pwd
  CODE_DIR_AUTO="$(find $CODE_LOC -name '*.java')"
  mkdir -p bin
  javac -d "./bin" -classpath "./$LIBS_FOLDER/*" $CODE_DIR_AUTO
  if [ $? -eq 0 ]; then
    echo "Code compilation successful."
  else
    echo "Code compilation FAILED."
  fi;
}

if [ "$1" == "clean" ]; then
  remove
elif [ "$1" == "-help" ]; then
  echo "USAGE: <host> <player number>; clean"
else
  if [ -z "$2" ] ; then
    echo "Player number requred; USAGE: <host> <player number>; clean"
    exit 1
  fi
  remove
  compile
  java -cp "./bin:./"$LIBS_FOLDER"/*" $PACKAGE_NAME_TO_MAIN $1 $2
fi;