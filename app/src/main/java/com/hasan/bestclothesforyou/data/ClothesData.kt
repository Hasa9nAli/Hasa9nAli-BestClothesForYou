package com.hasan.bestclothesforyou.data
private val listOfClothes = mutableListOf<ClothesData>()
data class ClothesData(
    var id : String,
    val name : String,
    val imageUrl : String,
    val weatherDegree : Pair<Int, Int>,
    val Category : String,
    val seasonType : String
)
fun getAllClothes(): List<ClothesData> {
    return listOf(
        ClothesData(
            id = "1" ,
            name = "T-shirt",
            imageUrl = "https://cdn.pixabay.com/photo/2016/12/06/09/31/blank-1886008__340.png",
            weatherDegree = Pair(0, 50),
            Category = "T-shirt",
            seasonType = "summer"
        ),
        ClothesData(
            id = "2",
            name = "Cote",
            imageUrl = "https://images.unsplash.com/photo-1585412459212-8def26f7e84c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80",
            weatherDegree = Pair(0, 50),
            Category = "T-shirt",
            seasonType = "summer"
            ),
        ClothesData(
            id = "3",
            name = "Cote",
            imageUrl = "https://cdn.pixabay.com/photo/2017/01/13/04/56/t-shirt-1976334_960_720.png",
            weatherDegree = Pair(0, 50),
            Category = "T-shirt",
            seasonType = "summer"
          ),
    )
}
fun addNewClothes(clothes : ClothesData){
    listOfClothes.add(clothes)
}