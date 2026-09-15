package org.example.project.domain.models.appmodels

import org.jetbrains.compose.resources.DrawableResource


data class DropDownItem(
    val icon: DrawableResource,          // Drawable resource
    val text: String,
    val onClick: () -> Unit // Action when item clicked
)
