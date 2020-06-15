package Sets.Model;

public class LostValuesToUInteresection {

	private static MixedSet lastCalculationLostValues = null;

	private LostValuesToUInteresection(){};

	public static void resetValues(){
		lastCalculationLostValues = new MixedSet(false);
	}

	public static void addValues(Object o){
		lastCalculationLostValues.add(o);
	}

	public static MixedSet getSet(){
		return lastCalculationLostValues;
	}

	public static String getSetToString(){
		return lastCalculationLostValues.toStringNoIntersectionWithUniversalSet();
	}

	public static void createFirst(){
		if (lastCalculationLostValues == null){
			lastCalculationLostValues = new MixedSet(false);
		}
	}
}
