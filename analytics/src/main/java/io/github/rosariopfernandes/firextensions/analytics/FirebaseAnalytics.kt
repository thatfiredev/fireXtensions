package io.github.rosariopfernandes.firextensions.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase

/** Returns the default [FirebaseAnalytics] instance*/
fun Firebase.analytics(context: Context) = FirebaseAnalytics.getInstance(context)
