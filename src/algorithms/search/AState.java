package algorithms.search;

public abstract class AState {
    private String state;
    private double cost;
    private AState cameFrom;
    public boolean isvisited;
    private int distance;
    public boolean crossTome;
    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return this.state;
    }



    public void setCost(double cost)
    {
        this.cost = cost;
    }
    public double getCost()
    {
        return this.cost;
    }

    public void setCameFrom(AState cameFrom)
    {
        this.cameFrom = cameFrom;
    }
    public void setIsvisited(){this.isvisited=true;};
    public int getDistance()
    {
        return this.distance ;
    }

    public AState getCameFrom()
    {
        return this.cameFrom;
    }
    public boolean isVisited(){
        return this.isvisited;
    }
    public abstract void updateVisited();
    public void setDistance(int distance1){this.distance=distance1;};


}


