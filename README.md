Game of life
============

JavaFx implementation of the famous Conway's "Game of life".
see
https://conwaylife.com/wiki/Main_Page


### What is "Game of life"
It's an infinite grid with only 4 rules
1.    Any live cell with fewer than two live neighbors dies, as if by underpopulation.
2.    Any live cell with two or three live neighbors lives on to the next generation.
3.    Any live cell with more than three live neighbors dies, as if by overpopulation.
4.    Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.


### File formats
There are many resource file for game of life. All are ascii format, here are the main one:

 - life
 - cell
 - rle
 - gol (My own variation of life & cell)

Here are the references :http://www.conwaylife.com/wiki/Run_Length_Encoded

### usage
You can download any life file form a reference here:
http://www.conwaylife.com/wiki/Main_Page

Use "file/open" to open any of the file previously downloaded
Then run it step by step, or simply using "play"

### build
The build uses maven:

    $ mvn install
    $ java -jar

### Evolution
- [x] visual edition
- [x] file saving
- [ ] dynamic load pattern with buttons
- [ ] other automat rules, reading file comments&headers

### References
Here are some links to explain the "magic" of this "game".

- [Le Jeu de la Vie — Science étonnante #49](https://www.youtube.com/watch?v=S-W0NX97DB0)
- [epic conway's game of life](https://www.youtube.com/watch?v=C2vgICfQawE&t=311s)
- [Wiki](http://www.conwaylife.com/wiki/Main_Page)
- [Dump files](https://conwaylife.com/patterns)

Upgrade to java 17
==================
 1. add dependencies, plugin in pom.xml
 2. launch with right module
 3. run with mvn javafx:run
 4. add module
 5. build with jlink
 
 see example : /home/philippe/private/external/samples/CommandLine/Modular/Maven/hellofx/
 https://github.com/openjfx/samples/tree/master/CommandLine/Modular/Maven

1. 
		  <dependency>
		    <groupId>org.openjfx</groupId>
		    <artifactId>javafx-controls</artifactId>
		    <version>17</version>
		  </dependency>
		<dependency>
			<groupId>org.openjfx</groupId>
			<artifactId>javafx-fxml</artifactId>
			<version>17</version>
		</dependency>
		
4. add module		
		module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens pdemanget.gameoflife to javafx.fxml;
    exports pdemanget.gameoflife;
}
5.mvn clean javafx:jlink

The OpenJFX
------------
javaFX became openjfx and binaries must be installed!
SEE https://openjfx.io/
2 options: install the sdk OR use maven (recommanded and simplest)

### MAVEN
To run in developpement:

 mvn clean javafx:run

To create and run a custom JRE:
   
    mvn clean javafx:jlink
    target/<your-app>/bin/launcher

### troubleshoot
It can be a little bit tricky to build for javafx:
- check with a manual run on the jar: java --module-path $PATH_TO_FX --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.swing, -jar 
yourjar.jar, or with the classpath
- remove unused import in FXML (it's removed by jlink, but needed by FxmlLoader (bug to report?)

 
 
### SDK
The procedure for manjaro
search latest (j17) java-openjfx package (AUR installation) 
pacman -Ql java-openjfx
java --module-path /usr/lib/jvm/java-17-openjfx/ --add-modules=javafx.controls -jar target/gameOfLife-0.0.1-SNAPSHOT.jar

Also see project examples:
/home/philippe/private/external/samples/CommandLine/Modular/Maven/

                                       
