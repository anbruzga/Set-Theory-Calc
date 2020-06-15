package Sets.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class TableData implements Serializable {
    private static final long serialVersionUID = -1240697744316073937L;
    private ArrayList<SingleTableLine> data;

    public TableData(){
        data = new ArrayList<>();
    }

    public void add(SingleTableLine line){
        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).getExercise().equals(line.getExercise())){

                // User checked answer before answering correctly
                if (line.isAnswerCheckBeforeSuccess() && !data.get(i).isSuccess()){
                    data.get(i).setAnswerCheckBeforeSuccess(true);
                }
                // If the answer was checked, stop gatherng stats for exercise
                else if(data.get(i).isAnswerCheckBeforeSuccess()){
                    return;
                }
                // User answered incorrectly
                else if (!line.isSuccess() && !data.get(i).isSuccess()) {
                    data.get(i).addFail();
                }
                // User answered correctly
                else if (line.isSuccess()) {
                    data.get(i).setSuccess(true);
                }
                return;
            }
        }
        data.add(line);
       // data.clear(); // uncomment this to clear all the serialised data
    }

    public SingleTableLine getLine(int i){
        return data.get(i);
    }

    public boolean setLine(int i, SingleTableLine line){
        if (data.size()-1 <= i) {
            data.remove(i);
            data.add(i, line);
            return true;
        }
        return false;
    }

    public int getRows(){
        return data.size();
    }

}
