package com.reddit.image.saver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reddit.image.saveraver.R
import com.reddit.image.saver.model.Post
import com.reddit.image.saver.utils.cache.ImageLoader
import com.reddit.image.saver.utils.common.Constants.BEFORE_LOAD_SIZE
import com.reddit.image.saver.utils.common.Constants.DOT
import com.reddit.image.saver.utils.common.Constants.IMAGE_FORMAT_JPG
import com.reddit.image.saver.utils.common.Constants.IMAGE_FORMAT_PNG
import com.reddit.image.saver.utils.common.Constants.RESPONSE_KEY_DEFAULT
import com.reddit.image.saver.utils.common.Constants.RESPONSE_KEY_SELF

class PostAdapter(
    private val onDirectoryItemClick: OnPostClick,
    private val onReachEndListener: OnReachEndListener,
    private val context: Context
) :
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {
    private var list = mutableListOf<Post>()

    fun addPosts(posts: List<Post>) {
        list.addAll(posts)
        notifyDataSetChanged()
    }

    fun getPostByPosition(position: Int): Post {
        return list[position]
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return MyViewHolder(view, onDirectoryItemClick, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = list[position]
        val thumbnail = post.thumbnail
        val imageUrl = post.imageUrl
        holder.textViewAuthor?.text = post.author
        holder.textViewTitle?.text = post.title
        holder.textViewDateAdded?.text = post.dateAdded + " ago"
        holder.textViewCountComments?.text = post.commentsCounter

        val imageFormat = imageUrl.substringAfterLast(DOT)
        val finalUrl = if (imageFormat == IMAGE_FORMAT_JPG || imageFormat == IMAGE_FORMAT_PNG) {
            imageUrl
        } else {
            thumbnail
        }

        if (finalUrl == RESPONSE_KEY_SELF || finalUrl == RESPONSE_KEY_DEFAULT) {
            holder.imageViewImage!!.visibility = View.GONE
        } else {
            holder.imageViewImage!!.visibility = View.VISIBLE
            holder.imgLoader.displayImage(finalUrl, R.drawable.ic_reddit, holder.imageViewImage!!)
        }

        if (position > list.size - BEFORE_LOAD_SIZE) {
            onReachEndListener.onReachEnd()
        }
    }

    class MyViewHolder(itemView: View, onDirectoryItemClick: OnPostClick, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var textViewAuthor: TextView? = null
        var textViewTitle: TextView? = null
        var textViewDateAdded: TextView? = null
        var textViewCountComments: TextView? = null
        var imageViewImage: ImageView? = null
        val imgLoader = ImageLoader(context.applicationContext)

        init {
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor)
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewDateAdded = itemView.findViewById(R.id.textViewDateAdded)
            textViewCountComments = itemView.findViewById(R.id.textViewCountComments)
            imageViewImage = itemView.findViewById(R.id.imageViewImage)

            imageViewImage?.setOnClickListener {
                onDirectoryItemClick.onImageClick(
                    adapterPosition
                )
            }
        }
    }
}