package Sets.Controler;

import Sets.Model.*;
import Sets.View.PopUp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import static Sets.Model.Symbols.*;


public class Calculate {


	private static boolean syntaxCheckPassed = true;


	public Calculate() {
	}


	private static boolean isSyntaxOk(String str) throws IncorrectInputSyntaxException {


		boolean balanced = SyntaxAnalyser.checkSyntaxSanity(str);
		if (!balanced) {
			syntaxCheckPassed = false;
		}

		if (!syntaxCheckPassed) {
			syntaxCheckPassed = true;
			throw new IncorrectInputSyntaxException("Please check your syntax", "Calculate.evaluate");
		}
		return true;

	}

	private static ArrayList<MixedSet> stringToSet(ArrayList<Pair<String, Boolean>> pairListOfStringAndNotationBool)
			throws IncorrectInputSyntaxException {

		ArrayList<MixedSet> sets = new ArrayList<>();
		for (Pair e : pairListOfStringAndNotationBool) {

			if ((boolean) e.getRight() == true) {
				//if(!e.getLeft().toString().contains("{")){
				//	MixedSet rangeSet = SyntaxAnalyser.rangeToSet((String) e.getLeft());
				//	sets.add(rangeSet);
				//}
				//else{
					sets.add(SyntaxAnalyser.rosterToSet((String) e.getLeft()));
				//}
			} else {

				SetComprehension<Double> setComprehension = new SetComprehension<>();

				MixedSet set = setComprehension.stringToSet((String) e.getLeft());
				sets.add(set);
			}
			// it.next();
			//it.remove(); // avoids a ConcurrentModificationException
		}
		return sets;

	}

	public static MixedSet evaluate(String expr) throws IncorrectInputSyntaxException {

		resetStaticVars();
		MixedSet answer = null;


		//1. Check the Syntax
		isSyntaxOk(expr);

		//2. Get Operator queue
		Queue operatorQueue = SyntaxAnalyser.getOperatorQueue(expr);

		//3. crop ( and )
		String exprNoParenthesis = strRemoveParenthesis(expr);


		//4. Distribute notation -> HashMap<setStr, isRosterNotation>
		ArrayList<Pair<String, Boolean>> setsStr = SyntaxAnalyser.getListOfSetNotationTypes(exprNoParenthesis);

		//5. Define sets in terms of MixedSet
		ArrayList<MixedSet> sets = null;
		try {
			sets = stringToSet(setsStr);
		} catch (IncorrectInputSyntaxException e) {
			return null;
		} finally {
			resetStaticVars();
		}


		//5.1. Print sets that were constructed
		System.out.println("Sets formed:");
		for (int i = 0; i < sets.size(); i++) {
			System.out.println(i + ". " + sets.get(i).toStringNoIntersectionWithUniversalSet());
		}


		//6. Calculate
		try {
			answer = evaluate(sets, operatorQueue);
		} catch (IncorrectInputSyntaxException ex) {
			throw ex;
		} finally {
			resetStaticVars();
		}


		return answer;
	}

	private static void resetStaticVars() {
		syntaxCheckPassed = true;
	}


	public static ArrayList<MixedSet> strExprToListOfSets(String expr) throws IncorrectInputSyntaxException {
		resetStaticVars();

		//1. Check the Syntax
		isSyntaxOk(expr);

		//2. crop ( and )
		String exprNoParenthesis = strRemoveParenthesis(expr);

		//3. Distribute notation -> HashMap<set, isRosterNotation>
		ArrayList<Pair<String, Boolean>> setsStr = SyntaxAnalyser.getListOfSetNotationTypes(exprNoParenthesis);


		//4. Define sets in terms of MixedSet
		ArrayList<MixedSet> sets = null;
		try {
			sets = stringToSet(setsStr);
		} catch (IncorrectInputSyntaxException e) {
			return null;
		} finally {
			resetStaticVars();
		}

		return sets;
	}

