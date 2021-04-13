package com.example.recipeholder;

import androidx.fragment.app.Fragment;

public class RecipeListActivity extends SingleFragmentActivity {

    //You app will now launch RecipeListActivity instead of MainActivity. This has been changed in
    //the manifest. This class returns the new RecipeListFragment that contains RecyclerView and
    //our list of Recipe objects
    //At this time, RecipeListFragment is empty

    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }


}
