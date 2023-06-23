package com.gitan.mystoryapp

import com.gitan.mystoryapp.data.response.ListStoryItem

object DataDummy {
    fun generateDummyStory(): List<ListStoryItem> {
        val stories: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                i.toString(),
                i.toString(),
                i.toString(),
                i.toDouble(),
                i.toString(),
                i.toDouble()
            )
            stories.add(story)
        }
        return stories
    }
}