package com.preggiapp.testproject.network.response;

import com.preggiapp.testproject.model.User;
import java.util.List;

public class UsersListResponse extends BaseResponse {

    private List<User> result;

    public List<User> getResult() {
        return result;
    }

    public void setResult(List<User> result) {
        this.result = result;
    }
}