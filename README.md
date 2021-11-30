# CSE 110 Honors Contract
To complete an Honors Contract for the CSE 110 class, I created a project written in Java. The project is a randomly generated dungeon crawling game, where the key feature is the combat system. Combat is evaluated by the player completing one of three kinds of challenges: Math, Unscramble, and Copy. Enemy health, frequency, and challenge difficulty increases until the player eventually dies. There's also a tavern in between dungeon floors to redeem favors to help the player in the next dungeons. See the in-game tutorial for further details about the game and its mechanics.

## How to Compile
If you don't have an IDE or don't want to compile the project from the source code, just download the .jar from the [releases page](https://github.com/Nathmon565/CSE110HC/releases) and see "How to run" below.
### Visual Studio Code
1) Open the project folder containing all source code files.
2) There should be no compile time errors, and the game should run in the console.
3) Open the Command Palette (Ctrl + Shift +P) and select "Java: Export Jar..."
4) Select the main class as CSE110HC. Include `bin` and `sounds\tinysound-1.1.1.jar` as elements.
5) Confirm the destination folder and click Save.
### Eclipse
1) Either create a new workspace, or use a relatively clean workspace.
2) Open the project folder containing all source code files with File -> Open Projects from File System...
3) There should be a significant amount of compile time errors citing how there's an undefined class "Music" and unresolved imports.
4) Right click on the new project folder in the Package Explorer, select Build Path -> Configure Build Path.
5) Select the Libraries tab. Click Classpath, then "Add External JARs..." using `CSE110HC\sounds\tinysound-1.1.1.jar` as the path to the jar file.
6) Click Ok, then Apply and Close.
7) At this point there should be no compile time errors, and the game should run in the console.
8) Right click the CSE110HC folder in the Package Explorer -> Export -> Java -> Runnable JAR file
9) Set the launch Configuration, export destination, and file name then click Finish.
## How to run
The program has only been tested on windows and requires __Java 17__ or newer to run.
In the same folder as the .jar, run the following command in a terminal:
`java -jar .\CSE110HC.jar`
To enable the tutorial, input option 5 on the main menu. This will explain the major features of the game as you progress.

## Dependencies
[TinySound](https://github.com/finnkuusisto/TinySound)