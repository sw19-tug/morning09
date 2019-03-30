package at.tugraz.ist.swe.lang;


import android.content.Context;

import org.junit.*;

import static org.junit.Assert.*;

public class AddWordsActivityUnitTest {

    AddWordsActivity newTest = new AddWordsActivity();

    @Before
    public void firstInsertToJson(){

        newTest.insertToJson("apple", "Apfel");

    }


    @Test
    public void nowReadFromJson(){

        String testString = newTest.readFromJson();
        String expectedString = "{\"vocabulary\":[{\"english\":\"apple\",\"german\":\"Apfel\"}]}";
        assertEquals(testString, expectedString);

    }



}
