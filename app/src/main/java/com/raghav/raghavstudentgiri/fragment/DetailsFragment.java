package com.raghav.raghavstudentgiri.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.raghav.raghavstudentgiri.R;
import com.raghav.raghavstudentgiri.model.Todo;


public class DetailsFragment extends Fragment {

    private Todo todoItem;
    private TextView idTV, userIdTV, titleTV, statusTV;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            todoItem = bundle.getParcelable("item");
        }

        idTV = (TextView) view.findViewById(R.id.idTextview);
        userIdTV = (TextView) view.findViewById(R.id.userIdTextview);
        titleTV = (TextView) view.findViewById(R.id.title);
        statusTV = (TextView) view.findViewById(R.id.status);

        idTV.setText(String.format("id: %s", String.valueOf(todoItem.getId())));
        userIdTV.setText(String.format("userId: %s", String.valueOf(todoItem.getUserId())));
        titleTV.setText(String.format("Title: %s", todoItem.getTitle()));

        if (todoItem.isCompleted())
            statusTV.setText(String.format("Status: %s", "Completed"));
        else
            statusTV.setText(String.format("Status: %s", "Pending"));

        return view;
    }
}