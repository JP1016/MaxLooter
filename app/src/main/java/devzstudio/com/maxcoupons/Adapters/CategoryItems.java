package devzstudio.com.maxcoupons.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goebl.david.Webb;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import devzstudio.com.maxcoupons.API.APIClass;
import devzstudio.com.maxcoupons.CouponsListActivity;
import devzstudio.com.maxcoupons.Data.MainDataHolder;
import devzstudio.com.maxcoupons.NetworkError;
import devzstudio.com.maxcoupons.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by LostRider on 6/4/2016.
 */
public class CategoryItems extends CardWithList {


    List<ListObject> mObjects;
    Activity ac;
    ItemGateway gatewayo;
    Card parentcard;

    String url;
    public CategoryItems(Activity context)
    {
        super(context);
        ac = context;
     }
    @Override
    protected CardHeader initCardHeader() {
        return null;
    }

    @Override
    protected void initCard() {
        setEmptyViewViewStubLayoutId(R.layout.loading);

    }

    @Override
    protected List<ListObject> initChildren() {
        mObjects = new ArrayList<ListObject>();
        gatewayo = new ItemGateway(this);
        parentcard = this;
        new getItems().execute();
        return mObjects;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {
        TextView siteText = (TextView) convertView.findViewById(R.id.siteText);
        ImageView siteImage = (ImageView) convertView.findViewById(R.id.siteImage);

        Button viewBtn = (Button) convertView.findViewById(R.id.viewButton);


        final ItemGateway gatewayobject = (ItemGateway) object;

        if (gatewayobject.image!= "" || gatewayobject.image != null) {
            Picasso.with(ac).load(gatewayobject.image).into(siteImage);
        }

        siteText.setText(gatewayobject.name);

       viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDataHolder.SITE_ID=gatewayobject.id;
                MainDataHolder.SITE_IMAGE=gatewayobject.image;
                MainDataHolder.SITE_NAME=gatewayobject.name;

                Intent i = new Intent(ac, CouponsListActivity.class);
                ac.startActivity(i);
            }
        });
        return convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.mainsiteslist;
    }


    public class getItems extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                int len = jsonArray.length();


                for (int i = 0; i < len; i++) {

                    ItemGateway gatewayo = new ItemGateway(parentcard);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String img = jsonObject.getString("image");
                    String name = jsonObject.getString("name");
                    int category = jsonObject.getInt("cat");

                    gatewayo.image = img;
                    gatewayo.name = name;
                    gatewayo.cat = category;
                    gatewayo.id = id;

                    MainDataHolder.CAT_ID=id;
                    mObjects.add(gatewayo);

                    notifyDataSetChanged();

                }
            } catch (JSONException e) {
                Intent intent = new Intent(ac, NetworkError.class);
                ac.startActivity(intent);
            }
            catch(Exception e)
            {
                Intent intent = new Intent(ac, NetworkError.class);
                ac.startActivity(intent);
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            Webb webb = Webb.create();

            String response = webb.get(MainDataHolder.SELECTED_TILE_URL)
                    .ensureSuccess()
                    .asString().getBody();
            return response;

        }
}
    public class ItemGateway extends DefaultListObject {

        int id;
        String image;
        String name;
        int cat;



        public ItemGateway(Card parentCard) {
            super(parentCard);
            init();

        }

        private void init() {
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                    MainDataHolder.SITE_ID=id;

                    Intent i = new Intent(ac, CouponsListActivity.class);
                    ac.startActivity(i);
                }
            });
        }

    }
}
