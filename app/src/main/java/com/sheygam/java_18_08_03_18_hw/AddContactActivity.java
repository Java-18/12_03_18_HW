package com.sheygam.java_18_08_03_18_hw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputName, inputEmail, inputPhone, inputAddress;
    private Button okBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPhone = findViewById(R.id.input_phone);
        inputAddress = findViewById(R.id.input_address);
        okBtn = findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ok_btn){
            save();
        }
    }

    private void save() {
        String id = getSharedPreferences("AUTH",MODE_PRIVATE)
                .getString("ID",null);
        if(id == null){
            finish();
        }else{
            String name = inputName.getText().toString();
            String email = inputEmail.getText().toString();
            String phone = inputPhone.getText().toString();
            String address = inputAddress.getText().toString();
            Contact contact = new Contact(name,email,phone,address);
            String all = getSharedPreferences("CONTACTS",MODE_PRIVATE)
                    .getString(id,null);
            if(all == null){
                getSharedPreferences("CONTACTS",MODE_PRIVATE)
                        .edit()
                        .putString(id,contact.toString())
                        .commit();
            }else{
                all += ";" + contact.toString();
                getSharedPreferences("CONTACTS",MODE_PRIVATE)
                        .edit()
                        .putString(id,all)
                        .commit();
            }

            finish();
        }
    }
}
