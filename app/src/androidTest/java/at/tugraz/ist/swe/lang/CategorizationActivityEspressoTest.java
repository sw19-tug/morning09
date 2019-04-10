package at.tugraz.ist.swe.lang;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class CategorizationActivityEspressoTest {
    @Rule
    public ActivityTestRule<CategorizationActivity> CategorizationActivityTestRule = new ActivityTestRule<>(CategorizationActivity.class);

    @Test
    public void testElementsVisible() {
        onView(withId(R.id.btnAlphabeticSort)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAlphabeticSort)).check(matches(withText("Sort by alphabet")));
        onView(withId(R.id.spLanguageSort)).check(matches(isDisplayed()));
        onView(withId(R.id.spTagSort)).check(matches(isDisplayed()));
        onView(withId(R.id.lvVocabulary)).check(matches(isDisplayed()));
    }


}
