package algorithms.mazeGenerators;

import java.util.Random;

public class Maze {
    // constructor creates an empty maze[][] & initialize 0's
    public int[][] maze;
    public Position startPosition;
    public Position goalPosition;
    public int row;
    public int col;
    public Position[][] PositionArray;


    public Maze(int row, int col) {
        try {
            if (row == 0 && col == 0)
                throw new Exception("can't be  Maze");
        } catch (Exception e) {
            e.getMessage();
        }

        this.maze = new int[row][col];
        this.row = row;
        this.col = col;
        this.PositionArray = new Position[row][col];
        this.startPosition = new Position();
        this.goalPosition = new Position();
        this.startPosition.setColumnIndex(0);
        this.startPosition.setRowIndex(0);
        this.goalPosition.setRowIndex(this.maze.length - 1);
        this.goalPosition.setColumnIndex(this.maze[0].length - 1);
        //now create default maze with no walls :
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.maze[i][j] = 0;
                this.PositionArray[i][j] = new Position(i, j);

            }
        }


    }


    public void print() {

        for (int row = 0; row < this.maze.length; row++) {
            System.out.printf("%s", "{ ");
            for (int col = 0; col < this.maze[0].length; col++) {
                if (row == startPosition.getRowIndex() && col == startPosition.getColumnIndex()) {
                    System.out.printf("%s", "S ");
                    continue;
                }
                if (row == goalPosition.getRowIndex() && col == goalPosition.getColumnIndex()) {
                    System.out.printf("%s", "E ");
                    continue;
                }

                System.out.printf("%s", this.maze[row][col] + " ");
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

    public byte[] Bytecalc() {
        byte[] arr_of_maze = new byte[this.col * this.row];
        int k = 0;
        for (int i = 0; i < this.row; i++) {//this loop change 2d array to 1d array of bytes
            for (int j = 0; j < this.col; j++) {
                arr_of_maze[k] = (byte) this.maze[i][j];
                k++;
            }
        }
        //byte[] final_arr = new byte[this.col * this.row];//array of 0,1 that hold maze data , this array
        // representing each number(0/1) in a raw
        //for example - if we have 0,0,0,0,0,1,1,0,0,0,0 -in final_arr it looks like 5,2,4
        byte zero = 0;
        byte one = 0;
        int index = 0;
        byte curr = arr_of_maze[0];
        int final_arr_index = 0;
        byte[] start_with_zero_arr = new byte[this.col * this.row];//array of 0,1 that hold maze data , first, count number of times we get 0 .
        if (curr == 1) {
            start_with_zero_arr[0] = 0;
            final_arr_index++;
        }
        while (index<arr_of_maze.length ) {
            if (curr == 0) {
                if (one != 0) {
                    start_with_zero_arr[final_arr_index]=one;
                    final_arr_index++;
                    index++;
                    curr = arr_of_maze[index];
                    one = 0;
                }
                zero++;
                curr = arr_of_maze[index];
                continue;
            }

            if (curr == 1) {
                if (zero != 0) {
                    start_with_zero_arr[final_arr_index]=zero;
                    final_arr_index++;
                    index++;
                    curr = arr_of_maze[index];
                    zero = 0;
                }
            }
            one++;
            curr = arr_of_maze[index];
            continue;
        }
        return start_with_zero_arr;
    }







    public byte[] toByteArray() {
        byte[] arr_of_bytes = new byte[5];
        arr_of_bytes[0] = (byte) this.row;
        arr_of_bytes[1] = (byte) this.col;
        byte [] arr_of_maze = Bytecalc();
    }
}