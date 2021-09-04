package com.raghav.raghavstudentgiri.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.raghav.raghavstudentgiri.R;
import com.raghav.raghavstudentgiri.retrofit.TodoApi;
import com.raghav.raghavstudentgiri.model.Todo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUpdateTodo extends AppCompatActivity {

    private TextInputEditText userIdEditText, titleEditText;
    private TextInputLayout userTIL, titleTIL;
    private RadioGroup statusRadio;
    private Button addbtn;
    private int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        Intent intent = getIntent();


        userIdEditText = (TextInputEditText) findViewById(R.id.userId);
        titleEditText = (TextInputEditText) findViewById(R.id.title);

        if (intent.getParcelableExtra("item") != null) {
            Todo item = intent.getParcelableExtra("item");
            id=item.getId();
            userIdEditText.setText(String.valueOf(item.getUserId()));
            titleEditText.setText(item.getTitle());
        }

        userTIL = (TextInputLayout) findViewById(R.id.userIdTIL);
        titleTIL = (TextInputLayout) findViewById(R.id.titleTIL);
        statusRadio = (RadioGroup) findViewById(R.id.statusRadioGroup);

        addbtn = (Button) findViewById(R.id.btnAdd);
        addbtn.setText(intent.getStringExtra("type"));
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    if (intent.getStringExtra("type").equalsIgnoreCase("add"))
                        addTodoItem();
                    else
                        updateTodoItem();
                }
            }
        });

    }

    private void addTodoItem() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while we add your item..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        boolean status = false;
        if (statusRadio.getCheckedRadioButtonId() == R.id.completedRadio) status = true;

        Todo item = new Todo(Integer.parseInt(userIdEditText.getText().toString()), titleEditText.getText().toString(), status);
        Call<Todo> call = TodoApi.getService().addTodoItem(item);
        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Toast.makeText(AddUpdateTodo.this, "Successfully Added Item!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(AddUpdateTodo.this, "Some error occured", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void updateTodoItem() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while we update your item..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        boolean status = false;
        if (statusRadio.getCheckedRadioButtonId() == R.id.completedRadio) status = true;

        Todo item = new Todo(id,Integer.parseInt(userIdEditText.getText().toString()), titleEditText.getText().toString(), status);
        Call<Todo> call = TodoApi.getService().updateItem(item);
        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Toast.makeText(AddUpdateTodo.this, "Successfully Updated Item!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(AddUpdateTodo.this, "Some error occured", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private boolean validate() {
        if (userIdEditText.getText().toString().isEmpty()) {
            userTIL.setErrorEnabled(true);
            userTIL.setError("Required");
            return false;
        }
        if (titleEditText.getText().toString().isEmpty()) {
            titleTIL.setErrorEnabled(true);
            titleTIL.setError("Required");
            return false;
        }
        return true;
    }
}