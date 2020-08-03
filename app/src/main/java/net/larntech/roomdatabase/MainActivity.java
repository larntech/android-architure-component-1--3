package net.larntech.roomdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UsersAdapter.ClickListener {

    RecyclerView recyclerView;
    ViewModel userViewModel;
    UsersAdapter usersAdapter;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.btnNewUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        usersAdapter = new UsersAdapter(this);

        userViewModel.getAllUsers().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                if(users.size()> 0){
                    usersAdapter.setData(users);
                    recyclerView.setAdapter(usersAdapter);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

    }

    public void addUser(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        Button btnAddUser = view.findViewById(R.id.addUsersBtn);
        final EditText edUsers = view.findViewById(R.id.edUser);
        TextView tvDetails = view.findViewById(R.id.tvDetails);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edUsers.getText() != null){
                    String username = edUsers.getText().toString().trim();
                    Users users = new Users();
                    users.setUsername(username);

                    userViewModel.insertUser(users);

                    alertDialog.dismiss();

                }
            }
        });

        alertDialog.show();



    }


    @Override
    public void updateClicked(Users users) {
        updateUser(users);

    }

    @Override
    public void deleteClicked(Users users) {

        userViewModel.deleteUser(users);

    }


    public void updateUser(final Users users){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        Button btnAddUser = view.findViewById(R.id.addUsersBtn);
        final EditText edUsers = view.findViewById(R.id.edUser);
        TextView tvDetails = view.findViewById(R.id.tvDetails);

        edUsers.setText(users.getUsername());

        tvDetails.setText("Update Details");
        btnAddUser.setText("Update");

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edUsers.getText() != null){
                    String username = edUsers.getText().toString().trim();
                    users.setUsername(username);
                    userViewModel.updateUser(users);
                    alertDialog.dismiss();

                }
            }
        });

        alertDialog.show();



    }
}