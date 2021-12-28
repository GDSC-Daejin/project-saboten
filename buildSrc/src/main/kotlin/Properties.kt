object Properties {

    const val androidMinSDK = 24
    const val androidTargetSDK = 31
    const val androidCompileSDK = 31
    const val androidPackageName = "app.saboten.android"

    const val androidAppVersionName = "1.00.00"
    val androidAppVersionCode = androidAppVersionName.filter { it.isDigit() }.toInt()

}