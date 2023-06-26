import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import tester.Tester; 


class SnakeWorld extends World {
  int size;
  ArrayList<ArrayList<Cell>> board;
  ArrayList<Cell> snake;

  boolean up;
  boolean down;
  boolean right;
  boolean left;

  int appleX;
  int appleY;
  int score;

  static Color boardColor = new Color(99, 178, 112);

  boolean gameOver;

  SnakeWorld(int size){
    this.size = size;
    this.board = new ArrayList<ArrayList<Cell>>();
    this.snake = new ArrayList<Cell>();

    this.up = false;
    this.right = false;
    this.left = false;
    this.down = false;

    this.score = 0;

    this.appleX = new Random().nextInt(size - 1) + 1;
    this.appleY = new Random().nextInt(size - 1) + 1;

    this.gameOver = false;

    this.makeBoard();
  }

  //makes the background board
  void makeBoard() {
    for(int j = 0; j < size; j++) { // j is column
      this.board.add(new ArrayList<Cell>());
      for(int i = 0; i < size; i++) { // i is row
        this.board.get(j).add(new Cell(j, i, Color.black, false, false));
      }
    }
    this.board.get(size / 2).get(size / 2).isSnake = true;
    this.snake.add(this.board.get(size / 2).get(size / 2));

    this.board.get((size / 2) - 1).get((size / 2)).isSnake = true;
    this.snake.add(this.board.get((size / 2) - 1).get((size / 2)));

    this.board.get(appleX).get(appleY).isApple = true;
  }

  //ON KEY EVENT
  public void onKeyEvent(String key) {
    if(key.equals("up") && !this.gameOver) {
      //check if was down
      this.down = false;
      this.right = false;
      this.left = false;
      this.up = true;
    }
    else if(key.equals("down") && !this.gameOver) {
      //check if was up
      this.up = false;
      this.right = false;
      this.left = false;
      this.down = true;
    }
    else if(key.equals("right") && !this.gameOver) {
      //check if was left
      this.down = false;
      this.up = false;
      this.left = false;
      this.right = true;
    }
    else if(key.equals("left") && !this.gameOver) {
      //check if was right
      this.down = false;
      this.right = false;
      this.up = false;
      this.left = true;
    }
  }

  //ON TICK
  public void onTick() {

    //key handlers
    if(up && this.snake.get(0).row - 1 >= 0) {
      moveUp();
    }
    else if(down && this.snake.get(0).row + 1 < this.board.size()) {
      moveDown();
    }
    else if(right && this.snake.get(0).column + 1 < this.board.size()) {
      moveRight();
    }
    else if(left && this.snake.get(0).column - 1 >= 0) {
      moveLeft();
    }

    //apple handler
    if(this.snake.get(0).column == this.appleX && this.snake.get(0).row == this.appleY) {
      score++;
      this.board.get(appleX).get(appleY).isApple = false;
      addToTail();

      this.appleX = new Random().nextInt(size - 1) + 1;
      this.appleY = new Random().nextInt(size - 1) + 1;

      this.board.get(appleX).get(appleY).isApple = true;
    }

    //gameOver handlers
    if(this.snake.get(0).row == 0) {
      this.gameOver = true;
    }
    else if(this.snake.get(0).row == this.board.size() - 1) {
      this.gameOver = true;
    }
    else if(this.snake.get(0).column == this.board.size() - 1) {
      this.gameOver = true;
    }
    else if(this.snake.get(0).column == 0) {
      this.gameOver = true;
    }

    hitsItself();
  }

  void hitsItself() {
    for(int i = 3; i < this.snake.size(); i++) {
      if(this.snake.get(0).row == this.snake.get(i).row
          && this.snake.get(0).column == this.snake.get(i).column) {
        this.gameOver = true;
      }
    }
  }

