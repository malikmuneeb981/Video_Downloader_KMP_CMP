package com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels


import kotlinx.serialization.Serializable

@Serializable
data class RemoteConfigModel(

    val interstitialAdId: String = "",
    val bannerAdId: String = "",
    val nativeAdId: String = "",
    val appopenADId: String = "",
    val rewardedADId: String = "",
    val RewardedIntestritialId: String = "",

    val TopCollapsableADID: String = "",
    val BottomCollapsableADID: String = "",

    val splashappopenonoff: Int = 0,
    val AppOpenOnOFF: Int = 0,
    val ShowReviewDialog: Int = 0,
    val AppOpenLoadInterval: Int = 30000,
    val splashinterstitial: Int = 0,

    val privacyPolicyLink: String = "",

    val interstitialcountonoff: Int = 0,
    val interstitialAdsToShowCount: Int = 0,
    val timetoshowinterstitalafter: Int = 9000,
    val RewardedOrRewardedInterstitial: Int = 0,
    val interstitialClickRemoteConfig: Int = 0,



    val homenativeonoff: Int = 0,
    val homebanneronoff: Int = 1,

    val downloadernativeonoff: Int = 0,
    val downloaderbanneronoff: Int = 1,

    val downloadsnativeonoff: Int = 0,
    val downloadsbanneronoff: Int = 1,

    val statussavernativeonoff: Int = 0,
    val statussaverbanneronoff: Int = 1,

    val imageviewnativeonoff: Int = 0,
    val imageviewbanneronoff: Int = 1,

    val videoplayernativeonoff: Int = 0,
    val videoplayerbanneronoff: Int = 1,

    val musicplayernativeonoff: Int = 0,
    val musicplayerbanneronoff: Int = 1,

    val moresnativeonoff: Int = 0,
    val moresbanneronoff: Int = 0,

    val favreelsnativeonoff: Int = 0,
    val favreelsbanneronoff: Int = 0,

    val videosinfoldernativeonoff: Int = 0,
    val videossinfolderbanneronoff: Int = 0,

    val languagenativeonoff: Int = 0,
    val languagebanneronoff: Int = 0,

    val TopCollapsableOnoFF: Int = 0,
    val BottomCollapsableOnoFF: Int = 0,

    val exitscreennativeonoff: Int = 0,
    val exitscreenbanneronoff: Int = 0,

    val trendingnativeonoff: Int = 0,
    val trendingbanneronoff: Int = 0,

    val feedbacknativeonoff: Int = 0,
    val feedbackbanneronoff: Int = 0,

    val BaseUrl:String = "",
    val SecretKey:String = "",
    val PrivacyPolicyUrl:String = "",
    val TermsOfServiceUrl:String = "",
    val PremiumPackageIdWeekly:String = "",
    val PremiumPackageIdMonthly:String = "",
    val PremiumPackageIdYearly:String = "",

    val PremiumOnStart: Int = 0
)