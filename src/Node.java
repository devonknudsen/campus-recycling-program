/* ***************************************************
 * Devon Knudsen
 * Last Updated - 4/26/19
 *
 * Node Class - handles any form of data (modified version for trie)
 *************************************************** */

class Node<Type>
{
	private Type data;
	private Node<Type> link;
	private List<Type> children;

	// constructor
	public Node()
	{
		this.data = null;
		this.link = null;
		this.children = new List<Type>();
	}

	// accessor and mutator for the data component
	public Type getData()
	{
		return this.data;
	}

	public void setData(Type data)
	{
		this.data = data;
	}

	// accessor and mutator for the link component
	public Node<Type> getLink()
	{
		return this.link;
	}

	public void setLink(Node<Type> link)
	{
		this.link = link;
	}

	public List<Type> getChildren(){
		return this.children;
	}
}