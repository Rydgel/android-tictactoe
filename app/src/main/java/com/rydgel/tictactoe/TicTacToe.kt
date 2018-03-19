package com.rydgel.tictactoe

class TicTacToe {
    enum class Player { Yellow, Red }

    sealed class GridSlot {
        data class Occupied(val player: Player): GridSlot()
        object Empty: GridSlot()
    }

    sealed class MoveResult {
        data class Winning(val player: Player): MoveResult()
        object Draw: MoveResult()
        object KeepPlaying: MoveResult()
    }

    private var gameState: Array<GridSlot> = Array(9, { _ -> GridSlot.Empty })

    private val allConfigurations = arrayOf(
    //               Horizontal  |  Vertical |     Diagonal
    /* 0 */ arrayOf( arrayOf(1, 2), arrayOf(3, 6), arrayOf(4, 8)                ),
    /* 1 */ arrayOf( arrayOf(0, 2), arrayOf(4, 7)                               ),
    /* 2 */ arrayOf( arrayOf(0, 1), arrayOf(5, 8), arrayOf(4, 6)                ),
    /* 3 */ arrayOf( arrayOf(4, 5), arrayOf(0, 6)                               ),
    /* 4 */ arrayOf( arrayOf(3, 5), arrayOf(1, 7), arrayOf(0, 8), arrayOf(2, 6) ),
    /* 5 */ arrayOf( arrayOf(3, 4), arrayOf(2, 8)                               ),
    /* 6 */ arrayOf( arrayOf(7, 8), arrayOf(0, 3), arrayOf(2, 4)                ),
    /* 7 */ arrayOf( arrayOf(6, 8), arrayOf(1, 4)                               ),
    /* 8 */ arrayOf( arrayOf(6, 7), arrayOf(2, 5), arrayOf(0, 4)                )
    )

    fun dropCoin(player: Player, tag: Int): MoveResult {
        gameState[tag] = GridSlot.Occupied(player)
        return when {
            didPlayerWin(player, tag) -> MoveResult.Winning(player)
            isDraw() -> MoveResult.Draw
            else -> MoveResult.KeepPlaying
        }
    }

    private fun didPlayerWin(player: Player, index: Int): Boolean {
        val configs = allConfigurations[index]
        return configs.any { configuration ->
            configuration.all { idx ->
                gameState[idx] == GridSlot.Occupied(player)
            }
        }
    }

    private fun isDraw(): Boolean {
        return gameState.all { slot -> slot != GridSlot.Empty }
    }
}