package com.whatsycrrop.dpmaker.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.whatsycrrop.dpmaker.R
import com.whatsycrrop.dpmaker.interfaceces.selectectposion

import java.util.ArrayList

class MyListAdapter(
    private val listdata: ArrayList<Int>,
    private val activity: Activity,
    private val selectectposion: selectectposion
) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {
    private var select = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(
            R.layout.row_resource,
            parent,
            false
        )
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        if (select == position) {
            holder.cv_change.backgroundTintList =
                ColorStateList.valueOf(activity.resources.getColor(R.color.purple_200))
        } else {
            holder.cv_change.backgroundTintList =
                ColorStateList.valueOf(activity.resources.getColor(R.color.white))

        }



        Glide.with(activity).load(listdata[position]).into(holder.imageView)
        holder.cv_change.setOnClickListener {

            select = position
            notifyDataSetChanged()
            selectectposion.potinodate(listdata[position])

        }
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var relativeLayout: ConstraintLayout
        var cv_change: CardView

        init {
            imageView = itemView.findViewById<View>(R.id.imageView) as ImageView
            relativeLayout = itemView.findViewById<View>(R.id.relativeLayout) as ConstraintLayout
            cv_change = itemView.findViewById<View>(R.id.cv_change) as CardView
        }
    }
}

