package john.snow.rickandmorty.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import john.snow.rickandmorty.R
import john.snow.rickandmorty.model.RMCharacter
import kotlinx.android.synthetic.main.cell_character.view.*


class CharactersAdapter(
        private val clickListener: OnCharacterClickListener
) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {
    private var data: MutableList<RMCharacter?> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_character, parent, false)
        return CharacterViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        data[position]?.let { character ->
            holder.bind(character)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CharacterViewHolder(itemView: View, private val clickListener: OnCharacterClickListener) : RecyclerView.ViewHolder(itemView) {
        fun bind(character: RMCharacter) {
            itemView.apply {
                nameTextView.text = character.name
                statusTextView.text = character.status
                Picasso.get().load(character.image).into(characterImageView)
                setOnClickListener { clickListener.onClick(character) }
            }
        }
    }

    fun setCharacters(characters: List<RMCharacter>) {
        data.clear()
        addCharacters(characters)
    }

    fun addCharacters(characters: List<RMCharacter>) {
        data.addAll(characters)
        notifyDataSetChanged()
    }

    interface OnCharacterClickListener {
        fun onClick(character: RMCharacter)
    }
}