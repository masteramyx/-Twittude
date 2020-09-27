package com.example.twittude.api

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.twittude.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.context.GlobalContext
import org.koin.core.qualifier.named
import timber.log.Timber
import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.OAuth2Token
import twitter4j.conf.Configuration
import twitter4j.conf.ConfigurationBuilder
import kotlin.jvm.Throws

class TwitMainRepositoryImpl() : TwitMainRepository {

    lateinit var twitter: Twitter
    private val dataStore =
        GlobalContext.get().koin.get<DataStore<Preferences>>(qualifier = named("tweets"))

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

    override suspend fun incrementCounter() {
        val dataStore: DataStore<Preferences> =
            GlobalContext.get().koin.get(qualifier = named("counter"))
        val EXAMPLE_COUNTER = preferencesKey<Int>("example_counter")
        dataStore.edit { settings ->
            val counterValue = settings[EXAMPLE_COUNTER] ?: 0
            settings[EXAMPLE_COUNTER] = counterValue + 1
            Timber.d("SAVED!%s", counterValue)
        }
    }

    override suspend fun saveTweetToDisk(tweet: String) {
        val TWEET_KEY = preferencesKey<String>("tweet_key")
        dataStore.edit { tweetPreferences ->
            tweetPreferences[TWEET_KEY] = tweet
            Timber.d("SAVED!%s", tweet)
        }
    }

    override suspend fun readTweetFromDisk(): Flow<String> {
        val TWEET_KEY = preferencesKey<String>("tweet_key")
        Timber.d("Is it here?: %s", dataStore.data.first().contains(TWEET_KEY))
        return dataStore.data.map {
            it[TWEET_KEY] ?: ""
        }
    }

}