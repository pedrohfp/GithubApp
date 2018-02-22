package br.com.githubapp.feature.repo

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import br.com.githubapp.feature.TestApplication
import com.example.mocks.JsonPaths
import com.example.mocks.getJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by pedro on 22/02/18.
 */
@RunWith(AndroidJUnit4::class)
class RepoActivityTest{

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<RepoActivity>(RepoActivity::class.java,
            true, false)

    private val server: MockWebServer by lazy{ MockWebServer() }

    private val json = "{\n" +
            "  \"total_count\": 40,\n" +
            "  \"incomplete_results\": false,\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"id\": 3081286,\n" +
            "      \"name\": \"Tetris\",\n" +
            "      \"full_name\": \"dtrupenn/Tetris\",\n" +
            "      \"owner\": {\n" +
            "        \"login\": \"dtrupenn\",\n" +
            "        \"id\": 872147,\n" +
            "        \"avatar_url\": \"https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png\",\n" +
            "        \"gravatar_id\": \"\",\n" +
            "        \"url\": \"https://api.github.com/users/dtrupenn\",\n" +
            "        \"received_events_url\": \"https://api.github.com/users/dtrupenn/received_events\",\n" +
            "        \"type\": \"User\"\n" +
            "      },\n" +
            "      \"private\": false,\n" +
            "      \"html_url\": \"https://github.com/dtrupenn/Tetris\",\n" +
            "      \"description\": \"A C implementation of Tetris using Pennsim through LC4\",\n" +
            "      \"fork\": false,\n" +
            "      \"url\": \"https://api.github.com/repos/dtrupenn/Tetris\",\n" +
            "      \"created_at\": \"2012-01-01T00:31:50Z\",\n" +
            "      \"updated_at\": \"2013-01-05T17:58:47Z\",\n" +
            "      \"pushed_at\": \"2012-01-01T00:37:02Z\",\n" +
            "      \"homepage\": \"\",\n" +
            "      \"size\": 524,\n" +
            "      \"stargazers_count\": 1,\n" +
            "      \"watchers_count\": 1,\n" +
            "      \"language\": \"Assembly\",\n" +
            "      \"forks_count\": 0,\n" +
            "      \"open_issues_count\": 0,\n" +
            "      \"master_branch\": \"master\",\n" +
            "      \"default_branch\": \"master\",\n" +
            "      \"score\": 10.309712\n" +
            "    }\n" +
            "  ]\n" +
            "}"

    @Before
    fun setup() {
        server.start()

        val application = InstrumentationRegistry.getInstrumentation().targetContext.
                applicationContext as TestApplication

        application.setBaseUrl(server.url("/").toString())
    }

    @Test
    fun testing(){
        server.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(json))

        activityRule.launchActivity(null)

        onView(withText("Tetris")).check(matches(isDisplayed()))
    }
}