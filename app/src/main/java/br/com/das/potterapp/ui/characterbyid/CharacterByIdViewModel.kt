package br.com.das.potterapp.ui.characterbyid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.das.potterapp.data.model.Character
import br.com.das.potterapp.data.repository.PotterRepository
import br.com.das.potterapp.util.Result
import kotlinx.coroutines.launch

class CharacterByIdViewModel : ViewModel() {
    
    private val repository = PotterRepository()
    
    private val _character = MutableLiveData<Result<List<Character>>>()
    val character: LiveData<Result<List<Character>>> = _character
    
    fun getCharacterById(id: String) {
        _character.value = Result.Loading
        viewModelScope.launch {
            _character.value = repository.getCharacterById(id)
        }
    }
}
