package poly.pom.tryokhttpwithrxjava.Utility;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.schedulers.Schedulers;



public class ApiManager {

//    http://api.fixer.io/latest?symbols=USD,GBP

    public static Observable<Response> requestUsdToGBP() {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://api.fixer.io/latest?symbols=USD,GBP").build();
        return Observable.defer(new Func0<Observable<Response>>() {
            @Override
            public Observable<Response> call() {

                try {
                    return Observable.just(client.newCall(request).execute());
                } catch (IOException e) {
                    throw Exceptions.propagate(e);
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
