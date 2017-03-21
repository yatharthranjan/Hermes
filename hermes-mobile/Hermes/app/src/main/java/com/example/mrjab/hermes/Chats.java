package com.example.mrjab.hermes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;


public class Chats extends AppCompatActivity implements AsyncResponse,AsyncResponseMessages{

    private Toolbar toolbar;
    ChatList chatList = new ChatList();
    ArrayList<ChatInfo> allChats= new ArrayList<>();

    int [] profileImages={};
    ArrayList<String> times=new ArrayList<>();
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> username = new ArrayList<>();
    String currentMessage = "Message";
    Date currentDate = new Date();
    ArrayList<String> unames;
    ArrayList<String> messa;
    ArrayList<String> ti;
    ArrayList<ChatInfo> allChats2;
    int i=0;
    SwipeMenuListView listView;
    int userID;
    HermesDbHelper dbHelper;
    CustomAdapter adapter;
    int f = 0;
    boolean flag = false;
    Handler handler;
    Runnable runnableCode;
    int prevCountMessages=0;
    int currentCountMessages=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);


        unames = new ArrayList<>(username);
        messa = new ArrayList<>(messages);
        ti = new ArrayList<>(times);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        listView= (SwipeMenuListView) findViewById(R.id.listView);
        Intent in =getIntent();
        userID = in.getIntExtra("userID",0);
        /*ContentValues values = new ContentValues();
        values.put("ChatID",10);
        values.put("fk_InitUserID_by", 1);
        values.put("fk_InitUserID_with",2);
        values.put("KeyValue",5);
        values.put("CreateDate",new Date().toString());


        Uri CONTENT_URI =
                Uri.withAppendedPath(
                        ChatItemsContract.CONTENT_URI,
                        "chats");

        ChatContentProvider ccp =new ChatContentProvider();
        if(ccp.initialize(getApplicationContext()) == true) {
            Toast.makeText(getApplicationContext(),"Init working",Toast.LENGTH_LONG).show();
            ccp.insert(CONTENT_URI, values);
        }//long newRowId = db.insert("tbl_user", null, values);*/

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth((int)(dipToPixels(Chats.this,90)));
                // set item title
                openItem.setTitle("Clear Cache");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth((int)dipToPixels(Chats.this,90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
                handler.removeCallbacks(runnableCode);
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
                //handler.post(runnableCode);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), unames.get(i) + " Clicked", Toast.LENGTH_SHORT).show();
                Intent in =new Intent(Chats.this,ChatDetails.class);
                in.putExtra("Messages", allChats2.get(i).messages);
                in.putExtra("uname",unames.get(i));
                in.putExtra("userID",userID);
                in.putExtra("chatID",allChats2.get(i).chatID);
                startActivity(in);
            }
        });


        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // clear cache
                        Toast.makeText(getApplicationContext(), "Clear Cache", Toast.LENGTH_SHORT).show();

                        break;
                    case 1:
                        // delete
                        Toast.makeText(getApplicationContext(), "Delete chat", Toast.LENGTH_SHORT).show();

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        //listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);


        // Create the Handler object (on the main thread by default)
       /* handler = new Handler();
        runnableCode = new Runnable() {
            @Override
            public void run() {

                chatList.renewChats();
                chatList.asyncChats.delegate = Chats.this;
                userID =1;
                String [] userid = {String.valueOf(userID)};

                chatList.asyncChats.execute(userid);
                // Do something here on the main thread
                Log.d("Handlers", "Called on main thread");
                // Repeat this the same runnable code block again another 2 seconds

                handler.postDelayed(this, 2000);
            }
        };*/
       // handler.post(runnableCode);

        final EditText search = (EditText) findViewById(R.id.search_chat);
        search.clearFocus();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handler.removeCallbacks(runnableCode);
            }
        });

        ImageButton startSearch = (ImageButton) findViewById(R.id.start_search);
       /*startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> unames = new ArrayList<>();
                ArrayList<String> mess = new ArrayList<>();
                ArrayList<String> times1 = new ArrayList<>();
                for (String uname: username
                     ) {
                    if(uname.contains(search.getText().toString()))
                    {
                        unames.add(uname);
                        mess.add(messages.get(username.indexOf(uname)));
                        times1.add(times.get(username.indexOf(uname)));

                    }
                }

                listView.setAdapter(new CustomAdapter(Chats.this,unames,profileImages,mess,times1));

            }
        });*/

        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    handler.post(runnableCode);
                }
            }
        });

    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent dbmanager = new Intent(Chats.this,AndroidDatabaseManager.class);
            startActivity(dbmanager);
            return true;
        }
        if (id == R.id.action_exit) {
            System.exit(0);
            return true;
        }
        if (id == R.id.action_add) {
            // Add a new Chat
            Toast.makeText(getApplicationContext(),"Add new Chat",Toast.LENGTH_LONG).show();

            Intent i = new Intent(Chats.this, SearchForUser.class);
            i.putExtra("userID",userID);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onResume() {
        super.onResume();

        i=0;
        // Create the Handler object (on the main thread by default)
        handler = new Handler();
        runnableCode = new Runnable() {
            @Override
            public void run() {
                i=0;
                chatList.renewChats();
                chatList.asyncChats.delegate = Chats.this;
                String [] userid = {String.valueOf(userID)};

                chatList.asyncChats.execute(userid);
                // Do something here on the main thread
                Log.d("Handlers", "Called on main thread");

                // Repeat this the same runnable code block again another 2 seconds

                handler.postDelayed(this, 2000);
            }
        };
        handler.post(runnableCode);
    }

    @Override
    public void processfinish(ArrayList<ChatInfo> output) {

        i=0;
        if(f==1) {
            i = 0;
            username.clear();
            messages.clear();
            times.clear();
            allChats.clear();
            //adapter.notifyDataSetChanged();
        }

        Log.v("process finish ", "here i am 2 : " + output.size());
        currentCountMessages = 0;
        for (ChatInfo ch  : output
             ) {
            //search.setText(search.getText()+"\n"+ch.getChatID()+" "+ch.getUserIDSender()+" "+ch.getUserIDReceiver());
            username.add("User ID : " + ch.getUserIDReceiver());
            Log.v("Latest Chat", String.valueOf(ch.getChatID()));
            GetLastMessageFromChat getMessages = new GetLastMessageFromChat(userID,ch.getChatID());
            String [] userid = {String.valueOf(userID)};
            getMessages.asyncMessages.delegate=this;
            getMessages.asyncMessages.execute(userid);
        }
        allChats=new ArrayList<>(output);
    }


    @Override
    protected void onPause(){
        super.onPause();
        handler.removeCallbacks(runnableCode);

    }


    @Override
    public void processfinishMessages(ArrayList<MessageInfo> allmessages) {
        if(i<allChats.size())
        allChats.get(i).setMessages(allmessages);
        //currentCountMessages +=allmessages.size();
        i++;
        if(allmessages.size() > 0) {
            currentMessage = allmessages.get(allmessages.size() - 1).getContent();
            currentDate = allmessages.get(allmessages.size() - 1).getReceived();
        }
        else {
            currentMessage = "No Messages yet";
        }

        messages.add(currentMessage);
        System.out.print(currentMessage);

        Log.v("Latest Message", currentMessage);




        if( DateUtils.isToday(currentDate.getDate()))
        times.add(currentDate.getHours()+":"+currentDate.getMinutes());
        else{
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String newDate = df.format(currentDate);
            times.add(newDate);
        }


        unames.clear();
        messa.clear();
        ti.clear();
        unames.addAll(username);
        messa.addAll(messages);
        ti.addAll(times);
        if(username.size()==messages.size() && f == 0) {
            adapter = new CustomAdapter(this, unames, profileImages, messa, ti);
            listView.setAdapter(adapter);
            allChats2 = new ArrayList<ChatInfo>(allChats);
            f=1;
        }
        else  if(username.size()==messages.size() && f == 1){
                Log.v("f=1", "Adapter notified");
            unames.clear();
            messa.clear();
            ti.clear();
            unames.addAll(username);
            messa.addAll(messages);
            ti.addAll(times);
            adapter.notifyDataSetChanged();
            Log.v("adapter :", adapter.messages.toString());
            listView.setAdapter(adapter);
            allChats2 = new ArrayList<ChatInfo>(allChats);
                i=0;
        }
    }
}

