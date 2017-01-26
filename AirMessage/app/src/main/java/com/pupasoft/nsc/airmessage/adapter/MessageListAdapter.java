package com.pupasoft.nsc.airmessage.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pupasoft.nsc.airmessage.dao.MessageItemDao;
import com.pupasoft.nsc.airmessage.manager.MessageListManager;
import com.pupasoft.nsc.airmessage.view.MessageListItem;

/**
 * Created by wong_ on 26-Jan-17.
 */
public class MessageListAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        if (MessageListManager.getInstance().getDao() == null)
            return 0;
        if (MessageListManager.getInstance().getDao().getInformation() == null)
            return 0;
        return MessageListManager.getInstance().getDao().getInformation().size();
    }

    @Override
    public Object getItem(int position) {
        return MessageListManager.getInstance().getDao().getInformation().get(position);
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
        item.setTvMessage(dao.getStatement());
        item.setTvDate(dao.getDate());
        return item;
    }
}
