package artemdivin.ru.randomuser.mvp.short_list.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import artemdivin.ru.randomuser.entity.Name;
import artemdivin.ru.randomuser.entity.Person;
import artemdivin.ru.randomuser.entity.PersonModel;
import artemdivin.ru.randomuser.mvp.short_list.model.IPersonInteractor;
import artemdivin.ru.randomuser.mvp.short_list.model.PersonInteractor;
import artemdivin.ru.randomuser.mvp.short_list.view.IPersonView;
import artemdivin.ru.randomuser.util.Constant;

/**
 * Created by admin on 28.01.2018.
 */

public class PersonPresenter implements IPersonPresenter, IDataReadyListener {
    private IPersonView personView;
    private IPersonInteractor personInteractor;
    private int offset = 0;

    public PersonPresenter(IPersonView personView) {
        this.personView = personView;
        this.personInteractor = new PersonInteractor();
    }

    @Override
    public void loadMoreData(boolean isInternetAvailable) {
        if (isInternetAvailable)
        personInteractor.onLoadMoreData(this);

        else
        {
            personInteractor.getLocalData(getOffset(), Constant.LIMIT, this);
            setOffset(offset + Constant.LIMIT);
        }

    }

    @Override
    public void onLoadSuccess(List<Person> personList) {
        Log.d("TAG", "onLoadSuccess: " + personList.size());
        List<PersonModel> modelList = new ArrayList<>();
        PersonModel person;
        Name name;
        for (int i = 0; i < personList.size(); i++) {
            person = new PersonModel();
            //person.setFullName("fff fff fff");
            person.setFullName(personList.get(i).getName().getTitle() + " " + personList.get(i).getName().getFirst() + " " + personList.get(i).getName().getLast());
            person.setPhotoPath(personList.get(i).getPicture().getMedium());
            person.setHolderType(Constant.TYPE_PERSON);
            person.setEmail(personList.get(i).getEmail());
            modelList.add(person);
        }
        if (personList.size()  == Constant.LIMIT) {
            person = new PersonModel();
            person.setHolderType(Constant.TYPE_PROGRESS);
            modelList.add(person);
        }

        personView.onShowData(modelList);
    }


    @Override
    public void onLoadFailure(String errorText) {
        personView.showError(errorText);

    }

    @Override
    public void stopLoading() {
        personView.stopProgress();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
