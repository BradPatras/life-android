package io.github.bradpatras.life.ui.main.controllers

import android.util.Size
import io.github.bradpatras.life.ui.main.models.Cell

class LifeController() {
    companion object {
        /**
         * Run the rules of life on the given cell.
         *
         * 1. Any live cell with two or three live neighbours survives.
         * 2. Any dead cell with three live neighbours becomes a live cell.
         *
         * @param cell the cell that will be tested against the rules of life
         * @param neighbors the immediately adjacent neighbors of the `cell`
         * @return a new cell representing the results of applying the rules
         */
        fun applyRules(cell: Cell, neighbors: Array<Cell>): Cell {
            return if (cell == Cell.ALIVE && (2..3).contains(neighbors.count { it == Cell.ALIVE })) {
                Cell.ALIVE
            } else if (cell == Cell.DEAD && neighbors.count { it == Cell.ALIVE } == 3) {
                Cell.ALIVE
            } else {
                Cell.DEAD
            }
        }
    }
}