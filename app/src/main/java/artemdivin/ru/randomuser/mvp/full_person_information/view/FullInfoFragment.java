package artemdivin.ru.randomuser.mvp.full_person_information.view;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import artemdivin.ru.randomuser.R;
import artemdivin.ru.randomuser.entity.Person;
import artemdivin.ru.randomuser.entity.PersonModel;
import artemdivin.ru.randomuser.mvp.MainActivity;
import artemdivin.ru.randomuser.mvp.full_person_information.presenter.FullPresenter;
import artemdivin.ru.randomuser.mvp.full_person_information.presenter.IFullPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullInfoFragment extends Fragment implements IFullView {

    @BindView(R.id.iv_avatar_full_fragment)
    ImageView ivAvatar;

    @BindView(R.id.tv_full_name_full_fragment)
    TextView tvFullName;

    @BindView(R.id.tv_birthday_full_fragment)
    TextView tvBirthday;

    @BindView(R.id.tv_address_full_fragment)
    TextView tvAddress;

    @BindView(R.id.tv_telephone_full_fragment)
    TextView tvTelephone;

    @BindView(R.id.tv_email_full_fragment)
    TextView tvEmail;
    private View view;
    private IFullPresenter fullPresenter;
    private PersonModel currentPeron;


    public static FullInfoFragment Instance(String email) {
        FullInfoFragment fullInfoFragment = new FullInfoFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        fullInfoFragment.setArguments(args);
        return fullInfoFragment;
    }

    public String getEmail() {
        return getArguments().getString("email", "");
    }
    private String getPhone() {
        return currentPeron.getTelephoneNumber();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_full_info, container, false);
        ButterKnife.bind(this, view);
        if (getEmail() != null) {
            fullPresenter = new FullPresenter(this);
            fullPresenter.getDataByEmail(getEmail());
        } else {
            Toast.makeText(getActivity(), "нет данных", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
        return view;
    }

    @OnClick(R.id.tv_telephone_full_fragment)
    public void telephoneClick(View view) {
        if (getPhone() != null) {
            String uri = "tel:" + getPhone();
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
            phoneIntent.setData(Uri.parse(uri));
            startActivity(phoneIntent);
        }
        else showMessage("no telephone");

    }

    @OnClick(R.id.tv_email_full_fragment)
    public void emailClick(View view) {

        String uriText = "mailto:" + getEmail();
        Uri uri = Uri.parse(uriText);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(uri);
        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    @Override
    public void showResult(PersonModel personModel) {
        currentPeron = personModel;

        tvFullName.setText(personModel.getFullName());
        tvAddress.setText(personModel.getAddress());
        tvBirthday.setText(personModel.getBirthday());
        tvEmail.setText(personModel.getEmail());
        tvEmail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvTelephone.setText(personModel.getTelephoneNumber());
        tvTelephone.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        Picasso.with(getActivity()).load(personModel.getPhotoPath()).placeholder(R.drawable.ic_svg_user).into(ivAvatar);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}

