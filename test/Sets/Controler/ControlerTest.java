package Sets.Controler;

import Sets.Model.MixedSet;
import Sets.Model.Symbols;
import Sets.Model.UniversalSet;
import org.junit.Before;
import org.junit.Test;

import static Sets.Model.Symbols.*;

public class ControlerTest {

    private static final char union = '\u222A';
    private static final char intersection = '\u2229';
    private static final char product = '\u2A2F';
    private static final char difference = '\u2216';

    private String str;
    private String answ;

    @Before public void setUniversalSet() throws IncorrectInputSyntaxException {
        String testVal = "[-99,101]";
        String setName = "U";
        UniversalSet.setType('Z');
        SyntaxAnalyser.rangeToUniversalSet(testVal);
    }

    @Test
    public void TestCalculator() throws IncorrectInputSyntaxException {

        //test 1
        String testVal = "[-99,101]";
        String setName = "U";
        SyntaxAnalyser.rangeToUniversalSet(testVal);
        int equalsIndex;


        System.out.println("\nTEST 1 ");
        str = "{1,2,3,4}" + union + "{1,3,4,5}";
        answ = Calculate.evaluate(str).toString();
        equalsIndex = answ.indexOf('=');
        //  answ = answ.substring(equalsIndex + 2);
        assert (answ.equals("{1, 2, 3, 4, 5}"));

        //test 2
        System.out.println("\nTEST 2 ");
        str = "{1,2,3,4}" + intersection + "{1,3,4,5}";
        answ = Calculate.evaluate(str).toString();
        equalsIndex = answ.indexOf('=');
        // answ = answ.substring(equalsIndex + 2);
        assert (answ.equals("{1, 3, 4}"));

        //test 3
        System.out.println("\nTEST 3 ");
        str = "{1,2,3,4}" + difference + "{1,3,4,5}";
        answ = Calculate.evaluate(str).toString();
        equalsIndex = answ.indexOf('=');
        //answ = answ.substring(equalsIndex + 2);
        assert (answ.equals("{2}"));

        //test 3
        System.out.println("\nTEST 4 ");
        str = "{1,2,3,4,{1,4}}" + difference + "{1,3,4,5}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        equalsIndex = answ.indexOf('=');
        //  answ = answ.substring(equalsIndex + 2);
        System.out.println(answ);
        assert (answ.equals("{2, {1, 4}}"));

        //test 5
        System.out.println("\nTEST 5 ");
        str = "({1,2,3,4}" + difference + "{1,3,4,5})" + union + "{1 , 2}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        equalsIndex = answ.indexOf('=');
        //  answ = answ.substring(equalsIndex + 2 );
        assert (answ.equals("{2, 1}"));

    }

    @Test
    public void TestCalculator2() throws IncorrectInputSyntaxException {

        System.out.println("\nTEST 6 ");
        str = "({1,2}" + difference + "{1,3})" + difference + "({1 , 2,3} " + union + "{4,5,6})";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        //  answ = answ.substring(equalsIndex + 1);
        System.out.println("answ = " + answ);
        assert (answ.equals("{}"));

    }

    @Test
    public void TestCalculator3() throws IncorrectInputSyntaxException {

        System.out.println("\nTEST 7 ");
        str = "{1,2,3, {Black, Red },4,5} " + union + "{x: x " + belongsTo + " Z, x > -5, x < 5}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        // answ = answ.substring(equalsIndex + 2);
        System.out.println("answ = " + answ);

    }

    @Test
    public void deleteMe() throws IncorrectInputSyntaxException {
        System.out.println(product);
        System.out.println("\u2208");
        System.out.println("\u221E");
        System.out.println("\u1D43");
        System.out.println(Symbols.properSubset);
        System.out.println(Symbols.weakSubset);
    }

    @Test
    public void TestCalculator4() throws IncorrectInputSyntaxException {

        System.out.println("\nTEST 8 ");
        str = "{1,2}" + difference + "{1,3}" + difference + "{1} ";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        //  answ = answ.substring(equalsIndex + 2);

        assert (answ.equals("{2}"));
    }

    @Test
    public void TestCalculator5() throws IncorrectInputSyntaxException {

        str = "{1,2}" + difference + "{1,3}" + difference + "{1} " + union + "{4} " + union + "{1,3}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        //  answ = answ.substring(equalsIndex + 2 );

        assert (answ.equals("{2, 4, 1, 3}"));
    }


