package com.example.favouritecaller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favouritecaller.Adapters.MainActivityAdapter;
import com.example.favouritecaller.Database.ContactModel;
import com.example.favouritecaller.ViewModel.ContactViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity  {
    public static final String TAG = MainActivity.class.getName();

    RecyclerView savedRv;
    MainActivityAdapter savedAdapter;

    ContactViewModel mContactViewModel;


    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setElevation(8.0f);

        savedRv = findViewById(R.id.main_recycler_view);
        mContactViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(ContactViewModel.class);

        savedRv.setLayoutManager(new GridLayoutManager(this,3));
        savedAdapter = new MainActivityAdapter(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            getDataFromDb();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent contact_list_intent = new Intent(this,ContactListActivity.class);
        startActivity(contact_list_intent);

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getDataFromDb();
            }
        }
    }

    private void getDataFromDb() {

        mContactViewModel.getAllContact().observe(this, contactModels -> {
            savedAdapter.getList(contactModels);
            savedRv.setAdapter(savedAdapter);
        });
    }

}