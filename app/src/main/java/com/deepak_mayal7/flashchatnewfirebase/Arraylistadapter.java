package com.deepak_mayal7.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Pooja on 08-01-2018.
 */

class ArraylistAdapter extends BaseAdapter {

    private DatabaseReference mDatabaseReference;
    private String mdisplayname;
    private Activity mActivity;
    private ArrayList<DataSnapshot> mDataSnapshot;


    private  ChildEventListener mListener=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
         mDataSnapshot.add(dataSnapshot);
         notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public ArraylistAdapter(DatabaseReference databaseReference, String mdisplayname, Activity activity) {
        mDatabaseReference = databaseReference.child("messages");

        this.mdisplayname = mdisplayname;
        mActivity = activity;
        mDatabaseReference.addChildEventListener(mListener);
        mDataSnapshot=new ArrayList<>();
    }
    static class ViewHolder{
        TextView author;
        TextView msg;
        LinearLayout.LayoutParams mParams;
    }


    @Override
    public int getCount() {
        return mDataSnapshot.size();
    }

    @Override
    public Instantmsg getItem(int i) {
        DataSnapshot dataSnapshot=mDataSnapshot.get(i);
        return dataSnapshot.getValue(Instantmsg.class);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            LayoutInflater inflater=(LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.chat_msg_row,viewGroup,false);
            final ViewHolder holder=new ViewHolder();
            holder.author=(TextView)view.findViewById(R.id.author);
            holder.msg=(TextView)view.findViewById(R.id.message);
            holder.mParams=(LinearLayout.LayoutParams)holder.author.getLayoutParams();
            view.setTag(holder);
        }
         Instantmsg instantmsg=getItem(i);
        final ViewHolder viewHolder=(ViewHolder) view.getTag();
        String q=instantmsg.getAuthor();

        boolean isme=q.equals(mdisplayname);
        setchatrow(isme,viewHolder);


        String author= instantmsg.getAuthor();
        viewHolder.author.setTag(author);

        String message=instantmsg.getMessage();
        viewHolder.msg.setTag(message);

        return view;
    }
    private void setchatrow(boolean isme,ViewHolder viewHolder){
        if(isme){
          viewHolder.mParams.gravity= Gravity.END;
          viewHolder.author.setTextColor(Color.GREEN);
          viewHolder.msg.setBackgroundResource(R.drawable.bubble2);

        }
        else {
            viewHolder.mParams.gravity= Gravity.START;
            viewHolder.author.setTextColor(Color.BLUE);
            viewHolder.msg.setBackgroundResource(R.drawable.bubble1);

        }
        viewHolder.author.setLayoutParams(viewHolder.mParams);

        viewHolder.msg.setLayoutParams(viewHolder.mParams);

    }
    public void cleanup(){
        mDatabaseReference.removeEventListener(mListener);
    }
}

