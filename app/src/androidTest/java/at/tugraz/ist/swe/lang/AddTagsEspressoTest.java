package at.tugraz.ist.swe.lang;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.BaseAdapter;
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
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class AddTagsEspressoTest {

    @Rule
    public ActivityTestRule<RatingActivity> addTagsTestRule = new ActivityTestRule<>(RatingActivity.class);

    @Test
    public void testElementsVisible() {

        // Just basic implementation

        onView(withId(R.id.textViewDe)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewEn)).check(matches(isDisplayed()));
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()));
        
        onView(withId(R.id.btnDeleteTag)).check(matches(isDisplayed()));
        onView(withId(R.id.lvTags)).check(matches(isDisplayed()));
        onView(withId(R.id.ptNewTag)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAddTag)).check(matches(isDisplayed()));
        onView(withId(R.id.btnAddTag)).check(matches(withText("ADD")));

    }


}
