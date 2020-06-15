package Sets.Controler;


import Sets.Model.*;
import Sets.View.PopUp;
import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.util.*;
import java.util.function.*;

import static Sets.Model.Symbols.belongsTo;
import static java.util.stream.Collectors.toList;


// put license not my parts
// this will fail with VERY big values
@SuppressWarnings("unchecked")
public class SetComprehension<T> {


	private Function<T, T> outputExpression = x -> x;
	private BiFunction<T, T, ?> pairOutputExpression = (x, y) -> Pair.of(x, y);

	private ArrayList<BiPredicate<Double, Double>> biPredicatesList = new ArrayList<>();
	private boolean xBelongsToY = false;
	private boolean yBelongsToX = false;
	private int yMin;
	private int yMax;
	private int xMin;
	private int xMax;
	private static int variablesAmount = 0;
	private static String setName;
	private ArrayList<Pair<Double, Double>> pairs = new ArrayList<>();

	private ArrayList<Double> xList = new ArrayList<>();
	private  ArrayList<Double> yList = new ArrayList<>();
	private ArrayList<Predicate<Double>> xPredicates = new ArrayList<>();
	private ArrayList<Predicate<Double>> yPredicates = new ArrayList<>();
	private static boolean intersectWithUniversal = true;

	public SetComprehension() {

	}

	private static AbstractSet listToSet(List list) {
		MixedSet set = new MixedSet(true);
		Comparator<Double> order = Double::compare;
		if (variablesAmount != 2) list.sort(order);
		for (int i = 0; i < list.size(); i++) {
			set.add(list.get(i));
		}

		set.setName(setName);
		//System.out.println(set.toString());
		//System.out.println(UniversalSet.getInstance());
		//  set = set.intersection(UniversalSet.getInstance());
		return set;
	}

	public static void setIntersectWithUnviersalSet(boolean bool){
		intersectWithUniversal = bool;
	}

	public MixedSet stringToSet(String exprStr) throws IncorrectInputSyntaxException {
		variablesAmount = 1;
		Pair expr = splitExpression(exprStr);
		LinkedList<Double> list = domainAnalysis((char[]) expr.getLeft(), (char[]) expr.getRight());
		ArrayList<Predicate<Double>> predicates = new ArrayList<>();// generatePredicates((char[]) expr.getRight());
		AbstractSet setBuilderSet = constructSet(list, predicates);
		// System.out.println("Expression: " + exprStr);
		// System.out.println("Set Formed: ");
		// System.out.println(setBuilderSet.toString());
		xBelongsToY = false;
		yBelongsToX = false;
		variablesAmount = 0;
		pairs.clear();

		return (MixedSet) setBuilderSet;
	}


