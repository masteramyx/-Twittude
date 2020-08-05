package com.example.twittude.api

import android.annotation.SuppressLint
import com.example.twittude.BuildConfig
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.OAuth2Token
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

/**
 * The Twitter4J api wrapper does not make asynchronus calls, you must you [AsyncTwitterImpl.java].
 * For practice and to keep the app consistent with RxJava, I provided my own implementations
 */
object TwitObservables {

    private val config: ConfigurationBuilder = ConfigurationBuilder()
    lateinit var twitter: Twitter

    init {

    }

    val getTwitterAuthenticationObservable: Single<Twitter> by lazy(LazyThreadSafetyMode.NONE) {
        Single.create(SingleOnSubscribe<Twitter> {
            try {
                config.setDebugEnabled(true)
                    .setApplicationOnlyAuthEnabled(true)
                    .setOAuthConsumerKey(BuildConfig.TWIT_CONSUMER_KEY)
                    .setOAuthConsumerSecret(BuildConfig.TWIT_CONSUMER_SECRET)
                val twit = with(TwitterFactory(config.build()).instance.oAuth2Token) {
                    addOAuthToken(this)
                }
                twitter = TwitterFactory(twit).instance
                it.onSuccess(twitter)
            } catch (e: Exception) {
                println("Can't get OAuth2 Token (Reactive Stream)")
                println(e.toString())
            }
        })
    }

    @SuppressLint("CheckResult")
    fun search(query: String): Single<QueryResult> {
        return Single.create(SingleOnSubscribe<QueryResult> {
            try {
                it.onSuccess(twitter.search(Query(query)))
            } catch (e: Exception) {
                println("Can't get Query Result (Reactive Stream)")
                println(e.toString())
            }
        })
    }

    private fun addOAuthToken(oAuth2Token: OAuth2Token): Configuration {
        val configCopy = ConfigurationBuilder()
            .setDebugEnabled(true)
            .setApplicationOnlyAuthEnabled(true)
            .setOAuthConsumerKey(BuildConfig.TWIT_CONSUMER_KEY)
            .setOAuthConsumerSecret(BuildConfig.TWIT_CONSUMER_SECRET)
        configCopy
            .setOAuth2TokenType(oAuth2Token.tokenType)
            .setOAuth2AccessToken(oAuth2Token.accessToken)
        return configCopy.build()
    }
}