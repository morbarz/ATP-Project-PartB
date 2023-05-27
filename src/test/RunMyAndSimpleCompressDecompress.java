package test;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import IO.SimpleCompressorOutputStream;
import IO.SimpleDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class RunMyAndSimpleCompressDecompress {
    public static void main(String[] args){
        int[][] rowsColumnsCombinations = {
                {50,50},{100,100},{150,150},{200,200},{250,250},{300,300},{350,350},{400,400},{450,450},{500,500},{3000,3000}
        };
        for (int i = 0; i < rowsColumnsCombinations.length; i++){
            int rows = rowsColumnsCombinations[i][0];
            int cols = rowsColumnsCombinations[i][1];

            System.out.println("testing {" + rows + "," + cols + "}..." );
            testSimpleCompressDecompress(rows, cols);
            System.out.print("Simple Size:\n\t");
            printFileSizeNIO("simpleSavedMaze.maze");

            testMyCompressDecompress(rows, cols);
            System.out.print("My Size:\n\t");
            printFileSizeNIO("mySavedMaze.maze");
            System.out.println();
        }
    }

    public static void testSimpleCompressDecompress(int rows, int cols){
        System.out.println("Simple Comp&Decomp");
        String mazeFileName = "simpleSavedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(rows, cols); //Generate new maze

        try {
            // save maze to a file
            OutputStream out = new SimpleCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new SimpleDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
    }

    static void testMyCompressDecompress(int rows, int cols){
        System.out.println("My Comp&Decomp");
        String mazeFileName = "mySavedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(rows, cols); //Generate new maze

        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Maze loadedMaze = new Maze(savedMazeBytes);
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals));
    }

    private static void printFileSizeNIO(String fileName) {

        Path path = Paths.get(fileName);
        try {

            // size of a file (in bytes)
            long bytes = Files.size(path);
            System.out.println(String.format("%,d bytes", bytes));
            System.out.println(String.format("\t%,d kilobytes", bytes / 1024));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}