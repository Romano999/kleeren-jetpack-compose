package nl.romano.kleeren.util

import nl.romano.kleeren.model.MProduct

/*
    This class is made to fill out firebase database with products.
    This is done so that any new firebase connecting to this project
    can be filled with a selection of products.
 */
class WebshopProducts {
    companion object {
        fun fillWebshopWithProducts(): List<MProduct> {
            val productOne = MProduct(
                name = "Blue Sneakers",
                description = "Blue sneakers made of the finest materials for any gender.",
                price = 10,
                productUrl = "https://i.postimg.cc/g0r4JwG0/blue-sneakers.jpg"
            )

            val productTwo = MProduct(
                name = "Designer Shoes",
                description = "Modern shoes for the trendiest people around.",
                price = 20,
                productUrl = "https://i.postimg.cc/zfB5GkLR/designer-shoes.jpg"
            )

            val productThree = MProduct(
                name = "Grey Boys Jeans",
                description = "Grey jeans for the boys!",
                price = 20,
                productUrl = "https://i.postimg.cc/VLLKTZ2M/grey-boys-jeans.webp"
            )

            val productFour = MProduct(
                name = "LA Cap",
                description = "LA cap with trendy camouflage",
                price = 20,
                productUrl = "https://i.postimg.cc/66DdVjtt/la-cap.jpg"
            )

            val productFive = MProduct(
                name = "Levis Blue Jeans",
                description = "Comfortable jeans from a popular brand.",
                price = 20,
                productUrl = "https://i.postimg.cc/1t1Qr1CD/levis-bluejeans.jpg"
            )

            val productSix = MProduct(
                name = "Nike Cap",
                description = "Red Nike cap suitable for all!",
                price = 20,
                productUrl = "https://i.postimg.cc/qMh146MM/nike-cap.jpg"
            )

            val productSeven = MProduct(
                name = "Oakley Cap",
                description = "Black cap comfortable for all",
                price = 20,
                productUrl = "https://i.postimg.cc/RCnfXkSq/oakley-cap.jpg"
            )

            val productEight = MProduct(
                name = "Grey Liano Jeans",
                description = "Skinny jeans for men.",
                price = 20,
                productUrl = "https://i.postimg.cc/XJ59tdkT/skinnyjeans-grey-liano.jpg"
            )

            val productNine = MProduct(
                name = "Vans Shoes",
                description = "Yellow/Orange shoes for all.",
                price = 20,
                productUrl = "https://i.postimg.cc/0ypR5B2Q/vans-shoes.jpg"
            )

            val productTen = MProduct(
                name = "Black Shirt",
                description = "Simple black shirt for all occasions.",
                price = 20,
                productUrl = "https://i.postimg.cc/WbY6FNzY/black-shirt.webp"
            )

            val productEleven = MProduct(
                name = "Blue Shirt",
                description = "Simple blue shirt for all.",
                price = 20,
                productUrl = "https://i.postimg.cc/cJy3wrSh/yellowshirt.jpg"
            )

            val productTwelve = MProduct(
                name = "Yellow Shirt",
                description = "Yellow shirt made from the best materials!",
                price = 20,
                productUrl = "https://i.postimg.cc/fLbR9NJ5/blue-shirt.jpg"
            )

            return listOf(
                productOne,
                productTwo,
                productThree,
                productFour,
                productFive,
                productSix,
                productSeven,
                productEight,
                productNine,
                productTen,
                productEleven,
                productTwelve
            )
        }
    }
}
