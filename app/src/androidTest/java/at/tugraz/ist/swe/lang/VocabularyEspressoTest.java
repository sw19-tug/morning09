package at.tugraz.ist.swe.lang;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class VocabularyEspressoTest {

    @Rule
    public ActivityTestRule<AddWordsActivity> addWordsActivityTestRule = new ActivityTestRule<>(AddWordsActivity.class);

    @Test
    public void storeTest() {
        //save something in database
        Vocabulary database = new Vocabulary(addWordsActivityTestRule.getActivity().getApplicationContext());
        database.store(saveString);

        //check if file exists
        File file = new File(addWordsActivityTestRule.getActivity().getApplicationContext().getFilesDir(), "vocabulary.json");
        assert(file.exists());
    }
}
