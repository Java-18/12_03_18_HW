package com.sheygam.java_18_08_03_18_hw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputName, inputEmail, inputPhone, inputAddress;
    private TextView nameTxt, emailTxt, phoneTxt, addressTxt;
    private RelativeLayout viewContainer, editContainer;
    private Button okBtn;
    private int currentPosition = -1;
    private ArrayList<Contact> contacts;
    private String status;

    private MenuItem editItem, deleteItem, doneItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        loadContacts();

        Intent intent = getIntent();
        status = intent.getStringExtra("STATUS");
        if(status.equals("view")){
            currentPosition = intent.getIntExtra("POS",-1);
        }

        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPhone = findViewById(R.id.input_phone);
        inputAddress = findViewById(R.id.input_address);
        nameTxt = findViewById(R.id.name_txt);
        emailTxt = findViewById(R.id.email_txt);
        phoneTxt = findViewById(R.id.phone_txt);
        addressTxt = findViewById(R.id.address_txt);
        viewContainer = findViewById(R.id.view_container);
        editContainer = findViewById(R.id.edit_container);
        okBtn = findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        if(status.equals("view")){
            showView();
        }else{
            showEdit();
        }
        super.onStart();
    }

    private void showEdit() {
        if(currentPosition >= 0){
            Contact contact = contacts.get(currentPosition);
            inputName.setText(contact.getName());
            inputEmail.setText(contact.getEmail());
            inputPhone.setText(contact.getPhone());
            inputAddress.setText(contact.getAddress());
            viewContainer.setVisibility(View.GONE);
            editContainer.setVisibility(View.VISIBLE);
        }else{
            viewContainer.setVisibility(View.GONE);
            editContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_menu,menu);
        editItem = menu.findItem(R.id.edit_item);
        doneItem = menu.findItem(R.id.done_item);
        deleteItem = menu.findItem(R.id.delete_item);
        if(status.equals("view")){
            doneItem.setVisible(false);
        }else{
            editItem.setVisible(false);
            deleteItem.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.done_item:
                Contact contact = new Contact();
                contact.setName(inputName.getText().toString());
                contact.setEmail(inputEmail.getText().toString());
                contact.setPhone(inputPhone.getText().toString());
                contact.setAddress(inputAddress.getText().toString());
                if(currentPosition >= 0){
                    contacts.remove(currentPosition);
                    contacts.add(currentPosition,contact);
                }else{
                    contacts.add(contact);
                }
                newSave();
                break;
            case R.id.delete_item:
                contacts.remove(currentPosition);
                newSave();
                break;
            case R.id.edit_item:
                showEdit();
                deleteItem.setVisible(false);
                editItem.setVisible(false);
                doneItem.setVisible(true);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void showView() {
        Contact contact = contacts.get(currentPosition);
        nameTxt.setText(contact.getName());
        emailTxt.setText(contact.getEmail());
        phoneTxt.setText(contact.getPhone());
        addressTxt.setText(contact.getAddress());
        viewContainer.setVisibility(View.VISIBLE);
        editContainer.setVisibility(View.GONE);
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ok_btn){
            save();
        }
    }


    private void loadContacts(){
        contacts = new ArrayList<>();
        String id = getSharedPreferences("AUTH",MODE_PRIVATE)
                .getString("ID",null);
        if(id == null){
            finish();
        }else{
            String all = getSharedPreferences("CONTACTS",MODE_PRIVATE)
                    .getString(id,null);
            if(all != null && !all.isEmpty()){
                String[] arr = all.split(";");
                for (String c : arr){
                    contacts.add(Contact.newInstance(c));
                }
            }
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

    private void newSave(){
        String id = getSharedPreferences("AUTH",MODE_PRIVATE)
                .getString("ID",null);
        if(id == null){
            finish();
        }else{
            String str = "";
            for(int i = 0; i < contacts.size();i++){
                if(i == 0){
                    str += contacts.get(i).toString();
                }else{
                    str = str + ";" + contacts.get(i).toString();
                }
            }
            getSharedPreferences("CONTACTS",MODE_PRIVATE)
                    .edit()
                    .putString(id,str)
                    .commit();
            finish();
        }
    }
}
