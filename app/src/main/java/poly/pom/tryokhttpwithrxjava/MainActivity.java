package poly.pom.tryokhttpwithrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;
import poly.pom.tryokhttpwithrxjava.Utility.ApiManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_get)
    Button btGet;
    @BindView(R.id.tv_show)
    TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.bt_get)
    public void onClick() {

        Action1<String> onNext = new Action1<String>() {
            @Override
            public void call(String s) {
                tvShow.setText(s);

            }
        };
        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };
        ApiManager.requestUsdToGBP().map(new Func1<Response, String>() {
            @Override
            public String call(Response r) {
                String jsonString = null;
                try {
                    jsonString = r.body().string();
                } catch (IOException e) {
                    throw Exceptions.propagate(e);
                }
                return jsonString;
            }
        }).subscribe(onNext, onError);


    }
}
