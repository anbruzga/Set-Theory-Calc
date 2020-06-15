package Sets.Controler;

import Sets.Model.*;
import Sets.View.PopUp;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SyntaxAnalyser {

	private static final char union = '\u222A';
	private static final char intersection = '\u2229';
	private static final char product = '\u2A2F';
	private static final char difference = '\u2216';
	private static int toStringIndex;


	private SyntaxAnalyser() {

	}

	public static boolean checkSyntaxSanity(String exprStr) throws IncorrectInputSyntaxException {
		Stack<Character> parenthesisStack = new Stack<>();
		exprStr = SyntaxAnalyser.cutSpaces(exprStr);
		parenthesisStack.push('*');
		boolean operatorUsed = false;
		int isSetUsedAfterOperator = 0;
		boolean firstSetOpened = false;
		boolean firstSetClosed = false;
		int consecutiveOperators = 0;
		int unclosedSetsNo = 0;
		int colonSymbol = 0;
		boolean complementUsed = false;
		boolean valuesExist = false;
		boolean withinSet = false;
		if (exprStr.contains(",,") || exprStr.contains("}{") || exprStr.contains("..") || exprStr.contains(",.") || exprStr.contains(".,")) {
			throw new IncorrectInputSyntaxException("Please check your syntax", "Syntax Analyser");
		}

		if(exprStr.contains("P(set)")){
			throw new IncorrectInputSyntaxException("Incorrect syntax: \"P(set)\" has to have a set inside", "Syntax Analyser");
		}

		char[] expr = exprStr.toCharArray();
		int stackIndex = 1;
		for (int i = 0; i < expr.length; i++) {
			switch (expr[i]) {
				case '{':
					if(complementUsed){
						throw new IncorrectInputSyntaxException("Syntax error: set opening after complement", "Syntax Analyser");
					}
					parenthesisStack.push('{');
					stackIndex++;
					firstSetOpened = true;
					unclosedSetsNo++;
					break;
				case '(':
					if(complementUsed){
						throw new IncorrectInputSyntaxException("Syntax error: ( opening after complement", "Syntax Analyser");
					}
					if (unclosedSetsNo != 0) {
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used in unclosed set", "Syntax Analyser");
					}
					parenthesisStack.push('(');
					stackIndex++;
					break;
				case '[':
					throw new IncorrectInputSyntaxException("Syntax error: Illegal symbol used: [", "Syntax Analyser");
					//parenthesisStack.push('[');
				//	stackIndex++;
				//	break;
				case ']':
					throw new IncorrectInputSyntaxException("Syntax error: Illegal symbol used: ]", "Syntax Analyser");
					// Uncomment to allow,  could be needed for ranges
					//if (parenthesisStack.get(stackIndex - 1) != '[' || parenthesisStack.get(stackIndex - 1) != '(') {
					//	throw new IncorrectInputSyntaxException("Range closed before opening it.", "Syntax Analyser");
					//} else {
				//		parenthesisStack.pop();
				//		stackIndex--;
				//	}
				//	break;
				case '}':
					if(complementUsed){
						throw new IncorrectInputSyntaxException("Syntax error: } after complement", "Syntax Analyser");
					}
					unclosedSetsNo--;
					if (unclosedSetsNo == 0) {
						firstSetClosed = true;
					}
					if (parenthesisStack.get(stackIndex - 1) != '{') {
						throw new IncorrectInputSyntaxException("Syntax error: Set closed without opening it.", "Syntax Analyser");
					} else {
						parenthesisStack.pop();
						stackIndex--;
						operatorUsed = false;
					}
					break;

				case ')':
					if(!firstSetClosed){
						throw new IncorrectInputSyntaxException("Syntax error: ) while set is not closed", "Syntax Analyser");
					}

					if (unclosedSetsNo != 0) {
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used in unclosed set", "Syntax Analyser");
					}
					if (parenthesisStack.get(stackIndex - 1) != '(') { // because all the sets must be closed
						throw new IncorrectInputSyntaxException("Syntax error: Parenthesis closed without opening it.", "Syntax Analyser");
					} else {
						parenthesisStack.pop();
						stackIndex--;
						operatorUsed = false;
					}
					break;
				case union:
				case difference:
				case intersection:
				case product:
					complementUsed = false;
					if (!(firstSetOpened && firstSetClosed)) {
						throw new IncorrectInputSyntaxException("Syntax error: Operator used before a set", "Syntax Analyser");
					}

					if (unclosedSetsNo != 0) {
						throw new IncorrectInputSyntaxException("Syntax error: Operator used in unclosed set", "Syntax Analyser");
					}

					if (operatorUsed) {
						throw new IncorrectInputSyntaxException("Syntax error: Operator used in unclosed set", "Syntax Analyser");
					}
					operatorUsed = true;

					if (parenthesisStack.size() != 1) {
						for (int j = 1; j < parenthesisStack.size(); j++) {
							if (parenthesisStack.get(stackIndex - 1) == '{' || parenthesisStack.get(stackIndex - 1) == '}'
									|| parenthesisStack.get(stackIndex - 1) == '[' || parenthesisStack.get(stackIndex - 1) == ']') {
								// means that the operator was used inside {} or []
								throw new IncorrectInputSyntaxException("Syntax error: Operator used inside a set", "Syntax Analyser");
							}
						}
						if (!lookUpForSetAfterOperator(i, expr)) {
							// means that operator  is used and there is no set after
							throw new IncorrectInputSyntaxException("Syntax error: There is no set after the operator " + expr[i], "Syntax Analyser");
						}
					} else if (expr.length <= i || i == 0) {
						// means that operator is used as last or first char
						throw new IncorrectInputSyntaxException("Syntax error: Operator is used before set", "Syntax Analyser");
					}
					break;

				case '>':
				case '<':
				case '!':
				case ':':
					if(complementUsed){
						throw new IncorrectInputSyntaxException("Syntax error: incorrect symbol after complement", "Syntax Analyser");
					}
					if(unclosedSetsNo == 0) { // not allowed withing unclosed set
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used but couldn't find set", "Syntax Analyser");
					}
					//	consecutiveOperators++;
					//	if(consecutiveOperators > 2)
					//		throw new IncorrectInputSyntaxException("Syntax error. The operator repeats:  " + expr[i], "Syntax Analyser");
				case '=':
					break;
				case '.':
				case '*':
				case '+':
				case '-':
				case '/':
					if(complementUsed){
						throw new IncorrectInputSyntaxException("Syntax error: math symbol after complement", "Syntax Analyser");
					}
					if(unclosedSetsNo == 0) { // not allowed withing unclosed set
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used but couldn't find set", "Syntax Analyser");
					}
					try {
						if (!(Character.isDigit(expr[i + 1]) || expr[i + 1] == 'x' ||
								((expr[i + 1] == '-') && ((expr[i + 2] == 'x') || Character.isDigit(expr[i + 2]))))) {
							throw new IncorrectInputSyntaxException("Syntax error: After \""
									+ expr[i] + " there must  be a digit, 'x' or '-x'.", "Syntax Analyser");
						}

					} catch (IndexOutOfBoundsException ex) {
						throw new IncorrectInputSyntaxException("Syntax error: There is no set after the operator "
								+ expr[i], "Syntax Analyser");
					}
					break;

				case ',':
					if(unclosedSetsNo == 0) { // not allowed withing unclosed set
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used but couldn't find set", "Syntax Analyser");
					}
					try { // if NOT ( (digit OR letter) OR (minus AND THEN ( digit OR letter)) )
						if (!(Character.isDigit(expr[i + 1]) || Character.isLetter(expr[i + 1]) || (expr[i + 1]) == '-' || (expr[i + 1]) == '{')) {
							System.out.println("expr[i] = " + expr[i]);
							System.out.println("expr[i+1] = " + expr[i + 1]);
							throw new IncorrectInputSyntaxException("Syntax error: After \"" + expr[i] + "\" there must  be a digit, letter or opened set", "Syntax Analyser");
						}
					} catch (IndexOutOfBoundsException ex) {
						throw new IncorrectInputSyntaxException("Syntax error: There is no set after the operator " + expr[i], "Syntax Analyser");
					}
					break;
				case '⊂':
				case '⊆':
					complementUsed = false;
					if (unclosedSetsNo != 0) {
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used in unclosed set", "Syntax Analyser");
					}
					if (!lookUpForSetAfterOperator(i, expr)) {
						throw new IncorrectInputSyntaxException("Syntax error: After \"" + expr[i] + " there must  another set", "Syntax Analyser");
					}
					break;


				case '\'':
					if(unclosedSetsNo != 0) {
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used in unclosed set", "Syntax Analyser");
					}
					complementUsed = true;


					break;
				case '∈':
					if(complementUsed){
						throw new IncorrectInputSyntaxException("Syntax error: ∈ after complement", "Syntax Analyser");
					}
					if(unclosedSetsNo == 0) { // not allowed withing unclosed set
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used but couldn't find set", "Syntax Analyser");
					}
					if (expr[i + 1] != 'N' && expr[i + 1] != 'Z' && expr[i + 1] != 'Q') {
						throw new IncorrectInputSyntaxException("Syntax error: Illegal symbol after or before \"" + expr[i] + ".", "Syntax Analyser");
					}
					break;
				case '^':
					if(complementUsed){
						throw new IncorrectInputSyntaxException("Syntax error: ∈ after complement", "Syntax Analyser");
					}
					if(unclosedSetsNo == 0) { // not allowed withing unclosed set
						throw new IncorrectInputSyntaxException("Syntax error: '"
								+ expr[i] + "' used but couldn't find set", "Syntax Analyser");
					}
					if (!(Character.isDigit(expr[i - 1]) || expr[i - 1] == 'x') || ((expr[i - 2]) == '-' && ((expr[i - 1]) == 'x') || Character.isDigit(expr[i - 1]))) {
						System.out.println("expr[i] = " + expr[i]);
						System.out.println("expr[i+1] = " + expr[i + 1]);
						throw new IncorrectInputSyntaxException("Syntax error: After \"" + expr[i] + "\" there must  be a digit, letter or opened set", "Syntax Analyser");
					}
					break;
				case 'P':
					break;
				default:
					try {
						if (unclosedSetsNo == 0 && expr[i+1] != '=') { // not allowed withing unclosed set
							throw new IncorrectInputSyntaxException("Syntax error: '"
									+ expr[i] + "' used, but didn't find '=' after it", "Syntax Analyser");
						}
					}
					catch(IndexOutOfBoundsException e){
						throw new IncorrectInputSyntaxException("Syntax error: " + expr[i], "Syntax Analyser");
					}
					if (!(Character.isDigit(expr[i]) || Character.isLetter(expr[i]))) {
						throw new IncorrectInputSyntaxException("Syntax error: Illegal symbol is used: " + expr[i], "Syntax Analyser");
					}
					valuesExist = true;
					break;
			}
		}

		if (!firstSetClosed) {
			throw new IncorrectInputSyntaxException("Syntax error: could not find a set. \nTry clicking examples for examples", "Syntax Analyser");
		}
		if (!valuesExist) {
			//throw new IncorrectInputSyntaxException("Syntax error: lack of values", "Syntax Analyser");
			//this does not allow Venn diagrams with empty sets
		}
		if (operatorUsed)
			throw new IncorrectInputSyntaxException("Syntax error: Operator used, but no set after", "Syntax Analyser");

		if (stackIndex != 1) {
			throw new IncorrectInputSyntaxException("Syntax error: Unbanced parenthesis in input", "Syntax Analyser");
		}
		return stackIndex == 1;
	}

	public static MixedSet rangeToUniversalSet(String exprStr) throws IncorrectInputSyntaxException {

		String errorMsg = "Universal set syntax is not correct.";


		MixedSet set = new MixedSet(false);
		exprStr = exprStr.replaceAll(" ", "");

		if (exprStr.equals("")) {
			throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
		}
		if (exprStr.length() < "[1,2".length()) {
			throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
		}

		if(!exprStr.contains(",")){
			throw new IncorrectInputSyntaxException("Universal set numbers range\n" +
					"must be separated by comma", "SyntaxAnalyser");
		}

		char[] expr = exprStr.toCharArray();
		char letter;
		boolean firstInclude = false;
		boolean lastInclude = false;
		boolean minusSign = false;
		boolean firstPart = true;
		StringBuilder firstNum = new StringBuilder();
		StringBuilder secondNum = new StringBuilder();

		boolean firstNumAppended = false;
		boolean secondNumAppended = false;
		boolean comaUsed = false;

		for (int j = 0; j < expr.length; j++) {
			letter = expr[j];
			switch (letter) {
				case '[':
					if (j != 0) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					firstInclude = true;
					break;
				case ']':
					if (j != expr.length - 1) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					lastInclude = true;
					break;
				case '(':
					if (j != 0) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					break;
				case ')':
					if (j != expr.length - 1) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					if (j == expr.length - 1) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					if (j == 0) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					if (firstPart) {
						if (minusSign) {
							firstNum.append("-");
							minusSign = false;
							firstNum.append(letter);
						} else {
							firstNum.append(letter);
						}
						firstNumAppended = true;
					} else {
						if (minusSign) {
							secondNum.append("-");
							minusSign = false;
						}
						secondNum.append(letter);
						secondNumAppended = true;
					}
					break;
				case '-':
					minusSign = true;
					break;
				case ',':

					firstPart = false;
					if (!firstNumAppended || comaUsed) {
						throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					}
					comaUsed = true;
					break;
				default:
					throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
			}
		}


		int first = Integer.parseInt(firstNum.toString());
		if (UniversalSet.getType() == 'N') {
			first = 0;
		}

		int second = Integer.parseInt(secondNum.toString());

		if (first < UniversalSet.UNIVERSALSET_MIN_BOUND || second > UniversalSet.UNIVERSALSET_MAX_BOUND) {
			throw new IncorrectInputSyntaxException("Universal Set is limited to range:\n["
					+ UniversalSet.UNIVERSALSET_MIN_BOUND + "," + UniversalSet.UNIVERSALSET_MAX_BOUND + "]", "SyntaxAnalyser");
		}

	//	if (first < UniversalSet.UNIVERSALSET_MIN_BOUND) {
	//		first = UniversalSet.UNIVERSALSET_MIN_BOUND;
	//	}
	//	if (second > UniversalSet.UNIVERSALSET_MAX_BOUND) {
	//		second = UniversalSet.UNIVERSALSET_MAX_BOUND;
	//	}


		if (!firstInclude) {
			first++;
		}
		if (!lastInclude) {
			second--;
		}

		if (first == second) {
			PopUp.infoBox("Universal set contains 1 member!", "SyntaxAnalyser");
		} else if (first > second) {
			throw new IncorrectInputSyntaxException("Universal set lower bound is bigger than " +
					"higher bound!", "SyntaxAnalyser");
		}



		IntRange range = new IntRange(first, second);
		set.add(range);

		char type = UniversalSet.getType();
		UniversalSet universalSet = UniversalSet.getInstance(set, second, first, type);

		return universalSet;
	}

	/*
	public static MixedSet rangeToSet(String exprStr) throws IncorrectInputSyntaxException{
		String errorMsg = "Range syntax is not correct.";


		MixedSet set = new MixedSet(false);
		exprStr = exprStr.replaceAll(" ", "");

		if (exprStr.equals("")) {
			throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
		}
		if (exprStr.length() < "[1,2".length()) {
			throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
		}

		if(!exprStr.contains(",")){
			throw new IncorrectInputSyntaxException("Range numbers range\n" +
					"must be separated by comma", "SyntaxAnalyser");
		}

		char[] expr = exprStr.toCharArray();
		char letter;
		boolean firstInclude = false;
		boolean lastInclude = false;
		boolean minusSign = false;
		boolean firstPart = true;
		StringBuilder firstNum = new StringBuilder();
		StringBuilder secondNum = new StringBuilder();

		boolean firstNumAppended = false;
		boolean secondNumAppended = false;
		boolean comaUsed = false;

		for (int j = 0; j < expr.length; j++) {
			letter = expr[j];
			switch (letter) {
				case '[':
					if (j != 0) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					firstInclude = true;
					break;
				case ']':
					if (j != expr.length - 1) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					lastInclude = true;
					break;
				case '(':
					if (j != 0) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					break;
				case ')':
					if (j != expr.length - 1) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					if (j == expr.length - 1) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					if (j == 0) throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					if (firstPart) {
						if (minusSign) {
							firstNum.append("-");
							minusSign = false;
							firstNum.append(letter);
						} else {
							firstNum.append(letter);
						}
						firstNumAppended = true;
					} else {
						if (minusSign) {
							secondNum.append("-");
							minusSign = false;
						}
						secondNum.append(letter);
						secondNumAppended = true;
					}
					break;
				case '-':
					minusSign = true;
					break;
				case ',':

					firstPart = false;
					if (!firstNumAppended || comaUsed) {
						throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
					}
					comaUsed = true;
					break;
				default:
					throw new IncorrectInputSyntaxException(errorMsg, "SyntaxAnalyser");
			}
		}


		int first = Integer.parseInt(firstNum.toString());
		if (UniversalSet.getType() == 'N') {
			first = 0;
		}

		int second = Integer.parseInt(secondNum.toString());

		if (first < UniversalSet.UNIVERSALSET_MIN_BOUND || second > UniversalSet.UNIVERSALSET_MAX_BOUND) {
			throw new IncorrectInputSyntaxException("Range is limited to U:\n["
					+ UniversalSet.UNIVERSALSET_MIN_BOUND + "," + UniversalSet.UNIVERSALSET_MAX_BOUND + "]", "SyntaxAnalyser");
		}

		//	if (first < UniversalSet.UNIVERSALSET_MIN_BOUND) {
		//		first = UniversalSet.UNIVERSALSET_MIN_BOUND;
		//	}
		//	if (second > UniversalSet.UNIVERSALSET_MAX_BOUND) {
		//		second = UniversalSet.UNIVERSALSET_MAX_BOUND;
		//	}


		if (!firstInclude) {
			first++;
		}
		if (!lastInclude) {
			second--;
		}

		if (first == second) {
			PopUp.infoBox("Range contains 1 member!", "SyntaxAnalyser");
		} else if (first > second) {
			throw new IncorrectInputSyntaxException("Range lower bound is bigger than " +
					"higher bound!", "SyntaxAnalyser");
		}


		IntRange range = new IntRange(first, second);
		set.add(range);
		return set;

	}
*/

	private static boolean lookUpForSetAfterOperator(int i, char[] expr) {
		boolean opening = false;
		boolean closing = false;
		for (int j = i; j < expr.length; j++) {
			switch (expr[j]) {
				case '{':
					opening = true;
					break;
				case '}':
					closing = true;
					break;
			}
		}
		if (opening && closing) {
			return true;
		}

		return false;
	}



	public static boolean IsRosterNotation(String expr) {

		char[] setCharArr = expr.toCharArray();
		for (int i = 0; i < setCharArr.length; i++) {
			if (setCharArr[i] == '|' || setCharArr[i] == ':') {
				return false;
			}
		}

		return true;
	}

	public static ArrayList<Pair<String, Boolean>> getListOfSetNotationTypes(String exprStr) {
		String[] sets = exprStr.split("(\u222a|\u2229|\u2216|\u2a2f|\u2286|u2282|\'|" + Symbols.properSubset + ")");

		ArrayList<Pair<String, Boolean>> setsNotations = new ArrayList<>();
		for (int j = 0; j < sets.length; j++) {
			if (!sets[j].equals("")) {
				Pair pair = new Pair(sets[j], IsRosterNotation(sets[j]));
				setsNotations.add(pair); // true stands for roster notation
			}
		}
		return setsNotations;
	}

	private static String[] processSetInput(String full, MixedSet set) {

		//    System.out.println("Input given: " + full);
		//System.out.println("After Splitting: ");
		full = full.trim(); //trimming white space on the sides

		// if set has a name, initialise it with name and crop input
		if (full.contains("=")) {
			int index = full.indexOf('=');
			String name = full.substring(0, index).trim();
			if(!name.equals("U")){
				set.setName(name);
			}
			full = full.substring(index + 1);
			full = full.trim();
		} else {
			//set = new MixedSet();
		}

		// crops { and } signs on the sides
		// if (full.charAt(0) == '{') {
		//      full = full.substring(1);
		//  }


		// Splits and trims each string NOT EFFICIENT??
		full = full.replaceAll("\\p{javaSpaceChar}{2,}", " ")
				.replaceAll(" ", "")
				.replaceAll("\\{", "{ ")
				.replaceAll("}", " }")
				.replaceAll("\\{  }", "{ }");


		String[] split = full.split("[,// ]");
		for (int i = 0; i < split.length; i++) {
			split[i] = split[i].trim();
			if (split[i] == null || split[i] == "" || split[i] == "\u0000") {
				split = ArrayUtils.removeElement(split, i);
			}
			// System.out.println("split[i] = " + split[i]);
		}
		return split;
	}


	// Converts a single set string to set, accepts Roster Notation
	public static MixedSet rosterToSet(String full) throws IncorrectInputSyntaxException {
		MixedSet set = new MixedSet(false);

		String[] split = processSetInput(full, set);

		// toStringIndex is private static. Because it is used for recursive rosterToSet function,
		// toStringIndex helps to find the right position of current element

		for (toStringIndex = 1; toStringIndex < split.length - 1; toStringIndex++) { // excluding first and last to add { } automatically


			try {
				split[toStringIndex].charAt(0);
			} catch (StringIndexOutOfBoundsException ex) {
				throw new IncorrectInputSyntaxException("Incorrect Syntax", "Syntax Analyser");
			}
			// pass to recursive helper if set has subsets
			if (split[toStringIndex].charAt(0) == '{') {
				MixedSet subset = rosterToSetHelper(split);
				set.add(subset);
				continue;

			} else if (split[toStringIndex].charAt(0) == '}') {
				continue;
			} else if (strIsNumeric(split[toStringIndex])) { // if no subsets were found
				double doubles = transformToDouble(split[toStringIndex]);
				set.add(doubles);
			} else {
				set.add(split[toStringIndex]);
			}
		}
		toStringIndex = 1;
		return set;
	}


	private static MixedSet rosterToSetHelper(String[] split) throws IncorrectInputSyntaxException {
		MixedSet subset = new MixedSet(true);

		while (true) {
			toStringIndex++;

			try {
				split[toStringIndex].charAt(0);
			} catch (StringIndexOutOfBoundsException ex) {
				throw new IncorrectInputSyntaxException("Incorrect Syntax", "Syntax Analyser");
			}
			if (split[toStringIndex].charAt(0) == '}') {
				return subset;
			}

			// If there is a subset within a subset, call recursively
			if (split[toStringIndex].charAt(0) == '{') {
				MixedSet subsubset = rosterToSetHelper(split);
				subset.add(subsubset);
				continue;
			}

			// If value has a "}" at the end
			if (strIsNumeric(split[toStringIndex])) {
				double doubles = transformToDouble(split[toStringIndex]);
				subset.add(doubles);

			} else {
				subset.add(split[toStringIndex]);
			}

			// if met the end
			if (split.length <= toStringIndex) {
				return subset;
			}

		}
	}

	private static Double transformToDouble(String s) {
		StringBuilder strBuilder = new StringBuilder();
		char[] arr = s.toCharArray();
		for (int j = 0; j < arr.length; j++) {
			if (arr[j] == '0' || arr[j] == '1' || arr[j] == '2' || arr[j] == '3' ||
					arr[j] == '4' || arr[j] == '5' || arr[j] == '6' ||
					arr[j] == '7' || arr[j] == '8' || arr[j] == '9' || arr[j] == '-'
					|| arr[j] == '.') {
				strBuilder.append(arr[j]);
			}
		}
		Double doubles = Double.parseDouble(strBuilder.toString());
		return doubles;
	}

	public static boolean strIsNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?(})?");
	}


	public static Queue getOperatorQueue(String expr) {
		Queue<Character> queue = new LinkedList<>();

		StringBuilder powerNotationChanger = new StringBuilder(expr);
		while (expr.contains("P({") || expr.contains("P(")) {
			int indexOfPower = expr.indexOf("P({");
			if (indexOfPower == -1) {
				indexOfPower = expr.indexOf("P(");
			}
			powerNotationChanger.delete(indexOfPower, indexOfPower + 2);
			powerNotationChanger.insert(indexOfPower, "@");


			expr = powerNotationChanger.toString();
		}

		// check for double complement and if it is found - delete it. As here is no need to calculate it
		expr = expr.replaceAll("\'\'", "");

		char[] exprCharArr = expr.toCharArray();


		int balancedParenthesis = 0;
		int innerSet = 0;
		for (int j = 0; j < exprCharArr.length; j++) {
			switch (exprCharArr[j]) {
				case '(':
					balancedParenthesis++;
				case '@': // used for noting where a not unary power sets begins
					queue.add('(');
					break;
				case ')':
					balancedParenthesis--;
					if (balancedParenthesis < 0) {
						balancedParenthesis++;
						queue.add(')');
						queue.add('^');
					} else {
						queue.add(')');
					}
					break;
				case '{':
					if (innerSet == 0) {
						queue.add('s'); // s for set
						innerSet++;
					}
					break;
				case '}':
					if (innerSet > 0) {
						innerSet--;
					}
					break;
				case '\'':
					//          if (j != 0 && (balancedParenthesis % 2 == 1 || balancedParenthesis == 0)){
					//              queue.add('(');
					//              queue.add('\'');
					//              queue.add(')');
					//          }
					//         else{
					queue.add('\'');
					//         }
					break;
				case '\u222A':
					queue.add(union);
					break;
				case '\u2216':
					queue.add(difference);
					break;
				//   private static final char union = '\u222A';
				//   private static final char intersection = '\u2229';
				//   private static final char product = '\u2A2F';
				//   private static final char difference = '\u2216';
				case '\u2A2F':
					queue.add(product);
					break;
				case '\u2229':
					queue.add(intersection);
					break;
				// public static final char belongsTo = '\u2208';
				// public static final char weakSubset = '\u2286';
				// public static final char properSubset = '\u2282';
				case '\u2282':
					queue.add(Symbols.properSubset);
					break;
				case '\u2286':
					queue.add(Symbols.weakSubset);
					break;
				case ';':
					queue.add(';'); // s is for adding to storage instead of calculations
					break;
			}
		}


		return queue;

	}


	public static String cutSpaces(String str) {
		char[] arr = str.toCharArray();
		StringBuilder noSpaces = new StringBuilder();
		for (int j = 0; j < arr.length; j++) {
			if (arr[j] != ' ') {
				noSpaces.append(arr[j]);
			}
		}
		return noSpaces.toString();

	}

}
