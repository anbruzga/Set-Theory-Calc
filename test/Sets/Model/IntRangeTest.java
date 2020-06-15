package Sets.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntRangeTest {



    @Test
    public void getDetailedRange() {
        System.out.println("IntRange:  from range to sequence");
        IntRange range = new IntRange(1, 4);
        double[] r = range.getArrayOfAllElementsInRange();

        System.out.println(range.toString());
        boolean pass = true;
        for (int i = 0; i < r.length; i++) {
            System.out.print(r[i] + " ");
            if (r[i] != (i+1)) fail();
        }
        assert pass;
    }

    @Test
    public void getDetailsRange2() {

        System.out.println("IntRange: range to sequence v2");
        IntRange range2 = new IntRange(-2, 11);
        double[] r2 = range2.getArrayOfAllElementsInRange();
        for (int i = 0; i < r2.length; i++) {
            //System.out.println("i. "+i + ", r2[" + i + "] " + r2[i] + " " + (range2.getStart() + i));
            System.out.print(r2[i] + " ");
            if (r2[i] != range2.getStart() + i) {
                fail();
            }
        }
    }

    @Test
    public void setAndGetStart() {
        System.out.println("IntRange: range start arg test");
        IntRange range = new IntRange(0, 1);
        for (int i = -1; i < 1; i++) {
            range.setStart(i);
            if (range.getStart() != i) fail();
        }
    }

    @Test
    public void setAndGetEnd() {
        System.out.println("IntRange: range end arg test");
        IntRange range = new IntRange(0, 1);
        for (int i = -1; i < 1; i++) {
            range.setEnd(i);
            if (range.getEnd() != i) fail();
        }
    }

    @Test
    public void testToString() {
        IntRange range = new IntRange(1, 4);
        if (!range.toString().equals("1, 2, 3, 4.")){
            System.out.println(range.toString());
            fail();
        }
    }
    @Test
    public void testToString2() {
        IntRange range = new IntRange(-1, 1);
        if (!range.toString().equals("-1, 0, 1.")){
            System.out.println(range.toString());
            fail();
        }
    }
}