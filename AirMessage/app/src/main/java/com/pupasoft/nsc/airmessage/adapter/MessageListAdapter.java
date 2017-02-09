package com.pupasoft.nsc.airmessage.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.pupasoft.nsc.airmessage.dao.MessageItemCollectionDao;
import com.pupasoft.nsc.airmessage.dao.MessageItemDao;
import com.pupasoft.nsc.airmessage.view.MessageListItem;

import java.util.ArrayList;

/**
 * Created by wong_ on 26-Jan-17.
 */
public class MessageListAdapter extends BaseAdapter implements Filterable{

    MessageItemCollectionDao dao;
    MessageItemCollectionDao daoFiltered;
    private ArrayList<MessageItemDao> informations;

    @Override
    public int getCount() {
        if (daoFiltered == null)
            return 0;
        if (daoFiltered.getInformation() == null)
            return 0;
        return daoFiltered.getInformation().size();
    }

    @Override
    public Object getItem(int position) {
        return daoFiltered.getInformation().get(position);
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
        //TODO: Set category in text view
        return item;
    }

    public void setDao(MessageItemCollectionDao dao) {
        this.dao = dao;
        this.daoFiltered = dao;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {


            FilterResults results = new FilterResults();


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (informations == null) {
                    informations = new ArrayList<MessageItemDao>(dao.getInformation()); // saves the original data in mOriginalValues
                }
                Log.d("filter-results", "start " + constraint);
                if (constraint.equals("Normal")) {
                    results.values = informations;
                    results.count = informations.size();
                    Log.d("getInformationDao", dao.getInformation().size() + "");
                    Log.d("resultsBefore", results.values.toString());
                    Log.d("resultsBefore", results.count + "");
                } else {
                    ArrayList<MessageItemDao> resultsMessage = new ArrayList<>();
                    for (MessageItemDao item : informations) {
                        if (constraint.equals(item.getCategory())) {
                            Log.d("item dao", item.getStatement());
                            resultsMessage.add(item);
                        }
                    }
                    results.count = resultsMessage.size();
                    results.values = resultsMessage;
                    Log.d("resultsAfter", results.values.toString());
                    Log.d("resultsAfter", results.count + "");
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.d("daoFiltered", daoFiltered.getInformation().toString());
                daoFiltered.setInformation((ArrayList<MessageItemDao>) results.values);
                Log.d("daoFiltered-results", results.values.toString());
                Log.d("daoFiltered-results", results.count + "");
                notifyDataSetChanged();
            }
        };
    }
}
