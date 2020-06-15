package Sets.Controler;


import Sets.Model.MixedSet;
import Sets.View.*;

import java.util.*;

public class VennDiagramOpener {


    public static void openVennDiagrams(ArrayList<MixedSet> setList) {
        int size = setList.size();

        switch (size) {
            case 2:
                Diagrams2Sets vennView2 = new Diagrams2Sets();
                vennView2.setSets(setList);
                break;
            case 3:
                Diagrams3Sets vennView3 = new Diagrams3Sets();
                vennView3.setSets(setList);
                break;
            case 4:
                Diagrams4Sets vennView4 = new Diagrams4Sets();
                vennView4.setSets(setList);
                break;
            case 1:
                PopUp.infoBox("There must be at least 2 sets", "VennDiagramOpener.OpenVennDiagrams");
                break;
            case 0:
                PopUp.infoBox("No checkbox is selected", "VennDiagramOpener.OpenVennDiagrams");
                break;
            default:
                PopUp.infoBox("Maximum 4 sets are allowed", "VennDiagramOpener.OpenVennDiagrams");
        }
    }
}
