package io.github.bradpatras.life.ui.main.controllers

interface LifeController {
    fun computeLivingState(currentlyAlive: IsAlive, neighbors: IsAliveArray): IsAlive
}