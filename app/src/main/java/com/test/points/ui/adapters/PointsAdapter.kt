package com.test.points.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.points.business.models.Point
import com.test.points.databinding.PointItemBinding

internal class PointsAdapter() : RecyclerView.Adapter<PointsAdapter.PointsItemHolder>() {
    private var list: ArrayList<Point> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PointsItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PointsItemHolder(PointItemBinding.inflate(inflater))
    }

    override fun onBindViewHolder(
        holder: PointsItemHolder,
        position: Int
    ) {
        holder.onBind(position)
    }

    override fun getItemCount() = list.count()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(points: ArrayList<Point>) {
        list = points
        notifyDataSetChanged()
    }

    inner class PointsItemHolder(private val binding: PointItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(position: Int) {
            val point = list[position]
            binding.coordX.text = "x: ${point.x}"
            binding.coordY.text = "y: ${point.y}"
        }
    }
}
