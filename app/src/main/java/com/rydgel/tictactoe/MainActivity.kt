package com.rydgel.tictactoe

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.rydgel.tictactoe.TicTacToe.*
import com.rydgel.tictactoe.TicTacToe.MoveResult.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var ticTacToe = TicTacToe()
    private var activePlayer = Player.Yellow
    private var isPlaying = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun dropIn(view: View) {
        if (isPlaying) {
            val counter = view as ImageView
            val tag = counter.tag.toString().toInt()

            val playMove = ticTacToe.dropCoin(activePlayer, tag)
            animateDrop(counter)
            counter.setOnClickListener(null)

            when (playMove) {
                is Winning -> displayWinnerScreen(playMove.player)
                is Draw -> displayDrawScreen()
                is KeepPlaying -> switchPlayer()
            }
        }
    }

    fun playAgain(view: View) {
        ticTacToe = TicTacToe()
        activePlayer = Player.Yellow
        isPlaying = true
        // remove all coins and rebind listeners
        (0 until gridLayout.childCount)
            .map { gridLayout.getChildAt(it) as ImageView }
            .forEach { imageView ->
                imageView.setOnClickListener{ dropIn(it) }
                imageView.setImageDrawable(null)
            }

        // hide button/text
        messageTextView.visibility = View.INVISIBLE
        playAgainButton.visibility = View.INVISIBLE
    }

    private fun displayWinnerScreen(player: Player) {
        isPlaying = false

        val winText = when(player) {
            Player.Yellow -> "Player Yellow won!"
            Player.Red -> "Player Red won!"
        }

        messageTextView.text = winText
        showTextAndReplayButton()
    }

    @SuppressLint("SetTextI18n")
    private fun displayDrawScreen() {
        isPlaying = false

        messageTextView.text = "Draw!"
        showTextAndReplayButton()
    }

    private fun showTextAndReplayButton() {
        // message
        messageTextView.alpha = 0f
        messageTextView.visibility = View.VISIBLE
        messageTextView.animate().setDuration(1000L).alpha(1f)
        // button
        playAgainButton.alpha = 0f
        playAgainButton.visibility = View.VISIBLE
        playAgainButton.animate().setDuration(1000L).alpha(1f)
    }

    private fun animateDrop(counter: ImageView) {
        counter.translationY = -1500f
        counter.setImageResource(drawableFromPlayer(activePlayer))
        counter.animate().setDuration(300L).rotation(3600f).translationYBy(1500f)
    }

    private fun drawableFromPlayer(color: Player): Int {
        return when (color) {
            Player.Yellow -> R.drawable.yellow
            Player.Red -> R.drawable.red
        }
    }

    private fun switchPlayer() {
        activePlayer = when (activePlayer) {
            Player.Yellow -> Player.Red
            Player.Red -> Player.Yellow
        }
    }
}
