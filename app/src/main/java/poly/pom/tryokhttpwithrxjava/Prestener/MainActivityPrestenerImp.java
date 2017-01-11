package poly.pom.tryokhttpwithrxjava.Prestener;

import java.io.IOException;

import okhttp3.Response;
import poly.pom.tryokhttpwithrxjava.Utility.ApiManager;
import poly.pom.tryokhttpwithrxjava.View.MainActivityView;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Roy.Leung on 11/1/17.
 */

public class MainActivityPrestenerImp implements MainActivityPrestener {
    private CompositeSubscription compositeSubsrciption;
    private MainActivityView mainActivityView;

    public static MainActivityPrestener newInstance(MainActivityView view) {


        return new MainActivityPrestenerImp(view);
    }

    public MainActivityPrestenerImp(MainActivityView view) {
        compositeSubsrciption = new CompositeSubscription();
        mainActivityView = view;
    }

    @Override
    public void requestUsdToGBP() {

        Action1<String> onNext = new Action1<String>() {
            @Override
            public void call(String s) {
                mainActivityView.updateView(s);

            }
        };
        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                mainActivityView.errorHandle(throwable);

            }
        };
        compositeSubsrciption.add(ApiManager.requestUsdToGBP().map(new Func1<Response, String>() {
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
        }).subscribe(onNext, onError));


    }

    @Override
    public void unsubscribe() {
        compositeSubsrciption.unsubscribe();
    }
}
