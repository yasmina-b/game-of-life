# game-of-life
CEBP Project

FUNCTIONAL requirements: 

  - Cells can be of two types, sexual and asexual.

  - Asexual cells are multiplying by division resulting two hungry cells.

  - Sexual cells are multiplying only if they find another cell looking to reproduce, resulting in one hungry cell.

  - Dying cells are resulting in a random number between 1 and 5 of food units.

  - Cells that ate at least 10 times will multiply before they get hungry again.

  - There is a limited number of food units that cells can consume.

  - A unit of food is enough for a cell for a given T_full time, after which it becomes hungry.

  - If a cell is hungry and it’s not eating for a given T_starve time, it dies.


NON-FUNCTIONAL requirements: 

  - The program should save the simulation if interrupted.


ENTITIES: 

  - Asexual cells
  - Sexual cells
  - Food
  - Space
  
  DIAGRAM :
  
  
  <img width="812" alt="Screenshot 2022-10-26 at 00 53 27" src="https://user-images.githubusercontent.com/80706108/197889533-e4ecddcd-e5da-4402-8503-f75fe5236a1c.png">

  
  
  
  
