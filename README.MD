# SHEET NODE CONVERTER
This app takes an `.xslx` file with nodes on different layers, converts it into a list of nodes, then saves it as a `.json` file and prints its structure out in the terminal.

## USAGE
On default, the app uses `test.xslx` file in `src/main/resources` directory. In order to use a different file, put it into the mentioned directory and change `FILE_NAME` in the `Main` class.


A custom table with nodes needs to follow the pattern:
* The first row consists of columns' names
* The last column consists of nodes' IDs
* Child nodes have names that extend the names of their parents

To fire up the app, simply run

`./gradlew run` on a UNIX machine or

`gradle run` on a Windows machine.
