/*
 * Custodi Alessandro, 	Matricola 7084103
 * Matassini Cosimo, 	Matricola 7083831
 */

import java.util.ArrayList;

public class DoppiaCatenaCircolare<T>
{
	private Node<T> HEAD;
	private int size;
	
	public DoppiaCatenaCircolare()
	{
		HEAD = null;
		size = 0;
	}
	
	public DoppiaCatenaCircolare(Node<T> HEAD)
	{
		if(HEAD == null)
		{
			this.HEAD = null;
			size = 0;
		}
		else
			addFirst(HEAD);
	}
	
	public DoppiaCatenaCircolare(T x, int chiave)
	{
		addFirst(new Node<T>(x, chiave));
	}

	public int size()
	{
		return size;
	}
	
	public Node<T> getFirst()
	{
		if (size == 0)
			throw new NullPointerException();
		return HEAD;
	}
	
	public Node<T> getLast()
	{
		if(size == 0)
			throw new NullPointerException();
		return HEAD.getPrevious();
	}
	
	public Node<T> get(int i)
	{
		i--;
		if (i < 0 || i >= size)
			throw new NullPointerException();
		Node<T> iterator = HEAD;
		while(i-- > 0)
			iterator = iterator.getNext();
		return iterator;
	}
	
//	versione in cui viene gestito il caso in cui 
//	l'indice i sia maggiore del numero di nodi presenti
//
//	public Node<T> get(int i)
//	{
//		i--;
//		if(i < 0)
//			throw new NullPointerException();
//		i %= size;
//		Node<T> iterator = HEAD;
//		while(i-- > 0)
//			iterator = iterator.getNext();
//		return iterator;
//	}
	
	public void remove(int chiave)
	{
		if (size == 0)
			throw new NullPointerException();
		if(chiave < 0 || chiave > 1000000)
			throw new IllegalArgumentException();
		
		Node<T> iteratorNext = HEAD;
		Node<T> iteratorPrevious = HEAD.getPrevious();
		int nodiDaControllare = size;
		do
		{
			nodiDaControllare--;
			if(iteratorNext.getChiave() == chiave)
			{
				iteratorNext.getNext().setPrevious(iteratorNext.getPrevious());
				iteratorNext.getPrevious().setNext(iteratorNext.getNext());
				if (iteratorNext == HEAD)
					HEAD = HEAD.getNext();
				size--;
				return;
			}
			if(nodiDaControllare-- > 0 && iteratorPrevious.getChiave() == chiave)
			{
				iteratorPrevious.getNext().setPrevious(iteratorPrevious.getPrevious());
				iteratorPrevious.getPrevious().setNext(iteratorPrevious.getNext());
				if (nodiDaControllare > 0 && iteratorNext.getNext().getChiave() == chiave)
				{
					iteratorNext = iteratorNext.getNext();
					iteratorNext.getNext().setPrevious(iteratorNext.getPrevious());
					iteratorNext.getPrevious().setNext(iteratorNext.getNext());
					size--;
				}
				size--;
				return;
			}
			
			iteratorPrevious = iteratorPrevious.getPrevious();
			iteratorNext = iteratorNext.getNext();
		}
		while(nodiDaControllare > 0);
	}
	
	private void addFirst(Node<T> newNode)
	{
		HEAD = newNode;
		HEAD.setNext(HEAD);
		HEAD.setPrevious(HEAD);
		size = 1;
	}
	
	public void add(T x, int k)
	{
		add(new Node<>(x, k));
	}
	
	public void add(Node <T> newNode)
	{
		switch (size)
		{
			case 0:
				addFirst(newNode);
				break;
			default:
				Node<T> iterator = HEAD;
				do {
					if (newNode.equals(iterator)) 
						return;
					iterator = iterator.getNext();
				} while (iterator != HEAD);
				newNode.setPrevious(HEAD);
				newNode.setNext(HEAD.getNext());
				HEAD.getNext().setPrevious(newNode);
				HEAD.setNext(newNode);
				size++;
		}
	}
	
	public String toString()
	{
		if (size == 0)
			return "[]";
		String lista = HEAD.toString();
		Node<T> iterator = HEAD.getNext();
		while(iterator != HEAD)
		{
			lista += ", " + iterator.toString();
			iterator = iterator.getNext();
		}
		return lista;
	}
	
