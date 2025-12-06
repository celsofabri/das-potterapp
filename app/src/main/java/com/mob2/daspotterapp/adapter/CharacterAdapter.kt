package com.mob2.daspotterapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mob2.daspotterapp.R
import com.mob2.daspotterapp.model.Character

class CharacterAdapter(
    private val characters: List<Character>
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCharacterImage: ImageView = itemView.findViewById(R.id.ivCharacterImage)
        val tvCharacterName: TextView = itemView.findViewById(R.id.tvCharacterName)
        val tvCharacterHouse: TextView = itemView.findViewById(R.id.tvCharacterHouse)
        val tvCharacterActor: TextView = itemView.findViewById(R.id.tvCharacterActor)
        val tvCharacterSpecies: TextView = itemView.findViewById(R.id.tvCharacterSpecies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character_card, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        
        holder.tvCharacterName.text = character.name
        
        // House
        if (!character.house.isNullOrEmpty()) {
            holder.tvCharacterHouse.text = character.house
            holder.tvCharacterHouse.visibility = View.VISIBLE
        } else {
            holder.tvCharacterHouse.visibility = View.GONE
        }
        
        // Actor
        if (!character.actor.isNullOrEmpty()) {
            holder.tvCharacterActor.text = "Ator: ${character.actor}"
            holder.tvCharacterActor.visibility = View.VISIBLE
        } else {
            holder.tvCharacterActor.visibility = View.GONE
        }
        
        // Species
        if (!character.species.isNullOrEmpty()) {
            holder.tvCharacterSpecies.text = "Espécie: ${character.species}"
            holder.tvCharacterSpecies.visibility = View.VISIBLE
        } else {
            holder.tvCharacterSpecies.visibility = View.GONE
        }
        
        // Load image with Glide
        if (!character.image.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(character.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivCharacterImage)
        } else {
            holder.ivCharacterImage.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    override fun getItemCount(): Int = characters.size
}
