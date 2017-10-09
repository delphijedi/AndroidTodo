package com.example.luke.todo;

/**
 * Created by luke on 08/10/17.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
import java.util.List;


    public class CustomAdapter extends ArrayAdapter<Todo> {

        public CustomAdapter(Context context, int resource, List<Todo> todo) {
            super(context, resource, todo);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.activity_view_todolist, parent, false);
            }

            Todo student = getItem(position);

            if (student != null) {
                TextView tvTodoId = (TextView) v.findViewById(R.id.Id);
                TextView tvTodoName = (TextView) v.findViewById(R.id.Name);
                tvTodoId.setText( Integer.toString(student.id));
                tvTodoName.setText(student.name);
            }

            return v;
        }
}
