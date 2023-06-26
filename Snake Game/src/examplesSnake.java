import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import javalib.impworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.RectangleImage;
import tester.Tester;

class ExamplesSnake{
  ExamplesSnake(){}
  
  void testMazeWorld(Tester t) {
    SnakeWorld starterWorld = new SnakeWorld(15);
    starterWorld.bigBang(starterWorld.size * 50, 
        starterWorld.size * 50, 0.1);
  }
}