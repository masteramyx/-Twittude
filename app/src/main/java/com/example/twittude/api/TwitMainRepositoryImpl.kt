package com.example.twittude.api

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.twittude.model.TwitListItem
import kotlinx.coroutines.flow.*
import org.koin.core.context.GlobalContext
import org.koin.core.qualifier.named
import timber.log.Timber

class TwitMainRepositoryImpl(
    val service: TwitApiService
) : TwitMainRepository {

    private val tweetDataStore =
        GlobalContext.get().get<DataStore<Preferences>>(qualifier = named("tweets"))
    private val tokenDataStore =
        GlobalContext.get().get<DataStore<Preferences>>(qualifier = named("bearer"))

    /**
     * DataStore exposes data in Flow<Preferences>, emits everytime a preference has changed.
     * In this case, if bearer is not on disk, it fetches and saves. Changing the preference item and reemitting the DataStore value.
     * We don't need this, collect first item emitted, if empty/null, make network request and save token.
     */
    override suspend fun checkCacheForBearer() {
        val bearer = readBearerTokenFromDisk().firstOrNull()
        getTwitterAuthentication(bearer = bearer.orEmpty())
    }

    override suspend fun getTwitterAuthentication(bearer: String) {
        if (bearer.isEmpty()) {
            service.authenticate().apply {
                if (token_type == "bearer") {
                    //store w/ DataStore
                    saveBearerTokenToDisk(access_token)
                    Timber.d("SAVED BEARER TOKEN")
                }
            }
        } else {
            Timber.d("TOKEN FROM CACHE")
        }
    }


    override suspend fun searchQuery(queryString: String): List<TwitListItem> {
        val tweets = mutableListOf<TwitListItem>()
        service.search(queryString).tweets.map {
            tweets.add(TwitListItem(it.text.orEmpty(), it.authorId.orEmpty()))
        }
        return tweets
    }

    override suspend fun saveTweetToDisk(tweet: String) {
        tweetDataStore.edit { tweetPreferences ->
            tweetPreferences[TWEET_KEY] = tweet
            Timber.d("SAVED!%s", tweet)
        }
    }

    override suspend fun readTweetFromDisk(): Flow<String> {
        Timber.d("Is it here?: %s", tweetDataStore.data.first().contains(TWEET_KEY))
        return tweetDataStore.data.map {
            it[TWEET_KEY] ?: ""
        }
    }

    override suspend fun saveBearerTokenToDisk(token: String) {
        tokenDataStore.edit { tokenPreferences ->
            tokenPreferences[TOKEN_KEY] = token
            Timber.d("SAVED! %s", token)
        }
    }

    override suspend fun readBearerTokenFromDisk(): Flow<String> {
        return tokenDataStore.data.map {
            it[TOKEN_KEY] ?: ""
        }
    }

    companion object {
        val TOKEN_KEY = preferencesKey<String>("bearer_key")
        val TWEET_KEY = preferencesKey<String>("tweet_key")
    }
}