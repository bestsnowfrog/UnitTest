import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//import org.junit.*;

//import java.util.ArrayList;

public class TestRingBuffer {

    private RingBufferImpl<Integer> empty_buf;
    private RingBufferImpl<Integer> nonempty_buf;
    private RingBufferImpl<Integer> full_buf;
    private RingBufferImpl<Integer> overfull_buf;

    private static final int capacity = 5;
    private static final int startElem = 1;
    private static final int endElem = 7;

    @Before
    public void setUp() {
        empty_buf = new RingBufferImpl<Integer>(Integer.class, capacity);
        nonempty_buf = new RingBufferImpl<Integer>(Integer.class, capacity);
        nonempty_buf.add(startElem);
        nonempty_buf.add(2);
        full_buf = new RingBufferImpl<Integer>(Integer.class, capacity);
        full_buf.add(startElem);
        full_buf.add(2);
        full_buf.add(3);
        full_buf.add(4);
        full_buf.add(5);
        overfull_buf = new RingBufferImpl<Integer>(Integer.class, capacity);
        overfull_buf.add(startElem);
        overfull_buf.add(2);
        overfull_buf.add(3);
        overfull_buf.add(4);
        overfull_buf.add(5);
        overfull_buf.add(6);
        overfull_buf.add(endElem);
    }

    @Test
    public void add_empty() {
        int expected = 1;
        empty_buf.add(0);
        int actual = empty_buf.getSize();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void add_nonempty() {
        int expected = nonempty_buf.getSize() + 1;
        nonempty_buf.add(0);
        int actual = nonempty_buf.getSize();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void add_full() {
        full_buf.add(0);
        int actual = full_buf.getSize();
        Assert.assertEquals(capacity, actual);
    }

    @Test
    public void add_overfull() {
        overfull_buf.add(0);
        int actual = overfull_buf.getSize();
        Assert.assertEquals(capacity, actual);
    }

    @Test
    public void poll_empty() {
        Assert.assertNull(empty_buf.poll());
    }

    @Test
    public void poll_nonempty() {
        int size = nonempty_buf.getSize();
        int actual = nonempty_buf.poll();
        Assert.assertEquals(startElem, actual);
        Assert.assertEquals(size - 1, nonempty_buf.getSize());
    }

    @Test
    public void poll_full() {
        int size = full_buf.getSize();
        int actual = full_buf.poll();
        Assert.assertEquals(startElem, actual);
        Assert.assertEquals(size - 1, full_buf.getSize());
    }

    @Test
    public void poll_overfull() {
        int size = overfull_buf.getSize();
        int actual = overfull_buf.poll();
        Assert.assertEquals(endElem - capacity + startElem, actual);
        Assert.assertEquals(size - 1, overfull_buf.getSize());
    }

    @Test
    public void peek_empty() {
        Assert.assertNull(empty_buf.peek());
    }

    @Test
    public void peek_nonempty() {
        int size = nonempty_buf.getSize();
        int actual = nonempty_buf.peek();
        Assert.assertEquals(startElem, actual);
        Assert.assertEquals(size, nonempty_buf.getSize());
    }

    @Test
    public void peek_full() {
        int size = full_buf.getSize();
        int actual = full_buf.peek();
        Assert.assertEquals(startElem, actual);
        Assert.assertEquals(size, full_buf.getSize());
    }

    @Test
    public void peek_overfull() {
        int size = overfull_buf.getSize();
        int actual = overfull_buf.peek();
        Assert.assertEquals(endElem - capacity + startElem, actual);
        Assert.assertEquals(size, overfull_buf.getSize());
    }


    @Test
    public void iterator_empty() {
        for (int i: empty_buf) {
            Assert.assertEquals(0, i);
        }
    }

    @Test
    public void iterator_nonempty() {
        int j = startElem;
        for (int i: nonempty_buf) {
            Assert.assertEquals(i, j++);
        }
    }

    @Test
    public void iterator_full() {
        int j = startElem;
        for (int i: full_buf) {
            Assert.assertEquals(i, j++);
        }
    }

    @Test
    public void iterator_overfull() {
        int j = endElem - capacity + startElem;
        for (int i: overfull_buf) {
            Assert.assertEquals(i, j++);
        }
    }

    @Test
    public void iterator_remove() {
        nonempty_buf.iterator().remove();
    }

}
