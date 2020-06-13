interface RingBuffer<E> extends Iterable<E> {
    E poll();
    E peek();
    void add(E item);
    int getSize();
}
