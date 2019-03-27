package at.tugraz.ist.swe.lang;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AddWordsActivityEspressoTest {
    @Rule
    public ActivityTestRule<AddWordsActivity> addWordsActivityTestRule = new ActivityTestRule<>(AddWordsActivity.class);


    @Test
    public void testAddButtonVisible() {

        onView(withId(R.id.btAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.btAdd)).check(matches(withText("ADD")));
    }

    @Test
    public void testAddEnglishVisible() {
        onView(withId(R.id.ptAddEnglish)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddEnglishVisible() {
        onView(withId(R.id.ptAddGerman)).check(matches(isDisplayed()));
    }

    @Test
    public void testWordListVisible() {
        onView(withId(R.id.lvWordList)).check(matches(isDisplayed()));
    }

}
