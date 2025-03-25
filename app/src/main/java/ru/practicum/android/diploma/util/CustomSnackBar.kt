package ru.practicum.android.diploma.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import ru.practicum.android.diploma.R

fun showCustomSnackBar(message: String, view: View, context: Context) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

    val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    textView.gravity = Gravity.CENTER
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    snackbar.setTextColor(ContextCompat.getColor(context, R.color.yp_white))

    snackbar.view.background = ContextCompat.getDrawable(context, R.drawable.red_rounded_rectangle)
    snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.yp_red))

    snackbar.show()
}
