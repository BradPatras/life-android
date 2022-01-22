package io.github.bradpatras.life.ui.main.controllers

class LifeController() {
    companion object {
        /**
         * Run the rules of life on the given cell.
         *
         * 1. Any live cell with less than two or more than three live neighbours dies.
         * 2. Any dead cell with three live neighbours becomes a live cell.
         *
         * @param currentlyAlive current state of subject that will be tested
         * @param neighbors the immediately adjacent neighbors of the `cell`
         * @return a new cell representing the results of applying the rules
         */
        fun computeLivingState(currentlyAlive: IsAlive, neighbors: IsAliveArray): IsAlive {
            val aliveNeighborsCount = neighbors.count { it }
           return when {
               currentlyAlive && (2..3).contains(aliveNeighborsCount) -> true
               !currentlyAlive && aliveNeighborsCount == 3 -> true
               else -> false
            }
        }
    }
}