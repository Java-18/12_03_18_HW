package com.sheygam.java_18_08_03_18_hw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView myList;
    private TextView emptyTxt;
    private Button logoutBtn, addBtn;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        myList = findViewById(R.id.contact_list);
        emptyTxt = findViewById(R.id.empty_list_txt);
        logoutBtn = findViewById(R.id.logout_btn);
        addBtn = findViewById(R.id.add_btn);

        logoutBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this,AddContactActivity.class);
                intent.putExtra("STATUS","view");
                intent.putExtra("POS",position);
                startActivity(intent);

            }
        });

        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) adapter.getItem(position);
                Toast.makeText(ListActivity.this, "Long click on " + contact.getName(),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        load();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.logout_btn){
            setResult(RESULT_OK);
            finish();
        }else if(v.getId() == R.id.add_btn){
            startActivity(new Intent(this,AddContactActivity.class));
        }
    }

    private void load(){
        String id = getSharedPreferences("AUTH",MODE_PRIVATE)
                .getString("ID",null);
        if(id == null){
            setResult(RESULT_OK);
            finish();
        }

        String data = getSharedPreferences("CONTACTS",MODE_PRIVATE)
                .getString(id,null);
        if(data == null || data.isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            adapter = new MyAdapter(new ArrayList<Contact>());
            myList.setAdapter(adapter);
        }else{
            emptyTxt.setVisibility(View.GONE);
            String[] arr = data.split(";");
            ArrayList<Contact> contacts = new ArrayList<>();
            for (String str : arr){
                contacts.add(Contact.newInstance(str));
            }
            adapter = new MyAdapter(contacts);
            myList.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_item){
            setResult(RESULT_OK);
            finish();
        }else if(item.getItemId() == R.id.add_item){
            Intent intent = new Intent(this,AddContactActivity.class);
            intent.putExtra("STATUS","add");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
