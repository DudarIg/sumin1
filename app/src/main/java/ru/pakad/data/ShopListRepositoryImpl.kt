package ru.pakad.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.pakad.domain.ShopItem
import ru.pakad.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private val livedata = MutableLiveData<List<ShopItem>>()
    private var autoIncrementId = 0



    init {
        for(i in 0 until 100) {
            val item = ShopItem("Name $i", i, Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateLiveData()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateLiveData()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found!")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return livedata
    }

    private fun updateLiveData() {
        livedata.postValue(shopList.toList())
    }

}