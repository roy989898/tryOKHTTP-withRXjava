package poly.pom.tryokhttpwithrxjava.Prestener;

/**
 * Created by Roy.Leung on 20/1/17.
 */

public interface RatioPrestener extends BasePrestener {
    void requestLatestForeignExchange();
    void tryRequestLatestForeignExchangeFromCache();
}
