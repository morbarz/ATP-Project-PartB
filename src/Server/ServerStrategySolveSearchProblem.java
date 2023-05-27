package Server;


import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    @Override
    public void applyStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            ASearchingAlgorithm Algo;
            switch (Configurations.createInstance().readConfig().getProperty("mazeSearchingAlgorithm")){
                case "BestFirstSearch" -> Algo = new BestFirstSearch();
                case "BreadthFirstSearch" -> Algo = new BreadthFirstSearch();
                case "DepthFirstSearch" -> Algo = new DepthFirstSearch();
                default -> Algo = null;
            };
            assert Algo != null;
            boolean found_flag = false;
            Maze inputMaze = (Maze) fromClient.readObject();
            int hashKey = inputMaze.hashCode();
            int specifier = 0;

            //Code line from project work sheet to get the Solution files from the temporary directory
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            File temp_directory = new File(tempDirectoryPath);

            //Other's properties
            byte[] Other_maze;
            Solution Other_solution = null;

            //Defining InputStreams in order to search each file in temp directory
            FileInputStream file_in_stream;
            ObjectInputStream object_in_stream;

            //Output streams
            FileOutputStream file_out_stream;
            ObjectOutputStream object_out_stream;

            //Checking if the Directory is not empty
            int dir_size = Objects.requireNonNull(temp_directory.list()).length;
            if (dir_size > 0){
                for (File curr: Objects.requireNonNull(temp_directory.listFiles()) ){
                    if (curr.isFile()){
                        if (curr.getName().contains("_Solution_")){
                            //try reading the file
                            try{
                                file_in_stream = new FileInputStream(curr);
                                object_in_stream = new ObjectInputStream(file_in_stream);
                                Object[] other_objects = (Objects[]) object_in_stream.readObject();
                                Other_maze = (byte[]) other_objects[0];
                                Other_solution = (Solution) other_objects[1];
                            } catch (Exception e){
                                // if we catch exception that means that it probably wasn't a file, so moving on to the next one.
                                continue;
                            }
                            /*if we got here that means that we found solution file and need to check it by 3
                            parameters - hashKey, length, and Maze comparison*/
                            found_flag = (curr.hashCode() == hashKey) && (Arrays.equals(Other_maze, inputMaze.toByteArray()))
                                    && (inputMaze.toByteArray().length == Other_maze.length);
                            if (!found_flag) {
                                //In order to make each file unique we use specifier, if they got the same hashKey.
                                specifier++;
                                break;
                            }
                            else{
                                //if found_flag is True then we found the solution file
                                toClient.writeObject(Other_solution);
                                toClient.flush();
                                object_in_stream.close();
                                file_in_stream.close();
                                break;
                            }
                        }
                    }
                }
            }

            if(!found_flag) {
                //Creating new solution and sending it back to client
                SearchableMaze search_problem = new SearchableMaze(inputMaze);
                Solution Sol = Algo.solve(search_problem);
                toClient.writeObject(Sol);
                toClient.flush();

                //Creating new Solution file
                Object[] new_objects = new Object[2];
                new_objects[0] = inputMaze.toByteArray();
                new_objects[1] = Sol;

                //Defining file's path and name
                String newPath = temp_directory.getPath() + '/' + "_Solution_" + hashKey + specifier;
                File new_Sol = new File(newPath);
                file_out_stream = new FileOutputStream(new_Sol);
                object_out_stream = new ObjectOutputStream(file_out_stream);
                object_out_stream.writeObject(new_objects);
                object_out_stream.flush();
                object_out_stream.close();
            }

            //closing connection
            toClient.close();
            fromClient.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
