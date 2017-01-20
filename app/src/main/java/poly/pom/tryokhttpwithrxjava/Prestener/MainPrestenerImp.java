package poly.pom.tryokhttpwithrxjava.Prestener;

import java.io.IOException;

import okhttp3.Response;
import poly.pom.tryokhttpwithrxjava.Utility.ApiManager;
import poly.pom.tryokhttpwithrxjava.View.MainView;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Roy.Leung on 11/1/17.
 */

public class MainPrestenerImp implements MainPrestener {
    private CompositeSubscription compositeSubsrciption;
    public MainView mainView;

    public static MainPrestener bind(MainView view) {


        return new MainPrestenerImp(view);
    }

    public MainPrestenerImp(MainView view) {
        compositeSubsrciption = new CompositeSubscription();
        mainView = view;
    }

    @Override
    public void requestUsdToGBP() {

        Action1<String> onNext = new Action1<String>() {
            @Override
            public void call(String s) {

                mainView.updateView(s);

            }
        };
        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

                mainView.errorHandle(throwable);

            }
        };
        compositeSubsrciption.add(ApiManager.requestUsdToGBP().map(new Func1<Response, String>() {
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
        }).subscribe(onNext, onError));


    }

    @Override
    public void unbind() {
        mainView = null;
        compositeSubsrciption.unsubscribe();
    }
}
