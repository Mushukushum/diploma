//package com.example.diploma.screens.statistics
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.diploma.animals.AnimalList
//import com.example.diploma.animals.data_source.AnimalsRepository
//import com.example.diploma.map.data.MapCropPolygon
//import com.example.diploma.map.data.repository.MapRepository
//import com.example.diploma.navigation.Screen
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class StatisticsViewModel @Inject constructor(
//    private val animalsRepository: AnimalsRepository,
//    private val statisticsRepository: StatisticsRepository,
//    private val mapRepository: MapRepository
//): ViewModel() {
//
//    private val _animalsList = MutableStateFlow<List<AnimalList>>(emptyList())
//    val animalsList: StateFlow<List<AnimalList>> = _animalsList
//
//    private val _cropsList = MutableStateFlow<List<MapCropPolygon>>(emptyList())
//    val cropsList: StateFlow<List<MapCropPolygon>> = _cropsList
//
//    private val _animalsStatistics = MutableStateFlow<List<AnimalsStatistics>>(emptyList())
//    val animalsStatisctis: StateFlow<List<AnimalsStatistics>> = _animalsStatistics
//
//    private val _finances = MutableStateFlow<List<Screen.FinanceStatistics>>(emptyList())
//    val finances: StateFlow<List<Screen.FinanceStatistics>> = _finances
//
//
//    private val _cropsStatistics = MutableStateFlow<List<CropsStatistics>>(emptyList())
//    val cropsStatisctis: StateFlow<List<CropsStatistics>> = _cropsStatistics
//
//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            val animalTypes = animalsRepository.getExpandableItemsForType()
//            _animalsList.emit(animalTypes)
//            val crops = mapRepository.getPolygons()
//            _cropsList.emit(crops)
//            val animalstats = statisticsRepository.getAllAnimalInfo()
//            _animalsStatistics.emit(animalstats)
//            val financeStats = statisticsRepository.getAllStatistics()
//            _finances.emit(financeStats)
//            val cropStats = statisticsRepository.getAllCropsInfo()
//            _cropsStatistics.emit(cropStats)
//        }
//    }
//
//    fun insertIntoAnimalsTable(animalsStatistics: AnimalsStatistics){
//        viewModelScope.launch(Dispatchers.IO) {
//            statisticsRepository.insertAnimalData(animalsStatistics = animalsStatistics)
//        }
//    }
//
//    fun insertFinanceStatistics(financeStatistics: Screen.FinanceStatistics){
//        viewModelScope.launch(Dispatchers.IO) {
//            statisticsRepository.insertFinanceStatistics(financeStatistics = financeStatistics)
//        }
//    }
//
//    fun insertIntoCropsTable(cropsStatistics: CropsStatistics){
//        viewModelScope.launch (Dispatchers.IO){
//            statisticsRepository.insertCropsData(cropsStatistics = cropsStatistics)
//        }
//    }
//}