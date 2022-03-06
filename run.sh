#!/usr/bin/bash

rm -f *.class *.jar

javac Main.java AwesomeTreeNode.java
jar cvf AwesomeCo.jar *.class
jar cvfe AwesomeCo.jar Main *.class
jar cvfm AwesomeCo.jar manifest.txt  *.class
java -jar *.jar
