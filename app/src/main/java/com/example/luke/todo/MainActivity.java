package com.example.luke.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView listView;
    Button btnlist;
    Button btncreate;
    RestService restService;
    TextView  todo_Id;
    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_main);
        restService = new RestService();
        setContentView(R.layout.activity_main);
        btnlist = (Button) findViewById(R.id.btnlist);
        btnlist.setOnClickListener(this);

        btncreate= (Button) findViewById(R.id.btncreate);
        btncreate.setOnClickListener(this);
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {

        super.onResume();
        refreshScreen();
    }

    @Override
    public void onClick(View v) {
        if (v== findViewById(R.id.btncreate)){

            Intent intent = new Intent(this,TodoDetail.class);
            intent.putExtra("_Id",0);
            startActivity(intent);

        }else {
            // You should use refreshScreen() instead, just show you an easier method only :P
            refreshScreen_SimpleWay();
        }
    }

    private void refreshScreen(){

        //Call to server to grab list of student records. this is a asyn
        restService.getService().getTodo(new Callback<List<Todo>>() {
            @Override
            public void success(List<Todo> todoList, Response response) {
                ListView lv = (ListView) findViewById(R.id.lstvwTodo);

                CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, R.layout.activity_view_todolist, todoList);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        todo_Id = (TextView) view.findViewById(R.id.Id);
                        String _Id = todo_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), TodoDetail.class);
                        objIndent.putExtra("Id", Integer.parseInt(_Id));
                        startActivity(objIndent);
                    }
                });
                lv.setAdapter(customAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    //I Don't like the idea of loop the List<Student> and put data into ArrayList, but I DON'T WANT
    //made a complicated tutorial also:D, So I showed you this simple way but is not encourage
    //just to make you easy to understand without knowing the CustomAdapter method
    private void refreshScreen_SimpleWay(){

        restService.getService().getTodo(new Callback<List<Todo>>() {
            @Override
            public void success(List<Todo> todoitems, Response response) {
                ListView lv = (ListView) findViewById(R.id.lstvwTodo);


                ArrayList<HashMap<String, String>> todoList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < todoitems.size(); i++) {
                    HashMap<String, String> todo = new HashMap<String, String>();
                    todo.put("id", String.valueOf(todoitems.get(i).id));
                    todo.put("name", String.valueOf(todoitems.get(i).name));

                    todoList.add(todo);
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        todo_Id = (TextView) view.findViewById(R.id.Id);
                        String todoId = todo_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(),TodoDetail.class);
                        objIndent.putExtra("Id", Integer.parseInt( todoId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter(MainActivity.this, todoList, R.layout.activity_view_todolist, new String[]{"id", "name"}, new int[]{R.id.Id, R.id.Name});
                lv.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
