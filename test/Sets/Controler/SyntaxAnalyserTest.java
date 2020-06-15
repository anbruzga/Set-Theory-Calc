package Sets.Controler;

import Sets.Model.MixedSet;
import Sets.Model.UniversalSet;
import org.junit.Test;

import static Sets.Controler.SyntaxAnalyser.rosterToSet;
import static junit.framework.TestCase.fail;

public class SyntaxAnalyserTest {

    private static final char union = '\u222A';
    private static final char intersection = '\u2229';
    private static final char product = '\u2A2F';
    private static final char difference = '\u2216';

    @Test
    public void checkBalancedParenthesisTest() throws IncorrectInputSyntaxException {
        String expr = "{}()[]";

        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = ")("; // not balanced
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = "(1)(2)";
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when supposed to");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = "(())";
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when supposed to");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = "([{]})"; // not balanced
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = "[(){][}]"; // not balanced
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = "({1}){}()(({})(){123})"; // balanced
        assert SyntaxAnalyser.checkSyntaxSanity(expr);

        expr = "({asdasd})dsds{}(     )(aaaa({dddddd})()fffff{ggg}ssss)"; // balanced
        try {
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw, when supposed to");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = ".....@@@!!({asdasd})dsds{}(     )[(aaaa[([{dddddd}])()fffff{ggg}]s@!@#sss)]"; // balanced but illegal symbols
        try {
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw, when supposed to");
        }
        catch(IncorrectInputSyntaxException ex){

        }
        expr = ".....@@@!!({asdasd})dsds{}(     [(aaaa[([{dddddd}])()fffff{ggg}]s@!@#sss)]"; //not balanced illegal symbols
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = ".....!(asdasd})dsds{}(     )[(aaaa[([{dddddd}])()fffff{ggg}]s!sss)]"; // not balanced
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }


        //  System.out.println("\u222A"); // union
        //  System.out.println("\u2229"); // intersection
        //  System.out.println("\u2A2F"); // cartesian product
        //  System.out.println("\u2216"); // set difference

        expr = "([\u2216])"; // balanced but no set after - fail
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = "([])\u2216"; // balanced but no set after - fail
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }

        expr = "(([])\u2216())"; // balanced but no set after - fail
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }
        expr = "(([])\u2216)"; // balanced but no set after - fail
        try{
            SyntaxAnalyser.checkSyntaxSanity(expr);
            fail("Didn't throw exception when expected");
        }
        catch(IncorrectInputSyntaxException ex){

        }
        expr = "S = (({}))"; // balanced
        assert SyntaxAnalyser.checkSyntaxSanity(expr);

    }

    @Test
    public void distributeNotationTest() {
        SyntaxAnalyser.getListOfSetNotationTypes("{1, 2 , 3, 4 } " + union + "{1,4,6,7}");
    }


    @Test
    public void rosterSplittingStringTest() throws IncorrectInputSyntaxException {

        //    Scanner in = new Scanner(System.in);
        //   System.out.println("Enter set");
        String full;// = in.next();

        String testVal = "[-99,101]";
        String setName = "U";
        UniversalSet.setType('Z');
        SyntaxAnalyser.rangeToUniversalSet(testVal);
        MixedSet set0 = null;
        int equalsIndex;
        String answ;
        // Test 1

        full = "{mama, papa, Agata}";
        set0 = rosterToSet(full);
        System.out.print("set toString:");
        System.out.println(set0.toString());
        System.out.println();
         equalsIndex = set0.toString().indexOf('=');
        answ = set0.toString().substring(equalsIndex + 2);
        assert (full.contains(answ));

        // Test 2
        full = "{mama, papa, Agata, {Gerdenis, Jurate}}";
        set0 = rosterToSet(full);
        System.out.print("set toString:");
        System.out.println(set0.toString());
        equalsIndex = set0.toString().indexOf('=');
        answ = set0.toString().substring(equalsIndex + 2);
        assert (full.contains(answ));


        // Test 3
        full = "{mama, papa, {Gerdenis, Jurate}, Jurcia, Babce}";
        set0 = rosterToSet(full);
        System.out.print("set toString:");
        System.out.println(set0.toString());
        equalsIndex = set0.toString().indexOf('=');
        answ = set0.toString().substring(equalsIndex + 2);
        assert (full.contains(answ));

        // Test 4
        full = "{1, {A, {D, E}}, Babce}";
        try {
            set0 = rosterToSet(full);
        } catch (IncorrectInputSyntaxException e) {
            e.printStackTrace();
        }
        //set0 = Calculate.evaluate(full);
        System.out.print("set toString:");
        System.out.println(set0.toString());
        equalsIndex = set0.toString().indexOf('=');
        answ = set0.toString().substring(equalsIndex + 2);
        System.out.println("answ = " + answ);
        assert (full.contains(answ));

        // Test 5
        full = "{1, {A, {D, E}}, Babce}";
        set0 = rosterToSet(full);
        System.out.println(set0.toString());
        System.out.println("answ =" + answ);
        assert (full.equals(set0.toString()));

        // Test 6
        full = "{1, {A, B}, {A, B}, Babce}";
        String correctOutput = "{1, {A, B}, Babce}";
        set0 = rosterToSet(full);
        System.out.print("set toString:");
        System.out.println(set0.toString());
        answ = set0.toString();
        System.out.println("answ = " + answ);
        assert (correctOutput.contains(answ));

        // Test 7
        full = "{1, {A, B}, {A, C}, Babce}";
        set0 = rosterToSet(full);
        System.out.print("set toString:");
        System.out.println(set0.toString());
        answ = set0.toString();
        assert (full.contains(answ));







        // Test 11
        full = "{1, {A, {B, C, D}, 1}, {A, 1, {C, D}, Babce, Ali}, 0}";
        set0 = Calculate.evaluate(full);
        System.out.print("set toString:");

        answ = set0.toString();
        System.out.println("answ = " + answ);
        assert (full.contains(answ));

    }

