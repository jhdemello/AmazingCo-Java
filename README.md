# AwesomeCo-Java
This is a simple Java program to create and maintain a hierarchical corporate directory of employees for Awesome Co.

*Who is John Galt?*

## Running AwesomeCo-Java locally
AwesomeCo-Java is a bare-bones Java application. Run it from the command line by building and running the following from a bash command line (it should work just as well with Java 8, 11 or 17):


```
git clone https://github.com/jhdemello/AwesomeCo-Java.git
cd AwesomeCo-Java
./run.sh
```

Optionally, on platforms without bash, the commands listed within ''run.sh'' can be invoked  individually:

```
javac Main.java AwesomeTreeNode.java
jar cvf AwesomeCo.jar *.class
jar cvfe AwesomeCo.jar Main *.class
jar cvfm AwesomeCo.jar manifest.txt  *.class
java -jar *.jar
```