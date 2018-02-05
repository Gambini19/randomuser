package artemdivin.ru.randomuser.mvp.full_person_information.model;

import artemdivin.ru.randomuser.mvp.full_person_information.presenter.IResultListener;

/**
 * Created by admin on 04.02.2018.
 */

public interface IFullInteractor {
    void getDataByID(String email, IResultListener listener);
}
