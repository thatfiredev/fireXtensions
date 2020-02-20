package io.github.rosariopfernandes.firextensions.analytics

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FirebaseAnalyticsTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `Firebase#analytics should delegate to FirebaseAnalytics#getInstance`() {
        assertThat(Firebase.analytics(context))
                .isSameInstanceAs(FirebaseAnalytics.getInstance(context))
    }

}