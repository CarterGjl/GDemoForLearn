package aop.demo.jetpack.android.gdemoforlearn;

import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter extends ListAdapter<User, UserAdapter.UserItemViewHolder> {

    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<User>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull User oldUser, @NonNull User newUser) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldUser.getUserId() == newUser.getUserId();
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull User oldUser, @NonNull User newUser) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldUser.equals(newUser);
                }
            };
    static AsyncDifferConfig build = new AsyncDifferConfig.Builder(DIFF_CALLBACK).build();

    protected UserAdapter() {
//        super(DIFF_CALLBACK);
        super(build);
    }


//    protected UserAdapter() {
//
//
//    }


    @NonNull
    @Override
    public UserAdapter.UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user_list, parent, false);
        return new UserItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder holder, int position) {
        User user= getItem(position);
        if(user!=null) {
            holder.bindTo(user);
        }
    }

    static class UserItemViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, userId;
        private final ProgressBar mProgressBar;

        public UserItemViewHolder(View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            userName = itemView.findViewById(R.id.userName);
            mProgressBar = itemView.findViewById(R.id.pro);
        }

        public void bindTo(User user) {
            userName.setText(user.firstName);
            userId.setText(String.valueOf(user.userId));
            mProgressBar.setVisibility(user.isloading?View.VISIBLE:View.INVISIBLE);
        }
    }
}
