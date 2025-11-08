package br.com.das.potterapp.ui.staff

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.das.potterapp.data.model.Character
import br.com.das.potterapp.data.repository.PotterRepository
import br.com.das.potterapp.util.Result
import kotlinx.coroutines.launch

class HogwartsStaffViewModel : ViewModel() {
    
    private val repository = PotterRepository()
    
    private val _staff = MutableLiveData<Result<List<Character>>>()
    val staff: LiveData<Result<List<Character>>> = _staff
    
    fun getHogwartsStaff() {
        _staff.value = Result.Loading
        viewModelScope.launch {
            _staff.value = repository.getHogwartsStaff()
        }
    }
}
