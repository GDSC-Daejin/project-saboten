package commonClient.domain.entity.user

/*
"myPost": 0,
    "scrapedPost": 0,
    "votedPost": 0
* */
data class MyPageCount(
    val myPost: Long,
    val scrapedPost: Long,
    val votedPost: Long,
)