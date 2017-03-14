package com.example.mrjab.hermes;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SearchForUser extends AppCompatActivity {
    EditText et;
    ImageButton search;
    ListView ls;
    CustomAdapterSearchUser adapterSearchUser = null;

    ArrayList<String> userNames = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> emailID = new ArrayList<>();
    ArrayList<String> userID = new ArrayList<>();
    int[] profImages;
    Toolbar toolbar;
    int userIDself;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_user);

        et = (EditText) findViewById(R.id.search_user);
        search = (ImageButton) findViewById(R.id.button_search_user);
        ls = (ListView) findViewById(R.id.list_view_search);
        Intent in =getIntent();
        userIDself = in.getIntExtra("userID",0);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Search For a User");
        setSupportActionBar(toolbar);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserSearchData();
            }
        });
    }

    public void getUserSearchData() {
        //Toast.makeText(getApplicationContext(),"getUserSearchData",Toast.LENGTH_LONG).show();
        Log.v("getsearch : ",et.getText().toString());
        String[] username = {et.getText().toString()};
        new GetUsers().execute(username);
    }

    public void populateListView(){

        if(userNames.size()>0 && !userID.contains(String.valueOf(userIDself))) {
            adapterSearchUser = new CustomAdapterSearchUser(this, userNames, profImages, names, userID);
            ls.setAdapter(adapterSearchUser);
        }
        else if(userID.contains(String.valueOf(userIDself)))
        {
            int pos= userID.indexOf(String.valueOf(userIDself));
            userID.remove(pos);
            userNames.remove(pos);
            names.remove(pos);
            adapterSearchUser = new CustomAdapterSearchUser(this, userNames, profImages, names, userID);
            ls.setAdapter(adapterSearchUser);
        }
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),userNames.get(i)+" Selected",Toast.LENGTH_LONG).show();

                Intent in =new Intent(SearchForUser.this,NewChat.class);
                in.putExtra("uname",userNames.get(i));
                in.putExtra("userID",userIDself);
                in.putExtra("userIDRec",Integer.parseInt(userID.get(i)));
                startActivity(in);
                finish();

            }
        });
    }


    public class GetUsers extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] username) {
            // TODO Auto-generated method stub

            final StringBuilder builder = new StringBuilder();
            //Do Your stuff here..

            final HttpClient httpclient = new DefaultHttpClient();
            final HttpPost httppost = new HttpPost("http://hermes.webutu.com/ChatSelect.php");

            final HttpGet httpget = new HttpGet("http://hermes.webutu.com/SP/UserSearchUsername.php?UsernameV=" + "'"+String.valueOf(username[0])+"'");
            String result;


            HttpResponse httpresponse = null;

            ArrayList<NameValuePair> postParameters;

            postParameters = new ArrayList<NameValuePair>();
            postParameters.add(
                    new BasicNameValuePair("userID", String.valueOf(username[0])));

            try {
                httppost.setEntity(new UrlEncodedFormEntity(postParameters));
                // reset to null before making a new post if it's being reused
                httpresponse = null;
                httpresponse = httpclient.execute(httpget);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (httpresponse != null) {

                try {

                    // Get the data in the entity
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    httpresponse.getEntity().getContent(), "UTF-8")
                    );


                    for (String line = null; (line = reader.readLine()) != null; ) {
                        builder.append(line).append("\n");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return builder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(adapterSearchUser != null) {
                userID.clear();
                userNames.clear();
                names.clear();
                adapterSearchUser.notifyDataSetChanged();
                ls.setAdapter(adapterSearchUser);
            }

            Log.v("Post : ", result);
            //chats.clear();
            JSONObject finaljson = null;

            try {
                JSONArray jArray = new JSONArray(result);
                //JSONTokener tokener = new JSONTokener(builder.toString());
                finaljson = jArray.getJSONObject(0);

                Log.v("JSON OUTPUT: ", finaljson.toString());

                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject oneObject = jArray.getJSONObject(i);
                        // Pulling items from the array
                        userID.add(String.valueOf(oneObject.getInt("UserID")));
                        userNames.add(oneObject.getString("Username"));
                        names.add(oneObject.getString("Name"));

                    } catch (JSONException e) {
                        // Oops
                        e.printStackTrace();
                    }
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }

            //this method will be running on UI thread

                populateListView();


        }
    }
}
