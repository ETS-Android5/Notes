package it.feio.android.omninotes;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class validateCreateChecklist {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateCreateChecklist() {
        ViewInteraction viewInteraction = onView(
                allOf(withId(R.id.fab_expand_menu_button),
                        childAtPosition(
                                allOf(withId(R.id.fab),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                3),
                        isDisplayed()));
        viewInteraction.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.fab_checklist),
                        withParent(allOf(withId(R.id.fab),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("Checklist"),
                        withParent(allOf(withId(R.id.fab),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Checklist")));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_checklist),
                        childAtPosition(
                                allOf(withId(R.id.fab),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.detail_title),
                        childAtPosition(
                                allOf(withId(R.id.title_wrapper),
                                        childAtPosition(
                                                withId(R.id.detail_tile_card),
                                                0)),
                                1),
                        isDisplayed()));
        editText.perform(replaceText("Checklist1"), closeSoftKeyboard());

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.detail_title), withText("Checklist1"),
                        childAtPosition(
                                allOf(withId(R.id.title_wrapper),
                                        childAtPosition(
                                                withId(R.id.detail_tile_card),
                                                0)),
                                1),
                        isDisplayed()));
        editText2.perform(pressImeActionButton());

        ViewInteraction editTextMultiLineNoEnter = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("it.feio.android.checklistview.models.CheckListViewItem")),
                                0),
                        2),
                        isDisplayed()));


        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.reminder_layout),
                        childAtPosition(
                                allOf(withId(R.id.fragment_detail_content),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                2)));
        linearLayout.perform(scrollTo(), click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.buttonPositive), withText("Ok"),
                        childAtPosition(
                                allOf(withId(R.id.button_layout),
                                        childAtPosition(
                                                withId(R.id.llMainContentHolder),
                                                2)),
                                5),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
