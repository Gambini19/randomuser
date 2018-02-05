package artemdivin.ru.randomuser.mvp.short_list.view;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import artemdivin.ru.randomuser.R;
import artemdivin.ru.randomuser.adapter.EndlessRecyclerViewScrollListener;
import artemdivin.ru.randomuser.adapter.RandomPersonAdapter;
import artemdivin.ru.randomuser.entity.PersonModel;
import artemdivin.ru.randomuser.mvp.short_list.presenter.PersonPresenter;
import artemdivin.ru.randomuser.network.InternetChecker;
import artemdivin.ru.randomuser.util.Constant;

public class PersonListFragment extends Fragment implements IPersonView {

    private RecyclerView rvPerson;
    private PersonPresenter personPresenter;
    private RandomPersonAdapter randomPersonAdapter;
    private LinearLayoutManager manager;
    private View fragmentView;
    private boolean isFirstEnter = true;
    private boolean isInternetAvailable;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static PersonListFragment Instance() {
        return new PersonListFragment();
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        if (isFirstEnter) {
            isInternetAvailable = InternetChecker.checkInternet(getActivity());
            fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
            rvPerson = fragmentView.findViewById(R.id.rv_person);

            randomPersonAdapter = new RandomPersonAdapter(getActivity());
            manager = new LinearLayoutManager(getActivity());
            rvPerson.setLayoutManager(manager);
            rvPerson.setDrawingCacheEnabled(true);
            rvPerson.setAdapter(randomPersonAdapter);
            personPresenter = new PersonPresenter(this);
            personPresenter.loadMoreData(isInternetAvailable);
            rvPerson.setOnScrollListener(new EndlessRecyclerViewScrollListener(manager){
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    isInternetAvailable = InternetChecker.checkInternet(getActivity());
                    personPresenter.loadMoreData(isInternetAvailable);
                }
            });
        }
        return fragmentView;
    }



    @Override
    public void onShowData(final List<PersonModel> modelList) {
         if (modelList.size() > 0) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    randomPersonAdapter.update(modelList);
                }
            });
        }

    }

    @Override
    public void showError(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopProgress() {
        showError("Все локальные данные загружены");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                randomPersonAdapter.stopProgress();
            }
        });
    }

    @Override
    public void onStop() {
        isFirstEnter = false;
        super.onStop();
    }

}
