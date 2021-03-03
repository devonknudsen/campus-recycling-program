public class Vertex<Type> {

    private Type value;
    private int index;
    private List<Edge> edges;

    public Vertex(Type newValue, int newIndex){
        value = newValue;
        index = newIndex;
        edges = new List<Edge>();
    }

    public Type getValue(){
        return value;
    }
    public void setValue(Type newValue){
        value = newValue;
    }

    public int getIndex(){
        return index;
    }
    public void setIndex(int newIndex){
        index = newIndex;
    }

    public List<Edge> getEdges(){
        return edges;
    }
//    public void setEdges(List<Edge> e){
//        edges = e;
//    }

}
