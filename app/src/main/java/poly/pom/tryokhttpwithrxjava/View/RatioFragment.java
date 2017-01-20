package poly.pom.tryokhttpwithrxjava.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import poly.pom.tryokhttpwithrxjava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatioFragment extends Fragment {


    public RatioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ratio, container, false);
    }

}
