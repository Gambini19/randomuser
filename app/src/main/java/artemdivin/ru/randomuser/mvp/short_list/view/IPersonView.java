package artemdivin.ru.randomuser.mvp.short_list.view;

import java.util.List;

import artemdivin.ru.randomuser.entity.PersonModel;

/**
 * Created by admin on 28.01.2018.
 */

public interface IPersonView {

    void onShowData(List<PersonModel> modelList);
    void showError(String text);
    void stopProgress();


}
