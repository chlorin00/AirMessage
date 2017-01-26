package com.pupasoft.nsc.airmessage.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pupasoft.nsc.airmessage.R;
import com.pupasoft.nsc.airmessage.adapter.MessageListAdapter;
import com.pupasoft.nsc.airmessage.dao.MessageItemCollectionDao;
import com.pupasoft.nsc.airmessage.manager.HttpManager;
import com.pupasoft.nsc.airmessage.manager.MessageListManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MessageFragment extends Fragment {

    ListView listView;
    MessageListAdapter listAdapter;

    public MessageFragment() {
        super();
    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new MessageListAdapter();
        listView.setAdapter(listAdapter);

        Call<MessageItemCollectionDao> call = HttpManager.getInstance().getService().loadMessageList();
        call.enqueue(new Callback<MessageItemCollectionDao>() {
            @Override
            public void onResponse(Call<MessageItemCollectionDao> call,
                                   Response<MessageItemCollectionDao> response) {
                if (response.isSuccessful()) {
                    MessageItemCollectionDao dao = response.body();
                    MessageListManager.getInstance().setDao(dao);
                    listAdapter.notifyDataSetChanged();
                    Log.d("Loading Status", String.valueOf(dao));
                } else {
                    try {
                        Log.d("Loading Status", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<MessageItemCollectionDao> call,
                                  Throwable t) {
                Log.d("Loading Status", t.toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
