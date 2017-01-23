package poly.pom.tryokhttpwithrxjava.Prestener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Response;
import poly.pom.tryokhttpwithrxjava.Utility.ApiManager;
import poly.pom.tryokhttpwithrxjava.View.RatioView;
import poly.pom.tryokhttpwithrxjava.widget.Ratio;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Roy.Leung on 20/1/17.
 */

public class RatioPrestenerlmp implements RatioPrestener {

    private CompositeSubscription compositeSubsrciption;
    public RatioView ratioView;
    private static Observable<ArrayList<Ratio>> cacheRequest;


    public RatioPrestenerlmp(RatioView ratioView) {
        compositeSubsrciption = new CompositeSubscription();
        this.ratioView = ratioView;
    }

    public static RatioPrestenerlmp bind(RatioView ratioView) {
        return new RatioPrestenerlmp(ratioView);
    }

    @Override
    public void requestLatestForeignExchange() {
        Action1<ArrayList<Ratio>> onNext = new Action1<ArrayList<Ratio>>() {
            @Override
            public void call(ArrayList<Ratio> ratios) {
                ratioView.showRatioList(ratios);

            }
        };
        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ratioView.errorHandle(throwable);
            }
        };

        cacheRequest = ApiManager.requestLatestRate().map(new Func1<Response, ArrayList<Ratio>>() {
            @Override
            public ArrayList<Ratio> call(Response response) {
                JSONObject rates;
                try {
                    String jsonString = new String(response.body().string());
                    response.close();
                    JSONObject jsonObject = new JSONObject(jsonString);
                    rates = jsonObject.optJSONObject("rates");
                } catch (IOException e) {
                    throw Exceptions.propagate(e);
                } catch (JSONException e) {
                    throw Exceptions.propagate(e);
                }
                if (rates == null)
                    throw Exceptions.propagate(new NullPointerException());


                Iterator<String> keys = rates.keys();
                ArrayList<Ratio> ratioList = new ArrayList<Ratio>();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String val = rates.optString(key);
                    if (val == null) val = 0 + "";
                    Ratio ratioObject = new Ratio(key, val);
                    ratioList.add(ratioObject);
                }
                return ratioList;
            }
        }).cache();

        compositeSubsrciption.add(cacheRequest.subscribe(onNext, onError));

    }

    @Override
    public void tryRequestLatestForeignExchangeFromCache() {
        Action1<ArrayList<Ratio>> onNext = new Action1<ArrayList<Ratio>>() {
            @Override
            public void call(ArrayList<Ratio> ratios) {
                ratioView.showRatioList(ratios);

            }
        };
        Action1<Throwable> onError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ratioView.errorHandle(throwable);
            }
        };
        if (cacheRequest != null) {
            compositeSubsrciption.add(cacheRequest.subscribe(onNext, onError));

        } else {
            requestLatestForeignExchange();
        }

    }

    @Override
    public void unbind() {
        if (compositeSubsrciption != null)
            compositeSubsrciption.unsubscribe();

    }
}
