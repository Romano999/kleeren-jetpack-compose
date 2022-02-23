package nl.romano.kleeren.util

import nl.romano.kleeren.model.MProduct
import nl.romano.kleeren.model.UserSearch

class ObjectMother {
    companion object {
        fun genericProduct(): MProduct {
            return MProduct(
                name = "Test",
                description = "Test product",
                price = 10,
                productUrl = "https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg"
            )
        }

        fun genericProductList(): List<MProduct> {
            val productOne = MProduct(
                name = "Test 1",
                description = "Test product 1",
                price = 10,
                productUrl = "https://i.postimg.cc/nLsZZ8hn/unnamughked.png"
            )

            val productTwo = MProduct(
                name = "Test 2",
                description = "Test product 2",
                price = 20,
                productUrl = "https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg"
            )

            return listOf(productOne, productTwo)
        }

        fun genericUserSearchList(): List<UserSearch> {
            val userSearchOne = UserSearch(searchTerm = "Shirt")
            val userSearchTwo = UserSearch(searchTerm = "Pants")
            return listOf(userSearchOne, userSearchTwo)
        }
    }
}
