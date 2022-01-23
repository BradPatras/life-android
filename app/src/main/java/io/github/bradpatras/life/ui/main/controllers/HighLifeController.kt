package io.github.bradpatras.life.ui.main.controllers

class HighLifeController: LifeController {
    override fun computeLivingState(currentlyAlive: IsAlive, neighbors: IsAliveArray): IsAlive {
        val aliveNeighborsCount = neighbors.count { it }
        return when {
            currentlyAlive && (2..3).contains(aliveNeighborsCount) -> true
            !currentlyAlive && (aliveNeighborsCount == 3 || aliveNeighborsCount == 6)  -> true
            else -> false
        }
    }
}