package artemdivin.ru.randomuser.mvp.short_list.presenter;

import java.util.List;

import artemdivin.ru.randomuser.entity.Person;

/**
 * Created by admin on 28.01.2018.
 */

public interface IDataReadyListener {
    void onLoadSuccess(List<Person> personList);
    void onLoadFailure(String errorText);
    void stopLoading();
}
