package Pis_Sudoku;

public interface SudokuInterface {

  boolean isGameOver();

  int[][] solve(int[][] board);

  int giveTip(int x, int y);

  boolean possible(int y, int x, int value);

  void undo();
}
