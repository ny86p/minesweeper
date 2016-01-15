=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: ryanpaul
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

  1. Modeling state using 2-D arrays or collections

  2. Using I/O to parse a novel file format

  3. Using JUnit to test some features of your model

  4.Implementing recursive algorithms


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
I have a GameGrid class which represents the minesweeper game board. It consists of a 2-d array
of all of the tiles, keeps track of the win/lose state of the game. This class does most of the work
in getting information about tiles, how many were clicked, and so on.

I have a tile class that is representative of every tile. It keeps track of whether a tile is
flagged or has a mine, and how to draw the tile. 

I also have a tuple class which was only used to help store the location of the mines on the grid.

- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?

I kept modeling as a 2-d array, I/O, and JUnit. I changed complex search of the game state to 
implementing a recursive algorithm. Initially I thought the action taken in order to reveal all
the proper tiles when a blank tile is selected would involve a complex search of the game, but 
I discovered rather quickly that a recursive algorithm which continually checks if a block has a 
neighboring block that is blank would do the trick.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

Things ran pretty smoothly. File IO gave me some trouble, but it was all due to weird spacings and 
stuff like that. Also, in all honesty, JUnit testing was difficult, as it was difficult to test
raw functions while ignoring the swing components.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I believe there is a good separation of functionality. Private state could be encapsulated better
and if I could it is what I would refactor. I would definitely refactor if I had the chance. Most of
my functions I am pleased with, but organization and handling of events could have been better.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.

I made use of Dialogue boxes often. Also stackoverflow was a good help.