  private void addToTail() {
    if(this.up) {
      this.board.get(this.snake.get(this.snake.size() - 1).column)
      .get(this.snake.get(this.snake.size() - 1).row - 1).isSnake = true;
      this.snake.add(this.board.get(this.snake.get(this.snake.size() - 1).column)
          .get(this.snake.get(this.snake.size() - 1).row - 1));
    }
    else if(this.down) {
      this.board.get(this.snake.get(this.snake.size() - 1).column)
      .get(this.snake.get(this.snake.size() - 1).row + 1).isSnake = true;
      this.snake.add(this.board.get(this.snake.get(this.snake.size() - 1).column)
          .get(this.snake.get(this.snake.size() - 1).row + 1));
    }
    else if(this.right) {
      this.board.get(this.snake.get(this.snake.size() - 1).column + 1)
      .get(this.snake.get(this.snake.size() - 1).row).isSnake = true;
      this.snake.add(this.board.get(this.snake.get(this.snake.size() - 1).column + 1)
          .get(this.snake.get(this.snake.size() - 1).row));
    }
    else if(this.left) {
      this.board.get(this.snake.get(this.snake.size() - 1).column - 1)
      .get(this.snake.get(this.snake.size() - 1).row).isSnake = true;
      this.snake.add(this.board.get(this.snake.get(this.snake.size() - 1).column - 1)
          .get(this.snake.get(this.snake.size() - 1).row));
    }
  }

  private void moveLeft() {
    this.snake.get(this.snake.size() - 1).isSnake = false;
    this.snake.remove(this.snake.get(this.snake.size() - 1));
    this.board.get(this.snake.get(0).column - 1).get(this.snake.get(0).row).isSnake = true;
    this.snake.add(0, this.board.get(this.snake.get(0).column - 1).get(this.snake.get(0).row));
  }

  private void moveRight() {
    this.snake.get(this.snake.size() - 1).isSnake = false;
    this.snake.remove(this.snake.get(this.snake.size() - 1));
    this.board.get(this.snake.get(0).column + 1).get(this.snake.get(0).row).isSnake = true;
    this.snake.add(0, this.board.get(this.snake.get(0).column + 1).get(this.snake.get(0).row));
  }

  private void moveDown() {
    this.snake.get(this.snake.size() - 1).isSnake = false;
    this.snake.remove(this.snake.get(this.snake.size() - 1));
    this.board.get(this.snake.get(0).column).get(this.snake.get(0).row + 1).isSnake = true;
    this.snake.add(0, this.board.get(this.snake.get(0).column).get(this.snake.get(0).row + 1));
  }

  private void moveUp() {
    this.snake.get(this.snake.size() - 1).isSnake = false;
    this.snake.remove(this.snake.get(this.snake.size() - 1));
    this.board.get(this.snake.get(0).column).get(this.snake.get(0).row - 1).isSnake = true;
    this.snake.add(0, this.board.get(this.snake.get(0).column).get(this.snake.get(0).row - 1));
  }

  @Override
  public WorldScene makeScene() {
    //create a worldScene that represent this maze (just the vertexes)
    WorldScene background = new WorldScene(size * 50, size * 50);

    background.placeImageXY(new RectangleImage(size * 50, size * 50, 
        OutlineMode.SOLID, boardColor),
        size * 25, size * 25);

    for (int j = 0; j < size; j++) {
      for (int i = 0; i < size; i++) {
        if(this.board.get(j).get(i).isSnake){
          background.placeImageXY(this.board.get(j).get(i).
              drawSnake(Color.blue),
              this.board.get(j).get(i).trueX(), 
              this.board.get(j).get(i).trueY());
        } 
        else if(this.board.get(j).get(i).isApple) {
          background.placeImageXY(this.board.get(j).get(i).
              drawApple(Color.red),
              this.board.get(j).get(i).trueX(), 
              this.board.get(j).get(i).trueY());
        }
        else {
          background.placeImageXY(this.board.get(j).get(i).
              drawSquare(Color.black),
              this.board.get(j).get(i).trueX(), 
              this.board.get(j).get(i).trueY());
        }
      }
    }
    return background;
  }

}