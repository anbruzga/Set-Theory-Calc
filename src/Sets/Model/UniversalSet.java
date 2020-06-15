package Sets.Model;

public class UniversalSet extends MixedSet {

	private static UniversalSet universalSet = null;
	private static int max;
	private static int min;
	private static char type;
	public static final int UNIVERSALSET_MAX_BOUND = 500;
	public static final int UNIVERSALSET_MIN_BOUND = -500;


	//CONSTRUCTORS
	private UniversalSet(MixedSet domain, int max, int min, char type) {
		super("U");
		universalSet = this;
		universalSet.addAll(domain);
		UniversalSet.max = max;
		UniversalSet.min = min;
		UniversalSet.type = type;
	}

	public static UniversalSet getInstance(MixedSet domain, int max, int min, char type) {
		if (max > UNIVERSALSET_MAX_BOUND) {
			max = UNIVERSALSET_MAX_BOUND;
		}
		if (min < UNIVERSALSET_MIN_BOUND) {
			min = UNIVERSALSET_MIN_BOUND;
		}

		universalSet = new UniversalSet(domain, max, min, type);
		return universalSet;
	}

	public static UniversalSet getInstance() {
		if (universalSet == null) {
			System.out.println("UniversalSet not initialised");
		}
		return universalSet;
	}


	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		if (max > UNIVERSALSET_MAX_BOUND) {
			max = UNIVERSALSET_MAX_BOUND;
		}
		UniversalSet.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		if (min < UNIVERSALSET_MIN_BOUND) {
			min = UNIVERSALSET_MIN_BOUND;
		}
		UniversalSet.min = min;
	}

	public static char getType() {
		if (type == '\u0000') {
			System.out.println("Universal set type was not set");
			return '\u0000';
		}
		return type;
	}

	public static void setType(char type) {
		UniversalSet.type = type;
	}


	public boolean doesUniversalSetContainDouble(double doubleValue) {
		if (type == 'Z' && doubleValue % 1 == 0) {
			return doubleValue >= min && doubleValue <= max;
		} else if (type == 'N' && doubleValue % 1 == 0) {
			return doubleValue >= 1 && doubleValue <= max;
		} else if (type == 'Q') {
			return doubleValue >= min && doubleValue <= max;
		} else return false;

	}

	 @Override
	 public boolean contains(Object o){
		if (o instanceof Double){
			 return universalSet.doesUniversalSetContainDouble((Double) o);
		 }
		else return super.contains(o);
	 }

	@Override
	public String toString() {
		String str = super.linkedHashSetToString();
		char[] charArr = str.toCharArray();
		StringBuilder modified = new StringBuilder();
		modified.append("U = ");
		return getSetBody(charArr, modified);
	}


	public String toStringNoName() {
		String str = super.linkedHashSetToString();
		char[] charArr = str.toCharArray();
		StringBuilder modified = new StringBuilder();
		return getSetBody(charArr, modified);
	}

	private String getSetBody(char[] charArr, StringBuilder modified) {
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] == '[') {
				modified.append('{');
			} else if (charArr[i] == ']') {
				modified.append('}');
			} else if (charArr[i] == '.' && charArr[i + 1] == '0') {
				i++;
			} else {
				modified.append(charArr[i]);
			}
		}

		return modified.toString();
	}

}
