package artemdivin.ru.randomuser.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import artemdivin.ru.randomuser.R;
import artemdivin.ru.randomuser.entity.PersonModel;
import artemdivin.ru.randomuser.mvp.IFragmentChanger;
import artemdivin.ru.randomuser.mvp.MainActivity;
import artemdivin.ru.randomuser.mvp.full_person_information.view.FullInfoFragment;
import artemdivin.ru.randomuser.util.Constant;

/**
 * Created by admin on 28.01.2018.
 */

public class RandomPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PersonModel> personList = new ArrayList<PersonModel>();
    private Context context;

    public RandomPersonAdapter(Context context) {
        this.context = context;
    }

    public void update(List<PersonModel> modelList) {
        //personList.clear();
        if (personList.size() > 0) personList.remove(personList.size()-1);

        personList.addAll(modelList);
        notifyDataSetChanged();
    }

    public void stopProgress() {
        if (personList.size() > 0 && personList.get(personList.size()-1).getHolderType() == Constant.TYPE_PROGRESS) {
            personList.remove(personList.size() - 1);
            notifyItemRemoved(personList.size()-1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case Constant.TYPE_PERSON:
            View personView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_short_list_item, parent, false);
            return new ViewHolderShortList(personView);

            case Constant.TYPE_PROGRESS:
                View progressView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_short_list_progress_bar, parent, false);
                return new ViewHolderProgress(progressView);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return personList.get(position).getHolderType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case Constant.TYPE_PERSON:
            ViewHolderShortList  vhShortList = (ViewHolderShortList) holder;
            vhShortList.tvPersonInfo.setText(personList.get(position).getFullName());
            vhShortList.ivAvatar.setImageDrawable(null);
            Picasso.with(context).load(personList.get(position).getPhotoPath()).fit().noFade().into(vhShortList.ivAvatar);
            break;
            case Constant.TYPE_PROGRESS:
                ViewHolderProgress vhProgress = (ViewHolderProgress) holder;
                vhProgress.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return personList == null ? 0 : personList.size();
    }


    public class ViewHolderShortList extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPersonInfo;
        ImageView ivAvatar;

        ViewHolderShortList(View itemView) {
            super(itemView);
            tvPersonInfo = itemView.findViewById(R.id.tv_full_name);
            ivAvatar = itemView.findViewById(R.id.iv_photo_main);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            String email = "";
            if (getAdapterPosition() > -1)
             email = personList.get(getAdapterPosition()).getEmail();
            if (context != null && context instanceof MainActivity && email != null  && !email.equals(""))
            ((IFragmentChanger)context).onChangeFragment(FullInfoFragment.Instance(email), "FULL");
        }
    }

    public class ViewHolderProgress extends RecyclerView.ViewHolder  {
        ProgressBar progressBar;
        ViewHolderProgress(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pb_short_list);
        }
    }
}
