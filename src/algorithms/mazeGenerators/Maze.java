package algorithms.mazeGenerators;

import java.math.BigInteger;
import java.util.Random;

public class Maze {
    // constructor creates an empty maze[][] & initialize 0's
    public int[][] maze;
    public Position startPosition;
    public Position goalPosition;
    public int row;
    public int col;
    public Position[][] PositionArray;
    public byte[] bytearr;


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
//    public byte[] toByteArray()
//    {
//        /// TODO Transform the maze into a byteArray and return it.
//        int start_row = this.startPosition.getRowIndex();
//        int start_col = this.startPosition.getColumnIndex();
//        int end_row = this.goalPosition.getRowIndex();
//        int  end_col = this.goalPosition.getColumnIndex();
//
//        this.bytearr = new byte[12+ this.row*this.col];
////        byte[] finalBytes = new byte[12 + this.row*this.col];
//        byte[] b1 = IntTooBytes(start_row);
//        byte[] b2 = IntTooBytes(start_col);
//        byte[] b3 = IntTooBytes(end_row);
//        byte[] b4 = IntTooBytes(end_col);
//        byte[] b5 = IntTooBytes(this.row);
//        byte[] b6 = IntTooBytes(this.col);
//
//        for (int i = 0; i < 2; i++)
//        {
//            bytearr[i] = b1[i];
//            bytearr[i+2] = b2[i];
//            bytearr[i+4] = b3[i];
//            bytearr[i+6] = b4[i];
//            bytearr[i+8] = b5[i];
//            bytearr[i+10] = b6[i];
//        }
//        int counter = 12;
//        for (int i = 0; i < row; i++)
//        {
//            for (int j = 0; j < col; j++)
//            {
//                bytearr[counter++] = (byte)this.maze[i][j];
//            }
//        }
//        return bytearr;
//    }
//
//    public Maze(byte[] bytes)
//    {
//        byte[] b1 = new byte[2];
//        byte[] b2 = new byte[2];
//        byte[] b3 = new byte[2];
//        byte[] b4 = new byte[2];
//        byte[] b5 = new byte[2];
//        byte[] b6 = new byte[2];
//        for (int i = 0; i < 2; i++)
//        {
//            b1[i] =bytes[i];
//            b2[i] =bytes[i+2];
//            b3[i] =bytes[i+4];
//            b4[i] =bytes[i+6];
//            b5[i] =bytes[i+8];
//            b6[i] =bytes[i+10];
//        }
//
//        int start_row = new BigInteger(b1).intValue();
//        int start_col = new BigInteger(b2).intValue();
//        int end_row = new BigInteger(b3).intValue();
//        int end_col = new BigInteger(b4).intValue();
//        int row = new BigInteger(b5).intValue();
//        int col = new BigInteger(b6).intValue();
//
//        try {
//            if (row == 0 && col == 0)
//                throw new Exception("No Maze");
//        }
//        catch (Exception e) {e.getMessage();}
//
//
//        this.maze =new int[row][col];
//        this.startPosition = new Position();
//        this.goalPosition = new Position();
//        this.startPosition.setRowIndex(start_row);
//        this.startPosition.setColumnIndex(start_col);
//        this.goalPosition.setRowIndex(end_row);
//        this.goalPosition.setColumnIndex(end_col);
//        int counter = 0;
//        for (int i = 0; i < row; i++)
//        {
//            for (int j = 0; j < col; j++)
//            {
//                this.maze[i][j] = bytes[12 + counter];
//                counter++;
//            }
//        }
//        this.PositionArray = new Position[row][col];
//        for (int i = 0; i < row; i++)
//        {
//            for (int j = 0; j < col; j++)
//            {
//                PositionArray[i][j] = new Position(i,j);
//            }
//        }
//        this.row = this.maze.length;
//        this.col = this.maze[0].length;
//
//
//    }
//
//
//    //converts int into bytes.
//    public byte[] IntTooBytes(int value)
//    {
//        byte[] bytes = new byte[2];
//        int length = bytes.length;
//        for (int i = 0; i < length; i++) {
//            bytes[length - i - 1] = (byte) (value & 0xFF);
//            value >>= 8;
//        }
//        return bytes;
//    }
//
//    // converts bytes into int.
//    public int BytesTooInt(byte[] bytes)
//    {
//        int v = new BigInteger(bytes).intValue();
//        return v;
//    }




    public byte[] toByteArray() {
        byte[] arr_of_bytes = new byte[(this.col * this.row)+12];
        int size = (this.col)*1000 + this.row;
        int startPosition = (this.getStartPosition().getRowIndex())*1000+this.getStartPosition().getColumnIndex();
        int goalposition = ((this.getGoalPosition().getRowIndex())*1000)+this.getGoalPosition().getColumnIndex();
        // Store the size information in the first four bytes of the byte array
        arr_of_bytes[0] = (byte) (size >> 24);
        arr_of_bytes[1] = (byte) (size >> 16);
        arr_of_bytes[2] = (byte) (size >> 8);
        arr_of_bytes[3] = (byte) size;
        // Store the Start Position information in the next four bytes of the byte array
        arr_of_bytes[4] = (byte) (startPosition >> 24);
        arr_of_bytes[5] = (byte) (startPosition >> 16);
        arr_of_bytes[6] = (byte) (startPosition >> 8);
        arr_of_bytes[7] = (byte) startPosition;
        // Store the Goal Position information in the next four bytes of the byte array
        arr_of_bytes[8] = (byte) (goalposition >> 24);
        arr_of_bytes[9] = (byte) (goalposition >> 16);
        arr_of_bytes[10] = (byte) (goalposition >> 8);
        arr_of_bytes[11] = (byte) goalposition;
        int k = 12; //from 7st place we place all the maze data
        for (int i = 0; i < this.row; i++) {//this loop change 2d array to 1d array of bytes
            for (int j = 0; j < this.col; j++) {
                arr_of_bytes[k] = (byte) this.maze[i][j];
                k++;
            }
        }
        return arr_of_bytes;
    }
    public byte[] IntTooBytes(int value) {
        byte[] bytes = new byte[2];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            bytes[length - i - 1] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return bytes;
    }
    public Maze(byte[] maze_arr) {
        // Extract the size information from the first four bytes of the maze_arr byte array
        int size = (maze_arr[0] << 24) | (maze_arr[1] << 16) | (maze_arr[2] << 8) | maze_arr[3];
        int col = size / 1000;
        int row = size % 1000;

        // Extract the start position information from the next four bytes of the maze_arr byte array
        int startPosition = (maze_arr[4] << 24) | (maze_arr[5] << 16) | (maze_arr[6] << 8) | maze_arr[7];

        // Extract the goal position information from the next four bytes of the maze_arr byte array
        int goalPosition = (maze_arr[8] << 24) | (maze_arr[9] << 16) | (maze_arr[10] << 8) | maze_arr[11];

        // Create a new Maze object with the extracted values
        Maze new_maze = new Maze(col, row);
        new_maze.setStartPosition(new Position(startPosition / 1000, startPosition % 1000));
        new_maze.setGoalPosition(new Position(goalPosition / 1000, goalPosition % 1000));

        // Populate the maze data from the remaining bytes of the maze_arr byte array
        int k = 12; // Start from index 12 to skip the size and position bytes
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                new_maze.maze[i][j] = maze_arr[k];
                k++;
            }
        }
    }

    private void setGoalPosition(Position position) {
        this.goalPosition= new Position(position.rowX,position.colY);
    }

    private void setStartPosition(Position position) {
        this.startPosition= new Position(position.rowX,position.colY);

    }

}
