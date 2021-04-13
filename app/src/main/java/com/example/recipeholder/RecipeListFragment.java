package com.example.recipeholder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeListFragment extends Fragment {

    private RecyclerView mRecipeRecyclerView;
    private RecipeAdapter mAdapter;

    //Why doesn't this class need an onCreate() method?
    //Rather than initiating an instance of Recipe, in this fragment we're initiating an instance of
    //RecyclerView. My best guess is that the RecyclerView library or the Layout Manager has already configured the
    //RecyclerView when it is initialized, so onCreate() doesn't have to do it.


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list_main, container, false);

        //recipe_recycle_view is the container view listed in fragment_recipe_list_main.xml
        mRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_recycler_view);
        mRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_recipe_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            //new_recipe here is the resource ID of the item in our menu resource
            case R.id.new_recipe:
                Recipe recipe = new Recipe();
                RecipeQueue.get(getActivity()).addRecipe(recipe);
                recipe.setName("Recipe #" + RecipeQueue.get(getActivity()).getRecipes().indexOf(recipe));
//                recipe.getName();
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Here, the base ViewHolder class holds on to the fragment_recipe_list view heirarchy (remember
        //that fragment_recipe_list is the container view for your list_item_recipe views which are the
        // views that have the two TextViews for name and date that appear in each line of the initial screen).
        // If you need this view heirarchy, you will find it in ViewHolder's itemView field

        //RecipeViewHolder is all skin and bones right now. But you will beef it up  as you give it more
        //work to do

        //It is here where we will change our code so that binding no longer occurs everytime a
        //view held by RecipeViewHolder is inflated. Rather, binding will occur when the
        //onBindViewHolder() method in Adapter is called (see below)

        private TextView mTitleTextView;
        private TextView mDateTextView;

        private Recipe mRecipe;


        public RecipeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));

            //This code makes RecipeViewHolder the OnClickListener for its view
            itemView.setOnClickListener(this);


            //itemViews are objects of RecycleView.ViewHolder
            mTitleTextView = (TextView) itemView.findViewById(R.id.recipe_title);
            mDateTextView = (TextView)  itemView.findViewById(R.id.recipe_date);
        }

        public void bind (Recipe recipe) {
            mRecipe = recipe;
            mTitleTextView.setText(mRecipe.getName());
            mDateTextView.setText(mRecipe.getDate().toString());

        }

        @Override
        public void onClick(View view) {
            //starts SingleFragmentActivity (via MainActivity) from RecipeListFragment
            //in this case, SingleFragmentActivity creates a new RecipeFragmentNew
//            Intent intent = new Intent(getActivity(), MainActivity.class);

            //We're going to create the intent using the newIntent() located in MainActivity instead
            //This intent is the key to starting MainActivity when a user clicks on a ViewHolder
            //Note:The activity used to define the new Intent is the one being started
            Intent intent = MainActivity.newIntent(getActivity(), mRecipe.getID());
            startActivity(intent);
        }
    }

    //With the RecipeViewHolder defined, create the Adapter.

    //When RecyclerView nneds to display a new ViewHolder or connect a Recipe object to an existing
    //ViewHolder, it will ask the adapter for help by calling a method on it. The RecyclerView itself
    //will not know anything about the Recipe object, but the Adapter will know all of its details

    //So, RecyclerView.Adapter is an interface that requires that you override the three methods

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

        private List<Recipe> mRecipes;

        //Clearly, recipes is the same list of recipes from our RecipeQueue singleton. The adaptor
        //retrieves it from another part of the program ????

        public RecipeAdapter(List<Recipe> recipes) {
            mRecipes = recipes;
        }


        //onCreateViewHolder is called by the RecyclerView when it needs a new ViewHolder to display
        //an item with. In this method, you create a LayoutInflater and use it to construct a new
        //RecipeViewHolder

        //Note: this code creates an Adapter class and defines its characteristics, but you still
        //will need to connect it an instance of this class to your RecyclerView in order for it
        //to work.


        @Override
        public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new RecipeViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecipeViewHolder holder, int position) {
            Recipe recipe = mRecipes.get(position);
            holder.bind(recipe);

        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }

        public void setRecipes (List<Recipe> recipes) {
            mRecipes = recipes;
        }
    }

    private void updateUI() {
        //The RecipeQueue.get() method retrieves your Recipe singleton (or creates one if needed)
        RecipeQueue recipeQueue = RecipeQueue.get(getActivity());

        //This code retrieves our list of Recipes from the singleton
        List<Recipe> recipes = recipeQueue.getRecipes();

        //mAdapter is a global variable of type RecipeAdapter defined above. This variable accepts
        //a new RecipeAdapter and takes in your list of Recipes from the singleton

        if(mAdapter == null) {
            mAdapter = new RecipeAdapter(recipes);

            //Finally, this code sets the Adapter for your RecyclerView to be the Adapter with the
            //characteristics defined in the previous three lines of code. This code effectively links
            //your adapter to your RecyclerView
            mRecipeRecyclerView.setAdapter(mAdapter);


        } else {
            mAdapter.setRecipes(recipes);
            mAdapter.notifyDataSetChanged();
        }

    }

}
