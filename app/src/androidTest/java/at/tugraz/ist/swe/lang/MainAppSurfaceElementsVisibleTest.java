package at.tugraz.ist.swe.lang;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainAppSurfaceElementsVisibleTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testButtonsVisible() {

        onView(withId(R.id.btMainAddWords)).check(matches(isDisplayed()));
//        onView(withId(R.id.btMainAddWords)).check(matches(withText("ADD WORD")));

        onView(withId(R.id.main_btn_translate)).check(matches(isDisplayed()));
//        onView(withId(R.id.main_btn_translate)).check(matches(withText("TRANSLATE")));

        onView(withId(R.id.simpleTest_btn)).check(matches(isDisplayed()));
//        onView(withId(R.id.simpleTest_btn)).check(matches(withText("SIMPLE TEST")));


        onView(withId(R.id.test_btn)).check(matches(isDisplayed()));
//        onView(withId(R.id.test_btn)).check(matches(withText("ADVANCED TEST")));

        onView(withId(R.id.btnSettings)).check(matches(isDisplayed()));
//        onView(withId(R.id.btnSettings)).check(matches(withText("SETTINGS")));

        onView(withId(R.id.btnHelp)).check(matches(isDisplayed()));
//        onView(withId(R.id.btnHelp)).check(matches(withText("HELP")));

        onView(withId(R.id.btnCategory)).check(matches(isDisplayed()));
//        onView(withId(R.id.btnCategory)).check(matches(withText("CATEGORIES")));

        onView(withId(R.id.btnLegal)).check(matches(isDisplayed()));
//        onView(withId(R.id.btnLegal)).check(matches(withText("LEGAL")));
    }
}
