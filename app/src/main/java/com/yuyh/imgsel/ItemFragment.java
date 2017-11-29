package com.yuyh.imgsel;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    private static final String PARAMS_TITLE = "url";

    private Uri uri;

    public static Fragment create(String url) {
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAMS_TITLE, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uri = Uri.parse("file://" + getArguments().getString(PARAMS_TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        ImageView ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        Glide.with(this).load(uri).into(ivPhoto);
        return view;
    }

}
