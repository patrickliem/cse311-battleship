# cse311-battleship

## Project Structure
cse311-battleship contains the Eclipse project for this project. There are 4 classes: Model.java, Controller.java, ViewGUI.java, ViewText.Java, and Driver.java

## How to play

### Text view
During setup, you will be prompted to enter 3 characters, separated by spaces to place each of your 5 ships. Valid orientations are either R for right or D for down.

You will see two boards: yours and your opponent's. On your board, ~ is water, a is your 5 length ship, b is your 4 length ship, c is your 3 length ship, d is your other 3 length ship, and e is your 2 length ship. Capital letters indicate that that part of the ship has been hit. m is a tile that your opponent fired at but did not hit a ship. Your ships are replaced by x's once they have been completely sunk. On your opponent's boards, you will only see 4 different characters: ~, m, h, and x. ~ represents water. m represents a tile that you previously fired at that was empty. h represents a tile that you previously fired at that had a ship. x represents a completely sunken ship.