package at.tugraz.ist.swe.lang;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.ListView;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AddWordsActivityEspressoTest {
    @Rule
    public ActivityTestRule<AddWordsActivity> addWordsActivityTestRule = new ActivityTestRule<>(AddWordsActivity.class);

    String toast = "Empty input!";

    @BeforeClass
    public static void insertWords() {
        Vocabulary vocabulary = new Vocabulary(InstrumentationRegistry.getInstrumentation().getTargetContext());
        vocabulary.resetVocab();

        vocabulary.add("Banane", "Banana");
        vocabulary.add("Birne", "Pear");
        vocabulary.add("Orange", "Orange");
        vocabulary.add("Pfirsich", "Peach");
        vocabulary.storeFile();
    }


    @Test
    public void testElementsVisible() {

        onView(withId(R.id.btnAdd)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAdd)).check(matches(withText("ADD")));
        onView(withId(R.id.ptAddEnglish)).check(matches(isDisplayed()));
        onView(withId(R.id.ptAddGerman)).check(matches(isDisplayed()));
        onView(withId(R.id.lvWordList)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddWordsVisible() {
        String inputEnglish = "Apple";
        String inputGerman = "Apfel";
        String display = inputEnglish + " : " + inputGerman;

        onView(withId(R.id.ptAddEnglish)).perform(typeText(inputEnglish));
        onView(withId(R.id.ptAddGerman)).perform(typeText(inputGerman));

        closeSoftKeyboard();

        onView(withId(R.id.btnAdd)).perform(click());

        ListView lvWordList = (ListView)addWordsActivityTestRule.getActivity().findViewById(R.id.lvWordList);
        int lvItemsCount = lvWordList.getAdapter().getCount();

        onData(anything())
                .inAdapterView(withId(R.id.lvWordList))
                .atPosition(lvItemsCount-1)
                .check(matches(withText(display)));
    }

    @Test
    public void testAddAndDeleteWordsVisible() {
        String inputEnglish = "Apple_delete";
        String inputGerman = "Apfel_loeschen";
        String display = inputEnglish + " : " + inputGerman;

        onView(withId(R.id.ptAddEnglish)).perform(typeText(inputEnglish));
        onView(withId(R.id.ptAddGerman)).perform(typeText(inputGerman));

        closeSoftKeyboard();

        onView(withId(R.id.btnAdd)).perform(click());

        ListView lvWordList = (ListView)addWordsActivityTestRule.getActivity().findViewById(R.id.lvWordList);
        int lvItemsCountBeforeDelete = lvWordList.getAdapter().getCount();
        onData(anything()).inAdapterView(withId(R.id.lvWordList)).atPosition(lvItemsCountBeforeDelete-1).perform(click());

        onView(withId(R.id.btnDeleteWord)).perform(click());

        int lvItemsCountAfterDelete = lvWordList.getAdapter().getCount()-1;

        // For TestingPurposes decreement
        lvItemsCountBeforeDelete--;

        // Must be equal if delete was successful.
        assertEquals(lvItemsCountBeforeDelete,lvItemsCountAfterDelete);
    }

    @Test
    public void testEmptyGermanPtToastVisible() {
        String inputEnglish = "Apple";

        onView(withId(R.id.ptAddEnglish)).perform(typeText(inputEnglish));

        closeSoftKeyboard();

        onView(withId(R.id.btnAdd)).perform(click());

        AddWordsActivity activity = addWordsActivityTestRule.getActivity();
        onView(withText(toast)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyEnglishPtToastVisible() {
        String inputGerman = "Apfel";

        onView(withId(R.id.ptAddGerman)).perform(typeText(inputGerman));

        closeSoftKeyboard();

        onView(withId(R.id.btnAdd)).perform(click());

        AddWordsActivity activity = addWordsActivityTestRule.getActivity();
        onView(withText(toast)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyPtsToastVisible() {
        closeSoftKeyboard();

        onView(withId(R.id.btnAdd)).perform(click());

        AddWordsActivity activity = addWordsActivityTestRule.getActivity();
        onView(withText(toast)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void testRatingWithSampleInput() {

        onData(anything()).inAdapterView(withId(R.id.lvWordList)).atPosition(0).perform(click());

        String inputTag = "difficult";

        onView(withId(R.id.ptNewTag)).perform(typeText(inputTag));

        closeSoftKeyboard();

        onView(withId(R.id.btnAddTag)).perform(click());

        ListView lvWordList = (ListView)addWordsActivityTestRule.getActivity().findViewById(R.id.lvTags);
        int lvItemsCount = lvWordList.getAdapter().getCount();

        onData(anything())
                .inAdapterView(withId(R.id.lvTags))
                .atPosition(lvItemsCount-1)
                .check(matches(withText(inputTag)));

    }


    @AfterClass
    public static void deleteVocab() {
        Vocabulary vocabulary = new Vocabulary(InstrumentationRegistry.getInstrumentation().getTargetContext());
        vocabulary.resetVocab();
    }

}