    @Test
    public void rosterToSet10Test() throws IncorrectInputSyntaxException {
        // Test 10
        String full = "{1, {A, {B, C, D}, 1}, {A, 1, {C, D}, Babce, Ali}}";
        MixedSet set0 = Calculate.evaluate(full);
        System.out.print("set toString:");
        String answ = set0.toString();
        System.out.println("answ = " + answ);
        assert (full.contains(answ));
    }

    @Test
    public void rosterToSet9Test() throws IncorrectInputSyntaxException {
        // Test 9
        String full = "{1, {A, B}, {A, C}, Babce, Ali}";
        MixedSet set0 = rosterToSet(full);
        System.out.print("set toString:");
        System.out.println(set0.toString());

        String answ = set0.toString();
        assert (full.contains(answ));
    }
    @Test
    public void rosterStringToSetsTest() throws IncorrectInputSyntaxException {
        String full = "{mama, papa, Agata}";
        MixedSet set;

        String testVal = "[-100,101]";
        SyntaxAnalyser.rangeToUniversalSet(testVal);


        set = Calculate.evaluate(full);
        int equalsIndex = set.toString().indexOf('=');
        String answ = set.toString();//.substring(equalsIndex + 2);
        System.out.println("answ = " + answ);
        assert (answ.contains("{mama, papa, Agata}"));


        full = "{mama, papa, Agata, {Gerdenis, Jurate}}";
        set = Calculate.evaluate(full);
        equalsIndex = set.toString().indexOf('=');
        answ = set.toString();//.substring(equalsIndex + 2);
        System.out.println("answ = " + answ);
        assert (answ.contains("{mama, papa, Agata, {Gerdenis, Jurate}}"));


    }
    @Test
    public void evaluateSimpleNestedSet() throws IncorrectInputSyntaxException {

        String full = "{mama, papa, {Gerdenis, Jurate}, Jurcia, Babce}";
        MixedSet set = Calculate.evaluate(full);
        int equalsIndex = set.toString().indexOf('=');
        String answ = set.toString();//.substring(equalsIndex + 1);
        assert (answ.contains("{mama, papa, {Gerdenis, Jurate}, Jurcia, Babce}"));
    }



    @Test
    public void rosterStringToSetsTestOperators() throws IncorrectInputSyntaxException {

        MixedSet set;

        String testVal = "[-100,101]";
        SyntaxAnalyser.rangeToUniversalSet(testVal);
        String full = "{mama, papa, Agata}\u2229{mama, papa, Agata, {Gerdenis, Jurate}}\u222A" +
                "{mama, papa, {Gerdenis, Jurate}, Jurcia, Babce}";
        set = Calculate.evaluate(full);
        System.out.println("set.toString() = " + set.toString());

        assert(set.toString().contains("{mama, papa, Agata, {Gerdenis, Jurate}, Jurcia, Babce}"));


        //  System.out.println("\u222A"); // union
        //   System.out.println("\u2229"); // intersection
        //  System.out.println("\u2A2F"); // cartesian product
        //  System.out.println("\u2216"); // set difference
    }


    @Test
    public void StringRangeToSet() throws IncorrectInputSyntaxException {
        String testVal;
        String setName;
        MixedSet set;
        UniversalSet universalSet;


        testVal = "[1,5]";
        setName = "U";
        SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(UniversalSet.getInstance().toString());
        assert (UniversalSet.getInstance().toString().equals("U = {1, 2, 3, 4, 5}"));

        testVal = "[1,5)";
        setName = "U";
        set = SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(set.toString());
        assert (set.toString().equals("U = {1, 2, 3, 4}"));

        testVal = "(1,5)";
        setName = "U";
        set = SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(set.toString());
        assert (set.toString().equals("U = {2, 3, 4}"));

        testVal = "[-2,2)";
        setName = "U";
        set = SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(set.toString());
        assert (set.toString().equals("U = {-2, -1, 0, 1}"));

        testVal = "(-2,2)";
        setName = "U";
        set = SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(set.toString());
        assert (set.toString().equals("U = {-1, 0, 1}"));

        testVal = "(-4,-1)";
        setName = "U";
        set = SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(set.toString());
        assert (set.toString().equals("U = {-3, -2}"));


        testVal = "(-4,-1]";
        setName = "U";
        set = SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(set.toString());
        assert (set.toString().equals("U = {-3, -2, -1}"));

        testVal = "[-4,-1]";
        setName = "U";
        set = SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(set.toString());
        assert (set.toString().equals("U = {-4, -3, -2, -1}"));


        testVal = "[-4,-1)";
        setName = "U";
        set = SyntaxAnalyser.rangeToUniversalSet(testVal);
        System.out.println(set.toString());
        assert (set.toString().equals("U = {-4, -3, -2}"));
    }
}