	private LinkedList<Double> domainAnalysis(char[] domain, char[] xPart) {
		String expr = String.valueOf(domain);
		String exprX = String.valueOf(xPart);
		expr = SyntaxAnalyser.cutSpaces(expr);

		LinkedList<Double> list = new LinkedList<>();

		int max = 101;
		int min = -99;//

		if (UniversalSet.getInstance() != null) {
			max = UniversalSet.getInstance().getMax();
			min = UniversalSet.getInstance().getMin();
		}


		if (expr.contains("y" + Symbols.belongsTo + "Q") || expr.contains("y" + Symbols.belongsTo + "R")
				|| expr.contains("x" + Symbols.belongsTo + "Q") || expr.contains("x" + Symbols.belongsTo + "R")) {
			PopUp.infoBox("Uncountable sets are not supported.\n" +
					"The program will return all integers instead.", "Set Comprehension");

			expr = expr.replaceAll("y∈Q", "y∈Z")
					.replaceAll("x∈Q", "x∈Z")
					.replaceAll("y∈R", "y∈Z")
					.replaceAll("x∈R", "x∈Z");
		}

		if (expr.contains("x" + Symbols.belongsTo + "N")) {
			xMin = 1;

			xMax = UniversalSet.UNIVERSALSET_MAX_BOUND;
			expr = expr.replace("x" + Symbols.belongsTo + "N", "");
		} else if (expr.contains("x" + Symbols.belongsTo + "Z")) {
			xMin = UniversalSet.UNIVERSALSET_MIN_BOUND;
			xMax = UniversalSet.UNIVERSALSET_MAX_BOUND;
			expr = expr.replace("x" + Symbols.belongsTo + "Z", "");
		}

		if (expr.contains("y" + Symbols.belongsTo + "N")) {
			yMin = 1;
			yMax = max;
			variablesAmount = 2;
			expr = expr.replace("y" + Symbols.belongsTo + "N", "");
		} else if (expr.contains("y" + Symbols.belongsTo + "Z")) {
			yMin = min;
			yMax = max;
			expr = expr.replace("y" + Symbols.belongsTo + "Z", "");
			variablesAmount = 2;
		}

		if (expr.contains("x" + Symbols.belongsTo + "y")) {
			expr = expr.replace("x" + Symbols.belongsTo + "y", "");
			xBelongsToY = true;
			variablesAmount = 2;
			yMax = xMax;
			yMin = xMin;
		}
		if (expr.contains("y" + Symbols.belongsTo + "x")) {
			expr = expr.replace("y" + Symbols.belongsTo + "x", "");
			yBelongsToX = true;
			variablesAmount = 2;
			xMax = yMax;
			xMin = yMin;

		}


		//   expr = expr.replace(",", "");

		if (expr.contains("{")) {
			expr = expr.replace("{", "");
		}
		if (expr.equals("")) {
			expr = "x";
		}

		System.out.println("expr = " + expr);
		MixedSet domainSet = new MixedSet(false);
		System.out.println("variablesAmount = " + variablesAmount);
		if (variablesAmount == 1) {
			min = xMin;
			max = xMax;
		}


		for (int i = min; i <= max; i++) {
			list.add((double) i);
			domainSet.add((double) i);
		}



		MixedSet results = new MixedSet(false);
		DoubleEvaluator evaluator = new DoubleEvaluator();
		LinkedList<Double> resultsList = new LinkedList<>();

		if (exprX.isEmpty()) {
			exprX = expr;
		}
		if (!expr.isEmpty() && !expr.equals("x")) {
			exprX = "" + exprX + expr;
		}
		while (exprX.charAt(0) == ',') {
			exprX = exprX.substring(1);
		}

		String[] splitXExpr = exprX.split(",");


		for (int j = 0; j < splitXExpr.length; j++) {
			MixedSet currentIterationSet = new MixedSet(false);


			for (int i = 0; i < list.size(); i++) {
				Double current = list.get(i);
				String tempExpr = splitXExpr[j];

				tempExpr = tempExpr.replaceAll("x", current.toString());
				Double result;


				if (tempExpr.contains("!=")) {
					String[] split = tempExpr.split("!=");
					double resultLeftSide = evaluator.evaluate(split[0]);
					double resultRightSide = evaluator.evaluate(split[1]);
					if (resultLeftSide != resultRightSide) {
						result = resultLeftSide;

						currentIterationSet.add(current);

					}
				} else if (tempExpr.contains(">=")) {
					String[] split = tempExpr.split(">=");
					double resultLeftSide = evaluator.evaluate(split[0]);
					double resultRightSide = evaluator.evaluate(split[1]);
					if (resultLeftSide >= resultRightSide) {
						result = resultLeftSide;

						currentIterationSet.add(current);

					}
				} else if (tempExpr.contains("<=")) {
					String[] split = tempExpr.split("<=");
					double resultLeftSide = evaluator.evaluate(split[0]);
					double resultRightSide = evaluator.evaluate(split[1]);
					if (resultLeftSide <= resultRightSide) {
						result = resultLeftSide;

						currentIterationSet.add(current);

					}
				} else if (tempExpr.contains("<")) {
					String[] split = tempExpr.split("<");
					double resultLeftSide = evaluator.evaluate(split[0]);
					double resultRightSide = evaluator.evaluate(split[1]);
					if (resultLeftSide < resultRightSide) {
						result = resultLeftSide;

						currentIterationSet.add(current);

					}
				} else if (tempExpr.contains(">")) {
					String[] split = tempExpr.split(">");
					double resultLeftSide = evaluator.evaluate(split[0]);
					double resultRightSide = evaluator.evaluate(split[1]);
					if (resultLeftSide > resultRightSide) {
						result = resultLeftSide;

						currentIterationSet.add(current);

					}
				} else if (tempExpr.contains("==")) {
					String[] split = tempExpr.split("==");
					double resultLeftSide = evaluator.evaluate(split[0]);
					double resultRightSide = evaluator.evaluate(split[1]);
					if (resultLeftSide == resultRightSide) {
						result = resultLeftSide;

						currentIterationSet.add(current);

					}
				} else if (tempExpr.contains("=")) {
					String[] split = tempExpr.split("=");
					double resultLeftSide = evaluator.evaluate(split[0]);
					double resultRightSide = evaluator.evaluate(split[1]);
					if (resultLeftSide == resultRightSide) {
						result = resultLeftSide;

						currentIterationSet.add(current);

					}
				}

			}


			if (yBelongsToX) {
				BiPredicate<Double, Double> yBelongsToXPredicate = (x, y) -> x * 2 == y;
				biPredicatesList.add(yBelongsToXPredicate);
			}

			if (j == 0) {
				results.addAll(currentIterationSet);
			} else {
				results.intersection(currentIterationSet);
			}
			//System.out.println(results.toString());

		}

		results = results.intersection(domainSet);
        LostValuesToUInteresection.createFirst();
        if(intersectWithUniversal) {
	        results = results.intersectionWithUniveralSet();
        }
		resultsList = new LinkedList<>();
		Iterator it = results.iterator();
		while (it.hasNext()) {
			resultsList.add((Double) it.next());
		}


		return resultsList;
		//String expression = "(2^3-1)*sin(pi/4)/ln(pi^2)";
		// Evaluate an expression

		// Ouput the result
		// System.out.println(expression + " = " + result);

	}

