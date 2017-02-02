package com.pupasoft.nsc.airmessage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pupasoft.nsc.airmessage.R;
import com.pupasoft.nsc.airmessage.dao.SendMessageItemDao;
import com.pupasoft.nsc.airmessage.manager.HttpManager;
import com.pupasoft.nsc.airmessage.manager.SendingMessageManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */

public class WriteMessageFragment extends Fragment {

    /************
     * variable *
     ************/

    EditText editText;
    Spinner spinner;
    SendingMessageManager sendingMessageManager;

    SimpleDateFormat dateFormat;

    /************
     * function *
     ************/

    public WriteMessageFragment() {
        super();
    }

    public static WriteMessageFragment newInstance() {
        WriteMessageFragment fragment = new WriteMessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_write_message, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        setHasOptionsMenu(true);
        sendingMessageManager = new SendingMessageManager();
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        spinner = (Spinner) rootView.findViewById(R.id.spCategories);
        editText = (EditText) rootView.findViewById(R.id.editWriteMessage);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        sendingMessageManager.setDao(new SendMessageItemDao());

    }

    private void setPostMessage(SendMessageItemDao dao) {
        dao.setLocationId(3);
        dao.setStatement(editText.getText().toString());
        dao.setDate(dateFormat.format(new Date()));
        dao.setName("Tan");
        dao.setCategory(spinner.getSelectedItem().toString());
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
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_write_message, menu);

        MenuItem menuItem = (MenuItem) menu.findItem(R.id.postMessage);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setPostMessage(sendingMessageManager.getDao());
                Log.i("Check information", sendingMessageManager.getDao().getStatement());
                Log.i("Check information", sendingMessageManager.getDao().getDate());

                Call<SendMessageItemDao> call = HttpManager.getInstance().getService()
                        .postMessageItem(sendingMessageManager.getDao());
                call.enqueue(new Callback<SendMessageItemDao>() {
                    @Override
                    public void onResponse(Call<SendMessageItemDao> call, Response<SendMessageItemDao> response) {
                        if (response.isSuccessful()) {
                            Log.d("Post Message", response.body().toString());
                            Toast.makeText(getContext(),
                                    "Post message complete.",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            getActivity().finish();
                        } else {
                            try {
                                Log.d("Post Message", response.errorBody().string());
                                Toast.makeText(getContext(),
                                        "Can't connect to server.",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SendMessageItemDao> call, Throwable t) {
                        Log.d("Post Message", "Fail--" + t.toString());
                        Toast.makeText(getContext(),
                                "Disconnect to server.",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                return true;
            }
        });
    }

    /*******************
     * listener/method *
     *******************/

    /***************
     * inner class *
     ***************/

}
