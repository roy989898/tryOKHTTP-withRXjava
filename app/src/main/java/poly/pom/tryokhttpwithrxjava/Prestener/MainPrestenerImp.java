package poly.pom.tryokhttpwithrxjava.Prestener;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import poly.pom.tryokhttpwithrxjava.Utility.ApiManager;
import poly.pom.tryokhttpwithrxjava.View.MainView;

/**
 * Created by Roy.Leung on 11/1/17.
 */

public class MainPrestenerImp implements MainPrestener {
    public MainView mainView;
    private CompositeDisposable compositeSubsrciption;

    public MainPrestenerImp(MainView view) {
        compositeSubsrciption = new CompositeDisposable();
        mainView = view;
    }

    public static MainPrestener bind(MainView view) {


        return new MainPrestenerImp(view);
    }

    @Override
    public void requestUsdToGBP() {

        Consumer<String> onNext = (String s) -> {
            mainView.updateView(s);
        };
        Consumer<Throwable> onError = (Throwable throwable) -> {
            mainView.errorHandle(throwable);
        };
      /*  compositeSubsrciption.add(ApiManager.requestUsdToGBP().map(new Func1<Response, String>() {
            @Override
            public String call(Response r) {
                String jsonString = null;
                try {
                    jsonString = new String(r.body().string());
                    r.close();

                } catch (IOException e) {
                    throw Exceptions.propagate(e);
                }
                return jsonString;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));*/

        compositeSubsrciption.add(ApiManager.requestUsdToGBP().map((Response r) -> {
            String jsonString = null;
            try {
                jsonString = new String(r.body().string());
                r.close();

            } catch (IOException e) {
                throw Exceptions.propagate(e);
            }
            return jsonString;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));


    }

    @Override
    public void unbind() {
        mainView = null;
        compositeSubsrciption.dispose();
    }
}
