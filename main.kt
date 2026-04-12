import java.awt.Font
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel

class SlidePuzzleSwing : JFrame("Kotlin Slide Puzzle") {
    private val size = 3 // 3x3
    private val buttons = mutableListOf<JButton>()
    private var blankButton: JButton? = null

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = GridLayout(size, size)

        // 1. 数字リストの作成とシャッフル
        val numbers = (1 until size * size).map { it.toString() }.toMutableList()
        numbers.add("") // 空マス
        numbers.shuffle()

        // 2. ボタンの生成と配置
        for (text in numbers) {
            val button = JButton(text).apply {
                font = Font("Arial", Font.BOLD, 24)
                addActionListener { handleMove(this) }
            }
            if (text == "") blankButton = button
            buttons.add(button)
            add(button)
        }

        setSize(400, 400)
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun handleMove(clickedButton: JButton) {
        if (clickedButton == blankButton) return

        // インデックスを取得して隣接チェック
        val clickedIdx = buttons.indexOf(clickedButton)
        val blankIdx = buttons.indexOf(blankButton)

        if (isAdjacent(clickedIdx, blankIdx)) {
            // テキストを入れ替えて「空マス」を移動させる
            blankButton?.text = clickedButton.text
            clickedButton.text = ""
            blankButton = clickedButton

            checkWin()
        }
    }

    private fun isAdjacent(idx1: Int, idx2: Int): Boolean {
        val x1 = idx1 % size; val y1 = idx1 / size
        val x2 = idx2 % size; val y2 = idx2 / size
        return Math.abs(x1 - x2) + Math.abs(y1 - y2) == 1
    }

    private fun checkWin() {
        val current = buttons.map { it.text }
        val goal = (1 until size * size).map { it.toString() } + ""

        if (current == goal) {
            JOptionPane.showMessageDialog(this, "おめでとう！クリアです！")
        }
    }
}

fun main() {
    SlidePuzzleSwing()
}
