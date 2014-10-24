package main;


import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipe.R;
import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class MainClientActivity extends Activity {

	private ArrayList<Card> gCheeseCards = new ArrayList<Card>();
	private ArrayList<String> gCheeseDirections = new ArrayList<String>(
			Arrays.asList(
					"Grate the cheese.",
					"Let the butter melt completely.",
					"Pile the grated cheese in an even layer over the entire surface of the bread.",
					"Let the cheese melt until it's almost entirely melted"));

	private CardScrollView csvCardsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		buildGrilledCheeseCards();

		csvCardsView = new CardScrollView(this);
		csaAdapter cvAdapter = new csaAdapter();
		csvCardsView.setAdapter(cvAdapter);
		csvCardsView.activate();
		setContentView(csvCardsView);
	}
	
	private class csaAdapter extends CardScrollAdapter {
		@Override
		public int getCount() {
				return gCheeseCards.size();
		}

		@Override
		public Object getItem(int position) {
				return gCheeseCards.get(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
				return gCheeseCards.get(position).getView();
		}

		@Override
		public int getPosition(Object o) {
			return 0;
		}
	}
	
	private void buildGrilledCheeseCards() {

		for (int i = 0; i < gCheeseDirections.size(); i++) {
			Card newCard = new Card(this);
			newCard.setImageLayout(Card.ImageLayout.LEFT);
			newCard.setText(gCheeseDirections.get(i));

			// Picking the right image and adding it
			Resources res = getResources();
			String mDrawableName = "gc" + (i + 1);
			int resID = res.getIdentifier(mDrawableName, "drawable",
					getPackageName());
			Drawable drawable = res.getDrawable(resID);
			newCard.addImage(drawable);
			gCheeseCards.add(newCard);
		}
	}

}
