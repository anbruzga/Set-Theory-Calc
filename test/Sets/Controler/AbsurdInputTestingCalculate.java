package Sets.Controler;

import Sets.Model.MixedSet;
import Sets.Model.Symbols;
import Sets.Model.UniversalSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class AbsurdInputTestingCalculate {

	private static final char union = '\u222A';
	private static final char intersection = '\u2229';
	private static final char product = '\u2A2F';
	private static final char difference = '\u2216';
	private String str;
	private MixedSet answ;

	@Before
	public void setUniversalSet() throws IncorrectInputSyntaxException {
		String testVal = "[-99,101]";
		String setName = "U";
		UniversalSet.setType('Z');
		SyntaxAnalyser.rangeToUniversalSet(testVal);
	}


	@Test
	public void calculatorAbsurdInput1()  {
		str = "{1,2,3,}4}" + union + "{1,3,{4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput2()  {
		str = "{1,2,3,4" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput3()  {
		str = "1,2,3,4}" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput4()  {
		str = "(1,2,3,4" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput5()  {
		str = "(1,2,3,4)" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}
	@Test
	public void calculatorAbsurdInput6()  {
		str = "{1,2,3,4}}" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput7()  {
		str = "{1,2,3,4},}" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput8()  {
		str = ",{1,2,3,4" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput9()  {
		str = ".{1,2,3,4" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}
	@Test
	public void calculatorAbsurdInput10()  {
		str = "}1,2,3,4{" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}
	@Test
	public void calculatorAbsurdInput11()  {
		str = "({1,2,3,4" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput12()  {
		str = "{1,2,3,4" + union ;
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput13()  {
		str = "{1,2,3,4'";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}


	@Test
	public void calculatorAbsurdInput14()  {
		str = "" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput15()  {
		str = "{1,2,3,4}^" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}
	@Test
	public void calculatorAbsurdInput16()  {
		str = "@{1,2,3,4}^" + union + "{1,3,4,5}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput17()  {
		str = "";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput18()  {
		str = " ";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput19()  {
		str = "...";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput20()  {
		str = ".";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}


	@Test
	public void calculatorAbsurdInput21()  {
		str = "{{}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInpu22()  {
		str = "{}}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput23()  {
		str = "{}{}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput24()  {
		str = "{}{}{}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput25()  {
		str = "}{";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput26()  {
		str = "()  {}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput27()  {
		str = ")_)_";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput28()  {
		str = "@";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput29()  {
		str = "{}()[]";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput30()  {
		str = "{[]}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput31()  {
		str = "[]";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

	@Test
	public void calculatorAbsurdInput32()  {
		str = "{1,2} ' {1,3}";
		try {
			Calculate.evaluate(str);
			fail( "My method didn't throw when I expected it to" );
		} catch (IncorrectInputSyntaxException expectedException) {
		}
	}

}
