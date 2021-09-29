package com.androiddevs.mvvmnewsapp.preferences

import android.content.Context
import android.content.SharedPreferences
import com.androiddevs.mvvmnewsapp.models.Country
import com.androiddevs.mvvmnewsapp.models.User
import com.google.gson.Gson
import java.util.*


class SharedPreferences(var context: Context)
{
    var usersSession: SharedPreferences
    var editor: SharedPreferences.Editor




    fun storeCountry(country:Country)
    {

        val gson = Gson()
        val json = gson.toJson(country) // myObject - instance of MyObject

        editor.putString(COUNTRY, json)
        editor.apply()
    }
    fun getCountry(): Country {
        val gson = Gson()
        val json: String? = usersSession.getString(COUNTRY, "")
        return  gson.fromJson(json, Country::class.java)
    }










    fun storeCountryCode(countryCode :String)
    {
        editor.putString(KEY_COUNTRY_CODE,countryCode)
        editor.apply()
    }


    fun getCountryCode ():String?
    {
        return usersSession.getString(KEY_COUNTRY_CODE,"us")
    }


    fun  storeUser(user : User)
    {

        val gson = Gson()
        val json = gson.toJson(user) // myObject - instance of MyObject

        editor.putString(USER, json)
        editor.commit()
    }

    fun getUser(): User {
        val gson = Gson()
        val json: String? = usersSession.getString(USER, "")
        return gson.fromJson(json, User::class.java)
    }

    fun storeGategory(category:String?)
    {
        editor.putString(KEY_CATEGORY, category)
        editor.apply()
    }

    fun getCategory ():String?
    {
      return usersSession.getString(KEY_CATEGORY,"general")
    }


    fun createLoginSession(firstName: String?, lastName: String?)
    {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_FIRSTNAME, firstName)
        editor.putString(KEY_LASTNAME, lastName)
        editor.apply()
    }

    fun checkAccess(admin: Boolean?, familyMember: Boolean?)
    {
        editor.putBoolean(KEY_ADMIN, admin!!)
        editor.putBoolean(KEY_FAMILYMEMBER, familyMember!!)
        editor.apply()
    }

    val userDetailFromSession: HashMap<String, String?>
        get() {
            val userData = HashMap<String, String?>()
            userData[KEY_LASTNAME] =
                usersSession.getString(KEY_LASTNAME, null)
            userData[KEY_FIRSTNAME] =
                usersSession.getString(KEY_FIRSTNAME, null)
            return userData
        }
    val accessDetailFromSession: HashMap<String, Boolean>
        get() {
            val userData = HashMap<String, Boolean>()
            userData[KEY_ADMIN] =
                usersSession.getBoolean(KEY_ADMIN, false)
            userData[KEY_FAMILYMEMBER] =
                usersSession.getBoolean(KEY_FAMILYMEMBER, false)
            return userData
        }

    fun lockAndUnlockState(lock: Boolean?) {
        editor.putBoolean(KEY_LOCKANDUNLOCKSTATE, lock!!)
        editor.apply()
    }

    fun checkLogin(): Boolean {
        return usersSession.getBoolean(IS_LOGIN, false)
    }

    fun logoutUserFromSession() {
        editor.clear()
        editor.commit()
    }

    companion object {
        private const val IS_LOGIN = "IsLoggedIn"
        const val KEY_LASTNAME = "lastName"
        const val KEY_FIRSTNAME = "firstName"
        const val KEY_ADMIN = "admin"
        const val KEY_FAMILYMEMBER = "familyMember"
        const val KEY_LOCKANDUNLOCKSTATE = "lock"
        const val USER="object"
        const val COUNTRY="object"
        const val KEY_CATEGORY="none"
        const val KEY_COUNTRY_CODE="us"
    }

    init {


        usersSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE)
        editor = usersSession.edit()
    }
}
