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
public class SimpleTestActivityEspressoTest {

    @Rule
    public ActivityTestRule<SimpleTestActivity> mActivityTestRule = new ActivityTestRule<>(SimpleTestActivity.class);

    @Test
    public void onViewElements() {
        onView(withId(R.id.score)).check(matches(isDisplayed()));
        onView(withId(R.id.question)).check(matches(isDisplayed()));
        onView(withId(R.id.multiple)).check(matches(isDisplayed()));

    }
}