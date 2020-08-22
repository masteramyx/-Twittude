package com.example.twittude.api

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