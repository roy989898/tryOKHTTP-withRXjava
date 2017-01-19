package poly.pom.tryokhttpwithrxjava.View;

/**
 * Created by Roy.Leung on 11/1/17.
 */
public interface MainView {
    void updateView(String text);
    void errorHandle(Throwable throwable);
}
