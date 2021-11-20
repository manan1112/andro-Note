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
        Cursor data = dbhandler.getData(); //fetched data.
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
            } //false return specifies that full text search (non dynamic) is disabled

            @Override
            public boolean onQueryTextChange(String s) { //dynamic text search enabled.
                ((ArrayAdapter<?>) ad).getFilter().filter(s);
                return false;
            }
            public void onBackPressed()  //On clicking back button of android phone the application gets exited.
            {
                home.this.finish();
                System.exit(0);
            }
        });

        // Long click resulting in opting for delete option.
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
                return true;                         //false creating problem click+long click..clicking on long also invoke single click
            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),note.class);
                startActivity(i);
            }
        });

        //clicking the item will tell about the selected item.
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String param=adapterView.getItemAtPosition(i).toString(); //This will pickup the text of selected item.
                //Toast.makeText(getApplicationContext(),param, Toast.LENGTH_LONG).show();
                Intent in=new Intent(getApplicationContext(),editnote.class);
                in.putExtra("Selected",param);
                startActivity(in);
                finish();
            }
        });

    }
}