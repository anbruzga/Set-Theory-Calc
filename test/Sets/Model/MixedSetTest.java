package Sets.Model;

        import Sets.Controler.IncorrectInputSyntaxException;
        import Sets.Controler.SyntaxAnalyser;
        import org.junit.Test;

public class MixedSetTest {

    private static int i;
    private boolean skip = false;



    @Test
    public void addMixedSet() {
        System.out.println("Mixed Set: Adding MixedSet");
        MixedSet intSet = new MixedSet(false);
        intSet.add(new IntRange(-4, 4));
        MixedSet set = new MixedSet(false);
        set.add(intSet);
        System.out.println(set.toString());
    }

    @Test
    public void addMixedSet2() {
        System.out.println("Mixed Set: Adding MixedSet");
        MixedSet cSet = new MixedSet(false);
        cSet.add('n');
        MixedSet set = new MixedSet(false);
        set.add(cSet);
        System.out.println(set.toString());
    }

    @Test
    public void addCharAndMixedSet() {
        System.out.println("Mixed Set: Adding MixedSet and MixedSet");
        MixedSet cSet = new MixedSet(false);
        MixedSet set = new MixedSet(false);
        MixedSet intSet = new MixedSet(false);

        cSet.add('n');
        intSet.add(new IntRange(-4, 4));

        set.add(cSet);
        set.add(intSet);

        System.out.println(set.toString());
    }

    @Test
    public void union() {
        MixedSet cSet = new MixedSet(false);
        MixedSet intSet = new MixedSet(false);

        intSet.add(new IntRange(-1, 1));
        cSet.add('a');
        cSet.addNull();

        MixedSet set = new MixedSet(false);
        set.addAll(cSet);
        set.union(intSet);
        System.out.println(set.toString());
    }

    @Test
    public void union2() {
        MixedSet cSet = new MixedSet(false);
        MixedSet intSet = new MixedSet(false);

        intSet.add(new IntRange(-1, 1));
        cSet.add(new IntRange(2, 4));
        cSet.addNull();

        MixedSet set = new MixedSet(false);
        set.addAll(cSet);
        set.union(intSet);
        System.out.println(set.toString());
    }

    @Test
    public void Cartesian() {
        MixedSet iset = new MixedSet(false);
        iset.add(new IntRange(-2, 2));
        iset.remove(0);

        MixedSet sset = new MixedSet(false);
        sset.add("Foo");
        sset.add("Bar");

        MixedSet set = new MixedSet(false);

        set.addAll(iset);
        set = (MixedSet) set.cartesianProduct(sset);
        System.out.println(set.toString());
    }



    @Test
    public void powerSetTest() {
        MixedSet set = new MixedSet(false);
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");

        set = set.powerSet(set);
        String answ = set.toString();

        System.out.println(set.toString());
        assert(answ.contains("{{}, {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}, {4}, {1, 4}, {2, 4}, {1, 2, 4}, {3, 4}, {1, 3, 4}, {2, 3, 4}, {1, 2, 3, 4}}"));


    }

    @Test
    public void powerSetTest2() {
        MixedSet set = new MixedSet(false);
        set.add("12");
        set.add("A");
        set.add("Anana");

        set = set.powerSet(set);
        String answ = set.toString();

        System.out.println(set.toString());


    }

    @Test public void productTest(){
        MixedSet set = new MixedSet(false);
        set.add("12");
        set.add("A");

        set = (MixedSet) set.cartesianProduct(set);
        String answ = set.toString();

        System.out.println(set.toString());
        assert(set.getMultiplicity() == 4);

    }

    @Test public void productTest2(){
        MixedSet set = new MixedSet(false);
        set.add("12");
        set.add("A");
        set.add("-5");

        set = (MixedSet) set.cartesianProduct(set);

        String answ = set.toString();
        System.out.println("set.multiplicity() = " + set.getMultiplicity());
        System.out.println(set.toString());
        assert(set.getMultiplicity() == 9);
        // assert(answ.contains("{(12,12), (A,12), (12,A), (A,A)}"));
    }

    @Test public void hasInnerSetsTest(){
        MixedSet set = new MixedSet(false);
        set.add("12");
        set.add("A");
        set.add("Anana");

        set = set.powerSet(set);
        String answ = set.toString();

        System.out.println(set.toString());
        System.out.println(set.hasInnerSets());
        assert(set.hasInnerSets());
        // assert(answ.contains("{(12,12), (A,12), (12,A), (A,A)}"));
    }

