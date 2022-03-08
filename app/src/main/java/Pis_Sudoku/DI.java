package Pis_Sudoku;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;

public class DI extends PApplet {

  public int height = 900;
  public int width = 900;
  public int stage = 1;
  public boolean checkMenu = true;
  public boolean gameOver = false;
  public int begin;
  int duration;

  GameEngine gE;

  public static void main(String[] args) {
    PApplet.runSketch(new String[] {""}, new DI());
  }

  public void setup() {
    gE = new GameEngine();
    begin = millis();
    duration = 0;
  }

  public void settings() {
    size(width, height);
  }

  public void draw() {
    if (checkMenu) {
      drawMenu();
    } else {
      strokeDraw();
      checkBoard(gE.getGridMain());
      if (gE.getPlayerLife() == 0) {
        drawSolutionBoard();
        int timer = millis();
        duration = (timer - begin) / 1000;
        if (duration == 8) {
          gameOver = true;
          checkMenu = true;
        }
      } else if (gE.isGameOver() && gE.getPlayerLife() != 0) {
        gameOver = true;
        checkMenu = true;
      }
    }
  }

  public void mousePressed() {
    int x = mouseX / 100;
    int y = mouseY / 100;

    if (!checkMenu) {
      if (gE.getGridMain()[y][x] == 0) {
        if (gE.getX() == -1 && gE.getY() == -1) {
          gE.setXY(x, y);
          fill(200);
          rect(x * 100, y * 100, 100, 100);
        } else if ((x != gE.getX() && y != gE.getY()
                || x == gE.getX() && y != gE.getY()
                || x != gE.getX())
            && gE.getGridMain()[y][x] == 0) {
          fill(255);
          rect(gE.getX() * 100, gE.getY() * 100, 100, 100);
          gE.setXY(x, y);
          fill(200);
          rect(x * 100, y * 100, 100, 100);
        }
      } else if (gE.getGridMain()[y][x] != 0 && !gE.getUndoMoves().isEmpty()) {
        for (int[] a : gE.getUndoMoves()) {
          if (a[0] == y && a[1] == x) {
            gE.setXY(x, y);
            fill(200);
            rect(x * 100, y * 100, 100, 100);
            break;
          }
        }
      }
    }
  }

  public void keyPressed() {
    if (!checkMenu) {

      if (key == 'b' || key == 'B') {
        newGame();
        return;
      }

      if (key == 'u' || key == 'U') {
        if (!gE.getUndoMoves().isEmpty()) {
          gE.undo();
          fill(255);
          rect(gE.getX() * 100, gE.getY() * 100, 100, 100);
        }
        return;
      }

      if ((key == 'h' || key == 'H') && gE.getX() != -1 && gE.getY() != -1) {
        if (gE.getSolutionBoard().length == 1) {
          System.out.println("Es gibt keine Lösung. Zug rückgängig machen.");
        } else {
          int helpX = gE.getX();
          int helpY = gE.getY();
          gE.makeMove(helpY, helpX, gE.giveTip(helpX, helpY));
          System.out.println("Give Advise at\n(" + helpX + "/" + helpY + ")");
          fill(255);
          rect(helpX * 100, helpY * 100, 100, 100);
        }
        return;
      }

      if (gE.getX() != -1 && gE.getY() != -1) {
        int value = -1;
        if (key == '1') {
          value = 1;
        } else if (key == '2') {
          value = 2;
        } else if (key == '3') {
          value = 3;
        } else if (key == '4') {
          value = 4;
        } else if (key == '5') {
          value = 5;
        } else if (key == '6') {
          value = 6;
        } else if (key == '7') {
          value = 7;
        } else if (key == '8') {
          value = 8;
        } else if (key == '9') {
          value = 9;
        }

        if (!gE.possible(gE.getY(), gE.getX(), value) && value != -1) {
          System.out.println("False Move. Your current life is: " + (gE.getPlayerLife() - 1));
          fill(255, 0, 0);
        } else {
          fill(255);
        }
        gE.makeMove(gE.getY(), gE.getX(), value);
        rect(gE.getX() * 100, gE.getY() * 100, 100, 100);
      }
    }
  }

  public void newGame() {
    GameEngine gE1 = new GameEngine();
    gE = gE1;
    checkMenu = true;
    stage = 1;
    begin = millis();
    duration = 0;
    int timer = millis();
    duration = (timer - begin) / 1000;
  }

  public void drawBoard() {
    background(200);
    for (int i = 0; i < height; i += 100) { // Für Spalte
      for (int j = 0; j < width; j += 100) { // Für Zeile
        stroke(180);
        rect(j, i, 100, 100);
        if (j % 3 == 0 && j != 0) {
          stroke(0);
          line(j, 0, j, 900);
        }
      }
      if (i % 3 == 0 && i != 0) {
        stroke(0);
        line(0, i, 900, i);
      }
    }
  }

  public void strokeDraw() {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        stroke(180);
        if (j % 3 == 0 && j != 0) {
          stroke(0);
          line(j * 100, 0, j * 100, 900);
        }
      }
      if (i % 3 == 0 && i != 0) {
        stroke(0);
        line(0, i * 100, 900, i * 100);
      }
    }
  }

  public void checkBoard(int[][] grid) {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (grid[i][j] != 0) {
          textSize(30);
          fill(0);
          text(grid[i][j], j * 100 + 40, i * 100 + 60);
        }
      }
    }
  }

  public void drawSolutionBoard() {
    fill(255);
    drawBoard();
    strokeDraw();
    if (stage == 2) {
      gE.setGridMain(gE.getGridEasy());
    } else if (stage == 3) {
      gE.setGridMain(gE.getGridMiddle());
    } else if (stage == 4) {
      gE.setGridMain(gE.getGridHard());
    }
    gE.solve(gE.getGridMain());
    gE.setGridMain(gE.getSolutionBoard());
    checkBoard(gE.getGridMain());
  }

  public void drawMenu() {
    background(0);
    textSize(50);
    fill(255);
    if (!gameOver) {
      text("Sudoku Game", 290, 250);
      text("Press 1 for Easy Sudoku", 150, 350);
      text("Press 2 for Middle Sudoku", 150, 425);
      text("Press 3 for Hard Sudoku", 150, 500);
      textSize(20);
      text("Press:\n 'h' for advice\n 'u' for undo\n 'b' to go back to menu", 50, 700);

      if (key == '1') {
        stage = 2;
        gE.setGridMain(gE.getGridEasy());
        checkMenu = false;
        drawBoard();
        gE.solve(gE.getGridMain());
      } else if (key == '2') {
        stage = 3;
        gE.setGridMain(gE.getGridMiddle());
        checkMenu = false;
        drawBoard();
        gE.solve(gE.getGridMain());
      } else if (key == '3') {
        stage = 4;
        gE.setGridMain(gE.getGridHard());
        checkMenu = false;
        drawBoard();
        gE.solve(gE.getGridMain());
      }
    } else {
      if (gE.getPlayerLife() == 0) {
        text("Unfortunately, You Lose!", 200, 300);
      } else {
        text("Congratulations, You win!", 150, 300);
      }
      text("Press 'e' for exit.", 275, 450);
      if (key == 'e' || key == 'E') exit();
    }
  }
}
