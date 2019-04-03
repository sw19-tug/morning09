package at.tugraz.ist.swe.lang;

import android.content.Context;

public class DummyAddWordsActivity extends AddWordsActivity {

    public String tmpSave;
    public String jsonDataDummy;

    @Override
    public void insertToFile(Context context, String filename, String saveString) {
        tmpSave = saveString;
    }

    @Override
    public String readFromFile(Context context, String filename) {
        return jsonDataDummy;
    }
}