	private AbstractSet constructSet(LinkedList<Double> list, ArrayList<Predicate<Double>> predicates) {

		if (variablesAmount == 2) {
			List<Pair<Double, Double>> constructedList;
			constructedList = new SetComprehension<Double>()
					.suchThat((x, y) -> {
						x.belongsTo(xList);
						y.belongsTo(yList);
						for (int i = 0; i < xPredicates.size(); i++) {
							x.holds(xPredicates.get(i));
						}
						for (int i = 0; i < yPredicates.size(); i++) {
							y.holds(yPredicates.get(i));
						}
					});
			return listToSet(constructedList);
		}


		List<Double> constructedList;
		constructedList = new SetComprehension<Double>()
				.suchThat(s -> {
					s.belongsTo(list);
					for (int i = 0; i < predicates.size(); i++) {
						s.holds(predicates.get(i));
					}
				});
		return listToSet(constructedList);

	}

	private Pair splitExpression(String exprStr) throws IncorrectInputSyntaxException{
		try {
			exprStr = SyntaxAnalyser.cutSpaces(exprStr);
			boolean xyPair = false;
			if (exprStr.contains("={")) {
				setName = exprStr.substring(0, exprStr.indexOf('=')).trim();
				exprStr = exprStr.substring(exprStr.indexOf('=') + 1).trim();
			}

			boolean classNotation = false;
			if (exprStr.contains(":")) {
				classNotation = true;
				exprStr = exprStr.replace(":", "|");
				exprStr = exprStr.replaceAll("\\{", "")
						.replaceAll("}", "");
				// .replaceAll(" ","");
			}


			if (exprStr.contains("x,y^")) {
				xyPair = true;
			}
			int lastBelongsToIndex = -1;
			int stickIndex = exprStr.indexOf("|");
			if (classNotation) {
				int lastN = exprStr.indexOf(belongsTo + "N");
				int lastZ = exprStr.indexOf(belongsTo + "Z");
				int lastQ = exprStr.indexOf(belongsTo + "Q");
				int lastX = exprStr.indexOf(belongsTo + "x");
				int lastY = exprStr.indexOf(belongsTo + "y");
				List<Integer> list = new ArrayList<>();
				list.add(lastN);
				list.add(lastZ);
				list.add(lastQ);
				if (xyPair == true) {
					list.add(lastX);
					list.add(lastY);
				}
				lastBelongsToIndex = Collections.max(list);

				StringBuilder reshuffledExpr = new StringBuilder();
				String xPart = exprStr.substring(0, stickIndex).replace("^", "");

				System.out.println("xPart = " + xPart);
				String belongsToPart = exprStr.substring(stickIndex + 1, lastBelongsToIndex + 2);
				String everythingElsePart = exprStr.substring(lastBelongsToIndex + 3);
				String[] everythingElseSplit = everythingElsePart.split(",");


				System.out.println("everythingElsePart = " + everythingElsePart);
				reshuffledExpr.append(belongsToPart)
						.append(",");
				//  .append(xPart);
				// if it is range predicate, add after |, if it is equation, add before
				int everythingElseSplitLength = everythingElseSplit.length;
				ArrayList<String> rightPart = new ArrayList<String>();
				boolean notFirst = false;
				for (int i = 0; i < everythingElseSplitLength; i++) {
					if (!everythingElseSplit[i].contains(">") && !everythingElseSplit[i].contains("<")) {
						if (!notFirst) {
							reshuffledExpr.append(everythingElseSplit[i]);
							notFirst = true;
						} else {
							reshuffledExpr.append(",");
							reshuffledExpr.append(everythingElseSplit[i]);
						}

					} else {
						rightPart.add(everythingElseSplit[i]);
					}
				}

				if (reshuffledExpr.lastIndexOf(",") == reshuffledExpr.length() - 1) {
					reshuffledExpr.deleteCharAt(reshuffledExpr.length() - 1);
				}

				// now append predicates at the end
				reshuffledExpr.append("|");
				for (int i = 0; i < rightPart.size(); i++) {
					if (!rightPart.get(i).contains("∈")) {
						reshuffledExpr.append(rightPart.get(i));
						if (i + 1 < rightPart.size()) {
							reshuffledExpr.append(",");
						}
					}

				}

				System.out.println("reshuffledExpr = " + reshuffledExpr);
				exprStr = reshuffledExpr.toString();
			}
			// initial notation                  "{x E Z, x^3-4 | x > -5, x < 5}";
			// notation in classroom :           "{x: x E N, x^2 = x}";
			// transformations                  "{x| x E N, x^2 = x}";
			// transformations                   "x| x E N, x^2 = x";


			System.out.println("exprStr = " + exprStr);
			String strBeforeColon = exprStr.substring(0, exprStr.indexOf('|'));
			String strAfterColon = exprStr.substring(exprStr.indexOf('|') + 1);


			strAfterColon = strAfterColon.replaceAll("\\{", "");
			strAfterColon = strAfterColon.replaceAll("}", "");


			char[] exprBeforeSuch = strBeforeColon.toCharArray();
			char[] exprAfterSuch = strAfterColon.toCharArray();


			Pair exprPair = Pair.of(exprBeforeSuch, exprAfterSuch);

			return exprPair;
		}
		catch(Exception ex){
			throw new IncorrectInputSyntaxException("Set builder notation is incorrect", "Error");
		}
	}

