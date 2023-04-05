package org.diyorbek.mvvm_h1.util

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.snackBar(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
}