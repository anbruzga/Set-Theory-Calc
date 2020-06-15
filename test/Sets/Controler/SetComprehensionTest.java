package Sets.Controler;

import Sets.Model.MixedSet;
import Sets.Model.Symbols;
import Sets.Model.UniversalSet;
import org.junit.Before;
import org.junit.Test;

public class SetComprehensionTest {



    @Before
    public void setUniversalSet() throws IncorrectInputSyntaxException {
        String testVal = "[-99,101]";
        String setName = "U";
        SyntaxAnalyser.rangeToUniversalSet(testVal);
        UniversalSet.setType('Z');
    }

    private String expr = "x e N , x >= 1";
    // setC.testUsage(expr);
    int equalsIndex;
    private MixedSet set;
    private MixedSet universalSet = new MixedSet(false);
    private SetComprehension setC2 = new SetComprehension();

    @Test
    public void setComprehensionTest1() throws IncorrectInputSyntaxException {
        expr = "{x: x " + Symbols.belongsTo + " N, x >=-1, x <=5}";
        UniversalSet.setType('Q');
        set = setC2.stringToSet(expr);
        String answ = set.toString();
        equalsIndex = answ.indexOf('=');
        //answ = answ.substring(equalsIndex + 2 );
        assert ("{1, 2, 3, 4, 5}".equals(answ));
    }

    @Test
    public void setComprehensionTest2() throws IncorrectInputSyntaxException {
        expr = "{x:  x " + Symbols.belongsTo + " Z, x >= -2, x <= 3}";
        set = setC2.stringToSet(expr);
        String answ = set.toString();
        equalsIndex = answ.indexOf('=');
        //answ = answ.substring(equalsIndex + 2 );
        System.out.println("answ.toString() = " + answ.toString());
        assert ("{-2, -1, 0, 1, 2, 3}".equals(answ));
    }

    @Test
    public void setComprehensionTest3() throws IncorrectInputSyntaxException {
        expr = "{x: x " + Symbols.belongsTo + " Z , x > -5}";
        set = setC2.stringToSet(expr);
        String answ = set.toString();
        equalsIndex = answ.indexOf('=');
        //answ = answ.substring(equalsIndex + 2 );
        assert ("{-4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101}"
                .equals(answ));
    }

    @Test
    public void setComprehensionTest4() throws IncorrectInputSyntaxException {
        expr = "{x:  x " + Symbols.belongsTo + " Z , x >= -100, x <= 10}";
        set = setC2.stringToSet(expr);
        String answ = set.toString();
        equalsIndex = answ.indexOf('=');
        //answ = answ.substring(equalsIndex + 2 );
        assert (("{-99, -98, -97, -96, -95, -94, -93, -92, -91, -90, -89, -88, -87, -86, -85, -84, -83, -82, -81, -80," +
                " -79, -78, -77, -76, -75, -74, -73, -72, -71, -70, -69, -68, -67, -66, -65, -64, -63, -62, -61, -60, " +
                "-59, -58, -57, -56, -55, -54, -53, -52, -51, -50, -49, -48, -47, -46, -45, -44, -43, -42, -41, -40, -" +
                "39, -38, -37, -36, -35, -34, -33, -32, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -21, -20, -19," +
                " -18, -17, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, " +
                "7, 8, 9, 10}")
                .equals(answ));
    }


    @Test
    public void setComprehensionTest7() throws IncorrectInputSyntaxException {
        expr = "{x: x " + Symbols.belongsTo + " Z, x > -5, x < 0}";

        set = setC2.stringToSet(expr);
        String answ = set.toString();
        equalsIndex = answ.indexOf('=');
        //answ = answ.substring(equalsIndex + 2 );
        assert ("{-4, -3, -2, -1}".equals(answ));
    }

    @Test
    public void setComprehensionTest8() throws IncorrectInputSyntaxException {
        expr = "{x: x " + Symbols.belongsTo + " N, x > -5, x <= 5, x != 0, x != 2}";
        set = setC2.stringToSet(expr);
        String answ = set.toString();
        equalsIndex = answ.indexOf('=');
        //answ = answ.substring(equalsIndex + 2 );

        assert ("{1, 3, 4, 5}".equals(answ));
    }

    @Test
    public void setComprehensionTest9() throws IncorrectInputSyntaxException {
        expr = "{x:  x " + Symbols.belongsTo + " Z, x > -5, x <= 5, x != 0}";
        set = setC2.stringToSet(expr);
        String answ = set.toString();
        equalsIndex = answ.indexOf('=');
        //answ = answ.substring(equalsIndex + 2 );

        assert ("{-4, -3, -2, -1, 1, 2, 3, 4, 5}".equals(answ));
    }
}