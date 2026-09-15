package org.example.project.domain.models.appmodels

import kotlinx.serialization.Serializable

@Serializable
data class DownloaderEndpointModel(val links: String, val endpoint: String, val isAllowed: Int)