package Sets.Model;


import Sets.Controler.SetUtils;
import Sets.Controler.SyntaxAnalyser;
import Sets.View.PopUp;

import java.util.*;

public class MixedSet extends LinkedHashSet {
	private String name;
	private int row;
	private int col;
	private boolean cartesianProduct;
	private static boolean intersectedWithUniversalSet = false;
	private boolean innerSet;

	public MixedSet(boolean innerSet) {
		super();
		if (!innerSet) {
			//name = NameRandomised.getRandName();
			//NameRandomised.addName(name);
		}
		this.innerSet = innerSet;
	}


	public MixedSet(String name) {
		super();
		this.name = name;
	}


	// METHODS
	public void addNull() { // not really used that symbol in the end
		add(Symbols.nullSet);
	}

	public void add(Object[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] instanceof String && ((String) arr[i]).matches("-?\\d+")) {
				this.add(Double.parseDouble(arr[i].toString()));
			} else {
				super.add(arr[i]);
			}
		}
	}

	public void add(int i) {
		this.add((double) i);
	}

	public void add(Double d){
		if(innerSet){
			if(d >= UniversalSet.getInstance().getMin()
				&& d <= UniversalSet.getInstance().getMax()
				&& UniversalSet.getInstance().doesUniversalSetContainDouble(d)){
				super.add(d);
			}
		} else {
			super.add(d);
		}
	}

	public void add(String str) {
		if (str.matches("-?\\d+")) {
			this.add(Double.parseDouble(str));
		} else {
			super.add(str);
		}
	}

	public void add(double[] arr) {
		for (int i = 0; i < arr.length; i++) {
			this.add(arr[i]);
		}
	}

	public void add(IntRange range) {
		double[] rangeArr = range.getArrayOfAllElementsInRange();
		this.add(rangeArr);
	}

	// 1 = false
	// 2 = first
	// 3 = last

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProduct(boolean boolVal, int col, int row) {
		cartesianProduct = boolVal;
		this.col = col;
		this.row = row;
	}

	public void setProduct(boolean boolVal) {
		cartesianProduct = boolVal;
	}

	public Boolean isProduct() {
		return cartesianProduct;
	}

	public int getRows() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public LinkedHashSet cartesianProduct(MixedSet setB) {
		LinkedHashSet product = new LinkedHashSet();
		int row = 0; // using to store the rows and columns for toString
		int col = 0;

		Iterator itrOuter = super.iterator();
		while (itrOuter.hasNext()) {
			row++;
			String val1 = itrOuter.next().toString();
			Iterator itrInner = setB.iterator();
			while (itrInner.hasNext()) {

				String val2 = itrInner.next().toString();
				Pair pair = new Pair(val1, val2);
				product.add(pair);
				col++;
			}
		}
		col = col / row;
		this.setProduct(true, col, row);
		this.clear();
		this.addAll(product);
		return this;
	}


	public MixedSet difference(MixedSet setToSubtract) {

		super.removeAll(setToSubtract); //old and simple
		return this;

	}

	public MixedSet union(MixedSet setToUnion) {
		super.addAll(setToUnion);
		return this;
	}

	public MixedSet complement() {
		MixedSet uSet = UniversalSet.getInstance();
		MixedSet copy = new MixedSet(false);
		copy.addAll(uSet.difference(this));
		copy.setName(this.getName());
		this.clear();
		this.addAll(copy);
		return this;
	}

	public boolean hasInnerSets() {
		int count = (int) this.stream()
				.filter(e -> e instanceof Set)
				.count();

		return count > 0;
	}


	public MixedSet powerSet(MixedSet originalSet) {
		List<String> list = new ArrayList<String>(originalSet);
		int n = list.size();

		Set<Set<String>> powerSet = new LinkedHashSet<>();

		for (long i = 0; i < (1 << n); i++) {
			Set<String> element = new LinkedHashSet<>();
			for (int j = 0; j < n; j++)
				if ((i >> j) % 2 == 1) {
					element.add(String.valueOf(list.get(j)));
				}
			powerSet.add(element);
		}

		MixedSet setTemp = new MixedSet(false);
		setTemp.addAll(powerSet);
		return setTemp;
	}

	public MixedSet intersection(MixedSet setToIntersect) {
		super.retainAll(setToIntersect);
		return this;
	}

	public int getMultiplicity() {
		return (int) this.stream().count();
	}

	public boolean properSubset(MixedSet setToCompare) {
		if (!this.containsAll(setToCompare)) return false;
		this.difference(setToCompare);
		return getMultiplicity() != 0;
	}


	public boolean weakSubset(MixedSet argSet) {
		return this.containsAll(argSet);
	}


	public MixedSet intersectionWithUniveralSet() {

		char type = UniversalSet.getType();

		switch (type) {
			case 'Q':
				MixedSet setCopy = SetUtils.deepCopy(this);
				for (Object e : setCopy) {
					if (e instanceof MixedSet) {
						((MixedSet) e).intersectionWithUniveralSet();
					} else if (SyntaxAnalyser.strIsNumeric(e.toString())
							&& !UniversalSet.getInstance().doesUniversalSetContainDouble((Double) e)) {
						super.remove(e);
						LostValuesToUInteresection.addValues(e);
					}
				}
				return this;
			case 'N':
				UniversalSet.getInstance().setMin(1);

			case 'Z':

				MixedSet setCopy2 = SetUtils.deepCopy(this);

				for (Object e : setCopy2) {
					if (e instanceof MixedSet) { // if element is a set, pass recursively
						((MixedSet) e).intersectionWithUniveralSet();
					} else if (!UniversalSet.getInstance().contains(e) && SyntaxAnalyser.strIsNumeric(e.toString())) {
						remove(e); // if UniversalSet doesn't contain, remove this element
						LostValuesToUInteresection.addValues(e);
					}
					if (e.toString().replace(".0", "").contains(".")) {
						remove(e); // removing all numbers with precision point because
						LostValuesToUInteresection.addValues(e);
					}
					if (UniversalSet.getType() == 'N'
							&& SyntaxAnalyser.strIsNumeric(e.toString())
							&& ((Double) e) <= 0) {
						remove(e);
						LostValuesToUInteresection.addValues(e);
					}
				}

				return this;

			default:
				PopUp.infoBox("IntersectionWithUniversal Error", "MixedSet");
				return null;
		}

	}


	public boolean isIntersectedWithUniversalSet() {
		return intersectedWithUniversalSet;
	}

	public void setIntersectedWithUniversalSet(boolean isIntersected) {
		intersectedWithUniversalSet = isIntersected;
	}

	public String toString() {
		// Performing intersection with Universal Set
		// at the very last moment gives exponential
		// boost to speed

		this.intersectionWithUniveralSet();
		intersectedWithUniversalSet = true;

		return toStringNoIntersectionWithUniversalSet();
	}

	// Useful when working with universal set, to avoid circular dependency,
	// because deep copy of a set, used to intersect nested subsets with and without U,
	// would cause "concurrent modification exception"
	public String toStringNoIntersectionWithUniversalSet() {

		StringBuilder modified = new StringBuilder();
	//	if (name != null) {
	//		modified.append(name);
	//		modified.append(" = ");
	//	}

		if (isProduct()) {
			//return toStringProduct();
		}

		//String str = super.toString();
		Iterator<Object> it = iterator();
		if (!it.hasNext())
			return "{}";

		StringBuilder sb = new StringBuilder();
		sb.append(modified);
		sb.append('{');
		while (true) {
			Object e = it.next();
			if (e instanceof MixedSet) {
				sb.append(((MixedSet) e).linkedHashSetToString());
				//sb.append('}');
			} else if (SyntaxAnalyser.strIsNumeric(e.toString()) && e.toString().equals("-0")) {
				double zero = 0.0;
				sb.append(zero);
			} else {
				sb.append(e);
			}
			if (!it.hasNext()) {
				sb.append('}');
				break;
			}
			sb.append(',').append(' ');
		}


		return sb.toString()
				.replaceAll(".0,", ",")
				.replaceAll("]", "}")
				.replaceAll("\\[", "{")
				.replaceAll(".0}", "}")
				.replaceAll(".0\\)", ")");


	}



	private String toStringProduct() {
		String str = super.toString();
		char[] charArr = str.toCharArray();
		StringBuilder modified = new StringBuilder();

		if (name != null) {
			modified.append(name + "= ");
		}

		int colCounter = 0;
		char letter;
		for (int i = 0; i < charArr.length; i++) {
			letter = charArr[i];
			if (letter == '}') {
				colCounter++; // If closing the set pair
			}

			if (letter == '[') {
				modified.append('{');
			} else if (letter == ']') {
				modified.append('}');
			}

			// if the time has come, start from new line to keep formatting of cartesian product
			else if (col <= colCounter && (i + 1 <= charArr.length - 2)) {
				colCounter = 0;
				if ((i + 3) == charArr.length) {
					modified.append(")\n");
				} else {
					modified.append("),\n");
				}
				i++;
			} else if (letter == '{') {
				modified.append('(');
			} else if (letter == '}') {
				modified.append(')');
			} else {
				modified.append(letter);
			}


		}

		return modified.toString();

	}

	public String toStringNoName(boolean intersectWithUniversalSet) {
		String str;
		if (intersectWithUniversalSet) {
			str = this.toString();
		} else {
			str = this.toStringNoIntersectionWithUniversalSet();
		}
		if (str.contains("=")) {
			str = str.split("=")[1].trim();
		}

		return str;
	}


	@Override
	public boolean equals(Object o) {
		if (o instanceof Set) {
			return SetUtils.doesSetsContainsSameElements(this, (MixedSet) o);
		} else return super.equals(o);
	}

	// for implementing/extending classes to achieve LinkedHashSet toString
	protected String linkedHashSetToString() {
		return super.toString();
	}
}
