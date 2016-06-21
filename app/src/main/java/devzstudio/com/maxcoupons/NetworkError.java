package devzstudio.com.maxcoupons;

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

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class NetworkError extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_erro);
        getSupportActionBar().setTitle("Error!!");

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#060F1E"));
        }
        TextView tc = (TextView) findViewById(R.id.textView6);
        Iconify.with(new FontAwesomeModule());
        Iconify.addIcons(tc);

    }
}
