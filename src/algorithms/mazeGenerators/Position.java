package algorithms.mazeGenerators;

public class Position
{
    int rowX; // row
    int colY; // columns
    boolean isChecked;
    public  Position()
    { //override default  constructor
        this.rowX=0;
        this.colY=0;
        this.isChecked=false;
    }

    public  Position(int rowx , int coly)
    { // constructor
        //Position newP = new Position();
        this.rowX = rowx;
        this.colY = coly;
        this.isChecked = false;
    }
    public int getColumnIndex()
    {
        return colY;
    }
    public int getRowIndex()
    {
        return rowX;
    }
    public void setColumnIndex(int col)
    {
        this.colY=col;
    }
    public void setRowIndex(int row)
    {
        this.rowX=row;
    }
    @Override
    public String toString()
    {
        return "{" + getRowIndex() + " , "+ getColumnIndex() + "}";
    }
    public void setp(boolean checke_p)
    {
        this.isChecked = checke_p;
    }

}
