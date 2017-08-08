package poly.pom.tryokhttpwithrxjava.Prestener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import poly.pom.tryokhttpwithrxjava.Utility.ApiManager;
import poly.pom.tryokhttpwithrxjava.View.RatioView;
import poly.pom.tryokhttpwithrxjava.widget.Ratio;


/**
 * Created by Roy.Leung on 20/1/17.
 */

public class RatioPrestenerlmp implements RatioPrestener {

    private static Observable<ArrayList<Ratio>> cacheRequest;
    public RatioView ratioView;
    private CompositeDisposable compositeSubsrciption;


    public RatioPrestenerlmp(RatioView ratioView) {
        compositeSubsrciption = new CompositeDisposable();
        this.ratioView = ratioView;
    }

    public static RatioPrestenerlmp bind(RatioView ratioView) {
        return new RatioPrestenerlmp(ratioView);
    }

    @Override
    public void requestLatestForeignExchange() {
        Consumer<ArrayList<Ratio>> onNext = (ArrayList<Ratio> ratios) -> {
            ratioView.showRatioList(ratios);
        };

        Consumer<Throwable> onError = (Throwable throwable) -> {
            ratioView.errorHandle(throwable);
        };


        cacheRequest = ApiManager.requestLatestRate().map((Response response) -> {
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
        }).cache();

        compositeSubsrciption.add(cacheRequest.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));

    }

    @Override
    public void tryRequestLatestForeignExchangeFromCache() {
        Consumer<ArrayList<Ratio>> onNext = (ArrayList<Ratio> ratios) -> {
            ratioView.showRatioList(ratios);
        };


        Consumer<Throwable> onError = (Throwable throwable) -> {
            ratioView.errorHandle(throwable);
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
            compositeSubsrciption.dispose();

    }
}
