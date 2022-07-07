package com.learning.newsapiclient.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source?,               // in case of object reference variable other then strings in the entity
                                       // class we have two options simplest but inefficient solution is annotating
                                       // this source class also as an entity class and saving source data to separate
                                       // data base table. But this source class only has two property an id and name only
                                       // so for few data create another table is not efficient so the second solution is best
                                       // i am going to create a convertor class for source data using room type convertor
                                       // annotation.
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
):Serializable