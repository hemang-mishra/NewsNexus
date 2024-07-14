package com.project.newsnexus.ui.mainscreen.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationItems(
    val displayName: String,
    val iconSelected: ImageVector,
    val iconNotSelected: ImageVector
) {
    HOME("Home", Icons.Default.Home,Icons.Outlined.Home),
    SAVED("Saved", Icons.Default.Bookmark,Icons.Outlined.BookmarkBorder)
}