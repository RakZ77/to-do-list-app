package kh.edu.rupp.to_dolistapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.to_dolistapp.R
import kh.edu.rupp.to_dolistapp.models.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users: MutableList<User> = ArrayList()

    // Fixed: MutableList<User> — no nullable items, matches UserRepository output
    fun setUsers(users: MutableList<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_user_item_view, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        private val txtStatus: TextView = itemView.findViewById(R.id.txtStatus)

        fun bind(user: User) {
            tvUsername.text = user.name
            tvEmail.text = user.email

            if (user.status == "Active") {
                txtStatus.text = "Active"
                txtStatus.setBackgroundResource(R.drawable.status_active_bg)
                txtStatus.setTextColor(Color.parseColor("#4CAF50"))
            } else {
                txtStatus.text = "Away"
                txtStatus.setBackgroundResource(R.drawable.status_away_bg)
                txtStatus.setTextColor(Color.parseColor("#D32F2F"))
            }
        }
    }
}
