package com.learning.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiServiceTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        // write code to initialise the service and server
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    private fun enqueueMockResponse(
        filename: String,
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(filename)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @After
    fun tearDown() {
        // here write code to shut down the mock web server
        server.shutdown()
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")                 // This will start the mock web server & prepare a fake response using the local json file
            val responseBody = service.getTopHeadlines("us", 1)
                .body()                     // request to the mock server
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=c1f1b06b17484996972e3dec2be6abdf")
        }
    }


    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")                 // This will start the mock web server & prepare a fake response using the local json file
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articleList = responseBody!!.articles
            assertThat(articleList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")                 // This will start the mock web server & prepare a fake response using the local json file
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articleList = responseBody!!.articles
            val article = articleList[2]
            assertThat(article.author).isEqualTo("KARE 11 Staff")
            assertThat(article.url).isEqualTo("https://www.kare11.com/article/news/local/1-child-dead-2-others-and-a-woman-missing-at-vadnais-lake/89-13aae4d9-1ccf-4df9-8d1d-4cd8ed859cff")
            assertThat(article.publishedAt).isEqualTo("2022-07-02T02:54:00Z")
        }
    }
}