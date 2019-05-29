package at.tugraz.ist.swe.lang;

import static org.hamcrest.Matchers.anything;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;
import android.widget.Toast;

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
import static org.hamcrest.core.IsNot.not;

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

    @Test
    public void checkRightAnswer(){

        Vocabulary vocab = (Vocabulary)mActivityTestRule.getActivity().vocabulary;
        int score = (int)mActivityTestRule.getActivity().myScore;

        TextView questionTxt = mActivityTestRule.getActivity().findViewById(id.tvQuestion);

        String translateWord = questionTxt.getText().toString();
        System.out.println(translateWord);
        String answer = vocab.getTranslation("german", translateWord);

        onData(anything())
                .inAdapterView(withText(answer))
                .perform(click());

        onView(withId(id.score)).check(matches(not(withText("Score: "+score))));
    }

    @Test
    public void checkInvalidAnswer(){

        onData(anything())
                .inAdapterView(withId(id.multiple))
                .atPosition(0)
                .perform(click());
        onView(withId(id.score)).check(matches(withText("Score: 0")));



    }


    /*@Test
    public void checkMessage(){

        onData(anything())
                .inAdapterView(withId(id.multiple))
                .atPosition(0)
                .perform(click());
        onView(withId(R.id.myAttemps)).check(matches(withText("2")));



    }*/


}