package com.example.ryn41.tutum.firebase;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ryn41.tutum.etc.TempData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ryn41 on 2017-09-14.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    private String refreshedToken;
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        Log.d(TAG, "userId: " + TempData.getID());
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        while (TempData.getUserLogin() == 0) { }
        sendRegistrationToServer();
    }
    // [END refresh_token]

//    /**
//     * Persist token to third-party servers.
//     * <p>
//     * Modify this method to associate the user's FCM InstanceID token with any server-side account
//     * maintained by your application.
//     *
//     * @param token The new token.
//     */
    private void sendRegistrationToServer() {
        (new RegisterAsync()).execute();
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = new FormBody.Builder()
//                .add("Token", token)
//                .add("userId", TempData.getID())
//                .build();
//
//        //request
//        Request request = new Request.Builder()
//                .url("http://13.59.135.92/register.php")
//                .post(body)
//                .build();
//
//        try {
//            client.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    private class RegisterAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String str = "http://13.59.135.92/firebase/regist.php?Token=" + refreshedToken + "&userId=" + TempData.getID();
                URL url = new URL(str);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                line = sb.toString();
                Log.e("token", refreshedToken);
                Log.e("userId", TempData.getID());

                if(line.equals("success")) {
                    Log.e("instance id service", line);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}