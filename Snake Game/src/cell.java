import java.awt.Color;
import java.util.ArrayList;

import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

//represent a vertex/cell of a maze
class Cell {
  int column;
  int row;
  Color color;
  boolean isSnake;
  boolean isApple;

  //constructor for board cell
  Cell(int x, int y, Color color, boolean isSnake, boolean isApple) {
    this.column = x;
    this.row = y;
    this.color = color;
    this.isSnake = isSnake;
  }


  //checking equality of this vertex and a given object
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Cell)) {
      return false;
    }

    Cell v = (Cell)other;
    return this.column == (v.column)
        && this.row == v.row;
  }

  //overriding the hashcode for this object
  @Override
  public int hashCode() {
    return this.column * 10000 + this.row;
  }

  //draw a vertex
  WorldImage drawSquare(Color c) {
    return new RectangleImage(50, 50, OutlineMode.OUTLINE, c);
  }

  public int trueX() {
    return 50 * column + 50 / 2;
  }

  public int trueY() {
    return 50 * row + 50 / 2;
  }

  void setColor(Color c) {
    this.color = c;
  }

  WorldImage drawSnake(Color c) {
    return new RectangleImage(50, 50, OutlineMode.SOLID, c);
  }

  public WorldImage drawApple(Color c) {
    return new RectangleImage(50, 50, OutlineMode.SOLID, c);
  }

}