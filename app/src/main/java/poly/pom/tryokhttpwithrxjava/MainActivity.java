package poly.pom.tryokhttpwithrxjava;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import poly.pom.tryokhttpwithrxjava.View.MainFragment;

public class MainActivity extends AppCompatActivity {


    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, MainFragment.newInstance(null));
        fragmentTransaction.commit();


    }


}
