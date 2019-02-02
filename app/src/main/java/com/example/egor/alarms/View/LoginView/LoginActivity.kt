package com.example.egor.alarms.View.LoginView

import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.egor.alarms.Controller.ControllerSingleton
import com.example.egor.alarms.R
import com.example.egor.alarms.View.ActivitiesInterfaces.LoginActivityInterface

class LoginActivity : AppCompatActivity(), LoginActivityInterface {
    private var register = false

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var startRegisterButton: Button

    private var loading = false

    companion object {
        const val REGISTER_INTENT_EXTRA_NAME = "register"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usernameEditText = findViewById(R.id.login_username_text)
        passwordEditText = findViewById(R.id.login_password_text)
        passwordRepeatEditText = findViewById(R.id.login_password_repeat_text)
        fab = findViewById(R.id.login_fab)
        progressBar = findViewById(R.id.login_progress_bar)
        startRegisterButton = findViewById(R.id.login_register_button)

        progressBar.visibility = ProgressBar.INVISIBLE
        for (editText in arrayOf(usernameEditText, passwordRepeatEditText, passwordRepeatEditText)) {
            editText.setOnClickListener { editText.hint = "" }
        }
        register = intent.extras.getBoolean(REGISTER_INTENT_EXTRA_NAME)
        if (!register) {
            passwordRepeatEditText.visibility = EditText.INVISIBLE
        } else {
            startRegisterButton.visibility = ConstraintLayout.GONE
        }
        fab.setOnClickListener {
            if (!loading) {
                if (register) onRegisterClicked() else onLoginClicked()
            }
        }
        startRegisterButton.setOnClickListener { if (!register) startRegister() }
    }

    private fun startRegister() {
        register = true
        passwordRepeatEditText.visibility = EditText.VISIBLE
    }

    override fun getUsername(): String {
        return usernameEditText.text.toString()
    }

    override fun getPassword(): String {
        return passwordEditText.text.toString()
    }

    override fun onLoginError(error: String) {
        showUsernameError(error)
    }

    override fun onLoginOk() {
        finish()
    }

    override fun onRegisterOK() {
        finish()
    }

    override fun onRegisterError(error: String) {
        showUsernameError(error)
    }

    override fun startLoading() {
        loading = true
        setActivated(false)
    }

    override fun stopLoading() {
        loading = false
        setActivated(true)
    }

    override fun onInternetError() {
        Toast.makeText(applicationContext, "Check Internet connection", Toast.LENGTH_LONG).show()
    }

    private fun setActivated(activated: Boolean) {
        if (!activated) progressBar.visibility = ProgressBar.VISIBLE
        else progressBar.visibility = ProgressBar.INVISIBLE
        usernameEditText.isActivated = activated
        passwordEditText.isActivated = activated
        passwordRepeatEditText.isActivated = activated
        fab.isActivated = activated
    }

    private fun onLoginClicked() {
        ControllerSingleton.instance.onLoginActivityLoginClicked(this)
    }

    private fun onRegisterClicked() {
        val usernameError = checkUsername(usernameEditText.text.toString())
        val passwordError = checkPassword(passwordEditText.text.toString(), passwordRepeatEditText.text.toString())
        if (usernameError != "") {
            showUsernameError(usernameError)
        } else if (passwordError != "") {
            showPasswordError(passwordError)
        } else {
            ControllerSingleton.instance.onLoginActivityRegisterClicked(this)
        }
    }

    private fun showUsernameError(error: String) {
        usernameEditText.error = error
    }

    private fun showPasswordError(error: String) {
        passwordEditText.error = error
    }

    private fun checkUsername(username: String): String {
        if (username.contains(" ")) return "No spaces allowed"
        return ""
    }

    private fun checkPassword(password: String, passwordRepeat: String): String {
        if (password != passwordRepeat) return "Passwords didn't match"
        if (password.length < 8) return "password must be at least 8 symbols"
        return ""
    }

}
