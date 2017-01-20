package poly.pom.tryokhttpwithrxjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        MainFragment.newInstance(null);
        replaceFragment(MainFragment.newInstance(null), true, null);

    }


    public void replaceFragment(Fragment fragment, Boolean addToBackStack, String name) {
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, fragment);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}
