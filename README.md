# life-android
From [Wikipedia](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Rules):

> The universe of the Game of Life is an infinite, two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, live or dead, (or populated and unpopulated, respectively). Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:
> 1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
> 2. Any live cell with two or three live neighbours lives on to the next generation.
> 3. Any live cell with more than three live neighbours dies, as if by overpopulation.
> 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
>
> These rules, which compare the behavior of the automaton to real life, can be condensed into the following:
> 1. Any live cell with two or three live neighbours survives.
> 2. Any dead cell with three live neighbours becomes a live cell.
> 3. All other live cells die in the next generation. Similarly, all other dead cells stay dead.
>
> The initial pattern constitutes the seed of the system. The first generation is created by applying the above rules simultaneously to every cell in the seed, live or dead; births and deaths occur simultaneously, and the discrete moment at which this happens is sometimes called a tick. Each generation is a pure function of the preceding one. The rules continue to be applied repeatedly to create further generations.


## Update Algorithm
Before I look up algorithms for carrying out generational updates, I'll try to come up with something clever...

### The straight forward approach
keep a 2D array of spaces with a status of alive or dead.

Loop through the array and for each space, apply the rules of life to the current space depending on how many neighboring spaces are alive.

### Alive Spaces-based approach
Don't loop through the entire board, only test spaces that are neighboring the currently alive spaces.  

it'll need to keep track of the alive spaces in a separate property from the 2D board of spaces. 

First build up a list of "relevant spaces" by looping through the alive array and for each alive space, add its coordinate and all its neighbor coordinates.  Then run the life rules on the "relevant spaces" array.  
