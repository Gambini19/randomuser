package artemdivin.ru.randomuser.mvp.full_person_information.presenter;

import artemdivin.ru.randomuser.entity.Person;

/**
 * Created by admin on 04.02.2018.
 */

public interface IResultListener {
    void onSuccess(Person result);
    void onFailure(String message);
}