    @Test
    public void TestCalculatorA() throws IncorrectInputSyntaxException {

        str = "{1,2,3,4,5} " + union + "{x: x " + belongsTo + " Z, x > -5, x < 5}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        //   answ = answ.substring(equalsIndex + 2 );
        assert (answ.equals("{1, 2, 3, 4, 5, -4, -3, -2, -1, 0}"));
    }


    @Test
    public void TestCalculatorProperSubset() throws IncorrectInputSyntaxException {

        str = "{1,2,3,4,5} " + Symbols.properSubset + "{x: x " + belongsTo + " Z, x > -5, x < 5}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        //  int equalsIndex = answ.indexOf('=');
        System.out.println(answ);
        assert(answ.contains("false"));
    }

    @Test
    public void TestCalculatorProperSubsetA() throws IncorrectInputSyntaxException {
        str = "{1,2,3,4,5} " + Symbols.properSubset + "{x, x " + belongsTo + " Z, x > -5, x < 5}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        System.out.println(answ);
        assert(answ.contains("false"));
    }

    @Test
    public void TestCalculatorWeakSubsetA() throws IncorrectInputSyntaxException {
        str = "{1,2,3,4,5} " + Symbols.weakSubset + "{x: x " + belongsTo + " Z, x >= 1, x < 5}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toStringNoName(true);
        System.out.println(answ);
        assert(answ.contains("true"));

    }

    @Test
    public void TestCalculatorWeakSubsetB() throws IncorrectInputSyntaxException {
        str = "{1,2,3,4,5} " + Symbols.weakSubset + "{x: x " + belongsTo + " Z, x >= 1, x <= 5}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        // int equalsIndex = answ.indexOf('=');
        System.out.println(answ);
        assert(answ.contains("true"));

    }


    @Test
    public void TestCalculatorDoubleValues1() throws IncorrectInputSyntaxException {
        UniversalSet.setType('Q');

        str = "{1.0, 2.542} " + Symbols.union + "{1.23, 2542}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        System.out.println(answ);
        UniversalSet.setType('N');
        assert(answ.contains("{1, 2.542, 1.23}"));

    }

    @Test
    public void TestCalculatorComplementSimple() throws IncorrectInputSyntaxException {
        str = "{1,2,3,4,5,6,7,8,9,10}'";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        System.out.println(answ);
        assert(!answ.contains("1, 2, 3, 4, 5, 6, 7, 8, 9"));

    }

    @Test
    public void TestCalculatorComplementSimple2() throws IncorrectInputSyntaxException {
        str = "{0, 1,2,3,4,5,6,7,8,9,10}'" + Symbols.union + "{0,1,2,3,4}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        System.out.println("equalsIndex = " + equalsIndex);
        System.out.println(answ);
        System.out.println("equalsIndex = " + equalsIndex);
        assert(!answ.contains("5, 6, 7, 8, 9, 10"));
        assert(answ.contains("0, 1, 2, 3, 4"));

    }

    @Test
    public void TestCalculatorComplementSimple3() throws IncorrectInputSyntaxException {
        str = "{0, 1,2,3,4,5,6,7,8,9,10}'" + Symbols.union + "{0,1,2,3,4}" + union + "{7}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        System.out.println("equalsIndex = " + equalsIndex);
        System.out.println(answ);
        System.out.println("equalsIndex = " + equalsIndex);
        assert(!answ.contains("5, 6, 7, 8, 9, 10"));
        assert(answ.contains("7"));
        assert(answ.contains("0, 1, 2, 3, 4"));

    }


    @Test
    public void TestCalculatorPowerSetSimple() throws IncorrectInputSyntaxException {
        str = "P({1,2,3})";
        System.out.println(str);
        answ = Calculate.evaluate(str).toStringNoName(true);
        //int equalsIndex = answ.indexOf('=');
        System.out.println(answ);
        MixedSet setTest = new MixedSet(false);
        setTest.add(1);
        setTest.add(2);
        setTest.add(3);
        setTest = setTest.powerSet(setTest);
        assert(answ.contains(setTest.toStringNoName(true)));

    }
    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     * NEW TESTS
     * Tests cases taken from MATHS tutorial 6 sheet, year 2017
     * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
     */
    @Test
    public void testUpdatedSetBuilderNotation1A() throws IncorrectInputSyntaxException {
        String ex = "B = {x: x " + belongsTo + " N, x^2 = x}";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{1}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }

