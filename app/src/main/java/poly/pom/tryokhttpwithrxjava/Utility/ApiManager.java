package poly.pom.tryokhttpwithrxjava.Utility;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.exceptions.Exceptions;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class ApiManager {

//    http://api.fixer.io/latest?symbols=USD,GBP

    public static Observable<Response> requestUsdToGBP() {
        final Request request = new Request.Builder().url("http://api.fixer.io/latest?symbols=USD,GBP").build();
        return responseObservableCreater(request);
    }

    public static Observable<Response> requestLatestRate() {
        final Request request = new Request.Builder().url("http://api.fixer.io/latest").build();
        return responseObservableCreater(request);

    }

    private static Observable<Response> responseObservableCreater(final Request request) {
        final OkHttpClient client = new OkHttpClient();
        /*return Observable.defer(new Func0<Observable<Response>>() {
            @Override
            public Observable<Response> call() {
                try {
                    return Observable.just(client.newCall(request).execute());
                } catch (IOException e) {
                    throw Exceptions.propagate(e);
                }
            }
        });*/

        return Observable.defer(() -> {
            try {
                return Observable.just(client.newCall(request).execute());
            } catch (IOException e) {
                throw Exceptions.propagate(e);
            }
        });
    }
}
