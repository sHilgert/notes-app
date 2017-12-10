package com.sergiohilgert.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class NoteEditorActivity extends AppCompatActivity {
  int id;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_note_editor);
    final EditText editText = (EditText) findViewById(R.id.editText);
    Intent intent = getIntent();
    id = intent.getIntExtra("id", -1);
    
    if(id != -1) {
      editText.setText(MainActivity.notes.get(id));
    }else{
      MainActivity.notes.add("");
      MainActivity.adapter.notifyDataSetChanged();
      id = MainActivity.notes.size()-1;
    }
    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        
      }
  
      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        MainActivity.notes.set(id, String.valueOf(charSequence));
        MainActivity.adapter.notifyDataSetChanged();
        MainActivity.update_database();
      }
  
      @Override
      public void afterTextChanged(Editable editable) {
        
      }
    });
  }
  
}
