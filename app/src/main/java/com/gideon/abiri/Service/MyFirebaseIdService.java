package com.gideon.abiri.Service;

import androidx.annotation.NonNull;


import com.gideon.abiri.Common.Common;
import com.gideon.abiri.Model.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseIdService extends FirebaseMessagingService {
    //press ctrl + o

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        //String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        updateTokenToServer(s);//when have refres token, we need update to our realtime database

    }
   /* @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        updateTokenToServer(refreshedToken);//when have refres token, we need update to our realtime database

    }*/



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        updateTokenToServer(refreshedToken);//when have refres token, we need update to our realtime database
    }



    private void updateTokenToServer(String refreshedToken) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference(Common.token_tbl);

        Token token = new Token(refreshedToken);

        if(FirebaseAuth.getInstance().getCurrentUser() !=null)//if already login, must update token
            tokens.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .setValue(token);
    }
}
