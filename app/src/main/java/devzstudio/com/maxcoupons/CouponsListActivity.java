package devzstudio.com.maxcoupons;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import devzstudio.com.maxcoupons.Adapters.CategoryItems;
import devzstudio.com.maxcoupons.Adapters.CouponsList;
import it.gmariotti.cardslib.library.view.CardView;

public class CouponsListActivity extends AppCompatActivity {

    CouponsList categoryItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons_list);


        final ActionBar abar = getSupportActionBar();
        //abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_kitkat_selector));//line under the action bar
        View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        //TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        //textviewTitle.setText("Max Coupons");
        abar.setCustomView(viewActionBar, params);
        abar.setHomeButtonEnabled(true);

        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        // abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.drawable.logo);

        categoryItems = new CouponsList(this);
        categoryItems.init();

        //Set card in the cardView
        CardView cardView = (CardView) findViewById(R.id.couponsListCard);
        cardView.setVisibility(View.VISIBLE);
        cardView.setCard(categoryItems);

    }
}
