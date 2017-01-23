package poly.pom.tryokhttpwithrxjava.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import poly.pom.tryokhttpwithrxjava.Adapter.RatioAdapter;
import poly.pom.tryokhttpwithrxjava.Prestener.RatioPrestenerlmp;
import poly.pom.tryokhttpwithrxjava.R;
import poly.pom.tryokhttpwithrxjava.widget.Ratio;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatioFragment extends Fragment implements RatioView {


    @BindView(R.id.lv_ration)
    ListView lvRation;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private RatioPrestenerlmp prestener;
    private Unbinder viewUnbinder;
    private RatioAdapter ratioAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ratio, container, false);
        viewUnbinder = ButterKnife.bind(this, view);


        ratioAdapter = new RatioAdapter(getContext(), null);
        lvRation.setAdapter(ratioAdapter);
        prestener = RatioPrestenerlmp.bind(this);
        if (savedInstanceState != null) {
            prestener.tryRequestLatestForeignExchangeFromCache();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            prestener.requestLatestForeignExchange();
        }


        return view;
    }


    @Override
    public void onStop() {
        prestener.unbind();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewUnbinder.unbind();
    }


    public RatioFragment() {
        // Required empty public constructor
    }

    public static RatioFragment newInstance(Bundle args) {
        RatioFragment fragment = new RatioFragment();

        if (args != null)
            fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void showRatioList(ArrayList<Ratio> rList) {
        progressBar.setVisibility(View.GONE);
        ratioAdapter.setRatioList(rList);
        ratioAdapter.notifyDataSetChanged();

    }

    @Override
    public void errorHandle(Throwable throwable) {

    }
}
