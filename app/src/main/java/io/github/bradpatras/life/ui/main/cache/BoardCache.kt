package io.github.bradpatras.life.ui.main.cache

import io.github.bradpatras.life.ui.main.controllers.Board
import java.util.*

// Stores boards in memory
class BoardCache(private val boardStack: Stack<Board> = Stack()) {
    fun put(board: Board) {
        boardStack.push(board)
    }

    fun removeLatest(): Board? {
        return if (boardStack.isNotEmpty()) boardStack.pop() else null
    }

    fun clear() {
        boardStack.empty()
    }
}