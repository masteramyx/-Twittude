package com.example.twittude.api

import com.example.twittude.api.TwitObservables.getTwitterAuthenticationObservable
import com.example.twittude.api.TwitObservables.search
import io.reactivex.Single
import twitter4j.QueryResult
import twitter4j.Twitter

class TwitMainRepositoryImpl() : TwitMainRepository {

    override fun getTwitterAuthentication(): Single<Twitter> {
        return TwitObservables.getTwitterAuthenticationObservable
    }

    override fun searchQuery(query: String): Single<QueryResult> {
        return TwitObservables.search(query)
    }
}