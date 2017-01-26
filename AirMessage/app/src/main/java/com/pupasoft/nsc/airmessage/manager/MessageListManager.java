package com.pupasoft.nsc.airmessage.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.pupasoft.nsc.airmessage.dao.MessageItemCollectionDao;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MessageListManager {

    private MessageItemCollectionDao dao;

    private static MessageListManager instance;

    public static MessageListManager getInstance() {
        if (instance == null)
            instance = new MessageListManager();
        return instance;
    }

    private Context mContext;

    private MessageListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public MessageItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(MessageItemCollectionDao dao) {
        this.dao = dao;
    }
}
