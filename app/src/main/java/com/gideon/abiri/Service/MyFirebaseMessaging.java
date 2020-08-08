package com.gideon.abiri.Service;

import android.content.Intent;


import androidx.annotation.NonNull;

import com.gideon.abiri.Common.Common;
import com.gideon.abiri.CustommerCall;
import com.gideon.abiri.Model.Pickup;
import com.gideon.abiri.Model.Token;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        updateTokenToServer(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
     /*   ///
        LatLng customer_location = new Gson().fromJson(remoteMessage.getNotification().getBody(),LatLng.class);

        Intent intent = new Intent(getBaseContext(), CustommerCall.class);
        intent.putExtra("lat",customer_location.latitude);
        intent.putExtra("long",customer_location.longitude);
        intent.putExtra("customer", remoteMessage.getNotification().getTitle());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
*/
        if(remoteMessage.getNotification().getTitle().equals("Pickup")){
            Pickup pickup=new Gson().fromJson(remoteMessage.getNotification().getBody(), Pickup.class);
            Intent intent=new Intent(getBaseContext(), CustommerCall.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("lat", pickup.getLastLocation().latitude);
            intent.putExtra("lng", pickup.getLastLocation().longitude);
            intent.putExtra("rider", pickup.getID());
            intent.putExtra("token", pickup.getToken().getToken());
            startActivity(intent);


            /////

        }

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
