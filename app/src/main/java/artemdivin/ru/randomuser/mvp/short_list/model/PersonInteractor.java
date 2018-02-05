package artemdivin.ru.randomuser.mvp.short_list.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import artemdivin.ru.randomuser.entity.Location;
import artemdivin.ru.randomuser.entity.Name;
import artemdivin.ru.randomuser.entity.Person;
import artemdivin.ru.randomuser.entity.Picture;
import artemdivin.ru.randomuser.mvp.short_list.presenter.IDataReadyListener;
import artemdivin.ru.randomuser.network.RestManager;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 28.01.2018.
 */

public class PersonInteractor implements IPersonInteractor {

    @Override
    public void onLoadMoreData(final IDataReadyListener listener) {
        RestManager.getService().getRandomPerson().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String text = "";
                try {
                    if (response.isSuccessful()) {
                        text = response.body().string();
                        JSONArray objectArray = new JSONObject(text).getJSONArray("results");

                        List<Person> personData = saveDataToRealm(listener, objectArray);
                        listener.onLoadSuccess(personData);
                    }
                    else {
                        listener.onLoadFailure(response.message());
                    }

                }catch (IOException e) {
                    listener.onLoadFailure(e.getMessage());
                } catch (JSONException e) {
                    listener.onLoadFailure(e.getMessage());
                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                listener.onLoadFailure(t.getMessage());
            }
        });

    }

    //Берем локальные данные из БД
    @Override
    public void getLocalData(int offset, int limit, IDataReadyListener listener) {
        Realm realm = Realm.getDefaultInstance();
        List<Person> personList = new ArrayList<>();
        try{
            RealmResults<Person> personRealmResults = realm
                    .where(Person.class)
                    .findAll();
            personList = realm.copyFromRealm(personRealmResults);
            if (offset+limit < personList.size()) {
                listener.onLoadSuccess(personList.subList(offset, offset + limit));
            }
            else
            {
               listener.stopLoading();
            }

        }catch (RealmException e)
        {
            e.getMessage();
        }finally {
            realm.close();
        }

    }


    private List<Person> saveDataToRealm(IDataReadyListener listener, JSONArray objectArray) {
        Realm realm = Realm.getDefaultInstance();
        List<Person> nextData = new ArrayList<>();
        Person person;
        Name name;
        Location location;
        Picture picture;
        try {
            for (int i = 0; i < objectArray.length(); i++) {
                person = new Person();
                name = new Name();
                picture = new Picture();
                location = new Location();

                person.setEmail(objectArray.getJSONObject(i).getString("email"));
                person.setPhone(objectArray.getJSONObject(i).getString("phone"));
                person.setDob(objectArray.getJSONObject(i).getString("dob"));
                person.setAllreadyShown(false);

                name.setTitle(objectArray.getJSONObject(i).getJSONObject("name").getString(("title")));
                name.setFirst(objectArray.getJSONObject(i).getJSONObject("name").getString(("first")));
                name.setLast(objectArray.getJSONObject(i).getJSONObject("name").getString(("last")));
                person.setName(name);

                picture.setMedium(objectArray.getJSONObject(i).getJSONObject("picture").getString(("medium")));
                person.setPicture(picture);

                location.setCity(objectArray.getJSONObject(i).getJSONObject("location").getString(("city")));
                location.setState(objectArray.getJSONObject(i).getJSONObject("location").getString(("state")));
                location.setStreet(objectArray.getJSONObject(i).getJSONObject("location").getString(("street")));
                person.setLocation(location);

                nextData.add(person);
            }
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(nextData);
        realm.commitTransaction();
        }catch (Exception ex) {
            listener.onLoadFailure(ex.getMessage());
        }finally {
            realm.close();
        }
        return nextData;
    }
}
