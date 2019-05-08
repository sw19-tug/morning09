package at.tugraz.ist.swe.lang;

import static org.hamcrest.Matchers.anything;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static at.tugraz.ist.swe.lang.R.*;

import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class TranslationActivityEspressoTest {

    @Rule
    public ActivityTestRule<TranslationActivity> mActivityTestRule = new ActivityTestRule<>(TranslationActivity.class);

    @Test
    public void onViewElements() {
        onView(withId(R.id.btnGerman)).check(matches(isDisplayed()));
        onView(withId(R.id.en_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.lvVocabulary)).check(matches(isDisplayed()));
        onView(withId(R.id.tvTranslatedWord)).check(matches(isDisplayed()));
        onView(withId(R.id.btnGerman)).check(matches(withText("GERMAN")));
        onView(withId(R.id.en_btn)).check(matches(withText("ENGLISH")));
    }


    @Test
    public void onViewList() {

        onView(withId(id.btnGerman)).perform(click());
        onData(startsWith("Raum")).perform(click());
        onView(withId(id.en_btn)).perform(click());
        onData(startsWith("Room")).perform(click());
    }

    @Test
    public void onViewTranslate(){
        onData(anything())
                .inAdapterView(withId(R.id.lvVocabulary))
                .atPosition(0)
                .perform(click());
        onView(withId(R.id.tvTranslatedWord)).check(matches(withText("Foot")));

    }








}

