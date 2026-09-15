package com.cyberarsenals.video.downloader.save.videos.domain.models.appmodels

import org.jetbrains.compose.resources.DrawableResource

data class Language(val name:String, val flag: DrawableResource, val langcode:String, val selectlangtext:String,
                    val isSelected: Boolean = false,
                    val shadowEnabled: Boolean = false,
                    val showAnim: Boolean = false)