import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class ListIterator<T> implements Iterator<T> {
    private final Iterator<Iterator<T>> listIterator;
    private Iterator<T> currentIterator;

    public ListIterator(List<Iterator<T>> iterators) {
        this.listIterator = iterators.iterator();
        this.currentIterator = listIterator.next();
    }

    @Override
    public boolean hasNext() {
        if (!currentIterator.hasNext()) {
            if (!listIterator.hasNext()) {
                return false;
            }
        
            currentIterator = listIterator.next();
            hasNext();
        }

        return true;
    }

    @Override
    public T next() {
        hasNext();
        return currentIterator.next();
    }

    @Override
    public void remove() {
        hasNext();
        currentIterator.remove();
    }

    @Test
    public void multipleIterators() {
        final Iterator<Integer> a = Arrays.asList(1, 2, 3, 4, 5).iterator();
        final Iterator<Integer> b = Arrays.asList(6).iterator();
        final Iterator<Integer> c = new ArrayList<Integer>().iterator();
        final Iterator<Integer> d = new ArrayList<Integer>().iterator();
        final Iterator<Integer> e = Arrays.asList(7,8, 9).iterator();

        final Iterator<Integer> singleIterator = Iterators.singleIterator(Arrays.asList(a, b, c, d, e));

        assertTrue(singleIterator.hasNext());

        for (Integer i = 1; i <= 9; i++) {
            assertEquals(i, singleIterator.next());
        }

        assertFalse(singleIterator.hasNext());
    }
}
