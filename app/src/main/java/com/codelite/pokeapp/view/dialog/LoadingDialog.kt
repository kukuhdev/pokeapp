package com.codelite.pokeapp.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.codelite.pokeapp.databinding.DialogLoadingBinding

class LoadingDialog : DialogFragment(){
    private lateinit var binding: DialogLoadingBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLoadingBinding.inflate(inflater,container,false)
        isCancelable = false
        initData()

        return binding.root
    }

    private fun initData() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = LoadingDialog()
    }
}