	private static String strRemoveParenthesis(String str) {

		char[] exprCharArr = str.toCharArray();


		StringBuilder strCropped = new StringBuilder();

		boolean powerSetSymbol = false;
		int balancedParentheses = 0;
		for (int i = 0; i < exprCharArr.length; i++) {
			if (exprCharArr[i] == '(') {
				balancedParentheses++;
				//do nothing
			} else if (exprCharArr[i] == ')' && powerSetSymbol) {
				balancedParentheses--;
				if (balancedParentheses % 2 != 0) {
					strCropped.append('^'); // add power symbol operator
					powerSetSymbol = false;
				}
			} else if (exprCharArr[i] == ')') {
				//do nothing
			} else if (exprCharArr[i] == 'P' && exprCharArr[i + 1] == '(') {
				powerSetSymbol = true;
			} else if (exprCharArr[i] != ' ') {
				strCropped.append(exprCharArr[i]);
			}
		}
		return strCropped.toString();

		//return str.replaceAll("\\(","")
		//        .replaceAll("\\)","");
	}

	/**
	 * Scratch algorithm
	 * 1. form priority queue
	 * 2. map each instruction with its priority
	 * 3. find max priority
	 * 4. while instruction set does have max priority values & someBool = true -> perform calculation:
	 * 4.0. while there is 2 members with operator in between
	 * {
	 * 4.1. calculate
	 * 4.2. change 2 members into 1 in instruction set
	 * }
	 * someBool = false
	 * 5. decrease priority by 1
	 * 6. go to step 4.
	 */
	private static MixedSet evaluate(ArrayList<MixedSet> sets, Queue operatorQueue) throws IncorrectInputSyntaxException {


		//        Tuple <    Set, Priority,Operator>
		ArrayList<Tuple<MixedSet, Integer, Character>> instructions = new ArrayList<>();

		Iterator<Character> it = operatorQueue.iterator();
		int setIndex = 0;
		int priority = 0;
		int maxPriority = 0;
		boolean setRepeats = false;
		boolean answerIsBoolean = false; // weak/proper subset operations
		int counter = 0;

		// Generates instructions on further calculation.

		while (it.hasNext()) {
			char current = it.next();
			if (current == 's' && !setRepeats) {

				instructions.add(Tuple.of(sets.get(setIndex), priority, current));
				setIndex++;
				setRepeats = true;
			} else if (current == '(') {
				priority++;
				if (priority > maxPriority) {
					maxPriority = priority;
				}
			} else if (current == ')') {
				priority--;
			} else if (current == '\u222A' || current == '\u2216' ||
					current == '\u2229' || current == '\u2A2F') {
				instructions.add(Tuple.of(null, priority, current));
				setRepeats = false;
			} else if (current == '^') {
				int lastElemenet1 = instructions.size() - 1;
				if (lastElemenet1 < 2) {
					instructions.add(Tuple.of(null, priority, current));
				}
				// if last added set to instructions has the same priority, it means
				// that powerset is not for (AuB...)' but A', thus it has highest priority.
				else if (instructions.get(lastElemenet1 - 2).getMiddle() == priority) {

					if(sets.get(setIndex - 1).getMultiplicity() > 10) {
						System.out.println("Max cardinality of power set is 1024");
						throw new IncorrectInputSyntaxException("Max cardinality of power set is 1024", "Calculate.Evaluate");
					}
					// Perform the powerset immediately and change last instruction
					MixedSet setToChangePriority = sets.get(setIndex - 1).powerSet(sets.get(setIndex - 1));
					instructions.remove(instructions.size() - 1);
					instructions.add(Tuple.of(setToChangePriority, priority, current));
				} else {
					instructions.add(Tuple.of(null, priority, current));
				}

			} else if (current == '\'') {
				int lastElemenet2 = instructions.size() - 1;
				if (lastElemenet2 < 2) {
					instructions.add(Tuple.of(null, priority, current));
				}
				// if last added set to instructions has the same priority, it means
				// that complement is not for (AuB...)' but A', thus it has highest priority.
				else if (instructions.get(lastElemenet2 - 2).getMiddle() == priority) {
					// Perform the complement immediately and change last instruction
					if (UniversalSet.getType() == 'Q') {
						if (PopUp.confirmBox("When U belongs to Q, complement is uncountable set.\n" +
								" Click YES to calculate with U belongs to Z instead", "Calculate Algorithm")) {
							UniversalSet.getInstance().setType('Z');
						} else {
							return null;
						}
					}
					MixedSet setToChangePriority = sets.get(setIndex - 1).complement();
					instructions.remove(instructions.size() - 1);
					instructions.add(Tuple.of(setToChangePriority, priority, current));
				} else {
					instructions.add(Tuple.of(null, priority, current));
				}


			} else if (current == '\u2286' || current == '\u2282') {
				if (priority == 0) {
					priority++;
				}
				instructions.add(Tuple.of(null, priority, current));
				setRepeats = false;
			}


		}


		MixedSet answer = null;

		MixedSet booleanSet = new MixedSet(true);
		// while all calculated
		while (maxPriority >= 0 && instructions.size() > 1) {
			// for each instruction identify max priority
			boolean priorityFound = false;
			boolean operationApplied = false;
			boolean isLastOperationUnary = false;
			for (int x = 1; x < instructions.size(); x++) {
				if ((Integer) ((Tuple) instructions.get(x - 1)).getMiddle() == maxPriority) {
					char operator = (Character) ((Tuple) instructions.get(x)).getRight();
					answer = (MixedSet) ((Tuple) instructions.get(x - 1)).getLeft();
					MixedSet setToCount;
					if (operator != '\'' && operator != '^') {
						setToCount = (MixedSet) ((Tuple) instructions.get(x + 1)).getLeft();
					} else {
						setToCount = answer;
					}

					if (setToCount == null || answer == null) {
						throw new IncorrectInputSyntaxException("Incorrect Syntax", "Calculate Algorithm");
					}

					priorityFound = true;
					//boolean operationApplied = false;
					switch (operator) {
						case '\'':
							if (UniversalSet.getType() == 'Q') {
								if (PopUp.confirmBox("When U belongs to Q, complement is uncountable set.\n" +
										" Click YES to calculate with U belongs to Z", "Calculate Algorithm")) {
									UniversalSet.getInstance().setType('Z');
								} else {
									return null;
								}
							}
							isLastOperationUnary = true;
							operationApplied = true;
							answer = answer.complement();
							break;
						case '^':
							if(answer.getMultiplicity() > 10) { // 1024 = 2^10
								System.out.println("Max cardinality of power set is 1024");
								throw new IncorrectInputSyntaxException("Max cardinality of power set is 1024", "Calculate.Evaluate");
							}
							isLastOperationUnary = true;
							operationApplied = true;
							answer = answer.powerSet(answer);
							break;
						case difference:
							operationApplied = true;
							answer = answer.difference(setToCount);
							break;
						case intersection:
							operationApplied = true;
							answer = answer.intersection(setToCount);
							break;
						case union:
							if(answer.getMultiplicity() + setToCount.getMultiplicity() > 1024) {
								System.out.println("Max cardinality for cartesian product is 1024");
								throw new IncorrectInputSyntaxException("Max multiplicity for cartesian" +
										"product is 1024", "Calculate.Evaluate");
							}
							operationApplied = true;
							answer = answer.union(setToCount);
							break;
						case properSubset:
							operationApplied = true;
							booleanSet.add(answer.properSubset(setToCount));
							answerIsBoolean = true;
							return booleanSet;
						case weakSubset:
							operationApplied = true;
							booleanSet.add(answer.weakSubset(setToCount));
							answerIsBoolean = true;
							return booleanSet;
						case product:
							if(answer.getMultiplicity() > 32) { // 32 = sqrt(1024)
								System.out.println("Max cardinality for cartesian product 1024");
								throw new IncorrectInputSyntaxException("Cardinality for cartesian product" +
										" is max is 1024\"", "Calculate.Evaluate");
							}
							operationApplied = true;
							answer = (MixedSet) answer.cartesianProduct(setToCount);
							answer.setProduct(true);
					}

					if (operationApplied) {
						System.out.println("result " + answer.toStringNoName(false));
						if (!isLastOperationUnary) {
							instructions.remove(x - 1);
							instructions.remove(x - 1);
							instructions.remove(x - 1);
						} else {
							instructions.remove(x - 1);
							instructions.remove(x - 1);
						}

						if (maxPriority == 0) {
							instructions.add(x - 1, new Tuple(answer, maxPriority, 's'));
						} else if (operationApplied) {
							instructions.add(x - 1, new Tuple(answer, maxPriority - 1, 's'));
						}
						break;
					}
				} else {
					// if no right priority find, move not from set to operator, but from set to set
					x++;
				}
			}


			if (!priorityFound) {
				--maxPriority;
			}


		}
		if (answerIsBoolean) {
			return booleanSet;
		} else {

			try {
				return instructions.get(0).getLeft();
			} catch (java.lang.IndexOutOfBoundsException ex) {
				throw new IncorrectInputSyntaxException("Incorrect Syntax", "Calculate Algorithm");
			}
		}


	}

}
