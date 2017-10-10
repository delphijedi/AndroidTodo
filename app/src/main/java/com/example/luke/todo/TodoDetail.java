package com.example.luke.todo;

/**
 * Created by luke on 06/10/17.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TodoDetail extends AppCompatActivity implements View.OnClickListener{
    Button btnCreate, btnSave;
    Button btnList;
    Button btnDelete;
    Button btnClose;
    EditText editTextTodoItem;
    CheckBox chkbxIsComplete;

    private int _Id=0;
    RestService restService;


    protected void OnCreate(Bundle SavedState)
    {
        super.onCreate(SavedState);
        restService = new RestService();
        setContentView(R.layout.activity_view_todolist);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnCreate = (Button) findViewById(R.id.btncreate);
        btnList = (Button) findViewById(R.id.btnlist);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        editTextTodoItem = (EditText) findViewById(R.id.editTextTodoItem);
        chkbxIsComplete = (CheckBox) findViewById(R.id.chkbxIsComplete);


        btnSave.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        _Id =0;
        Intent intent = getIntent();
        _Id =intent.getIntExtra("Id", 0);
        if (_Id>0) {
            restService.getService().getTodoById(_Id, new Callback<Todo>() {
                @Override
                public void success(Todo todo, Response response) {

                    editTextTodoItem.setText(String.valueOf(todo.name));
                    chkbxIsComplete.setChecked(todo.isComplete);

                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(TodoDetail.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete)==v){
            restService.getService().deleteTodoById(_Id, new Callback<Todo>() {
                @Override
                public void success(Todo todo, Response response) {
                    Toast.makeText(TodoDetail.this, "Student Record Deleted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(TodoDetail.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                }

            });

            finish();
        }else if (v== findViewById(R.id.btnClose)){
            finish();
        }else if (findViewById(R.id.btnSave)==v){

            Todo todo=new Todo();
            Integer status =0;
            todo.name= editTextTodoItem.getText().toString();
            todo.isComplete = chkbxIsComplete.isChecked();
            todo.id = _Id;

            if (_Id == 0) {
                restService.getService().addTodoItem(todo, new Callback<Todo>() {
                    @Override
                    public void success(Todo todo, Response response) {
                        Toast.makeText(TodoDetail.this, "New Todo Item Inserted.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(TodoDetail.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                restService.getService().updateTodobyId(_Id,todo , new Callback<Todo>() {
                    @Override
                    public void success(Todo todo, Response response) {
                        Toast.makeText(TodoDetail.this, "Todo Record updated.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(TodoDetail.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });
            }




        }
    }
}
