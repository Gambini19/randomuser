package artemdivin.ru.randomuser.mvp.short_list.model;

import artemdivin.ru.randomuser.mvp.short_list.presenter.IDataReadyListener;

/**
 * Created by admin on 28.01.2018.
 */

public interface IPersonInteractor {
    void onLoadMoreData(IDataReadyListener listener);
    void getLocalData(int offset, int limit, IDataReadyListener listener);
}
