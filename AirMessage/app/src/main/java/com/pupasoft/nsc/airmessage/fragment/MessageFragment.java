package com.pupasoft.nsc.airmessage.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;
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

    /************
     * Variable *
     ************/

    ListView listView;
    MessageListAdapter listAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    MessageListManager messageListManager;
    Button btnNewMessage;

    /************
     * Function *
     ************/

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(savedInstanceState);
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        //Initialize Fragment's level
        messageListManager = new MessageListManager();
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        listView = (ListView) rootView.findViewById(R.id.listView);
        btnNewMessage = (Button) rootView.findViewById(R.id.btnNewMessage);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        listAdapter = new MessageListAdapter();
        listAdapter.setDao(messageListManager.getDao());
        listView.setAdapter(listAdapter);
        btnNewMessage.setOnClickListener(buttonClickListener);
        swipeRefreshLayout.setOnRefreshListener(pullToRefreshListener);
        listView.setOnScrollListener(listViewScollListener);

        Log.d("MessageFragment", "initInstances");
        refreshData();
    }

    private void refreshData() {
        if (messageListManager.getCount() == 0)
            loadData();
        else
            updateData();
    }

    private void updateData() {
        int maxId = messageListManager.getMaxId();
        Call<MessageItemCollectionDao> call = HttpManager.getInstance().getService()
                .updateMessageList(new MessageItemCollectionDao(3, maxId)); //TODO: change '3' to locationid
        call.enqueue(new MessageLoadCallback(MessageLoadCallback.MODE_UPDATE));
    }

    private void loadData() {
        Call<MessageItemCollectionDao> call = HttpManager.getInstance().getService()
                .loadMessageList(new MessageItemCollectionDao(3));  //TODO: change '3' to locationid
        call.enqueue(new MessageLoadCallback(MessageLoadCallback.MODE_LOAD));
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
        outState.putBundle("messageListManager",
                messageListManager.onSaveInstanceState());
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        messageListManager.onRestoreInstanceState(
                savedInstanceState.getBundle("messageListManager"));
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

    private void showButtonNewMessage() {
        btnNewMessage.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(),
                R.anim.zoom_fade_in);
        btnNewMessage.startAnimation(anim);
    }

    private void hideButtonNewMessage() {
        btnNewMessage.setVisibility(View.GONE);
        Animation anim = AnimationUtils.loadAnimation(Contextor.getInstance().getContext(),
                R.anim.zoom_fade_out);
        btnNewMessage.startAnimation(anim);
    }

    /*********************
     * Listener / Method *
     *********************/

    final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btnNewMessage) {
                listView.smoothScrollToPosition(0);
                hideButtonNewMessage();
            }
        }
    };

    final SwipeRefreshLayout.OnRefreshListener pullToRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    final AbsListView.OnScrollListener listViewScollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
        }
    };

    /***************
     * Inner class *
     ***************/

    class MessageLoadCallback implements Callback<MessageItemCollectionDao> {

        public static final int MODE_LOAD = 1;
        public static final int MODE_UPDATE = 2;

        int mode;

        public MessageLoadCallback(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<MessageItemCollectionDao> call, Response<MessageItemCollectionDao> response) {
            swipeRefreshLayout.setRefreshing(false);
            if (response.isSuccessful()) {
                MessageItemCollectionDao dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if (mode == MODE_UPDATE)
                    messageListManager.InsertDaoAtTop(dao);
                else
                    messageListManager.setDao(dao);
                listAdapter.setDao(messageListManager.getDao());
                listAdapter.notifyDataSetChanged();

                if (mode == MODE_UPDATE) {
                    int additionalSize =
                            (dao != null && dao.getInformation() != null) ? dao.getInformation().size() : 0;
                    listView.setSelectionFromTop(firstVisiblePosition + additionalSize, top);
                    if (additionalSize > 0)
                        showButtonNewMessage();
                }

                Log.d("Loading Status", "Success");
            } else {
                try {
                    Log.d("Loading Status", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    }
}
