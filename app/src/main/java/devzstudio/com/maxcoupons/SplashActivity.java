package devzstudio.com.maxcoupons;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.goebl.david.Webb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import devzstudio.com.maxcoupons.API.APIClass;
import devzstudio.com.maxcoupons.Data.MainDataHolder;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        try {

            if (isNetworkAvailable()) {
                new getItems().execute();




            } else {

                Intent intent = new Intent(this, NetworkError.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error Occured in Fetching Required Data!", Toast.LENGTH_SHORT).show();
        } finally {


        }



    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }


    public class getItems extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPostExecute(String s) {
            MainDataHolder.JSON_STRING=s;

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }

        @Override
        protected String doInBackground(Void... params) {

            Webb webb = Webb.create();

            String response = webb.get(APIClass.HOME_CARD)
                    .ensureSuccess()
                    .asString().getBody();
            return response;

        }
    }


}
