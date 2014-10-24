package main;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.recipe.R;
import com.google.android.glass.app.Card;
import com.google.android.glass.view.WindowUtils;
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

	
	private ArrayList<Card> tSandwichCards = new ArrayList<Card>();
	private ArrayList<String> tSandwichCardsDirections = new ArrayList<String>(
			Arrays.asList("Transfer the tuna to a bowl.",
					"Add the other ingredients to the tuna.",
					"Mix together. And add some mayo.", "Spread onto a bread."));

	private String currentDish = "Grilled Cheese";

	private CardScrollView csvCardsView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);


		buildGrilledCheeseCards();
		buildTunaSandwichCards();

		csvCardsView = new CardScrollView(this);
		csaAdapter cvAdapter = new csaAdapter();
		csvCardsView.setAdapter(cvAdapter);
		csvCardsView.activate();
		setContentView(csvCardsView);
	}

	private class csaAdapter extends CardScrollAdapter {
		@Override
		public int getCount() {
			if (currentDish.equals("Grilled Cheese")) {
				return gCheeseCards.size();
			} else {
				return tSandwichCards.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (currentDish.equals("Grilled Cheese")) {
				return gCheeseCards.get(position);
			} else {
				return tSandwichCards.get(position);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (currentDish.equals("Grilled Cheese")) {
				return gCheeseCards.get(position).getView();
			} else {
				return tSandwichCards.get(position).getView();
			}
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
	
	private void buildTunaSandwichCards() {

		for (int i = 0; i < tSandwichCardsDirections.size(); i++) {
			Card newCard = new Card(this);
			newCard.setImageLayout(Card.ImageLayout.LEFT);
			newCard.setText(tSandwichCardsDirections.get(i));

			Resources res = getResources();
			String mDrawableName = "tuna" + (i + 1);
			int resID = res.getIdentifier(mDrawableName, "drawable",
					getPackageName());
			Drawable drawable = res.getDrawable(resID);
			newCard.addImage(drawable);
			tSandwichCards.add(newCard);
		}

	}

	// Enable "ok glass" to inflate the menu
	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS
				|| featureId == Window.FEATURE_OPTIONS_PANEL) {

			getMenuInflater().inflate(R.menu.main, menu);

			return true;
		}
		return super.onCreatePanelMenu(featureId, menu);
	}
	
	public void showDish(String dish) {
		if (dish.equals("Grilled Cheese")) {
			currentDish = dish;
		} else {
			currentDish = dish;
		}

		csvCardsView = new CardScrollView(this);
		csaAdapter cvAdapter = new csaAdapter();
		csvCardsView.setAdapter(cvAdapter);
		csvCardsView.activate();
		setContentView(csvCardsView);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (featureId == WindowUtils.FEATURE_VOICE_COMMANDS
				|| featureId == Window.FEATURE_OPTIONS_PANEL) {
			switch (item.getItemId()) {
			case R.id.dish_1:
				showDish("Grilled Cheese");
				break;
			case R.id.dish_2:
				showDish("Tuna Sandwich");
				break;
			}
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	// Enable Tap for inflating the menu
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			openOptionsMenu();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
