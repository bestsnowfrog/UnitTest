import java.lang.reflect.Array;
import java.util.Iterator;

public class RingBufferImpl<E> implements RingBuffer<E> {
    private int maxSize;
    private int writePos = 0;
    private int size = 0;
    private E[] buf;

    RingBufferImpl(Class<E> clazz, int capacity) {
        this.maxSize = capacity;
        buf = (E[]) Array.newInstance(clazz, capacity);
    }

    //@Override
    public void add(E item) {
        if (writePos >= maxSize) {
            writePos = 0;
        }
        buf[writePos] = item;
        writePos++;
        if (size < maxSize) {
            size++;
        }
    }

    //@Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E elem = buf[getStartPos()];
        size--;
        return elem;
    }

    //@Override
    public E peek() {
        if (size == 0) {
            return null;
        }
        return buf[getStartPos()];
    }

    //@Override
    public int getSize() {
        return size;
    }

    //@Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentCount = size;
            private int currentIndex = getStartPos();

            //@Override
            public boolean hasNext() {
                return currentCount != 0;
            }

            //@Override
            public E next() {
                --currentCount;
                if (currentIndex == maxSize)
                    currentIndex = 0;
                return buf[currentIndex++];
            }

            public void remove() {

            }
        };
        return it;
    }

    private int getStartPos() {
        int startPos = writePos - size;
        if (startPos < 0) {
            startPos += maxSize;
        }
        return startPos;
    }
}
