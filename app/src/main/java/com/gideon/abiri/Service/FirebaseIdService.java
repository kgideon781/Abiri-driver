package com.gideon.abiri.Service;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.gideon.abiri.Common.Common;
import com.gideon.abiri.Model.Token;

public class FirebaseIdService extends FirebaseMessagingService {
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
//        updateTokenToServer(refreshedToken);
//    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        updateTokenToServer(s);
    }

    private void updateTokenToServer(String refreshedToken) {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference(Common.token_tbl);

        Token token=new Token(refreshedToken);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)tokens.child(FirebaseAuth.getInstance().getUid())
                .setValue(token);
    }
}
