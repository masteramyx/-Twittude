package com.example.twittude.api

import com.example.twittude.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.OAuth2Token
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder

class TwitMainRepositoryImpl() : TwitMainRepository {

    lateinit var twitter: Twitter

    @Throws(Exception::class)
    override suspend fun getTwitterAuthentication(): Twitter {
        val config = ConfigurationBuilder()
        withContext(Dispatchers.IO) {
            config.setDebugEnabled(true)
                .setApplicationOnlyAuthEnabled(true)
                .setOAuthConsumerKey(BuildConfig.TWIT_CONSUMER_KEY)
                .setOAuthConsumerSecret(BuildConfig.TWIT_CONSUMER_SECRET)
            val twit = with(TwitterFactory(config.build()).instance.oAuth2Token) {
                addOAuthToken(this)
            }
            twitter = TwitterFactory(twit).instance
        }
        return twitter
    }

    override suspend fun searchQuery(queryString: String): QueryResult {
        var result: QueryResult
        withContext(Dispatchers.IO) {
            result = twitter.search(Query(queryString))
        }
        return result
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