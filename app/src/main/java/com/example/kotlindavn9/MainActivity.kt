package com.example.kotlindavn9

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var emailText:EditText;
    lateinit var passwordText:EditText
    lateinit var rePasswordText: EditText;
    lateinit var submitButton: Button;
     private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
                "(?=.*[A-Z])" +
                "(?=.*[0-9])"+
                "(?=.*[@#$%^&+=!])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{8,}" +  // at least 4 characters
                "$"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailText = findViewById(R.id.editEmailText);
        passwordText = findViewById(R.id.editPassword)
        rePasswordText = findViewById(R.id.editPasswordAgain)
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener{

            if(validateData(emailText.text.toString(),passwordText.text.toString(),rePasswordText.text.toString())){
                mAuth.createUserWithEmailAndPassword(emailText.text.toString(),passwordText.text.toString())
                    .addOnCompleteListener {    task->
                        if(task.isSuccessful){
                            emailText.text.clear()
                            passwordText.text.clear()
                            rePasswordText.text.clear()
                            Toast.makeText(this,"User Created",Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,"Error On Firebase",Toast.LENGTH_SHORT).show()
                        }

                    }
            }else{
                Toast.makeText(this,"Fix Credentials",Toast.LENGTH_SHORT).show()
            }

        }


    }



    private fun validateData(email : String, password:String, rePassword:String):Boolean{

        if (email.trim { it <= ' ' }.matches(emailPattern.toRegex())
            && password == rePassword && PASSWORD_PATTERN.matcher(password.trim()).matches()
        ) {return true}




        return false
    }
}