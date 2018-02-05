package artemdivin.ru.randomuser.mvp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import artemdivin.ru.randomuser.R;
import artemdivin.ru.randomuser.mvp.short_list.view.PersonListFragment;
import artemdivin.ru.randomuser.util.Constant;

public class MainActivity extends AppCompatActivity implements IFragmentChanger {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFragment(PersonListFragment.Instance(), Constant.SHORT_FRAGMENT_TAG);
    }



    private void showFragment(Fragment fr, String tag) {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_place, fr, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onChangeFragment(Fragment fr, String fragmentTag) {
        showFragment(fr, fragmentTag);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1)
        {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(a);
            getFragmentManager().popBackStack(1,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        else super.onBackPressed();
    }
}
