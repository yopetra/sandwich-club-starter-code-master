package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView tvAlsoKnownTitle;
    TextView tvAlsoKnownAs;
    TextView tvIngrediencesTitle;
    TextView tvIngredients;
    TextView tvPlaceOfOringTitle;
    TextView tvPlaceOfOring;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView ingredientsIv = findViewById(R.id.image_iv);
        tvAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        tvAlsoKnownTitle = (TextView) findViewById(R.id.tv_also_know_title);
        tvIngrediencesTitle = (TextView) findViewById(R.id.tv_ingrediences_title);
        tvIngredients = (TextView) findViewById(R.id.ingredients_tv);
        tvPlaceOfOringTitle = (TextView) findViewById(R.id.tv_place_of_oring_title);
        tvPlaceOfOring = (TextView) findViewById(R.id.tv_place_of_orign);
        tvDescription = (TextView) findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Fill TextView alsoKnown
        List<String> alsoKnown = sandwich.getAlsoKnownAs();

        if(alsoKnown.size() == 0){
            tvAlsoKnownAs.setVisibility(View.INVISIBLE);
            tvAlsoKnownTitle.setVisibility(View.INVISIBLE);
        }else{
            for(String item : alsoKnown){
                tvAlsoKnownAs.append(item + " ");
            }
        }

        // Fill TextView ingredientes
        List<String> ingrediences = sandwich.getIngredients();

        if(ingrediences.size() == 0){
            tvIngrediencesTitle.setVisibility(View.INVISIBLE);
            tvIngredients.setVisibility(View.INVISIBLE);
        }else{
            for(String item : ingrediences){
                tvIngredients.append(item + " ");
            }
        }

        // Fill TextView place of orign
        if(TextUtils.isEmpty(sandwich.getPlaceOfOrigin()) ){
            tvPlaceOfOringTitle.setVisibility(View.INVISIBLE);
            tvPlaceOfOring.setVisibility(View.INVISIBLE);
        }else{
            tvPlaceOfOringTitle.setVisibility(View.VISIBLE);
            tvPlaceOfOring.setText(sandwich.getPlaceOfOrigin());
        }

        //Fill TextView description
        tvDescription.setText(sandwich.getDescription());
    }
}
