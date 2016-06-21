package devzstudio.com.maxcoupons;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dd.processbutton.FlatButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import devzstudio.com.maxcoupons.API.APIClass;
import devzstudio.com.maxcoupons.Adapters.CouponsList;
import devzstudio.com.maxcoupons.Adapters.HomeItems;
import devzstudio.com.maxcoupons.Data.MainDataHolder;
import it.gmariotti.cardslib.library.view.CardView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    TextView mainImg, mAndroidVersion, mDeviceModel, mInfoimg, mHacks;
    private TextView mTopApp,mCustomization;
    HomeItems homeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
       // abar.setHomeButtonEnabled(true);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

      //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-6606874228237363~6544079135");


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView1);
        adView.loadAd(new AdRequest.Builder().build());



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#0078ff"));
            window.setNavigationBarColor(Color.parseColor("#14191F"));
        }


        mInfoimg = (TextView) findViewById(R.id.infoimg);
        mTopApp = (TextView) findViewById(R.id.topappimg);
        mCustomization = (TextView) findViewById(R.id.customizationimg);
        mHacks = (TextView) findViewById(R.id.hacksimg);
        TextView dealsText = (TextView) findViewById(R.id.dealsText);


        Iconify.with(new FontAwesomeModule());
        Iconify.addIcons(mInfoimg);

        Iconify.with(new FontAwesomeModule());
        Iconify.addIcons(dealsText);

        Iconify.with(new FontAwesomeModule());
        Iconify.addIcons(mTopApp);



        Iconify.with(new FontAwesomeModule());
        Iconify.addIcons(mCustomization);

        Iconify.with(new FontAwesomeModule());
        Iconify.addIcons(mHacks);




        homeItems = new HomeItems(this);
        homeItems.init();

        //Set card in the cardView
        CardView cardView = (CardView) findViewById(R.id.homeCard);
        cardView.setVisibility(View.VISIBLE);
        cardView.setCard(homeItems);

        FlatButton viewMore = (FlatButton) findViewById(R.id.viewButton);
        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDataHolder.HOME_SELECTED_URL="http://www.maxlooter.com/forum";
                Intent intent=new Intent(MainActivity.this, WebExplorer.class);
                startActivity(intent);

            }
        });

    }

    public void Coupons(View view)
    {
        Intent intent = new Intent(this, CouponActivity.class);
        MainDataHolder.SELECTED_TILE_URL= APIClass.CATEGORY_COUPONS;
        MainDataHolder.CAT_ID=1;
        startActivity(intent);
    }

    public void Shopping(View view)
    {
        MainDataHolder.SELECTED_TILE_URL= APIClass.CATEGORY_SHOPPING;

        Intent intent = new Intent(this, CouponActivity.class);
        MainDataHolder.CAT_ID=2;

        startActivity(intent);
    }

    public void Cabs(View view)
    {
        MainDataHolder.SELECTED_TILE_URL= APIClass.CATEGORY_CABS;

        Intent intent = new Intent(this, CouponActivity.class);
        MainDataHolder.CAT_ID=3;
        startActivity(intent);
    }

    public void Fashion(View view)
    {
        MainDataHolder.SELECTED_TILE_URL= APIClass.CATEGORY_FASHION;

        Intent intent = new Intent(this, CouponActivity.class);
        MainDataHolder.CAT_ID=1;
        startActivity(intent);
    }
}
