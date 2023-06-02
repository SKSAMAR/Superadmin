package com.fintech.superadmin.clean.presentation.referEarn

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.fintech.superadmin.clean.common.BaseViewModel
import com.fintech.superadmin.clean.data.remote.FintechAPI
import com.fintech.superadmin.clean.data.remote.dto.refer.ReferDto
import com.fintech.superadmin.clean.domain.model.ScreenState
import com.fintech.superadmin.clean.domain.model.contact.Contact
import com.fintech.superadmin.data.network.APIServices
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class ReferEarnViewModel
@Inject constructor(private val api: FintechAPI) : BaseViewModel<ReferDto>() {

    var contactList by mutableStateOf<List<Contact>>(emptyList())
    var link by mutableStateOf("")

    @SuppressLint("CheckResult")
    fun earnedAndData(
        initialFetch: Boolean = true
    ){
        if (initialFetch){
            _state.value = ScreenState(isLoading = true)
        }
        api.earnedAndData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.value = ScreenState(receivedResponse = it)
                link = it.REFER_URL?:""
            },{
                if (initialFetch){
                    _state.value = ScreenState(error = it.localizedMessage?:"Some Error")
                }
            })
    }


    @SuppressLint("Recycle", "Range")
    fun fetchContacts(context: Context) {
        val contacts = HashSet<Contact>()
        if (this.contactList.isNotEmpty()) {
            return
        }
        _state.value = ScreenState(isLoading = true)
        Completable.fromRunnable {

            val sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC" // add sort order parameter
            val resolver = context.contentResolver
            val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, sortOrder)
            try {
                while (cursor?.moveToNext() == true) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val hasPhone =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                            .toInt()

                    if (hasPhone > 0) {

                        val phoneCursor = resolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )
                        while (phoneCursor!!.moveToNext()) {
                            val phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                            contacts.add(
                                Contact(
                                    name.trim().replace(" ",""),
                                    phoneNumber.trim().replace(" ",""),
                                    isAvailableOnWhatsApp = hasWhatsapp(
                                        context = context,
                                        contactID = id
                                    )
                                )
                            )


                        }
                        phoneCursor.close()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            cursor?.close()

        }.subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    val sortedContacts = contacts.sortedBy { it.name?.lowercase() }
                    contactList = sortedContacts
                    earnedAndData()
                }
                override fun onError(e: Throwable) {
                    earnedAndData()
                }
            })
    }


    private fun hasWhatsapp(context: Context, contactID: String): Boolean {
        var rowContactId: String? = null
        var hasWhatsApp = false
        try {
            val projection = arrayOf(ContactsContract.RawContacts._ID)
            val selection = ContactsContract.Data.CONTACT_ID + " = ? AND account_type IN (?)"
            val selectionArgs = arrayOf(contactID, "com.whatsapp")
            val cursor: Cursor? = context.contentResolver.query(
                ContactsContract.RawContacts.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
            )
            if (cursor != null) {
                hasWhatsApp = cursor.moveToNext()
                if (hasWhatsApp) {
                    rowContactId = cursor.getString(0)
                }
                cursor.close()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return hasWhatsApp
    }


}