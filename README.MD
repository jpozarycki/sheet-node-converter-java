# SHEET NODE CONVERTER
This app takes an `.xslx` file with nodes on different layers, converts it into list of nodes and then saves it into a .json file

## USAGE
On default, the app uses `test.xslx` file in `src/main/resources` directory. In order to use a different file, put it into the mentioned directory and change `FILE_NAME` in the `Main` class.

To run the app, simply run

`./gradlew run` on a UNIX machine or

`gradle run` on a Windows machine.
