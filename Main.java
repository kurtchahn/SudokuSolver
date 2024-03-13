package com.kurt.pack;

public class Main {

    public static void main(String[] args) {

        int[][] puzzleMatrix = {
            {0,0,3,0,1,0,0,0,6},
            {0,1,0,0,5,8,0,0,0},
            {0,0,0,0,9,0,0,0,0},
            {5,0,0,0,0,0,0,4,1},
            {0,0,0,0,0,7,5,0,0},
            {2,0,4,0,0,0,8,0,0},
            {7,0,0,0,4,0,0,1,3},
            {0,9,0,0,0,2,0,0,0},
            {0,0,0,0,8,0,9,7,0}
        };

        Puzzle puzzle = new Puzzle(puzzleMatrix,3,3);

        puzzle.bruteSolve();
    }
}
