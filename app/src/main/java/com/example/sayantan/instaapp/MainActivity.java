package com.example.sayantan.instaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mInstaList;
    //   private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("InstaApp");

        mInstaList = (RecyclerView)findViewById(R.id.insta_list);
        mInstaList.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mInstaList.setLayoutManager(mLayoutManager);

        //mInstaList.setLayoutManager(new LinearLayoutManager(this));

       mAuthListner = new FirebaseAuth.AuthStateListener() {
           @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null){
                  Intent loginIntent = new Intent(MainActivity.this,RegisterActivity.class);
                  loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  //if user press back , he/she might not get back to previous activity
                  startActivity(loginIntent);

               }
           }
      };
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListner);
        FirebaseRecyclerAdapter<Insta,InstaViewHolder> FBRA = new FirebaseRecyclerAdapter<Insta, InstaViewHolder>(

                Insta.class,//take from
                R.layout.insta_row,//layout created
                InstaViewHolder.class,//viewHolder class
                mDatabase//database Reference
        ) {
            @Override
            protected void populateViewHolder(InstaViewHolder viewHolder, Insta model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };
        mInstaList.setAdapter(FBRA);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class InstaViewHolder extends RecyclerView.ViewHolder{

        public InstaViewHolder(View itemView){

            super(itemView);
            View mView = itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView) itemView.findViewById(R.id.textTitle);
            post_title.setText(title);
        }

        public void setDesc(String desc){
            TextView post_description = (TextView) itemView.findViewById(R.id.textDescription);
            post_description.setText(desc);
        }

        public void setImage(Context applicationContext, String image){
            ImageView post_image = (ImageView) itemView.findViewById(R.id.post_image);
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/instaapp-54a9d.appspot.com" +
                    "/o/PostImage%2Fimage%3A8380?alt=media&token=73ee2937-b6e7-49ad-963a-f06010427fc8").into(post_image);
            //Picasso.get().load(image).placeholder(R.drawable.addimage).into(post_image);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.addIcon){
            Intent intent =new Intent(this,PostActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.logout){
            mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
}
