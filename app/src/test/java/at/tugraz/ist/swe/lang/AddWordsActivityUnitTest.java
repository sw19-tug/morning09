package at.tugraz.ist.swe.lang;
import org.junit.*;
import static org.junit.Assert.*;

public class AddWordsActivityUnitTest {

    AddWordsActivity newTest = new DummyAddWordsActivity();

    @Test
    public void insertToJsonEmptyTest(){

        ((DummyAddWordsActivity)newTest).jsonDataDummy = "";

        newTest.insertToJson(null,"apple", "Apfel");

        assertEquals(((DummyAddWordsActivity)newTest).tmpSave, "{\"vocabulary\":[{\"german\":\"Apfel\",\"english\":\"apple\"}]}");
    }

    @Test
    public void insertToJsonNotEmptyTest(){

        ((DummyAddWordsActivity)newTest).jsonDataDummy = "{\"vocabulary\":[{\"german\":\"Apfel\",\"english\":\"apple\"}]}";

        newTest.insertToJson(null,"pear", "Birne");

        assertEquals(((DummyAddWordsActivity)newTest).tmpSave, "{\"vocabulary\":[{\"german\":\"Apfel\",\"english\":\"apple\"},{\"german\":\"Birne\",\"english\":\"pear\"}]}");
    }


}
