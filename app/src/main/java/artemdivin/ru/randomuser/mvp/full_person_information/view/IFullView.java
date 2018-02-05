package artemdivin.ru.randomuser.mvp.full_person_information.view;

import artemdivin.ru.randomuser.entity.PersonModel;

/**
 * Created by admin on 04.02.2018.
 */

public interface IFullView {
    void showResult(PersonModel personModel);
    void showMessage(String text);
}
