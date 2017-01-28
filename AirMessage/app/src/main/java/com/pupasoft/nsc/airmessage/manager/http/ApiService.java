package com.pupasoft.nsc.airmessage.manager.http;

import com.pupasoft.nsc.airmessage.dao.MessageItemCollectionDao;
import com.pupasoft.nsc.airmessage.manager.MessageListManager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wong_ on 26-Jan-17.
 */
public interface ApiService {

    @POST("submitloc")
    Call<MessageItemCollectionDao> loadMessageList(@Body MessageItemCollectionDao messageItemCollectionDao);

    @POST("updatedata")
    Call<MessageItemCollectionDao> updateMessageList(@Body MessageItemCollectionDao messageItemCollectionDao);
}
