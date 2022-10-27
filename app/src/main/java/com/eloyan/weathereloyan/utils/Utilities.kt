package com.eloyan.weathereloyan.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import com.eloyan.weathereloyan.R

fun showEntryCityDialog(context: Context, getCityWeather: (city: String) -> Unit) {
    val cityEditText = EditText(context)
    cityEditText.setTextColor(context.getColor(R.color.white))
    cityEditText.isFocused

    val dialog = AlertDialog
        .Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
        .setView(cityEditText)
        .create()

    dialog.setTitle(context.getString(R.string.enter_name_dialog_title))
    dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.positive_btn_text)) { _, _ ->
        getCityWeather(cityEditText.text.toString())
        dialog.dismiss()
    }
    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.negative_btn_text)) { _, _ ->
        dialog.dismiss()
    }

    dialog.show()
}

fun showLocationSettingsDialog(context: Context, showSettings: () -> Unit) {
    val dialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom)).create()
    dialog.setTitle(context.getString(R.string.gps_dialog_title))
    dialog.setMessage(context.getString(R.string.gps_dialog_message))
    dialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.positive_btn_text)) { _, _ ->
        showSettings()
        dialog.dismiss()
    }
    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.negative_btn_text)) { _, _ ->
        dialog.dismiss()
    }

    dialog.show()
}

fun showToast(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()
}