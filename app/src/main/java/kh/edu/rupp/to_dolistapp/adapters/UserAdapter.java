package kh.edu.rupp.to_dolistapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kh.edu.rupp.to_dolistapp.R;
import kh.edu.rupp.to_dolistapp.models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_user_item_view, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvEmail, txtStatus;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }

        public void bind(User user) {
            tvUsername.setText(user.getName());
            tvEmail.setText(user.getEmail());

            if(user.getStatus().equals("Active")){
                txtStatus.setText("Active");
                txtStatus.setBackgroundResource(R.drawable.status_active_bg);
                txtStatus.setTextColor(Color.parseColor("#4CAF50"));
            }
            else{
                txtStatus.setText("Away");
                txtStatus.setBackgroundResource(R.drawable.status_away_bg);
                txtStatus.setTextColor(Color.parseColor("#D32F2F"));
            }
        }

    }
}
