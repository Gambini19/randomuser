package artemdivin.ru.randomuser.mvp.full_person_information.model;

import android.util.Log;

import artemdivin.ru.randomuser.entity.Person;
import artemdivin.ru.randomuser.mvp.full_person_information.presenter.IResultListener;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

/**
 * Created by admin on 04.02.2018.
 */

public class FullInteractor implements IFullInteractor{

    @Override
    public void getDataByID(String email, IResultListener listener) {
        Realm realm = Realm.getDefaultInstance();
        Person result = null;
        try{
            Person person = realm
                    .where(Person.class)
                    .equalTo("email", email)
                    .findFirst();
            if (person != null) {
                result = realm.copyFromRealm(person);
                listener.onSuccess(result);

            }
            else {
                listener.onFailure("Нет такой записи");
            }

        }catch (RealmException e)
        {
            listener.onFailure(e.getMessage());
        }finally {
            realm.close();
        }
    }
}
