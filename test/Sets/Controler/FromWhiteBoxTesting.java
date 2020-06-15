package Sets.Controler;

import Sets.Model.MixedSet;
import Sets.Model.UniversalSet;
import org.junit.Before;
import org.junit.Test;


public class FromWhiteBoxTesting {

	private static final char union = '\u222A';
	private static final char intersection = '\u2229';
	private static final char product = '\u2A2F';
	private static final char difference = '\u2216';

	private String str;
	private String answ;

	@Before
	public void setUniversalSet() throws IncorrectInputSyntaxException {
		String testVal = "[-99,101]";
		String setName = "U";
		UniversalSet.setType('Z');
		SyntaxAnalyser.rangeToUniversalSet(testVal);
	}


	// TESTS EXTRACTED FROM WHITE BOX TESTING
	@Test
	public void nestedRosterSet() throws IncorrectInputSyntaxException {
		String str = "{1, {1,2,3}, {ab}, {abc}, {1,2}, 4}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{1, {1, 2, 3}, {ab}, {abc}, {1, 2}, 4}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void tripleNestedRosterSet() throws IncorrectInputSyntaxException {
		String str = "{1,2,3,{1,2,{ab,ac,{bc}, ad}, 3}}"; // Unbalanced parenthesis
		MixedSet set = Calculate.evaluate(str);
		String expected = "{1, 2, 3, {1, 2, {ab, ac, {bc}, ad}, 3}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void setBuilderNotationReversedPredicate() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "{x: x ∈ Z,  9 <= x }";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{9, 10}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	//@Test //todo ENABLE THIS BACK
	public void powerSetOfSetBuilderNotation() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "P({x, x ∈ Z |  x <= 9, x > 5, x != 7 })";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{}, {6}, {8}, {6, 8}, {9}, {6, 9}, {8, 9}, {6, 8, 9}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void powerSetOfRosterNotation() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "P({1,2,5})";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{}, {1}, {2}, {1, 2}, {5}, {1, 5}, {2, 5}, {1, 2, 5}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void powerSetOfPowerSet() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "P(P({a}))";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{}, {{}}, {{a}}, {{}, {a}}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void intersectionBetweenThreeNestedSets1() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "{1,2,3,{john},4}∩{3,{john},4,5}∩{1,{john},4}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{john}, 4}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void intersectionBetweenThreeNestedSets2() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "{1,2,3,{john}}∩{3,{john}}∩{1,{john}, 3}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{3, {john}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void intersectionBetweenThreeNestedSets3() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "{1,2,3,{john},4}∩{3,{john},4,5}∩{1,{john},4}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{john}, 4}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void unionBetweenThreeNestedSets() throws IncorrectInputSyntaxException {
		//{x: x " + belongsTo + " N, x^2 > 4*x+1}
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "{{},1,2,3}∪{1,{},4,5,{a,b,c}}∪{x: x ∈ Z,  x >= -5, x <= 5, x!= -1 }";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{}, 1, 2, 3, 4, 5, {a, b, c}, -5, -4, -3, -2, 0}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void unionBetweenThreeNestedSetsSimpleExtractedFromPrevious() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "{{},1,2,3}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{}, 1, 2, 3}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void differenceBetweenThreeNestedSets() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "{1,2,3,{a,b,c}}∖{1,{a,c},4,5}∖{1,{b},6}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{1, 2, 3, {a, b, c}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		// assert (setStr.equals(expected)); todo
	}

	@Test
	public void weakSubsetWithThreeSets() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "({1,2,3}∪{0,-1,-2}) ⊆ {1,2,3}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{true}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void properSubsetWithThreeSets() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "({1,2,3}∪{0,-1,-2}) ⊂ {1,2,3}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{true}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void unaryPowerOperationHasHigherPriority() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "{1,2,3} ∪P({1,2,3})";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{1, 2, 3, {}, {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void notUnaryPowerOperation() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		String str = "P({1,2}∪{3})";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{}, {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void universalSetIntersectingCorrectlyN() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('N');
		String str = "{-1}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void universalSetIntersectingCorrectlyZ() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('Z');
		String str = "{-1.5,1.5, 1}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{1}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void universalSetIntersectingCorrectly3Q() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('Q');
		String str = "{-1.5, 1.5, 1}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{-1.5, 1.5, 1}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}


	@Test
	public void universalSetIntersectingCorrectlyLimitsQ() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('Q');
		String str = "{11, 10.1, 9, -10.1, -11, 12}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{9}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void universalSetIntersectingCorrectlyLimitsN() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('N');
		String str = "{11, 9, -11, -1, -0.01}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{9}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

	@Test
	public void universalSetIntersectingCorrectlyLimitsZ2() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("(-10,10)");
		UniversalSet.setType('Z');
		String str = "{10, 9, -10, -9}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{9, -9}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		System.out.println("U = " + UniversalSet.getInstance().toString());
		assert (setStr.equals(expected));
	}


	@Test
	public void threeSetsInParenthesisTest() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('Q');
		String str = "({1,2}∩{3,2})∪({1,2,3}∖{2}∖{3})";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{2, 1}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}
	@Test
	public void threeSetsInParenthesisTestSimplified() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('Q');
		String str = "{a}∪({1,2,3}∖{2}∖{3})";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{a, 1}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}

//(A∩B)∪(U∖A∖B)

	@Test
	public void universalSetIntersectingCorrectlyNested1() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('Z');
		String str = "{13, {13,12,11,10.1,10,9.9,-11,-10.1,-10,-9.9,{ABC,13,-12,DC}, {1,2,3,55}}}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{10, -10, {ABC, DC}, {1, 2, 3}}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}
	@Test
	public void universalSetIntersectingCorrectlyNested2() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('Q');
		String str = "{13, {13,12,11,10.1,10,9.9,-11,-10.1,-10,-9.9,{ABC,13,-12,DC}, {1,2,3,55}}}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{10, 9.9, -10, -9.9, {ABC, DC}, {1, 2, 3}}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}
	@Test
	public void universalSetIntersectingCorrectlyNested3() throws IncorrectInputSyntaxException {
		SyntaxAnalyser.rangeToUniversalSet("[-10,10]");
		UniversalSet.setType('N');
		String str = "{13, {13,9,-9,10,12,11,10.1,10,9.9,-11,-10.1,-10,-9.9,{ABC,13,-12,DC}, {1,2,3,55}}}";
		MixedSet set = Calculate.evaluate(str);
		String expected = "{{9, 10, {ABC, DC}, {1, 2, 3}}}";
		String setStr = set.toStringNoName(true);
		System.out.println("setStr = " + setStr);
		assert (setStr.equals(expected));
	}
}
