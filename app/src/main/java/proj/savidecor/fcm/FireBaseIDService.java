package proj.savidecor.fcm;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.ResponseBody;
import proj.savidecor.Utils.Apiclient;
import proj.savidecor.Utils.Constants;
import proj.savidecor.Utils.ItemAPI;
import retrofit2.Call;
import retrofit2.Response;


import static proj.savidecor.Utils.Constants.retryNum;

public class FireBaseIDService extends FirebaseInstanceIdService {

    SharedPreferences sharedPreferences;

    @SuppressLint("HardwareIds")
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        sharedPreferences=getSharedPreferences("VIEW",MODE_PRIVATE);
        String dID = sharedPreferences.getString(Constants.DID, "");

        FirebaseMessaging.getInstance().subscribeToTopic("neongroup");

        sendRegistrationToServer(dID,refreshedToken);
    }

    private void sendRegistrationToServer(String deviceid, String refreshedToken) {

        ItemAPI itemAPI= Apiclient.getClient().create(ItemAPI.class);
        Call<ResponseBody> TokenCall=itemAPI.SendToken(deviceid,refreshedToken);

        Log.d("devicedetails",""+Constants.SITEID+" "+deviceid+" "+refreshedToken);
        TokenCall.enqueue(new Constants.BackoffCallback<ResponseBody>(retryNum) {
            @Override
            public void onFailedAfterRetry(Throwable t) {
                Log.d("tokenFAILURE",t.getMessage().trim());
            }

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    Log.d("StatusCode", String.valueOf(response.code()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
