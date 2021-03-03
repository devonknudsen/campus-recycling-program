
/**
 * Write a description of class Stack here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Stack<Type> extends List<Type>
{

    /**
     * Constructor for objects of class Stack
     */
    public Stack()
    {
        super();
    }

    public void push(Type data)
    {
        super.InsertBefore(data);
    }

    public Type pop()
    {
        Type data = super.GetValue();
        super.Remove();
        return data;
    }

    public Type peek()
    {
        Type data = super.GetValue();
        return data;
    }

}
