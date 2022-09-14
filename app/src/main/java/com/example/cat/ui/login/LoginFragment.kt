package com.example.cat.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cat.R
import com.example.cat.databinding.FragmentLoginBinding
import com.example.cat.extensions.navigateSafely
import com.example.cat.extensions.observe
import com.example.cat.ui.cats.CatsFragmentDirections
import com.example.cat.utils.AlertDialogUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var views: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = FragmentLoginBinding.bind(view)

        observe(viewModel.loginInTheApp) { loginInTheApp ->
            if (loginInTheApp) {
                navigateToCatsScreen()
            }
        }

        observe(viewModel.showProgress) { showProgress ->
            views!!.progress.isVisible = showProgress
        }

        observe(viewModel.showError) { message ->
            Toast.makeText(
                context, message,
                Toast.LENGTH_LONG
            ).show()
        }

        observe(viewModel.showInvalidLogin) { messageId ->
            AlertDialogUtil.createCustomAlertDialog(
                context = requireContext(),
                alertTitle = getString(messageId),
                null,
                positiveButton = Pair(getString(R.string.lbl_ok)) { dialog, _ ->
                    dialog.dismiss()
                }, null
            ).show()
        }

        initButtons()
    }

    private fun navigateToCatsScreen() {
        val loginDirections = LoginFragmentDirections.actionLoginFragmentToCatsFragment()
        findNavController().navigateSafely(
            loginDirections
        )
    }

    private fun initButtons() {
        views?.btnForgotPassword?.setOnClickListener {
            val customDialogContentView = layoutInflater.inflate(
                R.layout.layout_alert_dialog_edit_text, null
            )
            val editText = customDialogContentView.findViewById<EditText>(R.id.et_name)
            editText.setText("")
            val dialog = AlertDialogUtil.createCustomAlertDialog(
                requireContext(),
                "", null,
                positiveButton = Pair(getString(R.string.lbl_recover)) { dialog, _ ->
                    dialog.dismiss()
                },
                negativeButton = Pair(getString(R.string.lbl_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }, customDialogContentView = customDialogContentView
            )
            editText.addTextChangedListener(object : TextWatcher {
                private fun handleText() {
                    val okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    okButton.isEnabled = editText.text.isNotBlank()
                }

                override fun afterTextChanged(arg0: Editable?) {
                    handleText()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            dialog.show()
        }

        views?.btnLogin?.setOnClickListener {
            viewModel.login(
                views?.inputEmail?.text.toString(),
                views?.inputPassword?.text.toString()
            )
        }
    }
}