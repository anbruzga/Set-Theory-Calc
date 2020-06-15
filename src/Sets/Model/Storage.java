package Sets.Model;

import java.util.ArrayList;
import java.util.Random;

public class Storage {

	private static final char union = '\u222A';
	private static final char intersection = '\u2229';
	private static final char product = '\u2A2F';
	private static final char difference = '\u2216';
	// VARIABLES
	private static int setId = -1;
	private ArrayList<Pair<Integer, MixedSet>> setList = new ArrayList<>();
	private ArrayList<String> examplesList = new ArrayList<>();
	private int lastExample = -1;


	//                   <set id> <isSuccess>
	private ArrayList<Pair<Integer, Boolean>> stats = new ArrayList<>();


	// CONSTRUCTORS
	public Storage() {
		initExamples();
	}

	public Storage(ArrayList<Pair<Integer, MixedSet>> setList) {
		this.setList = setList;
	}

	//SETTERS GETTERS
	public int getSetId(int listIndex) {
		return setList.get(listIndex).getLeft();
	}

	public int getSetId(MixedSet set) {
		for (int i = 0; i < setList.size(); i++) {
			if (setList.get(i).getRight() == set) {
				return setList.get(i).getLeft();
			}
		}
		return -1;
	}

	public int getSetId(Pair<Integer, MixedSet> pair) {
		for (int i = 0; i < setList.size(); i++) {
			if (setList.get(i) == pair) {
				return setList.get(i).getLeft();
			}
		}
		return -1;
	}

	public boolean isDefined(MixedSet set) {
		return getSetId(set) != -1;
	}

	public boolean isDefined(int id) {
		return getSetById(id) != null;
	}

	public ArrayList<Pair<Integer, MixedSet>> getSetList() {
		return setList;
	}

	public void setSetList(ArrayList<Pair<Integer, MixedSet>> setList) {
		this.setList = setList;
	}


	public MixedSet getSetById(int id) {
		for (int i = 0; i < setList.size(); i++) {

			if (setList.get(i).getLeft() == id) {
				return setList.get(i).getRight();
			}
		}
		return null;
	}


	// METHODS
	// SETTERS GETTERS
	public void addSet(MixedSet set) {
		if (set.getName() == "" || set.getName() == null) {
			createSetName();
		}
		setList.add(new Pair(set, ++setId));
	}

	private void createSetName() {
		ArrayList<String> setNames = new ArrayList<>();
		for (int i = 0; i < setList.size(); i++) {
//            if(setList.get(i).getRight().getName() != ""
//                    || setList.get(i).getRight().getName() != null) {
			//               setNames.add(setList.get(i).getRight().getName());
			//          }
		}

	}

	public void addSet(MixedSet[] set) {
		for (int i = 0; i < set.length; i++) {
			setList.add(new Pair(set[i], ++setId));
		}
	}

	public void addSet(ArrayList<MixedSet> set) {
		for (int i = 0; i < set.size(); i++) {
			setList.add(new Pair(set.get(i), ++setId));
		}
	}


	public boolean remove(MixedSet set) {
		int id = getSetId(set);
		if (id == -1) return false;
		Pair pair = new Pair(set, id);
		return setList.remove(pair);
	}

	public boolean remove(int id) {
		MixedSet set = getSetById(id);
		Pair pair = new Pair(set, id);
		return setList.remove(pair);
	}

	public boolean remove(Pair<MixedSet, Integer> pair) {
		return setList.remove(pair);
	}

	public void addGuess(int id, boolean right) {

	}

	public String getExample() {
		int max = examplesList.size() - 1;
		int min = 0;
		Random r = new Random();
		int rand;

		do {
			rand = r.nextInt((max - min) + 1) + min;
		} while (rand == lastExample);
		lastExample = rand;

		return examplesList.get(rand);
	}

