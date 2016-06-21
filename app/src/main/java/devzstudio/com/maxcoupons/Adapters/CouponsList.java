package devzstudio.com.maxcoupons.Adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import devzstudio.com.maxcoupons.API.APIClass;
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
public class CouponsList extends CardWithList{
    List<CardWithList.ListObject> mObjects;
    Activity ac;
    CouponGateway gatewayo;
    Card parentcard;

    public CouponsList(Activity context)
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
        gatewayo = new CouponGateway(this);
        parentcard = this;
        new getItems().execute();
        return mObjects;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {
        TextView siteText = (TextView) convertView.findViewById(R.id.siteTitle);
        ImageView siteImage = (ImageView) convertView.findViewById(R.id.siteImage);
        TextView couponDescription = (TextView) convertView.findViewById(R.id.couponDescription);
        TextView couponCode = (TextView) convertView.findViewById(R.id.couponCode);

        ImageView imageView=(ImageView)convertView.findViewById(R.id.fab);
        final CouponGateway gatewayobject = (CouponGateway) object;

        Picasso.with(ac).load(MainDataHolder.SITE_IMAGE).into(siteImage);
        siteText.setText(MainDataHolder.SITE_NAME);

        couponCode.setText(gatewayobject.code);
        couponDescription.setText(gatewayobject.details);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)

                {
                    final ClipboardManager clipboardManager = (ClipboardManager)
                            ac.getSystemService(Context.CLIPBOARD_SERVICE);
                    final ClipData clipData = ClipData
                            .newPlainText(gatewayobject.code,gatewayobject.code);
                    clipboardManager.setPrimaryClip(clipData);
                } else

                {
                    final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)
                            ac.getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(gatewayobject.code);
                }

                Toast.makeText(ac, "Coupon Code Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;

    }

    @Override
    public int getChildLayoutId() {
        return R.layout.couponcards;
    }


    public class getItems extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                int len = jsonArray.length();

                if(len==0)
                {
                    CouponGateway gatewayo = new CouponGateway(parentcard);
                    gatewayo.details="Sorry No Coupons Are Available for this Site...";


                    mObjects.add(gatewayo);

                    notifyDataSetChanged();
                }

                for (int i = 0; i < len; i++) {

                    CouponGateway gatewayo = new CouponGateway(parentcard);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String code = jsonObject.getString("code");
                    String details = jsonObject.getString("details");

                    gatewayo.code = code;
                    gatewayo.details = details;
                    gatewayo.id = id;

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

            String response = webb.get(APIClass.COUPONS_LIST + MainDataHolder.SITE_ID)
                    .ensureSuccess()
                    .asString().getBody();
            return response;

        }
    }
    public class CouponGateway extends DefaultListObject {

        int id;
        String code;
        String details;



        public CouponGateway(Card parentCard) {
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
