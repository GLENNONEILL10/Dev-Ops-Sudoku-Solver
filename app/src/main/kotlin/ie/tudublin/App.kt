package ie.tudublin

import java.io.File

fun main(args: Array<String>) {

    if (args.size != 1) {
        println("Usage: sudoku <path-to-board-file>")
        return
    }

    val filePath = args[0]
    val file = File(filePath)

    if (!file.exists()) {
        println("Error: file not found: $filePath")
        return
    }

    val lines = file.readLines().filter { it.isNotBlank() }

    val sudoku = try {
        Sudoku.fromLines(lines)
    } catch (e: IllegalArgumentException) {
        println("Error parsing board: ${e.message}")
        return
    }

    println("Input board:")
    println(sudoku.prettyString())

    val solved = sudoku.solve()
    if (solved) {
        println("Solved board (in ${sudoku.iterations} iterations):")
        println(sudoku.prettyString())
    } else {
        println(
            "Could not find a solution " +
            "(stopped after ${sudoku.iterations} iterations, " +
            "or the board is unsolvable)."
        )
    }
}
