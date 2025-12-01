package ie.tudublin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SudokuTest {

    @Test
    fun solvesEasyPuzzle() {
        val puzzle = listOf(
            "530070000",
            "600195000",
            "098000060",
            "800060003",
            "400803001",
            "700020006",
            "060000280",
            "000419005",
            "000080079"
        )

        val sudoku = Sudoku.fromLines(puzzle)
        val solved = sudoku.solve()

        assertTrue(solved, "Solver should find a solution")
        assertEquals(5, sudoku.get(0, 0))
    }

    @Test
    fun solvesAnotherPuzzle() {
        val puzzle = listOf(
            "200080300",
            "060070084",
            "030500209",
            "000105408",
            "000000000",
            "402706000",
            "301007040",
            "720040060",
            "004010003"
        )

        val sudoku = Sudoku.fromLines(puzzle)
        val solved = sudoku.solve()

        assertTrue(solved, "Solver should solve second puzzle")
    }

    @Test
    fun invalidBoardThrowsOrFails() {
        val puzzle = listOf(
            "553070000", // two 5s in row -> invalid
            "600195000",
            "098000060",
            "800060003",
            "400803001",
            "700020006",
            "060000280",
            "000419005",
            "000080079"
        )

        try {
            val sudoku = Sudoku.fromLines(puzzle)
            val solved = sudoku.solve()
            assertFalse(solved, "Invalid board should not be solvable")
        } catch (_: IllegalArgumentException) {
            // also acceptable: constructor rejects invalid board
        }
    }
}
