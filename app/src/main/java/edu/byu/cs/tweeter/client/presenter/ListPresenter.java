package edu.byu.cs.tweeter.client.presenter;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observer.ListObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class ListPresenter<T> extends UserPresenter implements ListObserver<T> {
    protected static final int PAGE_SIZE = 10;
    protected T last;
    protected boolean hasMorePages = true;
    public ListPresenter(ListView<T> view) {
        super(view);
    }

    public interface ListView<T> extends UserView {
        void setLoading(boolean value);
        void addItems(List<T> items);
    }

    public T getLast() {
        return last;
    }

    private void setLast(T last) {
        this.last = last;
    }

    public boolean isHasMorePages() {
        return hasMorePages;
    }

    private void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
    }
    /**
     * Called by the view to request that another page of "following" users be loaded.
     */
    public void loadMoreItems(User user) {
        if (!isLoading && hasMorePages) {
            ((ListView)view).setLoading(true);
            setLoading(true);
            getItems(Cache.getInstance().getCurrUserAuthToken(), user);
        }
    }

    public abstract void getItems(AuthToken authToken, User targetUser);
    /**
     * Adds new followees retrieved asynchronously from the service to the view.
     *
     * @param items    the retrieved T items.
     * @param hasMorePages whether or not there are more followees to be retrieved.
     */
    @Override
    public void handleSuccess(List<T> items, boolean hasMorePages) {
        setLast((items.size() > 0) ? items.get(items.size() - 1) : null);
        setHasMorePages(hasMorePages);

        //view.setLoading(false);
        ((ListView)view).addItems(items);
        setLoading(false);
    }
}