	private ArrayList<Predicate<Double>> generatePredicates(char[] exprAfterSuch) {

		//"B = {(x,y): x " + belongsTo + " N, x " + belongsTo + "y , x < y, y <= 3}";
		ArrayList<Predicate<Double>> predicates = new ArrayList<>();
		if (exprAfterSuch.length < 1) {
			return predicates;
		}


		for (int i = 0; i < exprAfterSuch.length; i++) {
			char letter = exprAfterSuch[i];
			if (letter == 'x') {
				i++;
				letter = exprAfterSuch[i];
				if (letter == '>' && exprAfterSuch[i + 1] == '=') {
					double num = getCompareToValue(exprAfterSuch, i + 1);
					Predicate<Double> pred = (Double x) -> x >= num;
					predicates.add(pred);
					i++;
				} else if (letter == '>') {
					double num = getCompareToValue(exprAfterSuch, i);
					Predicate<Double> pred = (Double x) -> x > num;
					predicates.add(pred);

				} else if (letter == '<' && exprAfterSuch[i + 1] == '=') {
					double num = getCompareToValue(exprAfterSuch, i + 1);
					Predicate<Double> pred = (Double x) -> x <= num;
					predicates.add(pred);
					i++;
				} else if (letter == '<') {
					double num = getCompareToValue(exprAfterSuch, i);
					Predicate<Double> pred = (Double x) -> x < num;
					predicates.add(pred);

				} else if (letter == '!' && exprAfterSuch[i + 1] == '=') {
					double num = getCompareToValue(exprAfterSuch, i + 1);
					Predicate<Double> pred = (Double x) -> x != num;
					predicates.add(pred);
					i++;
				} else if (letter == '=') {
					double num = getCompareToValue(exprAfterSuch, i + 1);
					Predicate<Double> pred = (Double x) -> x == num; // todo this is strange? probab there is never equation?
					predicates.add(pred);
					i++;
				} else if (letter == '%' && exprAfterSuch[i + 1] == '2' && exprAfterSuch[i + 2] == '=' && exprAfterSuch[i + 3] == '0') {
					double num = getCompareToValue(exprAfterSuch, i + 1);
					Predicate<Double> pred = (Double x) -> (x % 2 == 0); // todo this is strange? probab there is never equation?
					predicates.add(pred);
					i += 3;
				} else if (letter == '%' && exprAfterSuch[i + 1] == '1' && exprAfterSuch[i + 2] == '=' && exprAfterSuch[i + 3] == '1') {
					double num = getCompareToValue(exprAfterSuch, i + 1);
					Predicate<Double> pred = (Double x) -> (x % 2 == 0); // todo this is strange? probab there is never equation?
					predicates.add(pred);
					i += 3;
				}
			}
		}
		return predicates;
	}

	private double getCompareToValue(char[] seq, int index) {

		StringBuilder strNum = new StringBuilder();

		for (int i = index; i < seq.length; i++) {
			switch (seq[i]) {
				case '-':
					if (strNum.equals("")) {
						strNum.append('-');
						break;
					}
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
					strNum.append(seq[i]);
					break;

				default:
					if (!strNum.toString().equals("")) {
						return Double.parseDouble(strNum.toString());
					} else break;
			}
		}

		return Double.parseDouble(strNum.toString());
	}

