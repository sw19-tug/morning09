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
public class TranslationActivityEspressoTest {

    @Rule
    public ActivityTestRule<TranslationActivity> mActivityTestRule = new ActivityTestRule<>(TranslationActivity.class);

    @Test
    public void onViewElements() {
        onView(withId(R.id.de_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.en_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.word_list)).check(matches(isDisplayed()));
        onView(withId(R.id.translated_word)).check(matches(isDisplayed()));
        onView(withId(R.id.de_btn)).check(matches(withText("GERMAN")));
        onView(withId(R.id.en_btn)).check(matches(withText("ENGLISH")));
    }

    @Test
    public void onViewListWord(){
        onData(anything())
                .inAdapterView(withId(R.id.word_list))
                .atPosition(0)
                .perform(click());
        onView(withId(R.id.translated_word)).check(matches(withText("Fuß")));

    }

    @Test
    public void onViewSwitchListView(){
                onData(anything())
                .inAdapterView(withId(R.id.en_btn))
                .perform(click());
                onData(anything())
                        .inAdapterView(withId(R.id.word_list))
                        .atPosition(1)
                        .check(matches(withText("Room")));

    }



}

