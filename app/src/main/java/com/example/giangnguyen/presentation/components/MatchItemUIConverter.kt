package com.example.giangnguyen.presentation.components

import com.example.giangnguyen.domain.model.MatchDetailsModel
import com.example.giangnguyen.utils.DateTimeUtils

object MatchItemUIConverter {
    fun prepareFromMatchDetailsList(details: List<MatchDetailsModel>): List<MatchItemUiModel> {
        val sortedList = details.sortedBy { it.match.dateString }
        var lastDateStringKey = ""
        val matchItemUiList = mutableListOf<MatchItemUiModel>()
        sortedList.forEach {
            val dateString =
                DateTimeUtils.convertMatchDateStringToOnlyDateString(it.match.dateString)
            if (lastDateStringKey != dateString) {
                matchItemUiList += MatchItemUiModel.MatchItemUIHeader(dateString)
                lastDateStringKey = dateString
            }
            matchItemUiList += MatchItemUiModel.MatchItemUIItem(it)
        }
        return matchItemUiList
    }
}