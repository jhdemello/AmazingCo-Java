#!/usr/bin/bash

rm -f *.class *.jar

javac Main.java AwesomeTreeNode.java
jar cvf Main.jar *.class
jar cvfe Main.jar Main *.class
jar cvfm Main.jar manifest.txt  *.class
java -jar Main.jar
