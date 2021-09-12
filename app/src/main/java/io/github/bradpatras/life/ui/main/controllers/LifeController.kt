package io.github.bradpatras.life.ui.main.controllers

class LifeController() {
    companion object {
        /**
         * Run the rules of life on the given cell.
         *
         * 1. Any live cell with two or three live neighbours survives.
         * 2. Any dead cell with three live neighbours becomes a live cell.
         *
         * @param currentlyAlive current state of subject that will be tested
         * @param neighbors the immediately adjacent neighbors of the `cell`
         * @return a new cell representing the results of applying the rules
         */
        fun computeLivingState(currentlyAlive: IsAlive, neighbors: IsAliveArray): IsAlive {
            if (currentlyAlive && (2..3).contains(neighbors.count { it })) {
                return true
            } else if (!currentlyAlive && neighbors.count { it } == 3) {
                return true
            }

            return false
        }
    }
}