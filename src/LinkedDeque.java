import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * Model class of a generic doubly linked deque. Elements are added at the back
 * with enqueue() or the front with addFront(). Elements are removed from the
 * front with dequeue().
 *
 * @param <E>
 *            the element type
 * @author Justis Phillips justisp
 * @version Nov 12, 2026
 */
public class LinkedDeque<E>
{
    private static class Node<E>
    {
        private final E element;
        private Node<E> prev;
        private Node<E> next;

        Node(E element)
        {
            this.element = element;
        }
    }

    private Node<E> front;
    private Node<E> back;
    private int size;

    /**
     * Creates an empty deque.
     */
    public LinkedDeque()
    {
        front = null;
        back = null;
        size = 0;
    }


    /**
     * Adds an element to the back of the deque.
     *
     * @param item
     *            the element to add
     * @throws IllegalArgumentException
     *             if item is null
     */
    public void enqueue(E item)
    {
        requireNonNull(item);
        Node<E> node = new Node<>(item);
        if (back == null)
        {
            front = node;
            back = node;
        }
        else
        {
            node.prev = back;
            back.next = node;
            back = node;
        }
        size++;
    }


    /**
     * Adds an element to the front of the deque.
     *
     * @param item
     *            the element to add
     * @throws IllegalArgumentException
     *             if item is null
     */
    public void addFront(E item)
    {
        requireNonNull(item);
        Node<E> node = new Node<>(item);
        if (front == null)
        {
            front = node;
            back = node;
        }
        else
        {
            node.next = front;
            front.prev = node;
            front = node;
        }
        size++;
    }


    /**
     * Removes and returns the first element.
     *
     * @return the first element
     * @throws NoSuchElementException
     *             if the deque is empty
     */
    public E dequeue()
    {
        if (front == null)
        {
            throw new NoSuchElementException("The deque is empty.");
        }
        E item = front.element;
        unlink(front);
        return item;
    }


    /**
     * Returns the first element without removing it.
     *
     * @return the first element
     * @throws NoSuchElementException
     *             if the deque is empty
     */
    public E peek()
    {
        if (front == null)
        {
            throw new NoSuchElementException("The deque is empty.");
        }
        return front.element;
    }


    /**
     * @return true if the deque has no elements
     */
    public boolean isEmpty()
    {
        return size == 0;
    }


    /**
     * @return the number of elements
     */
    public int size()
    {
        return size;
    }


    /**
     * Removes the first element equal to item, wherever it is in the deque.
     *
     * @param item
     *            the element to remove
     * @return true if an element was removed, false if it was not found
     */
    public boolean remove(E item)
    {
        if (item == null)
        {
            return false;
        }
        Node<E> current = front;
        while (current != null)
        {
            if (current.element.equals(item))
            {
                unlink(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }


    /**
     * Returns the element at the given index without removing it. Index 0 is
     * the front of the deque.
     *
     * @param index
     *            the position, from 0 to size() - 1
     * @return the element at that index
     * @throws IndexOutOfBoundsException
     *             if index is out of range
     */
    public E get(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<E> current;
        if (index < size / 2)
        {
            current = front;
            for (int i = 0; i < index; i++)
            {
                current = current.next;
            }
        }
        else
        {
            current = back;
            for (int i = size - 1; i > index; i--)
            {
                current = current.prev;
            }
        }
        return current.element;
    }


    // Detaches a node and repairs front, back, and neighbor links.
    private void unlink(Node<E> node)
    {
        if (node.prev == null)
        {
            front = node.next;
        }
        else
        {
            node.prev.next = node.next;
        }

        if (node.next == null)
        {
            back = node.prev;
        }
        else
        {
            node.next.prev = node.prev;
        }
        node.prev = null;
        node.next = null;
        size--;
    }


    private void requireNonNull(E item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException(
                "Null elements are not allowed.");
        }
    }
}
