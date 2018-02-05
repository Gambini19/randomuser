package artemdivin.ru.randomuser.mvp.full_person_information.presenter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import artemdivin.ru.randomuser.R;
import artemdivin.ru.randomuser.entity.Person;
import artemdivin.ru.randomuser.entity.PersonModel;
import artemdivin.ru.randomuser.mvp.full_person_information.model.FullInteractor;
import artemdivin.ru.randomuser.mvp.full_person_information.model.IFullInteractor;
import artemdivin.ru.randomuser.mvp.full_person_information.view.IFullView;
import butterknife.BindView;

/**
 * Created by admin on 04.02.2018.
 */

public class FullPresenter implements IFullPresenter, IResultListener {
    private IFullInteractor fullInteractor;
    private IFullView view;

    public FullPresenter(IFullView view) {
        this.fullInteractor = new FullInteractor();
        this.view = view;
    }

    @Override
    public void getDataByEmail(String email) {
        fullInteractor.getDataByID(email, this);
    }

    @Override
    public void onSuccess(Person result) {
        PersonModel personModel = new PersonModel();
        personModel.setPhotoPath(result.getPicture().getMedium());
        personModel.setFullName(result.getName().getTitle()
                + " "+result.getName().getFirst()
                + " " + result.getName().getLast());
        personModel.setBirthday(result.getDob());
        personModel.setAddress(result.getLocation().getCity() + ", " + result.getLocation().getStreet());
        personModel.setEmail(result.getEmail());
        personModel.setTelephoneNumber(result.getPhone());

        view.showResult(personModel);
    }

    @Override
    public void onFailure(String message) {
        view.showMessage(message);

    }
}
