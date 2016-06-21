package devzstudio.com.maxcoupons.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goebl.david.Webb;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import devzstudio.com.maxcoupons.API.APIClass;
import devzstudio.com.maxcoupons.Data.MainDataHolder;
import devzstudio.com.maxcoupons.NetworkError;
import devzstudio.com.maxcoupons.R;
import devzstudio.com.maxcoupons.WebExplorer;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

/**
 * Created by LostRider on 6/5/2016.
 */
public class HomeItems extends CardWithList {

    List<CardWithList.ListObject> mObjects;
    Activity ac;
    HomeGateway gatewayo;
    Card parentcard;

    public HomeItems(Activity context)
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
        gatewayo = new HomeGateway(this);
        parentcard = this;
        new getItems().execute();
        return mObjects;    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {
        ImageView homeImage = (ImageView) convertView.findViewById(R.id.homeImage);
        TextView homeTitle=(TextView) convertView.findViewById(R.id.homeTitle);


        final HomeGateway gatewayobject = (HomeGateway) object;

        if (gatewayobject.image!= "" || gatewayobject.image != null) {
            Picasso.with(ac).load(gatewayobject.image).into(homeImage);
        }

        homeTitle.setText(gatewayobject.name);

        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDataHolder.HOME_SELECTED_URL=gatewayobject.link;
                Intent intent=new Intent(ac, WebExplorer.class);
                ac.startActivity(intent);
            }
        });

        homeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainDataHolder.HOME_SELECTED_URL=gatewayobject.link;
                Intent intent=new Intent(ac, WebExplorer.class);
                ac.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.homecard;
    }

    public class getItems extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(MainDataHolder.JSON_STRING);
                int len = jsonArray.length();


                for (int i = 0; i < len; i++) {

                    HomeGateway gatewayo = new HomeGateway(parentcard);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String image = jsonObject.getString("img");
                    String link = jsonObject.getString("link");

                    gatewayo.name = name;
                    gatewayo.image = image;
                    gatewayo.link = link;




                    mObjects.add(gatewayo);

                    notifyDataSetChanged();

                }
            } catch (JSONException e) {
                Intent intent = new Intent(ac, NetworkError.class);
                ac.startActivity(intent);            }
            catch(Exception e)
            {
                Intent intent = new Intent(ac, NetworkError.class);
                ac.startActivity(intent);
            }
        }

        @Override
        protected String doInBackground(Void... params) {

           /* Webb webb = Webb.create();

            String response = webb.get(APIClass.HOME_CARD)
                    .ensureSuccess()
                    .asString().getBody();
            System.out.print(response);
            return response;*/
            return "";

        }
    }
    public class HomeGateway extends DefaultListObject {

        int id;
        String name;
        String image;
        String link;


        public HomeGateway(Card parentCard) {
            super(parentCard);
            init();

        }

        private void init() {
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                   /* Intent i = new Intent(ac, ContentActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ContentBean contentBean=new ContentBean();
                    contentBean.setId(id);
                    contentBean.setImage(img);
                    contentBean.setTitle(title);
                    i.putExtra("content",contentBean);
                    ac.startActivity(i);*/
                }
            });
        }

    }
}