	private void initExamples() {

		examplesList.add("{1,2,3,4}" + union + "{1,3,4,5}");
		examplesList.add("{1,2,3,4}" + intersection + "{1,3,4,5}");
		examplesList.add("{1,2,3,4}" + difference + "{1,3,4,5}");
		examplesList.add("{1,2,3,4}" + difference + "{1,3,4,5}" + union + "{1 , 2}");
		examplesList.add("({1,2}" + difference + "{1,3})" + difference + "({1,2,3} " + union + "{4,5,6})");
		examplesList.add("{1,2,3, {Black, Red },4,5} " + union + "{x: x " + Symbols.belongsTo + " N, x > -5, x < 5}");
		examplesList.add("{1,2}" + difference + "{1,3}" + difference + "{1} ");
		examplesList.add("{1,2}" + difference + "{1,3}" + difference + "{1 } " + union + "{4} " + union + "{1,3}");
		examplesList.add("{1,2,3,4,5} " + union + "{x: x " + Symbols.belongsTo + " Z, x > -5, x < 5}");

		examplesList.add("P({1,2} ⨯ {3,4})");
		examplesList.add("P({1,2}) ⨯ P({3,4})");
		examplesList.add("P({1,2})  ∩  P({a,1})");
		examplesList.add("({1,2} ⨯ {a,b})⨯{C,D}");
		examplesList.add("({1,2} ⨯ {a,b})  ∖ ({2,1}⨯{b,a})");
		examplesList.add("{1,2}⨯ ({a,b}⨯{C,D})");
		examplesList.add("{{1}} ⨯ ({{a}}⨯{{C}})");
		examplesList.add("P({{2}})  ⨯  P({{a}})");


		examplesList.add("{1,2,3,4} ∪ {1,3,4,5}");
		examplesList.add("{1,2,3,4} ∩ {1,3,4,5}");
		examplesList.add("{1,2,3,4} ∖ {1,3,4,5}");
		examplesList.add("{1,2,3,4} ∖ {1,3,4,5} ∪ {1 , 2}");
		examplesList.add("{1,2} ∖ {1,3} ∖ {1}");
		examplesList.add("{x: x ∈ Z, x >= -2, x <= 3}");
		examplesList.add("{x: x ∈ Z, x > -5, x <= 2} ∪ {15, 13}");
		examplesList.add("{x: x ∈ Z, x > -5, x < 0}");
		examplesList.add("{1, 2} ⨯ {A, B}");
		examplesList.add("{x: x ∈ Z, x > -97, x <= 97}'");
		examplesList.add("{Phone, {Journal, Magazine}} ∪ {Lake, River}");
		examplesList.add("{Computer, {Journal, Magazine}} ∩ {Journal}");
		examplesList.add("{Computer, {Journal, Magazine}} ∩ {{Journal}}");
		examplesList.add("{Computer, {Journal, Magazine}} ∩ {{Journal, Magazine}}");
		examplesList.add("P({a,b,c})");
		examplesList.add("P({1})");
		examplesList.add("{x: x ∈ Z,  x^2 == x }");
		examplesList.add("{1,2,3} ⊂ {2,3}");
		examplesList.add("{1,2,3} ⊂ {1,2,3}");
		examplesList.add("{1,2} ⊂ {1,2,3}");
		examplesList.add("{1,2,3} ⊆ {2,3}");
		examplesList.add("{1,2,3} ⊆ {1,2,3}");
		examplesList.add("{1,2} ⊆ {1,2,3}");
		examplesList.add("{x: x ∈ N,  x^2 == x }");

		examplesList.add("({1,2} ∖ {1,3}) ∖ ({1,2,3} ∪ {4,5,6})");
		examplesList.add("{1,2,3, {Black, Red}, 4,5} ∪ {x: x ∈ Z, x > -5, x < 5}");
		examplesList.add("{1,2} ∖ {1,3} ∖ {1} ∪ {4} ∪ {1,3}");
		examplesList.add("{1,2,3,4,5} ∪ {x: x ∈ N,  x > -4, x <= 2}");
		examplesList.add("{x: x ∈ N, 2*x >=12/2, x <=6*x/7}");
		examplesList.add("{x: x ∈ N, x >= -100, x < 5}");
		examplesList.add("{x: x ∈ N, x/1.5 >= -99, x <= 99} ∩ {0, -1,-2,-3,-4,-5}");
		examplesList.add(" {x: x ∈ Z, x >= -100, x <= 100} ∩ {x: x ∈ N, x >= -100, x <= 100} ∩ {x: x ∈ Z, x >= -100, x <= 5}");
		examplesList.add("{x: x ∈ N, x > -5, x <= 5, x != 0, x != 2}");
		examplesList.add("{x: x ∈ N, x > -5, x <= 5, x != 0}");
		examplesList.add("{1,2,0}⊆{x: x ∈ Z, x>= 0, x <= 2}");
		examplesList.add("{1,{hy},2,3,{a,b,c}}∖{1,{a,b,c},4,5}∖{{hy},1,6}");
		examplesList.add("{1,{hy},2,3,{a,b,c}}∖{1,{a,b},{c},4,5}∖{{h},{y},1,6}");
		examplesList.add("{x: x ∈ N,  x^2 >= -5, x != 2, x<= 5  } ∪ {4, 1, {3, 5}}");
		examplesList.add("{1,{hy},3,{a,b,c}}∪{1,{a,b,c},5}∪{{hy},6}");

	}

}

