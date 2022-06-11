object Properties {

    const val androidMinSDK = 24
    const val androidTargetSDK = 32
    const val androidCompileSDK = 32
    const val androidPackageName = "app.saboten.androidApp"

    const val androidAppVersionName = "1.00.00"
    val androidAppVersionCode = androidAppVersionName.filter { it.isDigit() }.toInt()

}