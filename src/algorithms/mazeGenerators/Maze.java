package algorithms.mazeGenerators;

import java.util.Random;

public class Maze
{
    // constructor creates an empty maze[][] & initialize 0's
    public int[][] maze;
    public Position startPosition;
    public Position goalPosition;
    public int row;
    public int col;
    public Position[][] PositionArray;


    public  Maze(int row, int col) {
            try {
                if (row == 0 && col == 0)
                    throw new Exception("can't be  Maze");
            }
            catch (Exception e) {e.getMessage();}

            this.maze = new int[row][col];
            this.row=row;
            this.col=col;
            this.PositionArray = new Position[row][col];
            this.startPosition=new Position();
            this.goalPosition = new Position();
            this.startPosition.setColumnIndex(0);
            this.startPosition.setRowIndex(0);
            this.goalPosition.setRowIndex(this.maze.length-1);
            this.goalPosition.setColumnIndex(this.maze[0].length-1);
            //now create default maze with no walls :
            for (int i= 0 ;i<row ; i++){
                for (int j = 0 ; j <col ; j++){
                    this.maze[i][j]=0;
                    this.PositionArray[i][j] = new Position(i,j);

                }
            }



        }




    public void print() {

        for (int row = 0; row < this.maze.length; row++)
        {
            System.out.printf("%s","{ ");
            for (int col = 0; col < this.maze[0].length; col++)
            {
                if (row == startPosition.getRowIndex() && col == startPosition.getColumnIndex())
                {
                    System.out.printf("%s","S ");
                    continue;
                }
                if (row == goalPosition.getRowIndex()  && col == goalPosition.getColumnIndex())
                {
                    System.out.printf("%s","E ");
                    continue;
                }

                System.out.printf("%s",this.maze[row][col]+ " ");
            }
            System.out.printf("%s", "}\n");
        }

    }


    public Position getStartPosition() {
        return this.startPosition;
    }


    public Position getGoalPosition() {
        return this.goalPosition;


    }
}