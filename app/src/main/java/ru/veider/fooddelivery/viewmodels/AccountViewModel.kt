package ru.veider.fooddelivery.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.ByteArrayOutputStream
import android.util.Base64
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.domain.AccountData
import ru.veider.fooddelivery.domain.ScreenState
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
class AccountViewModel(
	private val application: Application
) : AndroidViewModel(application) {

	private val id = 1
	private val location = application.getString(R.string.location)
	private val imageString by lazy {
		val imageBitmap = BitmapFactory.decodeResource(application.resources, R.drawable.avatar)
		val stream = ByteArrayOutputStream()
		imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
		val byteArray = stream.toByteArray()
		byteArray.let { Base64.encodeToString(it, Base64.DEFAULT) }
	}
	private val date by lazy {
		SimpleDateFormat("d MMMM, yyyy").format(Date())
	}

	private val _account = MutableLiveData<ScreenState<AccountData>>()
	fun getAccountData(): LiveData<ScreenState<AccountData>> {
		_account.postValue(ScreenState.Loading())
		try {
			val account = AccountData(
				id = id,
				imageString = imageString,
				location = location,
				date = date
			)
			viewModelScope.launch {
				delay(1000)    // имитация сетевой загрузки
				_account.postValue(ScreenState.Success(account))
			}
		} catch (e: Exception) {
			_account.postValue(ScreenState.Error(e))
		}
		return _account
	}
}