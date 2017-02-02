package com.pupasoft.nsc.airmessage.manager;

import android.content.Context;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.pupasoft.nsc.airmessage.dao.SendMessageItemDao;

/**
 * Created by wong_ on 01-Feb-17.
 */
public class SendingMessageManager {

    private Context mContext;
    private SendMessageItemDao dao;

    public SendingMessageManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public SendMessageItemDao getDao() {
        return dao;
    }

    public void setDao(SendMessageItemDao dao) {
        this.dao = dao;
    }

}
