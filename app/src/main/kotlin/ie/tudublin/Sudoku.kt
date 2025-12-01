package ie.tudublin

class Sudoku private constructor(
    private val board: Array<IntArray>
) {
    var iterations: Long = 0
        private set

    companion object {
        const val SIZE = 9
        const val MAX_ITERATIONS = 2_000_000L

        // Build a Sudoku board from 9 lines of text
        fun fromLines(lines: List<String>): Sudoku {
            require(lines.size == SIZE) { "Board must have exactly 9 rows" }

            val board = Array(SIZE) { IntArray(SIZE) }

            for (r in 0 until SIZE) {
                val cleaned = lines[r].replace(" ", "")
                require(cleaned.length == SIZE) {
                    "Row ${r + 1} must have exactly 9 characters"
                }

                for (c in 0 until SIZE) {
                    val ch = cleaned[c]
                    val value = when (ch) {
                        in '1'..'9' -> ch.digitToInt()
                        '0', '.' -> 0
                        else -> throw IllegalArgumentException(
                            "Invalid character '$ch' at row ${r + 1}, col ${c + 1}"
                        )
                    }
                    board[r][c] = value
                }
            }

            return Sudoku(board)
        }
    }

    fun solve(): Boolean {
        iterations = 0
        return backtrack(0, 0)
    }

    fun get(row: Int, col: Int): Int {
        require(row in 0 until SIZE && col in 0 until SIZE)
        return board[row][col]
    }

    fun prettyString(): String {
        val sb = StringBuilder()
        for (r in 0 until SIZE) {
            if (r % 3 == 0 && r != 0) {
                sb.append("-".repeat(21)).append('\n')
            }
            for (c in 0 until SIZE) {
                if (c % 3 == 0 && c != 0) sb.append("| ")
                val v = board[r][c]
                sb.append(if (v == 0) ". " else "$v ")
            }
            sb.append('\n')
        }
        return sb.toString()
    }

    private fun backtrack(row: Int, col: Int): Boolean {
        if (iterations++ >= MAX_ITERATIONS) return false

        if (row == SIZE) return true  // finished

        val nextRow = if (col == SIZE - 1) row + 1 else row
        val nextCol = if (col == SIZE - 1) 0 else col + 1

        if (board[row][col] != 0) {
            return backtrack(nextRow, nextCol)
        }

        for (num in 1..9) {
            if (isValidPlacement(row, col, num)) {
                board[row][col] = num
                if (backtrack(nextRow, nextCol)) return true
                board[row][col] = 0
            }
        }

        return false
    }

    private fun isValidPlacement(row: Int, col: Int, value: Int): Boolean {
        // row
        for (c in 0 until SIZE) if (board[row][c] == value) return false
        // column
        for (r in 0 until SIZE) if (board[r][col] == value) return false
        // 3x3 box
        val boxRow = (row / 3) * 3
        val boxCol = (col / 3) * 3
        for (r in boxRow until boxRow + 3)
            for (c in boxCol until boxCol + 3)
                if (board[r][c] == value) return false
        return true
    }
}
