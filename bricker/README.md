liorvolfovich,yotam181
208660373,208541334

1. In part 1.7 We chose option 2. That is, the responsibility for adding and deleting members is only
in the BrickerGameManager class.
In our opinion, this option preserves encapsulation in a good way, since only the game manager can add
and remove objects from the game.
In addition, this option does not reveal to the other classes the list of all objects in the game.
For the game manegar to do this, We added two public methods - one for adding and one for removing game
objects. On the other hand, also sending the game manager to other classes is not a perfect solution, 
as they can invoke public methods of the game manager that are not relevant.

2. The BrickerGameManager class contains a lifeCounter field that is of type Couter. 
The presentation of life - both numerically and graphically is carried out according to this variable. 
To display life numerically, We created the NumericLifeCounter class which inherits from GameObject. 
In this class We overrode the update method. As a result, every time the amount of life in the game changes,
the numerical display is displayed according to the requirements (suitable color and number).
To present life graphically, We created the class- GraphicLifeCounter which inherits from GameObject.
One of the parameters that the class receives in the constructor is an array that contains four heart-shaped
game objects. Also, in this class We overrode the update method. As a result, the graphic presentation is 
presented to the user as required.
The hearts remain in the array throughout the game, but they are added to the game and removed from it
depending on the number of disqualifications left for the player.

3. a. Additional balls. To implement this behavior We created a class called Puck which inherits from Ball.
In this class we overrode the update method, because unlike the central ball, an object of this class
is removed from the game when it leaves the game boundaries. We also created a class called 
PuckCollisionStrategy that implements the CollisionStrategy interface. In this class the implementation of 
the method onCollision removes the brick from the game and creates two new pucks, with appropriate direction
and speed.
b. Additional paddle. To implement this behavior We created a new class called ExtraPaddle which inherits
from Paddle. This department cares to update the number of collisions she has had in the game and to remove
her from the game if she has had four collisions. We also created a class called PaddleCollisionStrategy 
which implements the CollisionStrategy interface. In the implementation of the onCollision method,
after removing the brick, an ExtraPaddle type object is added only if one does not already exist in the game.
This test is done by a public method on BrickerGameManager class, see detail in section 5.
c. Camera change. For this behavior We created a class called CameraCollisionStrategy which implements the 
CollisionStrategy interface. In this class, the implementation of the onCollision method removes the brick 
and creates a camera tracking of the ball if such tracking does not exist at that moment in the game. 
Additionally, by calling this behavior, the cameraCounter object is incremented to be the value of the 
number of collisions that have occurred to the ball so far. As a result, in BrickerGameManager's update 
method we check when the ball has hit 4 hits after creating the behavior. Afterwords, we reset the camera 
and reset the cameraCounter so that it can update properly within the next call to this strategy.
d. return disqualification. To implement this behavior We created a class called FallingHeart which inherits
from GameObject. In this class the implementation of the shouldCollideWith method causes the falling heart
to only collide with the central paddle. Furthermore, We created the HeartCollisionStrategy class which 
implements the CollisionStrategy interface. This class creates a new FallingHeart object in place of the
brick and adds it to the game with the appropriate speed.

4. For double behavior We created a class called DoubleCollisionStrategy which implements the
CollisinStrategy interface. The constructor of this behavior receives the behaviors factory, in order to be 
able to create special behaviors. Two random draws of an integer from 0 to 4 are made at the constructor. 
If in the first draw the number 4 was obtained (representing double behavior), it means that double behavior
was chosen again. If a different number was received in the first lottery, we create another lottery in wich
we also check whether the digit 4 was received. If on one of the occasions we received a double behavior,
we will create 3 special behaviors using the factory, and else we will create 2 special behaviors. We would 
like to emphasize that the constructor allows a situation of drawing a double behavior both at the first 
time and the second time, when in each such lottery the chance of drawing a double behavior is one in five.
A double behavior will hold an array of all the drawn special behaviors (2 or 3). When the onCollision 
method is being called, it goes through each member of the array and calls the method onCollision of the 
behavior in the array.

5. At first, we made some changes to the API of the BrickerGameManager class:
Firstly, we added the addGameObject and removeGameObject methods, which allow adding and removing objects
from the game, as described in question 1.
Secondly, we added the isExtraPaddle and setExtraPaddle methods, which can be used to check whether an 
ExtraPaddle object exists in the game, and set an appropriate Boolean value for the isExtraPaddle class 
variable. Adding these methods are used for the Additional paddle behavior.
Additionally, We created the Constants class which contains all the constants we used in the project.
Many constants were relevant to several classes, so We decided to combine them all into one class.

