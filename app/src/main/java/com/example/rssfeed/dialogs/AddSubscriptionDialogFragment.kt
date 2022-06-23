package com.example.rssfeed.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.rssfeed.databinding.DialogAddSubscriptionBinding

class AddSubscriptionDialogFragment : DialogFragment() {
    private var listener: ((String, String) -> Unit)? = null

    fun setOnSubscriptionAddedListener(listener: (String, String) -> Unit) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val binding = DialogAddSubscriptionBinding.inflate(layoutInflater)
            builder.setView(binding.root)
                .setPositiveButton("Add") { _, _ ->
                    val url = binding.subscriptionUrl.text.toString()
                    val name = binding.subscriptionName.text.toString()
                    listener?.invoke(name, url)
                }
                .setNegativeButton("Cancel") { _, _ -> dialog?.cancel() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}