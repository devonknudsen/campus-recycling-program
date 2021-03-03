/* ***************************************************
 * Devon Knudsen
 * Last Updated - 3/16/19
 *
 * List Class - handles any form of data
 *************************************************** */

public class List<Type> {
    // We don't actually have to set a max size with linked lists
    // But it is a good idea.
    // Just picture an infinite loop adding to the list! :O
    public static final int MAX_SIZE = 10000;

    private Node<Type> head;
    private Node<Type> tail;
    private Node<Type> curr;
    private int num_items;

    // constructor
    // remember that an empty list has a "size" of -1 and its "position" is at -1
    public List() {
        this.head = this.tail = this.curr = null;
        this.num_items = 0;
    }

    // copy constructor
    // clones the list l and sets the last element as the current
    public List(List<Type> l) {
        Node<Type> n = new Node<>();
        n.setLink(l.head.getLink());

        this.head = this.tail = this.curr = null;
        this.num_items = 0;
        
        while (n.getLink() != null)
        {
            this.InsertAfter(n.getLink().getData());
            n.setLink(n.getLink().getLink());
        }
    }

    // navigates to the beginning of the list
    public void First()
    {
        this.curr.setLink(this.head.getLink());
    }

    // navigates to the end of the list
    // the end of the list is at the last valid item in the list
    public void Last()
    {
        this.curr.setLink(this.tail.getLink());
    }

    public Node<Type> GetCurr() {
        return this.curr.getLink();
    }

    // navigates to the specified element (0-index)
    // this should not be possible for an empty list
    // this should not be possible for invalid positions
    public void SetPos(int pos) {
        if(!IsEmpty() && pos < GetSize() && pos >= 0) {
            Node<Type> temp = new Node<>();
            temp.setLink(this.head.getLink());
                
            for(int i = 0; i != pos; i++)
                temp.setLink(temp.getLink().getLink());
                
            this.curr.setLink(temp.getLink());
        }
    }

    // navigates to the previous element
    // this should not be possible for an empty list
    // there should be no wrap-around
    public void Prev() {
        if(!IsEmpty()) {
            if (this.GetSize() > 1)
                SetPos(GetPos() - 1);
        }
    }

    // navigates to the next element
    // this should not be possible for an empty list
    // there should be no wrap-around
    public void Next() {
        if(!IsEmpty()) {
            if (this.GetSize() > 1)
                SetPos(GetPos() + 1);
        }
    }

    // returns the location of the current element (or -1)
    // might need to use .getLink()
    public int GetPos() {
        if(!IsEmpty()) {
            int pos = 0;
            Node<Type> temp = new Node<>();
            temp.setLink(this.head.getLink());
            
            while(temp.getLink() != this.curr.getLink()) {
                temp.setLink(temp.getLink().getLink());
                pos++;
            }
            
            return pos;
        }
        else
            return -1;
    }

    // returns the value of the current element (or null)
    public Type GetValue() {
        if(IsEmpty())
            return null;
        else
            return this.curr.getLink().getData();
    }

    // returns the size of the list
    // size does not imply capacity
    public int GetSize() {
        return num_items;
    }

    // inserts an item before the current element
    // the new element becomes the current
    // this should not be possible for a full list
    public void InsertBefore(Type data) {
        if(!IsFull()) {
            Node<Type> newNode = new Node<>();
            newNode.setData(data);
            
            if(IsEmpty()) {
                InsertEmpty(newNode);
                
                this.num_items += 1;
            }
            else if(this.curr.getLink() == this.head.getLink()) {
                newNode.setLink(this.curr.getLink());
                    
                this.head.setLink(newNode);
                this.curr.setLink(newNode);
                    
                this.num_items += 1;
            }
            else {
                Prev();
                InsertAfter(data);
            }
        }
     }
    
