package com.pupasoft.nsc.airmessage.manager;

import android.content.Context;
import android.os.Bundle;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
import com.pupasoft.nsc.airmessage.dao.MessageItemCollectionDao;
import com.pupasoft.nsc.airmessage.dao.MessageItemDao;

import java.util.ArrayList;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MessageListManager {

    private Context mContext;
    private MessageItemCollectionDao dao;

    public MessageListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public MessageItemCollectionDao getDao() {
        return dao;
    }

    public void setDao(MessageItemCollectionDao dao) {
        this.dao = dao;
    }

    public int getMaxId() {
        if (dao == null)
            return 0;
        if (dao.getInformation() == null)
            return 0;
        if (dao.getInformation().size() == 0)
            return 0;

        int maxId = dao.getInformation().get(0).getId();
        for (int i = 1; i < dao.getInformation().size(); i++) {
            maxId = Math.max(maxId, dao.getInformation().get(i).getId());
        }
        return maxId;
    }

    public int getCount() {
        if (dao == null)
            return 0;
        if (dao.getInformation() == null)
            return 0;
        return dao.getInformation().size();
    }

    public void InsertDaoAtTop(MessageItemCollectionDao newDao) {
        if (dao == null)
            dao = new MessageItemCollectionDao(3);      //TODO: change '3' to locationid
        if (dao.getInformation() == null)
            dao.setInformation(new ArrayList<MessageItemDao>());
        dao.getInformation().addAll(0, newDao.getInformation());
    }

    public Bundle onSaveInstanceState() {
        Bundle bundle =new Bundle();
        bundle.putParcelable("dao",dao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        dao = savedInstanceState.getParcelable("dao");
    }
}
