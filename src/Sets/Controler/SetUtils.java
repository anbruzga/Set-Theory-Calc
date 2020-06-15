package Sets.Controler;

import Sets.Model.IntRange;
import Sets.Model.MixedSet;
import Sets.Model.UniversalSet;

public class SetUtils {


    public static MixedSet deepCopy(MixedSet set) {
        String setStr = set.toStringNoIntersectionWithUniversalSet();
        MixedSet setNew;
        try {
            setNew = SyntaxAnalyser.rosterToSet(setStr);
        } catch (IncorrectInputSyntaxException e) {
            return null;
        }
        return setNew;
    }

    public static String setStringCutEnds(MixedSet set) {
        String str = set.toStringNoName(false);
        str = str.substring(1, str.length() - 1);
        return str;
    }

    public static boolean doesSetsContainsSameElements(MixedSet set1, MixedSet set2) {
        if (set1.weakSubset(set2) && set2.weakSubset(set1)) {
            return true;
        } else return false;
    }
}
