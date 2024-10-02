package com.estellore.menu

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Dialog Title")
                .setMessage("This is the message of the dialog.")
                .setPositiveButton("OK") { _, _ ->
                    // Handle positive button (similar to 1st menu)
                    (activity as? MainActivity)?.openFragment(MyFragment())
                }
                .setNegativeButton("Cancel") { _, _ ->
                    // Handle negative button (similar to 3rd menu, exit app)
                    (activity as? MainActivity)?.finishAffinity()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
