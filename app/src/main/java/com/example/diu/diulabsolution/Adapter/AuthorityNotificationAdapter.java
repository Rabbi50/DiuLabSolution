package com.example.diu.diulabsolution.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.diu.diulabsolution.Activity.NotificationDetailsActivity;
import com.example.diu.diulabsolution.Model.Data;
import com.example.diu.diulabsolution.Model.Notification;
import com.example.diu.diulabsolution.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class AuthorityNotificationAdapter extends RecyclerView.Adapter<AuthorityNotificationAdapter.ViewHolder> {

    private List<Notification> notificationsList;
    private Context context;
    private String complainDocId,mUserId;
    private FirebaseAuth mAuth;
    public AuthorityNotificationAdapter(Context context, List<Notification> notificationList){
        this.notificationsList=notificationList;
        this.context=context;
        mAuth=FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public AuthorityNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.authority_notification_list,parent,false);
        return new AuthorityNotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.notificationTitleView.setText(notificationsList.get(position).getNotification_title());
        holder.complainTypeView.setText(notificationsList.get(position).getComplain_type());
        holder.senderIdView.setText(notificationsList.get(position).getNotification_sender_id());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserId=mAuth.getCurrentUser().getUid();
                complainDocId = notificationsList.get(position).getComplain_id();
                Intent intent=new Intent(context,NotificationDetailsActivity.class);
                intent.putExtra("complain_doc_id",complainDocId);
                intent.putExtra("current_user_id",mUserId);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView notificationTitleView,complainTypeView,senderIdView;
        private RelativeLayout notificationItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            notificationTitleView=(TextView)mView.findViewById(R.id.notificationTitle);
            complainTypeView=(TextView)mView.findViewById(R.id.complainType);
            senderIdView=(TextView)mView.findViewById(R.id.senderId);
            notificationItemView=(RelativeLayout) mView.findViewById(R.id.notification_list);
        }
    }


}
