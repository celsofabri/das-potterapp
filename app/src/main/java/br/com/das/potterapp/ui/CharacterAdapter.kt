package br.com.das.potterapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.das.potterapp.R
import br.com.das.potterapp.data.model.Character
import br.com.das.potterapp.databinding.ItemCharacterBinding
import coil.load
import coil.transform.CircleCropTransformation

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    
    private var characters = listOf<Character>()
    
    fun submitList(newCharacters: List<Character>) {
        characters = newCharacters
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }
    
    override fun getItemCount(): Int = characters.size
    
    class CharacterViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(character: Character) {
            binding.tvName.text = character.name
            binding.tvHouse.text = if (character.house.isNotEmpty()) {
                character.house
            } else {
                "Casa não informada"
            }
            
            // Load character image with Coil
            binding.ivCharacter.load(character.image) {
                crossfade(true)
                placeholder(R.drawable.ic_person_placeholder)
                error(R.drawable.ic_person_placeholder)
                transformations(CircleCropTransformation())
            }
        }
    }
}
