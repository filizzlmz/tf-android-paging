package com.filizuzulmez.turkiyefinans2.adapter

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.filizuzulmez.turkiyefinans2.R
import com.filizuzulmez.turkiyefinans2.model.Result

class SectionListAdapter (var mActivity: Activity): PagingDataAdapter<Result, SectionListAdapter.ProductListViewHolder>(DiffUtilCallBack()){


    companion object {
        private var mCallBack: CallBack? = null
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(getItem(position)!!, mActivity, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_product_list, parent, false)

        val displayMetrics = DisplayMetrics()
        mActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val screenWidth = displayMetrics.widthPixels
        val layoutParams = inflater.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.height = (screenWidth / 2 * 1.3).toInt()
        inflater.layoutParams = layoutParams

        return ProductListViewHolder(inflater)
    }

    class ProductListViewHolder(view: View): RecyclerView.ViewHolder(view){

        val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val tvPrimaryGenreName: TextView = itemView.findViewById(R.id.tvPrimaryGenreName)
        val tvArtistName: TextView = itemView.findViewById(R.id.tvArtistName)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)



        fun bind(item: Result, activity: Activity, position: Int) {

            if (item.getArtworkUrl30() !== "") {
                setProductImage(item.getArtworkUrl30(), activity, ivProductImage)
            } else if (item.getArtworkUrl60() !== "") {
                setProductImage(item.getArtworkUrl60(), activity, ivProductImage)
            } else if (item.getArtworkUrl100() !== "") {
                setProductImage(item.getArtworkUrl100(), activity, ivProductImage)
            } else {
                ivProductImage!!.setImageResource(
                    activity.resources.getIdentifier(
                        "ic_not_found",
                        "drawable",
                        activity.packageName
                    )
                )
            }

            tvPrimaryGenreName.setText(item.getPrimaryGenreName())
            tvArtistName.setText(item.getArtistName())
            tvProductName.setText(item.getCollectionName())
            val price = activity.resources.getString(
                R.string.tv_price,
                item.getCollectionPrice().toString()
            )
            tvPrice!!.text = price

            itemView.setOnClickListener {
                if (mCallBack != null) {
                    mCallBack!!.onClickProduct(item, position)
                }
            }
        }

        fun setProductImage(url: String?, context: Activity?, imageView: ImageView?) {
            Glide.with(context!!)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_not_found)
                .into(imageView!!)
        }

    }

    fun setCallBack(callBack: CallBack?) {
        mCallBack = callBack
    }

    interface CallBack {
        fun onClickProduct(product: Result?, position: Int)
    }



    class DiffUtilCallBack: DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.trackId == newItem.trackId
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.trackId == newItem.trackId
        }

    }
}