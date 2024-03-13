package com.kurt.pack;

public class Puzzle {

    private int[][] unsolvedPuzzle;
    private int[][] solvedPuzzle;
    private int rows;
    private int columns;
    private int blockRows;
    private int blockColumns;
    private int candidateRange;
    private int focusRow;
    private int focusColumn;

    private int debugCounter;

    public Puzzle(int[][] puzzle, int blockRows, int blockColumns)
    {
        this.debugCounter = 0;
        if (rows % blockRows != 0 || columns % blockColumns != 0)
        {
            throw new IllegalArgumentException("Invalid puzzle dimensions!");
        }

        this.rows = puzzle.length;
        this.columns = puzzle[0].length;

        this.unsolvedPuzzle = puzzle;
        this.solvedPuzzle = new int[rows][columns];
        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<columns; j++)
            {
                solvedPuzzle[i][j] = puzzle[i][j];
            }
        }

        this.blockRows = blockRows;
        this.blockColumns = blockColumns;

        this.candidateRange = blockRows * blockColumns;

        for (int[] row : puzzle)
        {
            for (int item : row)
            {
                if (item > candidateRange || item < 0)
                {
                    throw new IllegalArgumentException("Invalid puzzle elements!");
                }
            }
        }

        this.focusRow = 0;
        this.focusColumn = 0;
    }

    private void printSolvedPuzzle()
    {
        for (int[] row : solvedPuzzle)
        {
            for (int item : row)
            {
                System.out.print(item);
            }

            System.out.println();
        }
    }

    private boolean isReachedEnd()
    {
        boolean isReachedEnd = false;

        if (focusColumn == columns)
        {
            isReachedEnd = true;
        }

        return isReachedEnd;
    }

    private boolean isReturnedToBeginning()
    {
        boolean isReturnedToBeginning = false;

        if (focusColumn == -1)
        {
            isReturnedToBeginning = true;
        }

        return isReturnedToBeginning;
    }

    private boolean isCandidateUniqueInRow()
    {
        boolean isCandidateUniqueInRow = true;

        for (int testColumn = 0; testColumn < columns; testColumn++)
        {
            if(solvedPuzzle[focusRow][testColumn] == solvedPuzzle[focusRow][focusColumn] && testColumn != focusColumn)
            {
                isCandidateUniqueInRow = false;
                return isCandidateUniqueInRow;
            }
        }

        return isCandidateUniqueInRow;
    }

    private boolean isCandidateUniqueInColumn()
    {
        boolean isCandidateUniqueInColumn = true;

        for (int testRow = 0; testRow < rows; testRow++)
        {
            if (solvedPuzzle[testRow][focusColumn] == solvedPuzzle[focusRow][focusColumn] && testRow != focusRow)
            {
                isCandidateUniqueInColumn = false;
                return isCandidateUniqueInColumn;
            }
        }

        return isCandidateUniqueInColumn;
    }

    private boolean isCandidateUniqueInBlock()
    {

        boolean isCandidateUniqueInBlock = true;

        for (int testRow = 0; testRow < rows; testRow++)
        {
            if (testRow / blockRows != focusRow / blockRows)
            {
                continue;
            }

            for (int testColumn = 0; testColumn < columns; testColumn++)
            {
                if (testColumn / blockColumns != focusColumn / blockColumns)
                {
                    continue;
                }

                // We have now made sure the testBox is in the same block as the focusBox

                if (solvedPuzzle[testRow][testColumn] == solvedPuzzle[focusRow][focusColumn] && testRow != focusRow && testColumn != focusColumn)
                {
                    isCandidateUniqueInBlock = false;
                    return isCandidateUniqueInBlock;
                }
            }
        }

        return isCandidateUniqueInBlock;
    }

    private boolean isCandidateValid()
    {
        return (isCandidateUniqueInRow() && isCandidateUniqueInColumn() && isCandidateUniqueInBlock());
    }

    private void focusOnNextEmptyBox()
    {
        focusRow++;

        if (focusRow == rows)
        {
            focusColumn++;
            focusRow=0;
        }

        if (focusColumn == columns)
        {
            return;
        }

        if (unsolvedPuzzle[focusRow][focusColumn] != 0)
        {
            focusOnNextEmptyBox();
        }
    }

    private void focusOnPreviousEmptyBox()
    {
        focusRow--;

        if (focusRow == -1)
        {
            focusColumn--;
            focusRow = rows-1;
        }

        if (focusColumn == -1)
        {
            return;
        }

        if (unsolvedPuzzle[focusRow][focusColumn] != 0)
        {
            focusOnPreviousEmptyBox();
        }
    }

    private void testAllCandidates()
    {
        if (isReachedEnd())
        {
            System.out.println("Puzzle Solved! :D");
            printSolvedPuzzle();
            return;
        }

        for (int i=1; i<=candidateRange && !isReachedEnd(); i++)
        {
            solvedPuzzle[focusRow][focusColumn] = i;

            if (isCandidateValid())
            {
                focusOnNextEmptyBox();
                testAllCandidates();
            }
        }

        if (isReachedEnd())
        {
            return;
        }

        solvedPuzzle[focusRow][focusColumn] = 0;
        focusOnPreviousEmptyBox();

        if (isReturnedToBeginning())
        {
            System.out.println("Puzzle has no solution :(");
        }

    }

    public void bruteSolve()
    {
        this.testAllCandidates();
    }

}
