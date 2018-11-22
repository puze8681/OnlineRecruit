package kr.puze.onlinerecruit.Adapter

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.view.ViewGroup
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import kr.puze.onlinerecruit.Data.MainData
import kr.puze.onlinerecruit.Data.Repo
import kr.puze.onlinerecruit.Data.User
import kr.puze.onlinerecruit.R

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val OWNER_VIEW = 0
    private val REPO_VIEW = 1

    private var mContext: Context? = null
    private var mData: ArrayList<MainData>? = null

    fun RecyclerViewAdapter(mContext: Context) {
        this.mContext = mContext
    }

    fun addItems(item: MainData) {
        if (mData == null) {
            mData = ArrayList()
        }
        mData!!.add(item)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val (_, type) = mData!!.get(position)

        return when (type) {
            "owner" -> OWNER_VIEW
            "repo" -> REPO_VIEW
            else -> super.getItemViewType(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var view: View? = null

        return when (viewType) {
            OWNER_VIEW -> {
                view = inflater.inflate(R.layout.item_owner, parent, false)
                OwnerHolder(view!!)
            }
            REPO_VIEW -> {
                view = inflater.inflate(R.layout.item_repo, parent, false)
                RepoHolder(view!!)
            }
            else -> {
                ViewHolder(view!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mData == null) 0 else mData!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val (_, userImage, userName, repoName, repoDescription, repoStar) = mData!!.get(position)

        if (viewType == OWNER_VIEW) {
            val ownerHolder = holder as OwnerHolder
            Glide.with(holder.itemView.context)
                    .load(userImage)
                    .into(ownerHolder.userImageView)
            ownerHolder.userNameView.text = userName
        } else if (viewType == REPO_VIEW) {
            val repoHolder = holder as RepoHolder
            repoHolder.repoNameView.text = repoName
            repoHolder.repoDescriptionView.text = repoDescription
            repoHolder.repoStartView.text = repoStar

        }
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class OwnerHolder(itemView: View) : ViewHolder(itemView) {
        internal var userImageView: AppCompatImageView = itemView.findViewById(R.id.owner_image)
        internal var userNameView: AppCompatTextView = itemView.findViewById(R.id.owner_text)
    }

    class RepoHolder(itemView: View) : ViewHolder(itemView) {
        internal var repoNameView: AppCompatTextView = itemView.findViewById(R.id.repo_name)
        internal var repoDescriptionView: AppCompatTextView = itemView.findViewById(R.id.repo_description)
        internal var repoStartView: AppCompatTextView = itemView.findViewById(R.id.repo_star)
    }
}