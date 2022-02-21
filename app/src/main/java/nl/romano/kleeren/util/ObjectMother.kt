package nl.romano.kleeren.util

import nl.romano.kleeren.model.MProduct

class ObjectMother {
    companion object {
        fun genericProduct(): MProduct {
            return MProduct(
                name = "Test",
                description = "Test product",
                price = 10,
                productImageURL = "https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg"
            )
        }

        fun genericProductList(): List<MProduct> {
            val productOne = MProduct(
                name = "Test 1",
                description = "Test product 1",
                price = 10,
                productImageURL = "https://i.postimg.cc/nLsZZ8hn/unnamughked.png"
            )

            val productTwo = MProduct(
                name = "Test 2",
                description = "Test product 2",
                price = 20,
                productImageURL = "https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg"
            )

            return listOf(productOne, productTwo)
        }
    }
}