	// https://github.com/farolfo/jComprehension/blob/master/src/main/java/ListComprehension.java
	// Under MIT licence, altough adopted piece of code from user https://github.com/farolfo

	private void setOutputExpression(Function<T, T> outputExpression) {
		this.outputExpression = outputExpression;
	}

	private void setPairOutputExpression(BiFunction<T, T, ?> pairOutputExpression) {
		this.pairOutputExpression = pairOutputExpression;
	}

	public List<T> suchThat(Consumer<Var> predicates) {
		Var x = new Var<T>();
		predicates.accept(x);
		return (List<T>) x.value().stream().map(outputExpression).collect(toList());
	}

	public List<Pair<T, T>> suchThat(BiConsumer<Var, Var> predicates) {
		Var x = new Var<T>();
		Var y = new Var<T>();
		predicates.accept(x, y);
		return (List<Pair<T, T>>) x.value(y).stream()
				.map(pair -> pairOutputExpression.apply((T) ((Pair) pair).getLeft(), (T) ((Pair) pair).getRight()))
				.collect(toList());
	}

	public SetComprehension<T> outputExpression(Function<T, T> resultTransformer) {
		this.setOutputExpression(resultTransformer);
		return this;
	}

	public SetComprehension<T> outputExpression(BiFunction<T, T, ?> resultTransformer) {
		this.setPairOutputExpression(resultTransformer);
		return this;
	}

	public class Var<T> {
		private Set<T> belongsTo = new HashSet<>();
		private Set<Predicate<T>> predicates = new HashSet<>();
		private Set<BiPredicate<T, T>> biPredicates = new HashSet<>();

		Var() {
			this.predicates.add(x -> true);
			this.biPredicates.add((x, y) -> true);
		}

		public Var belongsTo(List<T> c) {
			this.belongsTo.addAll(c);
			return this;
		}

		public Var is(Predicate<T> p) {
			this.predicates.add(p);
			return this;
		}

		public Var is(ArrayList<Predicate<T>> p) {
			for (int i = 0; i < p.size(); i++) {
				predicates.add(p.get(i));
			}
			return this;
		}

		public Var holds(Predicate<T> p) {
			return is(p);
		}

		public Var is(BiPredicate<T, T> p) {
			this.biPredicates.add(p);
			return this;
		}

		public Var holds(BiPredicate<T, T> p) {
			return is(p);
		}

		private List<T> value() {
			return intersect(predicates.stream()
					.map(condition -> belongsTo.stream()
							.filter(condition)
							.collect(toList()))
					.collect(toList()));
		}

		private List<T> intersect(List<List<T>> lists) {
			return belongsTo.stream()
					.filter(x -> lists.stream()
							.filter(list -> list.contains(x)).count() == lists.size())
					.collect(toList());
		}

		private List<Pair<T, T>> value(Var yVar) {
			List<BiPredicate<T, T>> allBiPredicates = new LinkedList<>();
			allBiPredicates.addAll(this.biPredicates);
			allBiPredicates.addAll((Collection<? extends BiPredicate<T, T>>) yVar.biPredicates.stream()
					.map(new Function<BiPredicate<T, T>, BiPredicate<T, T>>() {
						@Override
						public BiPredicate apply(BiPredicate p) {
							return new BiPredicate<T, T>() {
								@Override
								public boolean test(T x, T y) {
									return p.test(y, x);
								}
							};
						}
					}).collect(toList()));

			List<Pair<T, T>> pairs = new LinkedList<>();

			this.value().stream().map(new Function<T, Boolean>() {
				@Override
				public Boolean apply(T x) {
					yVar.value().stream().map(new Function<T, Boolean>() {
						@Override
						public Boolean apply(T y) {
							return pairs.add(Pair.of(x, y));
						}
					}).collect(toList());
					return null;
				}
			}).collect(toList());

			return pairs.stream().filter(pair -> holdsAll(allBiPredicates, pair)).collect(toList());
		}

		private boolean holdsAll(List<BiPredicate<T, T>> predicates, Pair<T, T> pair) {
			return predicates.stream().filter(p -> p.test(pair.getLeft(), pair.getRight())).count() == predicates.size();
		}

		private <G> List<G> concat(List<List<G>> lists) {
			List<G> list = new LinkedList<>();
			lists.stream().map(l -> {
				list.addAll(l);
				return l;
			}).collect(toList());
			return list;
		}
	}
}