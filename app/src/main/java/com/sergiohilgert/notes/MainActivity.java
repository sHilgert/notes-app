package com.sergiohilgert.notes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  
  ListView notesListView;
  static ArrayList<String> notes;
  static SharedPreferences sharedPreferences;
  static ArrayAdapter<String> adapter;
  
  void remove_note(final int id){
    notes.remove(id);
    adapter.notifyDataSetChanged();
    update_database();
  }
  
  static void update_database(){
    try {
      sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    notesListView = (ListView) findViewById(R.id.notes_list);
    sharedPreferences = getSharedPreferences("notesApp",Context.MODE_PRIVATE);
    try {
      notes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("com.sergiohilgert.notes", ObjectSerializer.serialize(new ArrayList<String>())));
    } catch (IOException e) {
      e.printStackTrace();
    }
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notes);
    notesListView.setAdapter(adapter);
    notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        new AlertDialog.Builder(MainActivity.this)
            .setTitle("Delete item")
            .setMessage("Do you want to delete this item?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                remove_note(position);
              }
            })
            .setNegativeButton("No", null)
            .show();
        return true;
      }
    });
    notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
        intent.putExtra("id", i);
        startActivity(intent);
      }
    });
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = this.getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    
    if(item.getItemId() == R.id.add_note) {
      Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
      startActivity(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
    
  }
}
