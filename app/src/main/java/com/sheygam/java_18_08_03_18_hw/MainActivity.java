package com.sheygam.java_18_08_03_18_hw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail, inputPassword;
    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getCurrentId() != null){
            startNext();
        }
        setContentView(R.layout.activity_main);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    private String getCurrentId(){
        return getSharedPreferences("AUTH",MODE_PRIVATE)
                .getString("ID",null);

    }

    private void startNext(){
        startActivityForResult(new Intent(this, ListActivity.class),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            finish();
        }else{
            getSharedPreferences("AUTH",MODE_PRIVATE)
                    .edit()
                    .remove("ID")
                    .apply();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_btn){
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            getSharedPreferences("AUTH",MODE_PRIVATE)
                    .edit()
                    .putString("ID",email+password)
                    .commit();
            startNext();
        }
    }
}
