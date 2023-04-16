package com.hasan.bestclothesforyou.ui.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hasan.bestclothesforyou.R
import com.hasan.bestclothesforyou.data.Season
import com.hasan.bestclothesforyou.databinding.SeasonChipBinding
import com.hasan.bestclothesforyou.ui.adapter.SeasonAdapter.*

class SeasonAdapter(private val seasonList : List<Season>)
    : RecyclerView.Adapter<SeasonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val viewInflate = LayoutInflater.from(parent.context).inflate(R.layout.season_chip, parent, false)
        return SeasonViewHolder(viewInflate)
    }

    override fun getItemCount(): Int = seasonList.size

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = seasonList[position]
        holder.binding.apply {
            when (season.name) {
                SUMMER_SEASON -> {
                    imageView.setImageResource(R.drawable.icon_summer)
                    textView4.text = SUMMER_SEASON
                }
                WINTER_SEASON -> {
                    imageView.setImageResource(R.drawable.icon_clod)
                    textView4.text = WINTER_SEASON
                }
                SPRINT_SEASON -> {
                    imageView.setImageResource(R.drawable.icon_spring)
                    textView4.text = SPRINT_SEASON
                }
                AUTUMN_SEASON -> {
                    imageView.setImageResource(R.drawable.icon_autmn)
                    textView4.text = AUTUMN_SEASON
                }
            }
            var flage = true
            cardSeason.setOnClickListener {
                if (!(season.isSelected == flage)) {
                    cardSeason.setBackgroundColor(Color.rgb(0, 68, 148))
                    textView4.setTextColor(Color.rgb( 255, 255, 255))
//                    seasonList[position].isSelected = !seasonList[position].isSelected!!
                    season.isSelected = true
                }
                else{
                    cardSeason.setBackgroundColor(Color.rgb(250, 252, 255))
                    textView4.setTextColor(Color.rgb( 0, 0, 0))
                    season.isSelected = false
                }
            }
        }
    }

    class SeasonViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SeasonChipBinding.bind(itemView)

    }
    private companion object{
        const val SUMMER_SEASON = "summer"
        const val WINTER_SEASON = "winter"
        const val SPRINT_SEASON = "spring"
        const val AUTUMN_SEASON = "autumn"
    }
}