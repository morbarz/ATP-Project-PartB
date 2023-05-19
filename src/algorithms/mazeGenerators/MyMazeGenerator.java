package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {
    private Maze FullWallM ;
    private List<Position> PositionList ;// List with the position's from algorithm

    public MyMazeGenerator() {//init
       this.PositionList=new ArrayList<>();
    this.FullWallM=null;}


    @Override
    public Maze generate(int row, int col) {
        FullWallM= new Maze(row, col);//init maze (without walls)
        FullfillMaze(FullWallM); //  Fullfill maze  of walls
        Position startP = new Position();//init the start position
        startP = getRandomposition(row,col); //now we choose random points for this position
        FullWallM.startPosition=startP;
        //Markvisit(startP);//mark this position as visited
        Passage(startP,FullWallM);
        PositionList.add(startP);//add this random position to the list of the position'
        while (!PositionList.isEmpty()) { // if the position list is not empty- continue
            Random ran = new Random();//choose a random position from the list
            int index = ran.nextInt(PositionList.size());
            Position p = PositionList.get(index);
            if (!p.isChecked && CheckNeighbors(FullWallM, p)) { // if this position didnt visited , and her neighbored is in bound and never visited
                Passage(p, FullWallM);//make this like it didnt have wall , and mark it as visited
                Break(FullWallM, p);//break the wall between this position to it neighbor's if we not visit them and if they in bound
                AddNewWalls(p.getRowIndex(), p.getColumnIndex());}//finally add neighbors to the list if you didnt visited them
                PositionList.remove(p);//remove this position either way

        }
            Random r = new Random();
            int randomPick = r.nextInt(2);
            if (randomPick==1)
                Passage(this.FullWallM.PositionArray[row-1][col-2], FullWallM);
            else
                Passage(this.FullWallM.PositionArray[row-2][col-1], FullWallM);
            Passage(this.FullWallM.PositionArray[row-1][col-1], FullWallM);


        return FullWallM;





    }
        Position newPosition ;

    public void Passage(Position p, Maze m) //break the wall inside the maze in this specific position and make it as checked position
    {

        int r = p.getRowIndex();
        int c = p.getColumnIndex();
        FullWallM.PositionArray[r][c].isChecked = true;
        m.maze[r][c] = 0;
    }


        public Position getRandomposition(int row, int col) {
        Position randP;
        {
            randP = new Position();
            Random r1 = new Random();
            Random r2 = new Random();
            int randCol;
            int randRow;
            if (col < row) {
                randCol = r1.nextInt(col);
                randRow = r2.nextInt(row);
            } else if (col == row) {
                randCol = r1.nextInt(row);
                randRow = r2.nextInt(row);
            } else {
                randCol = r1.nextInt(col);
                randRow = r2.nextInt(row);

            }
            randP.setColumnIndex(randCol);
            randP.setRowIndex(randRow);
        }
        return randP;
    }
    public Maze FullfillMaze(Maze toF) { //fullfill wall's all over the maze
        for (int i =0 ; i < toF.row ; i ++){
            for (int j=0 ; j<toF.col ; j++){
                toF.maze[i][j]=1;//now with walls
            }

    }
        return toF;
}
public void Markvisit(Position v){ // mark position as visited
        v.isChecked=true;
     }

public boolean checkPosition(int Prow , int Pcol , Maze toC){ // check if this position value is in bound
        return  (Pcol<=toC.goalPosition.getColumnIndex() && Prow<=toC.goalPosition.getRowIndex() && Prow>=0 && Pcol>=0 ) ;
    }
    public void  addneighbor (Position p, Maze pfrom){ // add position neigbor's to PositionList ;
        //add only if position is not yet visited and if position is in bound of maze .
        if(!pfrom.PositionArray[p.getColumnIndex() - 1][p.getRowIndex()].isChecked && checkPosition((p.getColumnIndex() - 1),p.getRowIndex(),pfrom)){
            PositionList.add(pfrom.PositionArray[p.getColumnIndex() - 1][p.getRowIndex()]);}

        if(!pfrom.PositionArray[p.getColumnIndex() + 1][p.getRowIndex()].isChecked&& checkPosition((p.getColumnIndex() + 1),p.getRowIndex(),pfrom)){
            PositionList.add(pfrom.PositionArray[p.getColumnIndex() + 1][p.getRowIndex()]);}

        if(!pfrom.PositionArray[p.getColumnIndex() ][p.getRowIndex()-1].isChecked&& checkPosition(p.getColumnIndex() ,p.getRowIndex()-1,pfrom)){
            PositionList.add(pfrom.PositionArray[p.getColumnIndex() ][p.getRowIndex()-1]);}

        if(!pfrom.PositionArray[p.getColumnIndex() ][p.getRowIndex()+1].isChecked && checkPosition(p.getColumnIndex() ,p.getRowIndex()+1,pfrom)){
            PositionList.add(pfrom.PositionArray[p.getColumnIndex() ][p.getRowIndex()+1]);}
    }
    public boolean CheckNeighbors(Maze m, Position p) {
        int r = p.getRowIndex();
        int c = p.getColumnIndex();
        if (checkPosition(r + 1, c, m))
            if (FullWallM.PositionArray[r + 1][c].isChecked)
                return false;
        if (checkPosition(r + 1, c + 1, m))
            if (FullWallM.PositionArray[r + 1][c + 1].isChecked)
                return false;
        if (checkPosition(r, c + 1, m))
            if (FullWallM.PositionArray[r][c + 1].isChecked)
                return false;
        if (checkPosition(r - 1, c, m))
            if (FullWallM.PositionArray[r - 1][c].isChecked)
                return false;
        if (checkPosition(r - 1, c - 1, m))
            if (FullWallM.PositionArray[r - 1][c - 1].isChecked)
                return false;
        if (checkPosition(r, c -1, m))
            if (FullWallM.PositionArray[r][c - 1].isChecked)
                return false;
        if (checkPosition(r - 1, c + 1, m))
            if (FullWallM.PositionArray[r - 1][c + 1].isChecked)
                return false;
        if (checkPosition(r + 1, c - 1, m))
            if (FullWallM.PositionArray[r + 1][c - 1].isChecked)
                return false;
        return true;
    }
    public void AddNewWalls(int beginRow, int beginCol) {
        // adds the neighbor below if it is in bound.
        if (checkPosition(beginRow + 2, beginCol, FullWallM)) {
            Position p2 = FullWallM.PositionArray[beginRow + 2][beginCol];
            if (!p2.isChecked) {
                PositionList.add(p2);
            }
        }

        // adds the neighbor above if it is in bound.
        if (checkPosition(beginRow, beginCol + 2, FullWallM)) {
            Position p2 = FullWallM.PositionArray[beginRow][beginCol + 2];
            if (!p2.isChecked) {
                PositionList.add(p2);
            }
        }

        // adds the neighbor from the left if it is in bound.
        if (checkPosition(beginRow - 2, beginCol, FullWallM)) {
            Position p2 = FullWallM.PositionArray[beginRow-2][beginCol ];
            if (!p2.isChecked) {
                PositionList.add(p2);
            }
        }

        // adds the neighbor from the right if it is in bound.
        if (checkPosition(beginRow, beginCol -2, FullWallM)) {
            Position p2 = FullWallM.PositionArray[beginRow][beginCol -2];
            if (!p2.isChecked) {
                PositionList.add(p2);
            }
        }

    }
    public void Break(Maze m, Position p)
    {
        Position p1, p2, p3, p4;
        if (checkPosition( p.getRowIndex()+2, p.getColumnIndex(),m))
        {
            p1 = FullWallM.PositionArray[p.getRowIndex() + 2][p.getColumnIndex()];
            if (p1.isChecked)
            {
                Position pos = FullWallM.PositionArray[p.getRowIndex()+1][p.getColumnIndex()];
                Passage(pos, FullWallM);
                AddNewWalls(pos.getRowIndex(),pos.getColumnIndex());
                return;
            }

        }
        if (checkPosition( p.getRowIndex(), p.getColumnIndex()+2,m))
        {
            p2 = FullWallM.PositionArray[p.getRowIndex()][p.getColumnIndex() + 2];
            if (p2.isChecked)
            {
                Position pos = FullWallM.PositionArray[p.getRowIndex()][p.getColumnIndex()+1];
                Passage(pos, FullWallM);
                AddNewWalls(pos.getRowIndex(),pos.getColumnIndex());
                return;
            }
        }

        if (checkPosition( p.getRowIndex()-2, p.getColumnIndex(),m))
        {
            p3 = FullWallM.PositionArray[p.getRowIndex()-2][p.getColumnIndex()];
            if (p3.isChecked)
            {
                Position pos = FullWallM.PositionArray[p.getRowIndex()-1][p.getColumnIndex()];
                Passage(pos, FullWallM);
                AddNewWalls(pos.getRowIndex(),pos.getColumnIndex());
                return;
            }
        }
        if (checkPosition( p.getRowIndex(), p.getColumnIndex()-2,m))
        {
            p4 = FullWallM.PositionArray[p.getRowIndex()][p.getColumnIndex()-2];
            if (p4.isChecked)
            {
                Position pos = FullWallM.PositionArray[p.getRowIndex()][p.getColumnIndex()-1];
                Passage(pos, FullWallM);
                AddNewWalls(pos.getRowIndex(),pos.getColumnIndex());
                return;
            }
        }
    }
    }



