package Pis_Sudoku;

import java.util.ArrayList;
import java.util.Arrays;

public class GameEngine implements SudokuInterface {

  /************Class Invariantes*************/

  private int x;

  private int y;
  private int[][] gridMain;
  private int[][] gridEasy;
  private int[][] gridMiddle;
  private int[][] gridHard;
  private int playerLife = 3;

  private ArrayList<int[][]> solutionBoard = new ArrayList<>();
  private ArrayList<int[]> undoMoves = new ArrayList<>();

  /**************Constructor*****************/
  public GameEngine() {
    gridEasy =
        new int[][] {
          {5, 0, 0, 0, 8, 6, 0, 0, 1},
          {0, 0, 2, 7, 0, 1, 6, 0, 0},
          {0, 7, 1, 0, 0, 0, 2, 5, 0},
          {9, 1, 0, 0, 2, 0, 0, 7, 0},
          {3, 0, 0, 1, 4, 5, 0, 0, 6},
          {0, 6, 0, 0, 9, 0, 0, 2, 4},
          {0, 5, 3, 0, 0, 0, 4, 6, 0},
          {0, 0, 8, 9, 0, 3, 5, 0, 0},
          {2, 0, 0, 5, 1, 0, 0, 0, 7}
        };
    gridMiddle =
        new int[][] {
          {0, 0, 9, 0, 0, 5, 0, 0, 0},
          {0, 0, 0, 4, 3, 0, 1, 0, 0},
          {5, 4, 0, 0, 0, 9, 2, 0, 0},
          {0, 3, 0, 0, 2, 0, 9, 0, 0},
          {6, 0, 0, 0, 0, 0, 0, 0, 4},
          {0, 0, 5, 0, 6, 0, 0, 2, 0},
          {0, 0, 2, 3, 0, 0, 0, 6, 9},
          {0, 0, 7, 0, 5, 8, 0, 0, 0},
          {0, 0, 0, 1, 0, 0, 8, 0, 0}
        };
    gridHard =
        new int[][] {
          {6, 0, 0, 0, 1, 9, 7, 0, 0},
          {0, 0, 0, 0, 0, 0, 2, 0, 0},
          {0, 0, 0, 0, 0, 0, 3, 1, 9},
          {0, 0, 0, 4, 0, 0, 0, 0, 1},
          {3, 0, 0, 0, 0, 2, 9, 0, 0},
          {0, 0, 0, 0, 8, 5, 0, 2, 0},
          {9, 0, 0, 0, 0, 0, 0, 6, 5},
          {0, 0, 5, 3, 4, 8, 0, 0, 0},
          {8, 0, 7, 0, 0, 0, 0, 0, 0}
        };
    x = -1;
    y = -1;
  }

  /***************Override-Methods**********/
  @Override
  public boolean isGameOver() {
    solve(getGridMain());
    if (playerLife > 0 && getSolutionBoard().length != 1) {
      for (int i = 0; i < getSolutionBoard().length; i++) {
        if (!Arrays.equals(getGridMain()[i], getSolutionBoard()[i])) {
          return false;
        }
      }
      // return true;
    }
    return true; // vorher false
  }

  @Override
  public int[][] solve(int[][] board) {
    int[][] copyBoard = board.clone();

    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        if (copyBoard[y][x] == 0) {
          for (int n = 1; n <= 9; n++) {
            if (possible(y, x, n)) {
              copyBoard[y][x] = n;
              solve(copyBoard);
              copyBoard[y][x] = 0;
            }
          }
          return copyBoard;
        }
      }
    }
    if (solutionBoard.isEmpty()) {
      int[][] solution =
          Arrays.stream(copyBoard).map(i -> Arrays.stream(i).toArray()).toArray(int[][]::new);
      solutionBoard.add(solution);
    }
    return copyBoard;
  }

  @Override
  public int giveTip(int x, int y) {
    int[][] giveAdvise = getSolutionBoard();
    return giveAdvise[y][x];
  }

  @Override
  public boolean possible(int y, int x, int value) {
    if (value < 1 || value > 9) return false;
    int[][] board = getGridMain();

    for (int i = 0; i < 9; i++) {
      if (board[y][i] == value) return false;
    }

    for (int i = 0; i < 9; i++) {
      if (board[i][x] == value) return false;
    }

    int x0 = (x / 3) * 3;
    int y0 = (y / 3) * 3;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[y0 + i][x0 + j] == value) return false;
      }
    }
    return true;
  }

  @Override
  public void undo() {
    if (getY() == -1 && getX() == -1) return;
    System.out.println("Undo Move at Position (" + getX() + "/" + getY() + ")");
    this.getGridMain()[getY()][getX()] = 0;
    System.out.println(toString(getGridMain()));
  }

  /****************Getter/Setter**************/

  public ArrayList<int[]> getUndoMoves() {
    return undoMoves;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setXY(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int[][] getGridEasy() {
    return gridEasy;
  }

  public int[][] getGridMiddle() {
    return gridMiddle;
  }

  public int getGridMainIndex(int x, int y) {
    return this.gridMain[x][y];
  }

  public int[][] getGridHard() {
    return gridHard;
  }

  public int[][] getGridMain() {
    return gridMain;
  }

  public void setGridMain(int[][] gridMain) {
    int[][] copyMain = new int[9][9];
    for (int i = 0; i < copyMain.length; i++) {
      copyMain[i] = Arrays.copyOf(gridMain[i], gridMain[i].length);
    }
    // setPlayerLife(3);
    this.gridMain = copyMain;
  }

  public int[][] getSolutionBoard() {
    if (solutionBoard.isEmpty()) {
      System.out.println("Kein Solution!");
      return new int[1][1];
    } else {
      return solutionBoard.get(0);
    }
  }

  public int getPlayerLife() {
    return playerLife;
  }

  public void setPlayerLife(int playerLife) {
    this.playerLife = playerLife;
  }

  /*************Helper-Methods***************/

  public void makeMove(int y, int x, int value) {
    if (value > 0) {
      if (!possible(y, x, value)) {
        setPlayerLife(getPlayerLife() - 1);
      }
      this.getGridMain()[y][x] = value;
      System.out.println(toString(getGridMain()));
    } else {
      System.out.println("Incorrect Range. Pls try again between 1-9!");
    }
    undoMoves.add(new int[] {y, x, value});
  }

  public String toString(int[][] board) {
    String s = "";
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        s += " " + board[i][j];
      }
      s += "\n";
    }
    return s;
  }
}
