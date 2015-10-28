package com.giffedup.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giffedup.R;
import com.giffedup.adapters.StoryListAdapter;
import com.giffedup.model.ImageConfigurationModel;
import com.giffedup.model.StoryModel;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StoryListLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoryListLayoutFragment extends Fragment {

//    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private StoryListAdapter adapter;
    private List<StoryModel> storyList;
    // TODO: Rename and change types and number of parameters
    public static StoryListLayoutFragment newInstance() {
        StoryListLayoutFragment fragment = new StoryListLayoutFragment();

        return fragment;
    }

    public StoryListLayoutFragment() {
        // Required empty public constructor
    }

    public void getStories(){
        storyList = new ArrayList<StoryModel>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Story");
        query.include("smallImage");
        query.include("downSized");
        query.include("downsizedStill");
        query.include("original");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> object, ParseException e) {
                if (e == null) {
                    Log.d("stories", "Retrieved " + object.size() + " stories");
                    for (int i = 0; i < object.size(); i++) {
                        String title = object.get(i).getString("title");
                        String id = object.get(i).getString("id");
                        String contentId = object.get(i).getString("contentId");
                        Log.d("Stories", title);
                        ImageConfigurationModel downSized = ImageConfigurationModel.getConfiguration(object.get(i).getParseObject("downSized"));
                        ImageConfigurationModel downsizedStill = ImageConfigurationModel.getConfiguration(object.get(i).getParseObject("downsizedStill"));
                        ImageConfigurationModel original = ImageConfigurationModel.getConfiguration(object.get(i).getParseObject("original"));
                        ImageConfigurationModel smallImage = ImageConfigurationModel.getConfiguration(object.get(i).getParseObject("smallImage"));
                        Log.d("Stories", smallImage.getUrl());
                        StoryModel sm = new StoryModel(id, contentId, title, downSized, downsizedStill, original, smallImage);
                        storyList.add(sm);
                    }
                    checkAndSetAdapters();

                } else {
                    Log.d("stories", "Error: " + e.getMessage());
                }
            }
        });
        //Log.d("", "");
        //checkAndSetAdapters();
        //return storyList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_story_list_layout, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview_story_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getStories();
//        checkAndSetAdapters();
//        if(null == mGifList || mGifList.isEmpty()) {
//            fetchGifs();
//        }
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
    private void checkAndSetAdapters() {
        Log.d("Adapters", "inside set and get adapter " + storyList.size());
        if(adapter == null && storyList != null) {
            adapter = new StoryListAdapter(getActivity(), storyList);
            //adapter.setOnItemClicklistener(this);
            recyclerView.setAdapter(adapter);
        }
        else if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAndSetAdapters();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
