package com.example.andronote;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class home extends AppCompatActivity {
    DBHandler dbhandler;
    ImageView iv1;
    SearchView s1;
    ArrayList<String> al;
    ListView lv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        iv1=findViewById(R.id.iv1);
        lv1=findViewById(R.id.lv1);
        s1=findViewById(R.id.s1);
        dbhandler = new DBHandler(this);
        al=new ArrayList<>();
        Cursor data = dbhandler.getData();
        while(data.moveToNext()) {
            al.add(data.getString(0));

        }
        ListAdapter ad = new ArrayAdapter<String>(home.this, android.R.layout.simple_list_item_1, al);
        lv1.setAdapter(ad);
        //search view code
        s1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ((ArrayAdapter<?>) ad).getFilter().filter(s);
                return false;
            }
            public void onBackPressed()
            {
                home.this.finish();
                System.exit(0);
            }
        });

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int which_item= i;
                String item=adapterView.getItemAtPosition(i).toString();
                new AlertDialog.Builder(home.this).setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you Sure?").setMessage("Do you want to delete this item")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbhandler.deleteItem(item);
                                al.remove(which_item);
                                ((ArrayAdapter<?>) ad).notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("NO",null).show();
                return true;                         //false creating problem click+long click
            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),note.class);
                startActivity(i);
            }
        });
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String param=adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getApplicationContext(),param, Toast.LENGTH_LONG).show();
                Intent in=new Intent(getApplicationContext(),editnote.class);
                in.putExtra("Selected",param);
                startActivity(in);
                finish();
            }
        });

    }
}