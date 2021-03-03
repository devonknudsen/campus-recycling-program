public class Edge {

    private double weight;
    private Vertex origin;
    private Vertex destination;

    public Edge(Vertex newOrigin, Vertex newDest, double newWeight){
        weight = newWeight;
        origin = newOrigin;
        destination = newDest;
    }

    public double getWeight(){
        return weight;
    }
    public void setWeight(int newWeight){
        weight = newWeight;
    }

    public Vertex getOrigin(){
        return origin;
    }
    public void setOrigin(Vertex newOrigin){
        origin = newOrigin;
    }

    public Vertex getDestination(){
        return destination;
    }
    public void setDestination(Vertex newDest){
        destination = newDest;
    }

    public Vertex[] endPoints(){
        Vertex[] endPoints = new Vertex[2];
        endPoints[0] = origin;
        endPoints[1] = destination;

        return endPoints;
    }

    public Vertex opposite(Vertex current){
        if(current == origin)
            return destination;
        else
            return origin;
    }

}
