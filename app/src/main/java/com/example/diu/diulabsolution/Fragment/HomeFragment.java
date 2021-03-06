package com.example.diu.diulabsolution.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.diu.diulabsolution.Activity.ReportActivity;
import com.example.diu.diulabsolution.Activity.SignInActivity;
import com.example.diu.diulabsolution.Adapter.RoomAdapter;
import com.example.diu.diulabsolution.Model.Room;
import com.example.diu.diulabsolution.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {

    private View rootView;
    private GridView gridView;
    private AppCompatButton logoutBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFiresStore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_home, container, false);
        mAuth=FirebaseAuth.getInstance();
        findView();
        setGridView();
        logout();
        return rootView;
    }

    private void findView(){
        logoutBtn=(AppCompatButton)rootView.findViewById(R.id.logout_btn);
        gridView=(GridView)rootView.findViewById(R.id.gridView);
    }
    private void setGridView(){
        final String userId=mAuth.getCurrentUser().getUid();
        final ArrayList<Room> rooms=new ArrayList<Room>();
        rooms.add(new Room("Room Id","605",R.drawable.room));
        rooms.add(new Room("Room Id","404",R.drawable.room));
        rooms.add(new Room("Room Id","405",R.drawable.room));
        rooms.add(new Room("Room Id","304",R.drawable.room));

        // Create an {@link WordAdapter}, whose data source is a grid of {@link Word}s. The
        // adapter knows how to create grid items for each item in the grid.
        RoomAdapter roomAdapter =new RoomAdapter(getActivity(),R.layout.room_grid_view,rooms);
        gridView.setAdapter(roomAdapter);
        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String roomName=rooms.get(position).getRoomName();
                        Intent intent=new Intent(getContext(),ReportActivity.class);
                        intent.putExtra("user_id",userId);
                        intent.putExtra("room_id",roomName);
                       startActivity(intent);
                    }
                }
        );

    }

    private void logout(){
       logoutBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAuth.signOut();
              // upateUserLoginStatus();
               Intent intent=new Intent(getContext(),SignInActivity.class);
               startActivity(intent);
           }
       });
    }

    private void upateUserLoginStatus(String userType) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token = null;
                HashMap<String, Object> updateInfo = new HashMap<>();
                updateInfo.put("login_status", false);
                updateInfo.put("token", token);

                String userId = mAuth.getCurrentUser().getUid();

                mFiresStore.collection("users").document(userId).update(updateInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }




}
