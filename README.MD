# SHEET NODE CONVERTER
![api ss](https://github.com/jpozarycki/sheet-node-converter-java/blob/master/src/nodes_view.png)

This app takes an `.xslx` file with nodes on different layers, converts it into a list of nodes, then saves it as a `.json` file and prints its structure out in the terminal.
In a Fullstack mode, you can display nodes as a collapsible tree structure in your browser.

## USAGE

### Command line execution
On default, the app uses `test.xslx` file in `src/main/resources` directory. In order to use a different file, put it into the mentioned directory and change `FILE_NAME` in the `Main` class.


A custom table with nodes needs to follow the pattern:
* The first row consists of columns' names
* The last column consists of nodes' IDs
* Child nodes have names that extend the names of their parents

To fire up the app, simply run

`./gradlew run` on a UNIX machine or

`gradle run` on a Windows machine.

### Full stack mode

This mode was added in order to display nodes as a collapsible tree in a browser.
App instead of displaying list of nodes in the terminal and saving it into a file, it sends converted json to frontend.
Then, you can click on collapsible nodes ( - / + ) to navigate through the tree.

To run backend, use

`./gradlew :runPlay` on a UNIX machine or

`gradle :runPlay` on a Windows machine.

To run frontend, go to `webapp` directory, install dependencies with
`yarn` 
 and use

`ng serve`, then navigate in your browser to `http://localhost:4200`.

Frontend needs `node`, `npm`, `yarn` and `angular-cli` to be run.
