package com.example.lifegame

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class GridAdapter(
    private val context: Context,
    private val gameOfLife: GameOfLife
) : BaseAdapter() {

    override fun getCount(): Int {
        return gameOfLife.boardSize * gameOfLife.boardSize
    }

    override fun getItem(position: Int): Any {
        return gameOfLife.isCellAlive(
            x = position / gameOfLife.boardSize,
            y = position % gameOfLife.boardSize
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(context)
        val size = parent!!.width / gameOfLife.boardSize
        val alive = getItem(position) as Boolean
        imageView.layoutParams = ViewGroup.LayoutParams(size, size)
        imageView.setImageResource(if (alive) R.drawable.cell_alive else R.drawable.cell_dead)
        imageView.setOnClickListener {
            gameOfLife.setLiveOrDie(position)
            notifyDataSetChanged()
        }
        return imageView
    }
}
