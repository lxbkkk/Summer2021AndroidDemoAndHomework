package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.byted.camp.todolist.beans.Priority;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract.TodoNote;
import com.byted.camp.todolist.db.TodoDbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private RadioGroup radioGroup;
    private AppCompatRadioButton lowRadio;

    private TodoDbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);

        dbHelper = new TodoDbHelper(this);
        database = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();

        /*
        File userContent = new File("userContent");
        try {
            FileInputStream inStream = new FileInputStream(userContent);
            String StoreContent = new String();
            byte[] ByteString = new byte[1024*10];
            inStream.read(ByteString);
            StoreContent = ByteString.toString();

            Log.d("storecontent","------ read + " + StoreContent + " ------");
            if(!StoreContent.equals("")){
                editText.setText(StoreContent);
            }
            inStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        SharedPreferences sp = getSharedPreferences("usercontent",Context.MODE_PRIVATE);

        String contentstring = sp.getString("content","");

        SharedPreferences.Editor edit = sp.edit();

        edit.remove("content");

        Log.d("spcontent","------ " + contentstring + " ------");

        editText.setText(contentstring);

        Log.d("spcontent","------ " + editText.toString() + " ------");

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }
        radioGroup = findViewById(R.id.radio_group);
        lowRadio = findViewById(R.id.btn_low);
        lowRadio.setChecked(true);

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim(),
                        getSelectedPriority());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        /*
        File userContent = new File("userContent");
        try {
            FileOutputStream outStream = new FileOutputStream(userContent);
            String StoreContent = editText.getText().toString();
            Log.d("storecontent","------ write + " + StoreContent + " ------");
            outStream.write(StoreContent.getBytes());
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        SharedPreferences sp = getSharedPreferences("usercontent",Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sp.edit();

        edit.putString("content",editText.getText().toString());
        Log.d("spcontent","------ " + editText.toString() + " ------");

        edit.commit();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        /*
        File userContent = new File("userContent");
        try {
            FileOutputStream outStream = new FileOutputStream(userContent);
            String StoreContent = editText.getText().toString();
            Log.d("storecontent","------ write + " + StoreContent + " ------");
            outStream.write(StoreContent.getBytes());
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        SharedPreferences sp = getSharedPreferences("usercontent",Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sp.edit();

        edit.putString("content",editText.getText().toString());
        Log.d("spcontent","------ " + editText.toString() + " ------");

        edit.commit();

        super.onDestroy();
        database.close();
        database = null;
        dbHelper.close();
        dbHelper = null;
    }

    @Override
    protected void onPause() {
        SharedPreferences sp = getSharedPreferences("usercontent",Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sp.edit();

        edit.putString("content",editText.getText().toString());
        Log.d("spcontent","------ " + editText.toString() + " ------");

        edit.commit();

        super.onPause();
    }

    private boolean saveNote2Database(String content, Priority priority) {
        // TODO: 2021/7/19 8. 这里插入数据库
        ContentValues values = new ContentValues();

        values.put(TodoNote.DATE, System.currentTimeMillis());
        values.put(TodoNote.STATE, State.TODO.intValue);
        values.put(TodoNote.CONTENT,content);
        values.put(TodoNote.PRIORITY,priority.intValue);

        long newRowId = database.insert(TodoNote.TABLE_NAME,null,values);

        return true;
    }

    private Priority getSelectedPriority() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.btn_high:
                return Priority.High;
            case R.id.btn_medium:
                return Priority.Medium;
            default:
                return Priority.Low;
        }
    }
}
