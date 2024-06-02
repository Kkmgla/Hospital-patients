package com.example.hospital_patients;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.RowId;
import java.util.ArrayList;
import java.util.Objects;


public class ListFragment extends Fragment implements UserAdapter.OnItemClickListener{

    private UserAdapter userAdapter;
    private ArrayList<User> userArrayList;
    private DatabaseReference databaseReference;
    private SearchView searchView;
    private Dialog dialog;
    private static final String USER_KEY = "user", DB_URL = "https://hospital-patients-a3804-default-rtdb.europe-west1.firebasedatabase.app/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        searchView = view.findViewById(R.id.searchView);

        databaseReference = FirebaseDatabase.getInstance(DB_URL).getReference(USER_KEY);
        userArrayList = new ArrayList<>();
        userAdapter = new UserAdapter(userArrayList);
        getDataFromDB();

        RecyclerView recyclerView = view.findViewById(R.id.rvUserList);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        userAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "itemclicked", Toast.LENGTH_SHORT).show();
                dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.user_info);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.dialog_bg));
                dialog.setCancelable(false);

                User selectedUser = userArrayList.get(position);

                TextView tvName, tvAge, tvAnamnesis, tvAnamnesisFull, tvSex, tvEmail, tvBack;
                tvName = dialog.findViewById(R.id.tvUserInfoName);
                tvAge = dialog.findViewById(R.id.tvUserInfoAge);
                tvAnamnesis = dialog.findViewById(R.id.tvUserInfoAnamnesis);
                tvAnamnesisFull = dialog.findViewById(R.id.tvUserInfoAnamnesisFull);
                tvSex = dialog.findViewById(R.id.tvUserInfoSex);
                tvEmail = dialog.findViewById(R.id.tvUserInfoEmail);
                tvBack = dialog.findViewById(R.id.tvUserInfoBack);


                tvName.setText(selectedUser.getFirstname() + " " + selectedUser.getLastname());
                tvAge.setText(String.valueOf(selectedUser.getAge()));
                tvSex.setText(selectedUser.getSex());
                tvAnamnesis.setText(selectedUser.getAnamnesis());
                tvAnamnesisFull.setText(selectedUser.getAnamnesisFull());
                tvEmail.setText(selectedUser.getEmail());

                dialog.show();
                tvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.filter(newText);
                return false;
            }
        });

        return view;
    }


    @Override
    public void onItemClick(int position) {
        //Toast.makeText(getContext(), "itemclicked", Toast.LENGTH_SHORT).show();

    }
    private void getDataFromDB() {
        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!userArrayList.isEmpty()) {
                    userArrayList.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    assert user != null;
                    user.setId(dataSnapshot.getKey());
                    userArrayList.add(user);
                }
                userAdapter.updateData(userArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }
}