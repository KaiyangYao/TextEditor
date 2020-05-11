package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 * Edited by Kaiyang Yao on 05/2020
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// Implemented in week 3
		head = new LLNode<>(null);
		tail = new LLNode<>(null);
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// Implemented in week 3
		if(element == null) {
			throw new NullPointerException();
		}

		LLNode<E> addedNode = new LLNode<>(element);
		if(size == 0) {
			head.next = addedNode;
			addedNode.prev = head;
			addedNode.next = tail;
			tail.prev = addedNode;
		} else {
			addedNode.next = tail;
			addedNode.prev = tail.prev;
			addedNode.prev.next = addedNode;
			tail.prev = addedNode;
		}

		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// Implemented in week 3
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> currentNode = head.next;
		for(int i = 0; i < index; i++) {
			currentNode = currentNode.next;
		}

		return currentNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param index The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// Implemented in week 3
		if(element == null) {
			throw new NullPointerException();
		}
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> addedNode = new LLNode<>(element);

		if(size == 0) {
			head.next = addedNode;
			addedNode.prev = head;
			addedNode.next = tail;
			tail.prev = addedNode;
		} else {
			LLNode<E> previousNode = head;
			LLNode<E> nextNode = head.next;
			for (int i = 0; i < index; i++) {
				previousNode = previousNode.next;
				nextNode = nextNode.next;
			}
			addedNode.next = previousNode.next;
			previousNode.next = addedNode;
			nextNode.prev = addedNode;
			addedNode.prev = previousNode;
		}
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// Implemented in week 3
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// Implemented in week 3
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> removed = head.next;
		for(int i = 0; i < index; i++) {
			removed = removed.next;
		}

		removed.prev.next = removed.next;
		removed.next.prev = removed.prev;
		size --;
		return removed.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// Implemented in week 3
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if(element == null) {
			throw new NullPointerException();
		}
		LLNode<E> toSet = head.next;
		for(int i = 0; i < index; i++) {
			toSet = toSet.next;
		}

		E replaced = toSet.data;
		toSet.data = element;
		return replaced;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;


	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public String toString() {
		return data.toString();
	}
}
