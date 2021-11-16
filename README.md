# Covid_BrickBreaker

![image](https://user-images.githubusercontent.com/25754048/142043039-057bd648-ae95-4f0b-a5d9-fee92f1091a5.png)


Covid19 informational brick breaker game

Image of the application running – 1

![image](https://user-images.githubusercontent.com/25754048/142042679-9c88ff3c-2999-4711-bf2b-5dbfaf3ae39a.png)

Compling.gif – This shows the program compiling and running. 

From the screen shot and gif the you can see the ball is a COVID-19 cell. 

The Gif shows the player moving the blue paddle at the bottom and the ball interacting with the paddle. 

The score once the ball is off the screen (at the end of a game) the score is shown at the bottom right. – 2

![image](https://user-images.githubusercontent.com/25754048/142042907-b9dc74eb-801b-437e-a789-421891207dbc.png)

As, you can see from the gif, and comparing the text in screenshot 1 and 2 the facts relating to COVID-19 change after a block is destroyed. 

My .java consist of one Public class (CE203_1800579_Ass2) and inside four static classes that initiate actions. 

The static class Block is the class that holds the constructor for the blocks to be broken in the game and also the paddle. This allows for low coupling as the class can create a majority of the objects in the game. This created high cohesions as it is a well defined class.

The static class Score draws the score on the screen, the class CovidFacts draws the facts on the screen. These two classes show poor cohesions as I could have created one class to draw strings and made the methods wide reaching that could be used to draw both the facts and the score. 

