package com.dedany.secretgift.presentation.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.dedany.secretgift.R
import com.dedany.secretgift.databinding.ActivityLoginBinding
import com.dedany.secretgift.databinding.ActivityMainBinding
import com.dedany.secretgift.databinding.CodeInputDialogBinding
import com.dedany.secretgift.presentation.game.viewGame.ViewGameActivity
import com.dedany.secretgift.presentation.main.MainActivity
import com.dedany.secretgift.presentation.main.MainActivityViewModel
import com.dedany.secretgift.presentation.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    private var viewModel: LoginViewModel? = null
    private var loginAdapter: LoginAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setContentView(binding?.root)

        initObservers()
        initListeners()
    }

    /*private fun setAdapter(){
        loginAdapter = LoginAdapter(){
            viewModel?.loadListCode()
        }

        binding?.recyclerView?.adapter = loginAdapter*/

    private fun initObservers() {
        viewModel?.isLoginSuccess?.observe(this) { isSuccess ->
            if (isSuccess) {


            } else {
                Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel?.registeredUser?.observe(this) { registeredUser ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", registeredUser)

            startActivity(intent)
            finish()
        }

        viewModel?.canDoLogin?.observe(this) { isEnabled ->
        }
        viewModel?.isLoginFormValid?.observe(this) { isValid ->
            if (!isValid) {
                Toast.makeText(
                    this@LoginActivity,
                    "Rectifica correo o contraseña",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initListeners() {
        binding?.etEmail?.doOnTextChanged { text, start, before, count ->
            viewModel?.setEmail(text.toString())
        }

        binding?.etPassword?.doOnTextChanged { text, start, before, count ->
            viewModel?.setPassword(text.toString())
        }

        binding?.btnLogin?.setOnClickListener {
            viewModel?.login()
        }

        binding?.tvLinkCreateUser?.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding?.btnEvent?.setOnClickListener {

            val dialog = Dialog(this)

            dialog.setContentView(R.layout.code_input_dialog)

            dialog.show()
            val btnConfirm = dialog.findViewById<Button>(R.id.btn_confirm)
            val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
            val inputCodeField = dialog.findViewById<EditText>(R.id.inputCodeField)

            btnConfirm.setOnClickListener {
                startActivity(Intent(this, ViewGameActivity::class.java))
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            inputCodeField.doOnTextChanged { text, _, _, _ ->
                viewModel?.setCode(text.toString())
            }

        }

    }
}
