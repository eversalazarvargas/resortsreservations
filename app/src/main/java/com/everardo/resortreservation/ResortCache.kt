package com.everardo.resortreservation

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.reactivestreams.Publisher


/**
 * @author everardo.salazar on 1/30/19
 */
class ResortCache(private val context: Context) {

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        //TODO here he use the ContentResolver
        context.contentResolver.registerContentObserver(ArticleContract.Articles.CONTENT_URI, true, object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                val id = uri!!.lastPathSegment
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                var concat = ""
                while (cursor.moveToNext()) {
                    val idx = cursor.getColumnIndex(ArticleContract.Articles.COL_TITLE)
                    concat += cursor.getString(idx)

                }
                publishSubject.onNext(concat)
            }
        })
    }

    fun getResortReservations(): Observable<String> {
        //TODO create a publisher that listens for ContentResolver and publish results

        return publishSubject

    }

    fun saveResortReservation(myData: String): Completable {
        return Completable.create({emitter ->
            //TODO store the text in the database which will trigger a sync

            val contentValues = ContentValues()
            contentValues.put(ArticleContract.Articles.COL_ID, myData + "Id")
            contentValues.put(ArticleContract.Articles.COL_TITLE, myData + "Title")
            contentValues.put(ArticleContract.Articles.COL_CONTENT, myData + "Content")



            context.contentResolver.insert(ArticleContract.Articles.CONTENT_URI, contentValues)

            emitter.onComplete()
        })
    }
}