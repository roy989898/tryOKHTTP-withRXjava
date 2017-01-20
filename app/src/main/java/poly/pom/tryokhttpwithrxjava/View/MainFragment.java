package poly.pom.tryokhttpwithrxjava.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import poly.pom.tryokhttpwithrxjava.Prestener.MainPrestener;
import poly.pom.tryokhttpwithrxjava.Prestener.MainPrestenerImp;
import poly.pom.tryokhttpwithrxjava.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MainView {


    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.bt_ration)
    Button btRation;
    @BindView(R.id.bt_get)
    Button btGet;
    private Unbinder unbinder;
    private MainPrestener prestener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        prestener = MainPrestenerImp.bind(this);
    }

    @Override
    public void onStop() {
        prestener.unbind();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static MainFragment newInstance(Bundle args) {
        MainFragment fragment = new MainFragment();
        if (args != null)
            fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }


    /*@OnClick(R.id.bt_get)
    public void onClick() {
        prestener.requestUsdToGBP();
    }*/

    @Override
    public void updateView(String text) {
        tvShow.setText(text);
    }

    @Override
    public void errorHandle(Throwable throwable) {

    }

    @OnClick({R.id.bt_ration, R.id.bt_get})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_ration:
                break;
            case R.id.bt_get:
                prestener.requestUsdToGBP();
                break;
        }
    }
}
