package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final Map<Integer, Class<? extends Fragment>> fragmentMap = new HashMap<>();

    static {
        fragmentMap.put(R.id.navigation_tusach, BookselfFragment.class);
        fragmentMap.put(R.id.navigation_thuvien, LibraryFragment.class);
        fragmentMap.put(R.id.navigation_taikhoan, AccountFragment.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set default fragment (Library)
        if (savedInstanceState == null) {
            replaceFragment(new LibraryFragment());
        }

        // Set listener for item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Class<? extends Fragment> fragmentClass = fragmentMap.get(item.getItemId());
            if (fragmentClass != null) {
                try {
                    replaceFragment(fragmentClass.newInstance());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}