package ru.pakad.presentation

import androidx.lifecycle.ViewModel
import ru.pakad.data.ShopListRepositoryImpl
import ru.pakad.domain.DeleteShopItemUseCase
import ru.pakad.domain.EditShopItemUseCase
import ru.pakad.domain.GetShopListUseCase
import ru.pakad.domain.ShopItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)

    }

    fun changeEnabledState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)


    }
}