package at.tugraz.ist.swe.lang;

import static org.hamcrest.Matchers.anything;
import android.app.LauncherActivity;
import android.content.Context;
import android.provider.CalendarContract;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.AdapterViewProtocol;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.graphics.drawable.IconCompat;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static android.support.test.espresso.Espresso.getIdlingResources;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onIdle;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.AdapterViewProtocol.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static at.tugraz.ist.swe.lang.R.*;

import static org.hamcrest.Matchers.startsWith;

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
    public void onViewList() {

        onView(withId(id.de_btn)).perform(click());
        onData(startsWith("Raum")).perform(click());
        onView(withId(id.en_btn)).perform(click());
        onData(startsWith("Room")).perform(click());


    }

    @Test
    public void onViewTranslate(){
        onData(anything())
                .inAdapterView(withId(R.id.word_list))
                .atPosition(0)
                .perform(click());
        onView(withId(R.id.translated_word)).check(matches(withText("Foot")));

    }








}