    @Test
    public void testUpdatedSetBuilderNotation2B() throws IncorrectInputSyntaxException {
        String ex = "B = {x: x " + belongsTo + " N, x^2 > 4*x+1}";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, " +
                "32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, " +
                "57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82" +
                ", 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }

    /*
    @Test
    public void testUpdatedSetBuilderNotation3C(){
        String ex = "B = {(x,y): x " + belongsTo + " N, x " + belongsTo + "y , x < y, y <= 3}";
        str = new SetComprehension<Double>().stringToSet(ex).toStringNoName(false);
        //str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{(1,2),(1,3),(2,3)}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }

    @Test
    public void testUpdatedSetBuilderNotation4D(){
        String ex = "B = {(x,y): x " + belongsTo + "  N, y " + belongsTo + " N, x = y, y < 5}";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{(1,1),(2,2),(3,3),(4,4)}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }

    @Test
    public void testUpdatedSetBuilderNotation5E(){
        String ex = "B = {(x,y,z): (y " + belongsTo + " N, z "+ belongsTo + " N, z = x + 2y, x = 10, 1<y < 5} ";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{(10, 2, 14), (10, 3, 18), (10, 4, 26)}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }

    @Test
    public void testNumberAndInterval1A(){
        String ex = " 3 " + belongsTo +" (3, 5]";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{false}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }
*/
   /* @Test
    public void testNumberAndInterval2B(){
        String ex = " 5 " + belongsTo +" (3, 5]";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{true}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }
    */


   /* @Test
    public void testNumberAndInterval2B() throws IncorrectInputSyntaxException {
        String ex = " [2, 7] " + properSubset +" (3, 5]";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{true}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }*/


    @Test
    public void testWeakSubsetC() throws IncorrectInputSyntaxException {
        String ex = "{1,2,3}" + weakSubset +"{3,2,1}";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{true}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }

    @Test
    public void testProperSubsetD() throws IncorrectInputSyntaxException {
        String ex = "{1,2,3}" + properSubset +"{3,2,1}";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{false}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }

/*    @Test
    public void testIntervalTProperSubsetSetE(){
        String ex = "[1,2]" + properSubset +"{1,2,3}";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{false}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }


    @Test
    public void testSetWeakSubsetIntervalF(){
        String ex = "{1,2,3}" + properSubset +"[1,2]";
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{false}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }
*/

/*
    @Test
    public void testSetWeakSubsetIntervalG(){
        String ex = "{-1,0,1}" + belongsTo +"[-1,1]"; // it is whether not weak subset, or is not belongsTo
        str = Calculate.evaluate(ex).toStringNoName(true);
        answ = "{true}";
        System.out.println("str = " + str);
        assert(answ.equals(str));
    }
*/
    //###################################################################

    @Test
    public void TestCalculatorPowerSetSimple2() throws IncorrectInputSyntaxException {
        UniversalSet.setType('Z');
        str = "P({1,2,3}) " + union + "{-1,-2,0,1} ";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();
        int equalsIndex = answ.indexOf('=');
        System.out.println(answ);
        MixedSet setTest = new MixedSet(false);
        setTest.add(1);
        setTest.add(2);
        setTest.add(3);
        setTest = setTest.powerSet(setTest);
        MixedSet setTestForUnion = new MixedSet(false);
        setTestForUnion.add(-1);
        setTestForUnion.add(-2);
        setTestForUnion.add(0);
        setTestForUnion.add(1);
        setTest = setTest.union(setTestForUnion);

        System.out.println("setTest.toString() = " + setTest.toString());
        System.out.println("answ = " + answ);

        assert(answ.contains(setTest.toStringNoName(true)));

    }



    /*@Test
    public void  TestCalculatorB() throws IncorrectInputSyntaxException {

        System.out.println("\nTEST 7 ");
        str = "({-7, 0, 7} " +  union + "{1,2,3, {Black, Red, {Green, {}},4,5}) " + union + "{x e Z, x * -1 | x > -5, x < 5}";
        System.out.println(str);
        answ = Calculate.evaluate(str).toString();



    }*/


}

