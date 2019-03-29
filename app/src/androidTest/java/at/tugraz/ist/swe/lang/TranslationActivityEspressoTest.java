package at.tugraz.ist.swe.lang;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
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
}
