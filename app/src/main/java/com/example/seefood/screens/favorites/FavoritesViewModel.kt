package com.example.seefood.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seefood.database.objects.Dish
import com.example.seefood.database.repos.DishRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavoritesViewModelAbstract  {
   val dishesListFlow: Flow<List<Dish>>
   fun unfavoriteDish(dish: Dish)
   fun addDish(dish: Dish)
}

@HiltViewModel
class FavoritesViewModel
@Inject constructor(
   private val dishRepository: DishRepository
) : ViewModel(), FavoritesViewModelAbstract {

   override val dishesListFlow: Flow<List<Dish>>
      get() = dishRepository.getFavoriteDishes()


   override fun unfavoriteDish(dish: Dish) {
      if (dish.category == ""){
         viewModelScope.launch { dishRepository.deleteDish(dish) }
      }
      else {
         viewModelScope.launch {
            dishRepository.upsertDish(
               Dish(
                  name = dish.name,
                  recipe = dish.recipe,
                  imgLocalPath = dish.imgLocalPath,
                  category = dish.category,
                  isFavorite = false,

                  id = dish.id
               )
            )
         }
      }
   }

   override fun addDish(dish: Dish) {
      viewModelScope.launch { dishRepository.upsertDish(dish) }
   }
}