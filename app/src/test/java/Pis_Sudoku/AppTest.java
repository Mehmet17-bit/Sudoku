/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Pis_Sudoku;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {

  GameEngine geTest = new GameEngine();

  // The length of the Grid must be 81.
  @Test
  public void checkGridLengthAtBegin() {
    int[][] arEasy = geTest.getGridEasy();
    int[][] arMiddle = geTest.getGridMiddle();
    int[][] arHard = geTest.getGridHard();
    Assert.assertEquals("The length of the Grid isn't 81.", 81, (arEasy.length) * 9);
    Assert.assertEquals("The length of the Grid isn't 81.", 81, (arMiddle.length) * 9);
    Assert.assertEquals("The length of the Grid isn't 81.", 81, (arHard.length) * 9);
  }

  // Checks the Life of the Player at Begin of the Game
  @Test
  public void checkPlayerLifeAtBegin() {
    Assert.assertEquals("Player hasn't 3 Life at the Beginning.", geTest.getPlayerLife(), 3);
  }

  // Checks if the Player won and have mor than zero Life.
  @Test
  public void HasWon() {
    Assert.assertNotEquals("Player hasnt 0 Life and finished the Game.", 0, geTest.getPlayerLife());
  }

  // Checks false Move
  @Test
  public void makeFalseMove() {
    geTest.setGridMain(geTest.getGridEasy());
    Assert.assertFalse("The move is valid", geTest.possible(4, 4, 8));
  }

  // Checks the Range of setting number
  @Test
  public void checkPossibleRange() {
    geTest.setGridMain(geTest.getGridEasy());
    Assert.assertFalse("The Range isn't correctly.", geTest.possible(4, 2, -2));
  }

  // Checks if the current Grid changed after giving a Advice
  @Test
  public void checkIfGivingAdviceChangeTheCurrentGrid() {
    int[][] solution = geTest.getSolutionBoard();
    geTest.setGridMain(geTest.getGridMiddle());
    int advice = geTest.giveTip(0, 0);
    geTest.makeMove(0, 0, advice);
    Assert.assertEquals(
        "Give Advice doesn't change the current Grid.", solution[0][0], geTest.getGridMain()[0][0]);
  }

  // Checks if the Move is put on the current Grid
  @Test
  public void checkIfMoveWasSuccessfulToPut() {
    geTest.setGridMain(geTest.getGridMiddle());
    geTest.makeMove(0, 0, 2);
    Assert.assertEquals("The Move wasn't successful put.", geTest.getGridMainIndex(0, 0), 2);
  }

  // Checks if the Function solve has filled the current Grid
  @Test
  public void checkIfSolveFunctionFillTheGrid() {
    geTest.setGridMain(geTest.getGridEasy());
    geTest.solve(geTest.getGridMain());
    int[][] solveEasy = geTest.getSolutionBoard();
    for (int[] ints : solveEasy) {
      for (int j = 0; j < solveEasy.length; j++) {
        Assert.assertNotEquals("The Function doesn't filled the current Grid.", 0, ints[j]);
      }
    }
  }

  // Checks the Function undo and the Number at the position was removed
  @Test
  public void checkUndoMove() {
    geTest.setGridMain(geTest.getGridMiddle());
    geTest.makeMove(0, 0, 5);
    geTest.setXY(0, 0);
    geTest.undo();
    Assert.assertEquals(
        "The undo Function doesn't removed the Number.", 0, geTest.getGridMain()[0][0]);
  }

  // Checks if the Life of the Player decrements after folse Move
  @Test
  public void LosePlayerLife() {
    geTest.setGridMain(geTest.getGridEasy());
    geTest.makeMove(0, 1, 1);
    geTest.makeMove(0, 2, 2);
    geTest.makeMove(0, 3, 1);
    Assert.assertEquals("Player Lose Life by false move.", 0, geTest.getPlayerLife());
  }

  // Checks if the Grid is chenged
  @Test
  public void checkChangeGrid() {
    geTest.setGridMain(geTest.getGridEasy());
    geTest.setGridMain(geTest.getGridMiddle());
    Assert.assertArrayEquals(
        "The Grid doesn't changed.", geTest.getGridMiddle(), geTest.getGridMain());
  }

  // Checks if the current Grid is filling by the Function giveTip()
  @Test
  public void checksIfCurrentGridFilledByGiveTip() {
    geTest.setGridMain(geTest.getGridEasy());
    geTest.solve(geTest.getGridMain());
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (geTest.getGridMain()[i][j] == 0) {
          geTest.makeMove(i, j, geTest.giveTip(j, i));
        }
      }
    }
    Assert.assertArrayEquals(
        "The current Grid doesn't fill by the function 'giveTip()'.",
        geTest.getSolutionBoard(),
        geTest.getGridMain());
  }

  // Checks the Row with Index zero if its similar with the Solutionboard
  @Test
  public void checkRow() {
    geTest.setGridMain(geTest.getGridEasy());
    geTest.makeMove(0, 1, 3);
    geTest.makeMove(0, 2, 9);
    geTest.makeMove(0, 3, 2);
    geTest.makeMove(0, 6, 7);
    geTest.makeMove(0, 7, 4);
    geTest.solve(geTest.getGridMain());
    for (int i = 0; i < 9; i++) {
      if (geTest.getSolutionBoard()[i][0] == geTest.getGridMain()[i][0]) {
        Assert.assertEquals(
            "The giving Row doesn't similar with the Solutionboard.",
            geTest.getSolutionBoard()[i][0],
            geTest.getGridMain()[i][0]);
      }
    }
  }

  // Checks the Column with Index six if its similar with the Solutionboard
  @Test
  public void checkColumn() {
    geTest.setGridMain(geTest.getGridHard());
    geTest.makeMove(3, 6, 5);
    geTest.makeMove(5, 6, 6);
    geTest.makeMove(6, 6, 8);
    geTest.makeMove(7, 6, 1);
    geTest.makeMove(8, 6, 4);
    geTest.solve(geTest.getGridMain());
    for (int i = 0; i < 9; i++) {
      if (geTest.getSolutionBoard()[6][i] == geTest.getGridMain()[6][i]) {
        Assert.assertEquals(
            "The giving Column doesn't similar with the Solutionboard.",
            geTest.getSolutionBoard()[6][i],
            geTest.getGridMain()[6][i]);
      }
    }
  }

  // Checks the inner Field (in the middle up) if its similar with the Solutionboard
  @Test
  public void checkInnerField() {
    geTest.setGridMain(geTest.getGridMiddle());
    geTest.makeMove(0, 3, 8);
    geTest.makeMove(0, 4, 7);
    geTest.makeMove(1, 5, 2);
    geTest.makeMove(2, 3, 6);
    geTest.makeMove(2, 4, 1);
    geTest.solve(geTest.getGridMain());
    for (int i = 3; i < 6; i++) {
      for (int j = 0; j < 3; j++) {
        if (geTest.getSolutionBoard()[i][j] == geTest.getGridMain()[i][j]) {
          Assert.assertEquals(
              "The inner Field doesn't similar with the Solutionboard.",
              geTest.getSolutionBoard()[i][j],
              geTest.getGridMain()[i][j]);
        }
      }
    }
  }

  // Checks if the Grid can change several times
  @Test
  public void CheckGridChangeSeveralTimes() {
    geTest.setGridMain(geTest.getGridEasy());
    geTest.setGridMain(geTest.getGridMiddle());
    geTest.setGridMain(geTest.getGridHard());
    geTest.setGridMain(geTest.getGridEasy());
    Assert.assertArrayEquals(
        "The Grid can't change several Time.", geTest.getGridEasy(), geTest.getGridMain());
  }

  // Checks the Function undo() that can't use at the beginning
  @Test
  public void CantUseUndoAtFirstMove() {
    geTest.setGridMain(geTest.getGridMiddle());
    // getX() und getY() ist anfangs immer -1 und bei undo sollte nie was passieren.
    geTest.undo();
    Assert.assertArrayEquals(
        "The Function undo() is used at first Move.", geTest.getGridMiddle(), geTest.getGridMain());
  }

  // Checks if the Function undo() can used several Time
  @Test
  public void canUseUndoSeveralTime() {
    geTest.setGridMain(geTest.getGridMiddle());
    geTest.makeMove(0, 0, 1);
    geTest.makeMove(0, 1, 2);
    geTest.makeMove(1, 0, 8);
    geTest.makeMove(1, 1, 7);
    geTest.makeMove(1, 2, 6);
    geTest.makeMove(2, 2, 3);
    geTest.setXY(1, 0);
    geTest.undo();
    geTest.setXY(0, 0);
    geTest.undo();
    Assert.assertEquals("The Number doesn't removed.", 0, geTest.getGridMain()[0][1]);
    Assert.assertEquals("The Number doesn't removed.", 0, geTest.getGridMain()[0][0]);
  }

  // Checks the Life of the Player after changing the current Grid
  @Test
  public void checkPlayerLifeAfterChangeGrid() {
    geTest.setGridMain(geTest.getGridEasy());
    geTest.makeMove(4, 6, 1);
    geTest.setGridMain(geTest.getGridMiddle());
    Assert.assertTrue("The Life of the Player isn't correctly.", geTest.getPlayerLife() >= 2);
  }

  // Checks if the Player can make 3 false Moves after changing the current Grid to Lose the Game
  @Test
  public void checkPlayerLifeAfterWrongMoveAndThanGameOverOnNewBoard() {
    geTest.setGridMain(geTest.getGridEasy());
    geTest.makeMove(4, 6, 1);
    geTest.setGridMain(geTest.getGridMiddle());
    geTest.makeMove(0, 6, 1);
    geTest.makeMove(0, 7, 1);
    geTest.makeMove(0, 8, 1);
    Assert.assertTrue(
        "The Life of the Player isn't zero after making three false Moves after changing the Grid.",
        0 != geTest.getPlayerLife());
  }
}
