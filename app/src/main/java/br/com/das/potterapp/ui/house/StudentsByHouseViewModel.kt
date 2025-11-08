package br.com.das.potterapp.ui.house

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.das.potterapp.data.model.Character
import br.com.das.potterapp.data.repository.PotterRepository
import br.com.das.potterapp.util.Result
import kotlinx.coroutines.launch

class StudentsByHouseViewModel : ViewModel() {
    
    private val repository = PotterRepository()
    
    private val _students = MutableLiveData<Result<List<Character>>>()
    val students: LiveData<Result<List<Character>>> = _students
    
    fun getStudentsByHouse(house: String) {
        _students.value = Result.Loading
        viewModelScope.launch {
            _students.value = repository.getCharactersByHouse(house)
        }
    }
}