	public String sorted()
	{
		if (size == 0)
			return "[]";
		ArrayList<Node<T>> catenaOrdinata;
		
		if(size * Math.log(size)/Math.log(2) < 2000000 + 2 * size) //conviene mergeSort o countingSort in base alla dimensione della catena
			catenaOrdinata =  mergeSort();
		else
			catenaOrdinata = countingSort();
		String listaOrdinata = catenaOrdinata.get(0).toString();
		for(int c = 1; c < size; c++)
			listaOrdinata += ", " + catenaOrdinata.get(c).toString();
		return listaOrdinata;
	}
	
	private ArrayList<Node<T>> mergeSort()
	{
		ArrayList<Node<T>> array = new ArrayList<>();
		Node<T> iterator = HEAD;
		do {
			array.add(iterator);
			iterator = iterator.getNext();
		} while (iterator != HEAD);
		
		int grandezzaVettoreCorrente = 1;
		do {
			grandezzaVettoreCorrente *= 2;
			array = merge(array, grandezzaVettoreCorrente);
		} while (grandezzaVettoreCorrente < array.size());
		return array;
	}
	
	private ArrayList<Node<T>> merge(ArrayList<Node<T>> array, int grandezzaVettoreCorrente)
	{
		//grandezzaVettoreCorrente rappresenta la grandezza del vettore da ordinare corrente
		//grandezzaSottovettori è la grandezza dei due vettori da cui prendere i valori da ordinare
		int grandezzaSottovettori = grandezzaVettoreCorrente / 2;
		for(int i = 0; i < array.size(); i += grandezzaVettoreCorrente)
		{
			if (i + grandezzaVettoreCorrente > array.size()) 
			{
				grandezzaVettoreCorrente = array.size() - i;
				if (grandezzaSottovettori >= grandezzaVettoreCorrente) 
					grandezzaSottovettori = grandezzaVettoreCorrente / 2;
			}
			ArrayList<Node<T>> m1 = new ArrayList<>(array.subList(i, i + grandezzaSottovettori));
			ArrayList<Node<T>> m2 = new ArrayList<>(array.subList(i + grandezzaSottovettori, i + grandezzaVettoreCorrente));
			
			if (m1.size() == 0 || m2.size() == 0)
				break;
			
			int m1Counter = 0;
			int m2Counter = 0;

			for(int j = i; j < i + grandezzaVettoreCorrente; j++)
				if (m1Counter == m1.size())
					array.set(j, m2.get(m2Counter++));
				else if (m2Counter == m2.size())
					array.set(j, m1.get(m1Counter++));
				else if(m1.get(m1Counter).getChiave() <= m2.get(m2Counter).getChiave())
					array.set(j, m1.get(m1Counter++));
				else
					array.set(j, m2.get(m2Counter++));
		}
		return array;
	}
	
	private ArrayList<Node<T>> countingSort()
	{
		int[] countingArray = new int[1000000];
		
		Node<T> iterator = HEAD;
		do {
			countingArray[iterator.getChiave() - 1]++;
			iterator = iterator.getNext();
		}while(iterator != HEAD);			
		
		for(int j = 1; j < 1000000; j++)
			countingArray[j] = countingArray[j-1] + countingArray[j];
		
		ArrayList<Node<T>> sortedArray = new ArrayList<>();
		for(int c = 0; c < size; c++)
			sortedArray.add(HEAD);
		
		iterator = HEAD;
		
		for(int c = size; c > 0; c--)
		{
			iterator = iterator.getPrevious();
			sortedArray.set(--countingArray[iterator.getChiave() - 1], iterator);
		}
		return sortedArray;
	}
	
	public String findNode(int chiave)
	{
		if(size == 0)
			throw new NullPointerException();
		Node<T> iteratorNext = HEAD;
		Node<T> iteratorPrevious = HEAD.getPrevious();
		int nodiDaControllare = size;
		String found = "";
		do
		{
			nodiDaControllare--;
			if(iteratorNext.getChiave() == chiave)
				return iteratorNext.toString();
			if(nodiDaControllare-- > 0 && iteratorPrevious.getChiave() == chiave)
			{
				found = iteratorPrevious.toString();
				if (nodiDaControllare > 0 && iteratorNext.getNext().getChiave() == chiave)
					found += ", " + iteratorNext.getNext().toString();
				break;
			}
			iteratorPrevious = iteratorPrevious.getPrevious();
			iteratorNext = iteratorNext.getNext();
		}
		while(nodiDaControllare > 0);
		return found;
	}
}