    // inserts an item after the current element
    // the new element becomes the current
    // this should not be possible for a full list
    public void InsertAfter(Type data) {
        if(!IsFull()) {
            Node<Type> newNode = new Node<>();
            newNode.setData(data);

            if(IsEmpty())
                InsertEmpty(newNode);
            else if(this.curr.getLink() == this.tail.getLink()) {
                this.curr.getLink().setLink(newNode);
                this.tail.setLink(newNode);
                this.curr.setLink(newNode);
            }
            else {
                newNode.setLink(this.curr.getLink().getLink());
                this.curr.getLink().setLink(newNode);
                this.curr.setLink(newNode);
            }
            
            this.num_items += 1;
        }
    }

    // insert a node into an empty linked list
    private void InsertEmpty(Node<Type> n) {
        this.head = new Node<>();
        this.curr = new Node<>();
        this.tail = new Node<>();

        n.setLink(null);

        this.head.setLink(n);
        this.curr.setLink(n);
        this.tail.setLink(n);
    }
    

    // removes the current element 
    // this should not be possible for an empty list
    public void Remove() {
        if(!IsEmpty()) {
            if(this.curr.getLink() == this.tail.getLink()) {
                Prev();
                this.curr.getLink().setLink(null);
                this.tail.setLink(this.curr.getLink());
            }
            else if(this.curr.getLink() == this.head.getLink()) {
                Next();
                this.head.getLink().setLink(null);
                this.head.setLink(this.curr.getLink());
            }
            else {
               Node<Type> pacman = new Node<>();
               pacman.setLink(this.curr.getLink());
               Prev();
               this.curr.getLink().setLink(pacman.getLink().getLink());
               Next();
            }
            
            this.num_items -= 1;
        }
    }

    // replaces the value of the current element with the specified value
    // this should not be possible for an empty list
    // may actually just be changing the data within the current node and not replacing the whole node
    public void Replace(Type data) {
        if(!IsEmpty())
            curr.getLink().setData(data);
    }

    // returns if the list is empty
    public boolean IsEmpty() {
        if(this.num_items == 0)
            return true;
        else
            return false;
    }

    // returns if the list is full
    public boolean IsFull() {
        if(GetSize() == MAX_SIZE)
            return true;
        else
            return false;
    }

    // returns if two lists are equal (by value)
    public boolean Equals(List<Type> l) {
        if(this.GetSize() != l.GetSize())
            return false;
        
        Node<Type> temp1 = new Node<>();
        Node<Type> temp2 = new Node<>();
        
        temp1.setLink(this.head.getLink());
        temp2.setLink(l.head.getLink());
        
        for(int i = 0; i < this.GetSize(); i++) {
            if(temp1.getLink().getData() != temp2.getLink().getData())
                return false;

            temp1.setLink(temp1.getLink().getLink());
            temp2.setLink(temp2.getLink().getLink());
        }
        
        return true;
    }

    // returns the concatenation of two lists
    // l should not be modified
    // l should be concatenated to the end of *this
    // the returned list should not exceed MAX_SIZE elements
    // the last element of the new list is the current
    public List<Type> Add(List<Type> l) {
        if(this.toString().equals("NULL")) {
            List<Type> newList = new List<>(l);

            return newList;
        }
        else if(l.toString().equals("NULL")) {
            List<Type> newList = new List<>(this);

            return newList;
        }
        else {
            List<Type> newList = new List<>(this);
            
            Node<Type> temp1 = new Node<>();
            Node<Type> temp2 = new Node<>();
            
            temp1.setLink(newList.tail.getLink());
            temp2.setLink(l.head.getLink());
            
            for(int i = 0; i < GetSize(); i++) {
                newList.InsertAfter(temp2.getLink().getData());
                temp2.setLink(temp2.getLink().getLink());
            }
            
            return newList;
        }
    }

    // returns a string representation of the entire list (e.g., 1 2 3 4 5)
    // the string "NULL" should be returned for an empty list
    public String toString() {
        if(IsEmpty())
            return "NULL";
        
        Node<Type> temp = new Node<>();
        temp.setLink(this.head.getLink());
        
        String list = "";
        
        for(int i = 0; i < GetSize(); i++) {
                list += temp.getLink().getData() + " ";
                temp.setLink(temp.getLink().getLink());
        }
        
        return list;
    }
}