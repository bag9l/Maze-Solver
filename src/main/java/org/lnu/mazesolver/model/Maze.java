package org.lnu.mazesolver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Maze {
    private int[][] maze;

    public Maze(int mazeSize) {
        maze = new int[mazeSize][mazeSize];
        // Filling a maze with walls
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                maze[i][j] = 1;
            }
        }

        //  Recursive backtracking
        recursiveBackTracking(0, 0);

        //  Dirty fix - removing walls around exit
        maze[getWidth() - 2][getHeight() - 1] = 0;
    }

    public int getWidth() {
        return maze[0].length;
    }

    public int getHeight() {
        return maze.length;
    }

    public int getValue(int x, int y) {
        return maze[x][y];
    }

    private void recursiveBackTracking(int row, int col) {
        int[] directions = {1, 2, 3, 4}; // 1 - up, 2 - down, 3 - left, 4 - right
        Random random = new Random();

        //  Shuffle directions
        for (int i = directions.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int tmp = directions[index];
            directions[index] = directions[i];
            directions[i] = tmp;
        }

        //  Random choice
        for (int dir : directions) {
            int newRow = row;
            int newCol = col;

            switch (dir) {
                case 1: // up
                    newRow -= 2;
                    break;
                case 2: // down
                    newRow += 2;
                    break;
                case 3: // left
                    newCol -= 2;
                    break;
                case 4: // right
                    newCol += 2;
                    break;
            }

            //  Check if new coordinates are inside maze, and not visited
            if (newRow >= 0 && newRow < maze.length && newCol >= 0 && newCol < maze[0].length && maze[newRow][newCol] == 1) {
                //  Remove wall between current cell and new cell
                maze[newRow][newCol] = 0;
                maze[(newRow + row) / 2][(newCol + col) / 2] = 0;

                //  Recursion
                recursiveBackTracking(newRow, newCol);
            }
        }
    }
}
