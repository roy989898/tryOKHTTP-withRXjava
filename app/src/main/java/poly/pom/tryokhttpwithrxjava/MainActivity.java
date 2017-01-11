package poly.pom.tryokhttpwithrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import poly.pom.tryokhttpwithrxjava.Prestener.MainActivityPrestener;
import poly.pom.tryokhttpwithrxjava.Prestener.MainActivityPrestenerImp;
import poly.pom.tryokhttpwithrxjava.View.MainActivityView;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    @BindView(R.id.bt_get)
    Button btGet;
    @BindView(R.id.tv_show)
    TextView tvShow;
    private MainActivityPrestener prestener;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        prestener = MainActivityPrestenerImp.bind(this);


    }

    @OnClick(R.id.bt_get)
    public void onClick() {
        prestener.requestUsdToGBP();


    }

    @Override
    protected void onStop() {
        unbinder.unbind();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        prestener.unbind();
        super.onDestroy();
    }

    @Override
    public void updateView(String text) {
        tvShow.setText(text);

    }

    @Override
    public void errorHandle(Throwable throwable) {

    }
}
