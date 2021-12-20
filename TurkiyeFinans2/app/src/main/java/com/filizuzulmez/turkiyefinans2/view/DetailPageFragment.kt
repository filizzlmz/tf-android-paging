package com.filizuzulmez.turkiyefinans2.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.filizuzulmez.turkiyefinans2.R
import com.filizuzulmez.turkiyefinans2.model.Result


class DetailPageFragment : Fragment() {

    private var mProduct: Result? = null

    private var ivDetail: ImageView? = null
    private var tvArtistNameDetail: TextView? = null
    private  var tvProductNameDetail:TextView? = null
    private var btnUrl: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_detail_page, container, false)

        ivDetail = v.findViewById(R.id.ivDetail)
        tvArtistNameDetail = v.findViewById(R.id.tvArtistNameDetail)
        tvProductNameDetail = v.findViewById(R.id.tvProductNameDetail)
        btnUrl = v.findViewById(R.id.btnUrl)

        setPageDetail()

        onClickBtn()

        return v
    }

    private fun onClickBtn() {
        btnUrl!!.setOnClickListener {

            if(mProduct!!.artistViewUrl != null && mProduct!!.artistViewUrl != ""){
                if (mProduct!!.artistViewUrl.startsWith("https://") || mProduct!!.artistViewUrl.startsWith("http://")) {
                    val uri: Uri = Uri.parse(mProduct!!.artistViewUrl)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                } else {
                    Toast.makeText(activity, "Invalid Url", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    private fun setPageDetail() {
        tvArtistNameDetail!!.text = mProduct!!.artistName
        tvProductNameDetail!!.text = mProduct!!.collectionName
        if (mProduct!!.artworkUrl30 !== "") {
            setProductImage(mProduct!!.artworkUrl30, ivDetail)
        } else if (mProduct!!.artworkUrl60 !== "") {
            setProductImage(mProduct!!.artworkUrl60, ivDetail)
        } else if (mProduct!!.artworkUrl100 !== "") {
            setProductImage(mProduct!!.artworkUrl100, ivDetail)
        } else {
            ivDetail!!.setImageResource(
                requireActivity().resources.getIdentifier(
                    "ic_not_found",
                    "drawable",
                    requireActivity().packageName
                )
            )
        }
    }

    fun setProductImage(url: String?, imageView: ImageView?) {
        Glide.with(requireActivity())
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.ic_not_found)
            .into(imageView!!)
    }

    fun getData(product: Result) {
        mProduct = product
        Log.d("detail page: ", mProduct!!.getArtistName())
    }
}