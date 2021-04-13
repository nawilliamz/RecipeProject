package com.example.recipeholder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity_fragment is generic, so it can serve as container view for multiple different fragments
        setContentView(R.layout.activity_fragment);

        //fragment_container is the FrameLayout container view defined in activity_fragment
        FragmentManager fm = getSupportFragmentManager();

        //Here, I'm setting fragment equal to the fragment found in the container view
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);


        //If fragment is not already in fragment_container, then we will create a new fragment for it and
        //add it to fragment_container
        if (fragment == null) {

            //createFragment() is abstract so must be implemented
            //by the class that extends the SingleFragmentActivity class
            //Subclasses of SingleFragmentActivity will implement this method to return an instance
            //of the fragment that the activity is hosting
            fragment = createFragment();
            fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
        }

    }

}
