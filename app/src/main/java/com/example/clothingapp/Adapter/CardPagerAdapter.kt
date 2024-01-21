import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clothingapp.Data.CardData
import com.example.clothingapp.databinding.CardItemBinding

class CardPagerAdapter(private val cardList: List<CardData>) :
    RecyclerView.Adapter<CardPagerAdapter.CardViewHolder>() {

    inner class CardViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: CardData) {
            binding.titleTextView.text = card.title
            binding.descriptionTextView.text = card.description
            binding.imageView.setImageResource(card.imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}
