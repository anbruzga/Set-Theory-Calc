package Sets.Model;

import java.util.Iterator;

public class IntRange {
    private int start;
    private int end;

    public IntRange(IntRange range) {
    }


    public IntRange(int start, int end) {
        if (start < end) {
            this.start = start;
            this.end = end;
        } else if (end > start) { // reverse positions
            this.start = end;
            this.end = start;
        } else {
            System.out.println("RANGE: start == end!");
        }
    }


    public double[] getArrayOfAllElementsInRange() {
        final int arrSize = end - start + 1;
        double[] rangeInFull = new double[arrSize];
        for (int i = 0; i < arrSize; i++) {
            rangeInFull[i] = i + start;
        }
        return rangeInFull;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }



    @Override
    public String toString() {
        StringBuilder allRangeValues = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i < end) {
                allRangeValues.append(i + ", ");
            } else {
                allRangeValues.append(i + ".");
            }
        }
        return allRangeValues.toString();
    }


}
