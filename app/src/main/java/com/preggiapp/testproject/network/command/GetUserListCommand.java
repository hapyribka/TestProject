package com.preggiapp.testproject.network.command;

import com.preggiapp.testproject.data.AppData;
import com.preggiapp.testproject.model.User;
import com.preggiapp.testproject.network.callbacks.FragmentCallBack;
import com.preggiapp.testproject.network.callbacks.ServiceCallback;
import com.preggiapp.testproject.network.response.UsersListResponse;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Response;

public class GetUserListCommand extends BaseCommand {

    public GetUserListCommand(FragmentCallBack callback) {
        super(callback);
    }

    public void execute() {
        Call<UsersListResponse> resp = service.getUsers("v2");
        resp.enqueue(new ServiceCallback<UsersListResponse>() {
            @Override
            public void onResponse(Response<UsersListResponse> response, String error) {
                if(error != null) {
                    if(callback != null) {
                        callback.onLoadFinished(error);
                    }
                } else {
                    UsersListResponse body = response.body();
                    if(!checkErrors(body)) {
                        AppData.getInstance().setUserList((ArrayList<User>) body.getResult());
                        AppData.getInstance().getUserList().add(0, null);
                        if(callback != null) {
                            callback.onLoadFinished(null);
                        }
                    }
                }
            }
        });
    }
}
