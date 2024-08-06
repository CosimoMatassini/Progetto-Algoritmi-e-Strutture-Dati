/*
 * Custodi Alessandro, 	Matricola 7084103
 * Matassini Cosimo, 	Matricola 7083831
 */

public class Node<T>
{
	private T informazione;
	private int chiave;
	
	private Node<T> next;
	private Node<T> previous;
	
	public Node(T informazione, int chiave)
	{
		setInformazione(informazione);
		setChiave(chiave);
	}
	
	//getters e setters
	public Node<T> getNext()
	{
		return next;
	}

	public void setNext(Node<T> next)
	{
		this.next = next;
	}

	public Node<T> getPrevious()
	{
		return previous;
	}

	public void setPrevious(Node<T> previous)
	{
		this.previous = previous;
	}

	public T getInformazione()
	{
		return informazione;
	}
	
	public void setInformazione(T informazione) 
	{
		this.informazione = informazione;
	}

	public int getChiave()
	{
		return chiave;
	}
	
	public void setChiave(int chiave)
	{
		if(chiave < 1 || chiave > 1000000)
			throw new IllegalArgumentException();
		this.chiave = chiave;
	}
	
	public boolean equals(Node<T> altro)
	{
		return (chiave == altro.getChiave() && informazione == altro.getInformazione());
	}
	
	public String toString()
	{
		return "[" + informazione + ", " + chiave + "]";
	}
}