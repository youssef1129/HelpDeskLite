package fr.estiam.helpdesklite

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicketJourneyTest {
    @get:Rule val fixture = TicketUiFixture()

    @Test fun createsLowPriorityTicket() {
        val title = "Keyboard in lab B"
        val description = "Several keys do not respond."
        onView(withId(R.id.addTicket)).perform(click())
        onView(withId(R.id.titleInput)).perform(replaceText(title), closeSoftKeyboard())
        onView(withId(R.id.descriptionInput)).perform(replaceText(description), closeSoftKeyboard())
        onView(withId(R.id.priorityInput)).perform(scrollTo(), click())
        onData(equalTo<Any>("LOW")).perform(click())
        onView(withId(R.id.saveTicket)).perform(scrollTo(), click())
        fixture.awaitCatalogueRows()

        val row = allOf(
            withId(R.id.ticketRow),
            hasDescendant(allOf(withId(R.id.ticketTitle), withText(title))),
        )
        onView(withId(R.id.ticketList)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(row),
        )
        onView(row).check(matches(allOf(
            isDisplayed(),
            hasDescendant(allOf(withId(R.id.ticketPriority), withText("LOW"))),
        )))
    }
}