    @Test public void complementTest() throws IncorrectInputSyntaxException {
        MixedSet set = new MixedSet(false);
        set.add("12");
        set.add("A");
        set.add("Anana");
        String testVal = "(5,15)";
        MixedSet universalSet = SyntaxAnalyser.rangeToUniversalSet(testVal);
        set = set.complement();
        System.out.println(set.toString());
        assert(set.toString().contains("{6, 7, 8, 9, 10, 11, 13, 14}"));
    }


    @Test public void weakSubset(){
        MixedSet set = new MixedSet(false);
        set.add("1");
        set.add("2");
        set.add("3");

        MixedSet setToCompare = new MixedSet(false);
        setToCompare.add("1");
        setToCompare.add("2");
        setToCompare.add("3");

        boolean answ = set.weakSubset(setToCompare);
        assert answ;


        MixedSet set2 = new MixedSet(false);
        set2.add("");
        set2.add("2");
        set2.add("3");

        MixedSet setToCompare2 = new MixedSet(false);
        setToCompare2.add("1");
        setToCompare2.add("2");
        setToCompare2.add("3");

        boolean answ2 = set2.weakSubset(setToCompare2);
        assert !answ2;



        MixedSet set3 = new MixedSet(false);
        set3.add("1");
        set3.add("2");
        set3.add("3");

        MixedSet setToCompare3 = new MixedSet(false);
        setToCompare3.add("1");
        setToCompare3.add("2");
        setToCompare3.add("");

        boolean answ3 = set3.weakSubset(setToCompare3);
        assert !answ3;
    }


    @Test public void properSubset(){
        MixedSet set = new MixedSet(false);
        set.add("1");
        set.add("2");
        set.add("3");

        MixedSet setToCompare = new MixedSet(false);
        setToCompare.add("1");
        setToCompare.add("2");
        setToCompare.add("3");

        boolean answ = set.properSubset(setToCompare);
        System.out.println("answ = " + answ);
        assert !answ;
    }

    @Test public void properSubset2(){
        MixedSet set = new MixedSet(false);
        set.add("1");
        set.add("2");
        set.add("3");

        MixedSet setToCompare = new MixedSet(false);
        setToCompare.add("1");
        setToCompare.add("2");
        // setToCompare.add("3");

        boolean answ = set.properSubset(setToCompare);
        System.out.println("answ = " + answ);
        assert answ;
    }

    @Test public void properSubset3(){
        MixedSet set = new MixedSet(false);
        set.add("1");
        set.add("2");
        set.add("3");
        MixedSet subset = new MixedSet(true);
        subset.add("1");
        subset.add("2");
        subset.add("AN");
        set.add(subset);

        MixedSet setToCompare = new MixedSet(false);
        setToCompare.add("1");
        setToCompare.add("2");
        // setToCompare.add("3");

        boolean answ = set.properSubset(setToCompare);
        System.out.println("answ = " + answ);
        assert answ;
    }

    @Test public void properSubset4(){
        MixedSet set = new MixedSet(false);
        set.add("1");
        set.add("2");
        set.add("3");

        MixedSet setToCompare = new MixedSet(false);
        setToCompare.add("1");
        setToCompare.add("2");
        // setToCompare.add("3");

        MixedSet subset = new MixedSet(true);
        subset.add("1");
        subset.add("2");
        subset.add("AN");
        setToCompare.add(subset);

        boolean answ = set.properSubset(setToCompare);
        System.out.println("answ = " + answ);
        assert !answ;
    }


    @Test public void properSubset5(){
        MixedSet set = new MixedSet(false);
        set.add("1");
        set.add("2");
        set.add("3");

        MixedSet setToCompare = new MixedSet(false);
        setToCompare.add("1");
        setToCompare.add("2");
        // setToCompare.add("3");

        MixedSet subset = new MixedSet(true);
        subset.add("1");
        subset.add("2");
        subset.add("AN");
        setToCompare.add(subset);
        set.add(subset);

        boolean answ = set.properSubset(setToCompare);
        System.out.println("answ = " + answ);
        assert answ;
    }


}