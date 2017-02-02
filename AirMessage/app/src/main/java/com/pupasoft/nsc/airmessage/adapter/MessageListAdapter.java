package com.pupasoft.nsc.airmessage.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pupasoft.nsc.airmessage.dao.MessageItemCollectionDao;
import com.pupasoft.nsc.airmessage.dao.MessageItemDao;
import com.pupasoft.nsc.airmessage.manager.MessageListManager;
import com.pupasoft.nsc.airmessage.view.MessageListItem;

/**
 * Created by wong_ on 26-Jan-17.
 */
public class MessageListAdapter extends BaseAdapter {

    MessageItemCollectionDao dao;

    @Override
    public int getCount() {
        if (dao == null)
            return 0;
        if (dao.getInformation() == null)
            return 0;
        return dao.getInformation().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getInformation().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageListItem item;
        if (convertView != null)
            item = (MessageListItem) convertView;
        else
            item = new MessageListItem(parent.getContext());

        MessageItemDao dao = (MessageItemDao) getItem(position);
        item.setTvName(dao.getName());
        item.setTvMessage(dao.getStatement());
        item.setTvDate(dao.getDate());
        return item;
    }

    public void setDao(MessageItemCollectionDao dao) {
        this.dao = dao;
    }
}
