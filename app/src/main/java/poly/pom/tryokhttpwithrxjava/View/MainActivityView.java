package poly.pom.tryokhttpwithrxjava.View;

/**
 * Created by Roy.Leung on 11/1/17.
 */
public interface MainActivityView {
    void updateView(String text);
    void errorHandle(Throwable throwable);
}
